package main;

import exceptions.InvalidProgramArgumentException;
import exceptions.ConnectionException;
import exceptions.InvalidPortException;
import server.Server;
import io.*;

import static io.OutPutManager.print;

public class Main {
    //public static Logger logger = LogManager.getLogger("logger");
    //private static final Logger LOG = LogManager.getRootLogger();
    public static void main(String[] args) throws Exception {
        args = new String[]{"8085", "ttt"};
        int port = 0;
        String strPort = "";
        //путь к файлу надо сделать
        String path = "";

        try {
            //System.out.println(System.getenv().get("Lab6"));
            if (args.length >= 2) {
                path = args[1];
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
            //server.run();
            server.start();
            server.consoleMode();

        } catch (InvalidProgramArgumentException | ConnectionException e) {
            OutPutManager.print(e.getMessage());
        }
    }
}
