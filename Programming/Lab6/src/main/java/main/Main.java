package main;

import collection.CollectionManager;
import data.Product;
import exceptions.ConnectionException;
import exceptions.InvalidPortException;
import file.FileManager;
import log.Log;
import server.Server;

import java.util.Scanner;

import static io.OutPutManager.print;

public class Main {
    public static void main(String[] args) throws Exception {

        int port = 0;
        String strPort = "";
        FileManager fileManager;
        CollectionManager<Product> collectionManager;


        try {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите порт");
            String s = scanner.nextLine();
            strPort = s;

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

            server.runs();


        } catch (ConnectionException e) {
            print(e.getMessage());
        }
    }
}
