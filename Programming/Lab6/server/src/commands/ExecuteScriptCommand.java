package commands;

import collection.CollectionManager;
import command.CommandImplements;
import command.CommandManager;
import command.CommandType;
import data.Product;
import exceptions.FileException;
import exceptions.MissedCommandArgumentException;
import exceptions.RecursiveScriptException;

public class ExecuteScriptCommand extends CommandImplements {
    private ServerCommandManager commandManager;
    public ExecuteScriptCommand(ServerCommandManager cm) {
        super("execute_script", CommandType.NORMAL);
        commandManager = cm;
    }

    @Override
    public String execute() throws FileException {
        if (!hasStringArg()) throw new MissedCommandArgumentException();
        if (commandManager.getStack().contains(getStringArg())) throw new RecursiveScriptException();
        commandManager.getStack().push(getStringArg());
        ServerCommandManager process = new ServerCommandManager(commandManager.getServer());
        process.fileMode(getStringArg());

        commandManager.getStack().pop();
        return "script successfully executed";
    }
}
