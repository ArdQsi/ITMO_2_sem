package client;

import auth.User;
import commands.ClientCommandManager;
import connection.Request;
import connection.Response;
import exceptions.*;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;

public class Client extends Thread implements Closeable {
    private SocketAddress address;
    private DatagramSocket socket;
    private ClientCommandManager commandManager;
    boolean running;
    final int INCREMENT = 4096;
    private User user = null;

    public Client(String addr, int p) throws ConnectionException {
        this.init(addr, p);
    }

    private void init(String addr, int p) throws ConnectionException {
        this.running = true;
        this.connect(addr, p);
        this.commandManager = new ClientCommandManager(this);
    }

    /*public void starts() {
        this.commandManager.consoleMode();

        try {
            this.close();
        } catch (IOException var2) {
            OutPutManager.printErr("cannot close channel");
        }

    }*/

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void connect(String addr, int p) throws ConnectionException {
        try {
            this.address = new InetSocketAddress(InetAddress.getByName(addr), p);
        } catch (UnknownHostException var5) {
            throw new InvalidAddressException();
        } catch (IllegalArgumentException var6) {
            throw new InvalidPortException();
        }

        try {
            socket = new DatagramSocket();
            //this.channel.configureBlocking(false);
        } catch (IOException var4) {
            throw new ConnectionException("cannot open channel");
        }
    }

    /*public void send(Request request) throws ConnectionException {
        try {
            byte[] data = request.toString().getBytes();
            int position = 0;
            int limit = INCREMENT;

            for(int capacity = 0; data.length > capacity; limit += 4096) {
                byte[] window = Arrays.copyOfRange(data, position, limit);
                capacity += limit - position;
                Request1 request1;
                if (capacity >= data.length) {
                    request1 = new Request1(request, true);
                } else {
                    request1 = new Request1(window, false);
                }

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(request1);
                this.channel.send(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()), this.address);
                byteArrayOutputStream.close();
                position = limit;
            }

        } catch (IOException var10) {
            throw new ConnectionException("something went wrong while sending request");
        }
    }*/

    public void send(Request request) throws ConnectionException {
        try {
            request.setStatus(Request.Status.SENT_FROM_CLIENT);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(INCREMENT);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(request);
            DatagramPacket requestPacket = new DatagramPacket(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.size(),address);
            //this.channel.send(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()), this.address);
            socket.send(requestPacket);
            byteArrayOutputStream.close();
        } catch (IOException var10) {
            throw new ConnectionException("something went wrong while sending request");
        }

    }

    /*public Response receive() throws ConnectionException, InvalidDataException {
        ByteBuffer bytes = ByteBuffer.allocate(INCREMENT);

        try {
            this.channel.receive(bytes);
        } catch (ClosedChannelException var6) {
            throw new ClosedConnectionException();
        } catch (IOException var7) {
            throw new ConnectionException("something went wrong while receiving response");
        }

        try {
            StringBuilder stringBuilder = new StringBuilder();
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes.array()));
            Response1 res = (Response1) objectInputStream.readObject();
            if (res.getIsBoolean()) {
                return res.getResponse();
            } else {
                do {
                    stringBuilder.append(res.getResponse());
                    res = (Response1) objectInputStream.readObject();
                } while (!res.getIsBoolean());

                Response response = new Response1(stringBuilder.toString());
                return response;
            }
        } catch (ClassCastException | ClassNotFoundException var8) {
            throw new InvalidReceivedDataException();
        } catch (IOException var9) {
            throw new ClosedConnectionException();
        }
    }*/

    public Response receive() throws ConnectionException, InvalidDataException {
        ByteBuffer bytes = ByteBuffer.allocate(INCREMENT);
        DatagramPacket receivePacket = new DatagramPacket(bytes.array(), bytes.array().length);
        try {
            //this.channel.receive(bytes);
            socket.receive(receivePacket);
        } catch (ClosedChannelException var6) {
            throw new ClosedConnectionException();
        } catch (IOException var7) {
            throw new ConnectionException("something went wrong while receiving response");
        }

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes.array()));
            return (Response) objectInputStream.readObject();
        } catch (ClassCastException | ClassNotFoundException var8) {
            throw new InvalidReceivedDataException();
        } catch (IOException var9) {
            throw new ClosedConnectionException();
        }
    }

    @Override
    public void run() {
        commandManager.consoleMode();
        try {
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() throws IOException {
        this.running = false;
        this.commandManager.close();
        //this.channel.close();
        this.socket.close();
    }
}

