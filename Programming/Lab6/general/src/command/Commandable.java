package command;

import connection.Request;
import connection.Response;
import exceptions.FileException;

public interface Commandable {
    public default void addCommand(String key, Command cmd) {
        addCommand(key, cmd);
    }

    public void addCommand(Command cmd);

    public Response runCommand(Request req);

    public Command getCommand(String key);

    public default Command getCommand(Request req) {
        return getCommand(req.getCommandName());
    }

    public boolean hasCommand(String s);

    public default boolean hasCommand(Request req) {
        return hasCommand(req.getCommandName());
    }

    public void consoleMode();

    public void fileMode(String path) throws FileException;
}
