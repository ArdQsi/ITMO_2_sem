package connection;

import auth.User;
import data.Product;


public class CommandMsg implements Request {
    private final String commandName;
    private final String commandStringArgument;
    private final Product product;
    private User user;
    private Request.Status status;

    public CommandMsg(String commandName, String commandStringArgument, Product product) {
        this.commandName = commandName;
        this.commandStringArgument = commandStringArgument;
        this.product = product;
        this.user = null;
        this.status = Status.DEFAULT;
    }

    public CommandMsg(String commandName, String commandStringArgument, Product product, User user) {
        this.commandName = commandName;
        this.commandStringArgument = commandStringArgument;
        this.product = product;
        this.user = user;
        this.status = Status.DEFAULT;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getStringArg() {
        return commandStringArgument;
    }

    public Product getProduct() {
        return product;
    }

    public User getUser() {
        return user;
    }
}

