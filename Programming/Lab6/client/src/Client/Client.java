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

public class Client extends Thread implements Closeable{
   private SocketAddress address;
   private DatagramChannel channel;
   public final int MAX_TIME_OUT = 1000;

   private boolean running;
   private ClientCommandManager commandManager;

   public Client(String addr, int p) throws ConnectionException {
      init(addr,p);
   }

   private void init(String addr, int p) throws ConnectionException{
      connect(addr,p);
      running = true;
      commandManager = new ClientCommandManager(this);
   }

   public void connect(String addr, int p) throws ConnectionException {
      try {
         address = new InetSocketAddress(InetAddress.getByName(addr),p);
      } catch (UnknownHostException e) {
         throw new InvalidAddressException();
      } catch (IllegalArgumentException e) {
         throw new InvalidPortException();
      }
      try {
         channel = DatagramChannel.open();
         //channel.configureBlocking(false);
         //channel.bind(null);
      } catch (IOException e) {
         throw new ConnectionException("cannot open channel");
      }
   }


   /*public void send(DatagramChannel channel, Request request, SocketAddress address) {
      String req = request.getStringArg();
      ByteBuffer byteBuffer =ByteBuffer.wrap(req.getBytes());
      try {
         channel.send(byteBuffer,address);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }*/

   public void send(Request request) throws ConnectionException {
      try {
         //ByteBuffer buffer = ByteBuffer.wrap(request.getStringArg().getBytes());
         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
         ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
         objectOutputStream.writeObject(request);
         //DatagramPacket requestPacket = new DatagramPacket(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.size(),address);
         //socket.send(requestPacket);
         //channel.socket().send(requestPacket);
         channel.send(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()),address);
         //channel.send(buffer,address);
         byteArrayOutputStream.close();
      } catch (IOException e){
         //throw new ConnectionException("something went wrong while sending request 123213");
         e.printStackTrace();
      }
   }

   //надо исправить ошибку IOE
   public Response receive() throws ConnectionException, InvalidDataException {
      ByteBuffer bytes = ByteBuffer.allocate(4096);
      //DatagramPacket receivePacket = new DatagramPacket(bytes.array(),bytes.array().length);
      try {
         channel.receive(bytes);
      } catch (ClosedChannelException e){
         throw new ClosedConnectionException();
      } catch (IOException e) {
         throw new ConnectionException("something went wrong while receiving response");
      }
      try {
         ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes.array()));
         Response res = (Response) objectInputStream.readObject();
         return res;
         //return (Response) objectInputStream.readObject();
      } catch (ClassNotFoundException | ClassCastException | IOException e) {
         throw new InvalidReceivedDataException();
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
      running = false;
      commandManager.close();
      channel.close();
   }
}
