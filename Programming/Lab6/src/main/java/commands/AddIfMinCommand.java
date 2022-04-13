package commands;

import collection.CollectionManager;
import command.CommandImplements;
import command.CommandType;
import data.Product;
import exceptions.CommandException;

public class AddIfMinCommand extends CommandImplements {
    private CollectionManager<Product> collectionManager;
    public AddIfMinCommand(CollectionManager<Product> cm) {
        super("add_if_min", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() {
        boolean success = collectionManager.addIfMin(getProductArg());
        if (success) return ("Added element: " + getProductArg().toString());
        else throw new CommandException("cannot add");
    }
}
