package commands;

import Client.Client;
import command.Command;
import command.CommandManager;
import connection.AnswerMsg;
import connection.Request;
import connection.Status;
import exceptions.ConnectionException;
import exceptions.ConnectionTimeoutException;
import exceptions.InvalidDataException;

import static io.OutPutManager.print;

public class ClientCommandManager extends CommandManager {
    private Client client;
    public ClientCommandManager(Client c) {
        client = c;
        addCommand(new ExitCommand());
        addCommand(new ExecuteScriptCommand(this));
    }

    public Client getClient() {
        return client;
    }

    @Override
    public AnswerMsg runCommand(Request msg) {
        AnswerMsg res = new AnswerMsg();
        if(hasCommand(msg)) {
            Command cmd = getCommand(msg);
            cmd.setArgument(msg);
            res = (AnswerMsg) cmd.run();
        } else {
            try{
                client.send(msg);
                res = (AnswerMsg) client.receive();
            } catch(ConnectionTimeoutException e) {
                res.info("no attempts left, shutting down").setStatus(Status.EXIT);
            } catch (InvalidDataException| ConnectionException e) {
                res.error(e.getMessage());
            }
        }
        print(res);
        return res;
    }

}
