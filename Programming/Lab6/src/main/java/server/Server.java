package server;

import collection.CollectionManager;
import collection.ProductCollectionManager;
import commands.ServerCommandManager;
import log.Log;
import command.CommandType;
import connection.AnswerMsg;
import connection.Request;
import connection.Response;
import connection.Status;
import data.Product;
import exceptions.*;
import file.FileManager;


import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.time.LocalDate;


public class Server extends Thread {
    private CollectionManager<Product> collectionManager;
    private ServerCommandManager commandManager;
    private FileManager fileManager;
    private int port;
    private DatagramSocket socket;
    private InetSocketAddress clientAddress;
    InetAddress host;
    private volatile boolean running;

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
        ByteBuffer buf = ByteBuffer.allocate(4096);
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
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buf.array()));
            Request req = (Request) objectInputStream.readObject();
            return req;
        } catch (ClassNotFoundException | ClassCastException | IOException e) {
            throw new InvalidReceivedDataException();
        }
    }

    public void send(Response response) throws ConnectionException {
        if (clientAddress == null) throw new InvalidAddressException("no client address found");
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(response);
            DatagramPacket requestPacket = new DatagramPacket(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.size(), clientAddress);
            socket.send(requestPacket);
            byteArrayOutputStream.close();
            Log.logger.trace("sent response to " + clientAddress.toString());
        } catch (IOException e) {
            throw new ConnectionException("something went wrong during sending response");
        }
    }

    public void run() {
        while (running) {
            AnswerMsg answerMsg = new AnswerMsg();
            try {
                try {
                    Request commandMsg = receive();
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
