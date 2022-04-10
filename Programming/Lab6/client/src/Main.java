import Client.Client;
import exceptions.InvalidProgramArgumentException;
import exceptions.ConnectionException;
import exceptions.InvalidPortException;

import static io.OutPutManager.print;

public class Main {
    public static void main(String[] args) throws Exception {
        args = new String[]{"localhost", "8085"};
        String addr = "";
        int port = 0;
        try {
            if (args.length != 2) throw new InvalidProgramArgumentException("no address passed by argument");
            addr = args[0];
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e){
                throw new InvalidPortException();
            }
            Client client = new Client(addr,port);
            client.start();
        } catch (InvalidProgramArgumentException | ConnectionException e) {
            print(e.getMessage());
        }
    }
}
