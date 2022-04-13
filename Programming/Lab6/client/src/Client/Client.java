package Client;

import commands.ClientCommandManager;
import connection.Request;
import connection.Response;
import exceptions.*;


import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;


import static io.OutPutManager.printErr;

public class Client extends Thread implements Closeable {
    private SocketAddress address;
    private DatagramChannel channel;
    private ClientCommandManager commandManager;

    public Client(String addr, int p) throws ConnectionException {
        init(addr, p);
    }

    private void init(String addr, int p) throws ConnectionException {
        connect(addr, p);
        commandManager = new ClientCommandManager(this);
    }

    public void connect(String addr, int p) throws ConnectionException {
        try {
            address = new InetSocketAddress(InetAddress.getByName(addr), p);
        } catch (UnknownHostException e) {
            throw new InvalidAddressException();
        } catch (IllegalArgumentException e) {
            throw new InvalidPortException();
        }
        try {
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
        } catch (IOException e) {
            throw new ConnectionException("cannot open channel");
        }
    }

    public void send(Request request) throws ConnectionException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(request);
            channel.send(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()), address);
            byteArrayOutputStream.close();
        } catch (IOException e) {
            throw new ConnectionException("something went wrong while sending request");
        }
    }

    public Response receive() throws ConnectionException, InvalidDataException {
        ByteBuffer bytes = ByteBuffer.allocate(4096);
        try {
            channel.receive(bytes);
        } catch (ClosedChannelException e) {
            throw new ClosedConnectionException();
        } catch (IOException e) {
            throw new ConnectionException("something went wrong while receiving response");
        }
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes.array()));
            Response res = (Response) objectInputStream.readObject();
            return res;
        } catch (ClassNotFoundException | ClassCastException e) {
            throw new InvalidReceivedDataException();
        } catch (IOException e) {
            throw new ClosedConnectionException();
        }
    }

    @Override
    public void run() {
        commandManager.consoleMode();
        try {
            close();
        } catch (IOException e) {
            printErr("cannot open channel");
        }
    }

    public void close() throws IOException {
        commandManager.close();
        channel.close();
    }
}
