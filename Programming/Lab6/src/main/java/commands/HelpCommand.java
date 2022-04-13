package commands;

import command.CommandImplements;
import command.CommandManager;
import command.CommandType;

public class HelpCommand extends CommandImplements {
    public HelpCommand() {
        super("help", CommandType.NORMAL);
    }
    @Override
    public String execute() {
        return CommandManager.getHelp();
    }
}
