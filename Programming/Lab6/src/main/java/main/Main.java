package main;

import collection.CollectionManager;
import data.Product;
import exceptions.InvalidProgramArgumentException;
import exceptions.ConnectionException;
import exceptions.InvalidPortException;
import file.FileManager;
import log.Log;
import server.Server;


import java.io.IOException;
import java.io.OutputStream;

import static io.OutPutManager.print;

public class Main {
    public static void main(String[] args) throws Exception {
        args = new String[]{"8085"};
        int port = 0;
        String strPort = "";
        FileManager fileManager;
        CollectionManager<Product> collectionManager;


        try {
            //System.out.println(System.getenv().get("Lab6"));
            if (args.length >= 1) {
                strPort = args[0];
            }
            if (args.length == 1) strPort = args[0];
            if (args.length == 0) throw new InvalidProgramArgumentException("no address passed by argument");
            try {
                port = Integer.parseInt(strPort);
            } catch (NumberFormatException e) {
                throw new InvalidPortException();
            }

            Server server = new Server(port);
            fileManager = server.getFileManager();
            collectionManager = server.getCollectionManager();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                Log.logger.trace("collection saved");
                fileManager.writeToCSV(fileManager.getElement(collectionManager.getCollection()));
            }));

            server.start();
            server.consoleMode();


        } catch (InvalidProgramArgumentException | ConnectionException e) {
            print(e.getMessage());
        }
    }
}
