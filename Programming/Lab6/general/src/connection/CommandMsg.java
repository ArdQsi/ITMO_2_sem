package connection;

import data.Product;

import java.io.Serializable;

public class CommandMsg  implements Request{
    private String commandName;
    private String commandStringArgument;
    private Serializable commandObjectArgument;

    public CommandMsg(String commandName, String commandStringArgument, Serializable commandObjectArgument) {
        super();
        this.commandName = commandName;
        this.commandStringArgument = commandStringArgument;
        this.commandObjectArgument = commandObjectArgument;
    }

    public CommandMsg() {
    }

    public String getCommandName() {
        return commandName;
    }

    public String getStringArg() {
        return commandStringArgument;
    }

    public Product getProduct() {
        return (Product) commandObjectArgument;
    }
}
