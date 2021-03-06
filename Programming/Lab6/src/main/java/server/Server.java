package server;

import collection.CollectionManager;
import collection.ProductCollectionManager;
import commands.ServerCommandManager;
import connection.*;
import log.Log;
import command.CommandType;
import data.Product;
import exceptions.*;
import file.FileManager;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.time.LocalDate;
import java.util.Arrays;


public class Server {
    private CollectionManager<Product> collectionManager;
    private ServerCommandManager commandManager;
    private FileManager fileManager;
    private int port;
    private DatagramSocket socket;
    private InetSocketAddress clientAddress;
    InetAddress host;
    private volatile boolean running;
    final int INCREMENT = 4096;

    private void init(int p) throws ConnectionException {
        running = true;
        port = p;
        collectionManager = new ProductCollectionManager();
        fileManager = new FileManager();
        commandManager = new ServerCommandManager(this);
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            throw new ConnectionException("cannot open socket");
        }
        Log.logger.trace("starting server");
    }

    public Server(int p) throws ConnectionException {
        init(p);
    }


    public Request receive() throws ConnectionException, InvalidDataException {
        ByteBuffer buf = ByteBuffer.allocate(INCREMENT);
        DatagramPacket receivePacket = new DatagramPacket(buf.array(), buf.array().length);
        try {
            socket.receive(receivePacket);
            clientAddress = new InetSocketAddress(receivePacket.getAddress(), receivePacket.getPort());
            Log.logger.trace("received request from " + receivePacket.getAddress());
        } catch (ClosedChannelException e) {
            throw new ClosedConnectionException();
        } catch (IOException e) {
            throw new ConnectionException("something went wrong during receiving request");
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buf.array()));
            Request1 req = (Request1) objectInputStream.readObject();

            if (req.getIsBoolean()) {
                return req.getRequest();
            } else {
                do {
                stringBuilder.append(req.getRequest());
                socket.receive(receivePacket);
                req = (Request1) objectInputStream.readObject();
            } while(!req.getIsBoolean());
                Request request = new Request1(stringBuilder.toString());
                return request;
            }
        } catch (ClassNotFoundException  | IOException e) {
            throw new InvalidReceivedDataException();
        }
    }

    public void send(Response response) throws ConnectionException {
        if (clientAddress == null) throw new InvalidAddressException("no client address found");
        try {
            byte[] data = response.toString().getBytes();
            for (int position = 0, limit = INCREMENT, capacity = 0; data.length > capacity; position = limit, limit += INCREMENT) {
                byte[] window = Arrays.copyOfRange(data, position, limit);
                capacity += limit - position;
                Response1 response1;
                if (capacity >= data.length) {
                    response1 = new Response1(response,true);
                } else {
                    response1 = new Response1(window,false);
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(INCREMENT);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(response1);
                DatagramPacket requestPacket = new DatagramPacket(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.size(), clientAddress);
                socket.send(requestPacket);
                byteArrayOutputStream.close();
                Log.logger.trace("sent response to " + clientAddress.toString());
            }
        } catch (IOException e) {
            throw new ConnectionException("something went wrong during sending response");
        }
    }

    public void runs() {
        while (running) {
            AnswerMsg answerMsg = new AnswerMsg();
            try {
                try {
                    Request commandMsg =  receive();
                    if (commandMsg.getProduct() != null) {
                        commandMsg.getProduct().setCreationDate(LocalDate.now());
                    }
                    if (commandManager.getCommand(commandMsg).getType() == CommandType.SERVER_ONLY) {
                        throw new ServerOnlyCommandException();
                    }
                    answerMsg = commandManager.runCommand(commandMsg);
                    if (answerMsg.getStatus() == Status.EXIT) {
                        close();
                    }
                } catch (CommandException e) {
                    answerMsg.error(e.getMessage());
                    Log.logger.error(e.getMessage());
                }
                send(answerMsg);
            } catch (ConnectionException | InvalidDataException e) {
                Log.logger.error(e.getMessage());
            }
        }
    }

    public void consoleMode() {
        commandManager.consoleMode();
    }

    public void close() {
        running = false;
        socket.close();
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public CollectionManager<Product> getCollectionManager() {
        return collectionManager;
    }
}
