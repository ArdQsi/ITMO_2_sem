package commands;

import collection.CollectionManager;

import command.CommandImplements;
import command.CommandType;
import data.Product;
import exceptions.CommandException;
import exceptions.InvalidDataException;

public class AddCommand extends CommandImplements {
    private CollectionManager<Product> collectionManager;
    public AddCommand(CollectionManager<Product> cm) {
        super("add", CommandType.NORMAL);
        collectionManager = cm;
    }

    public CollectionManager<Product> getManager() {
        return collectionManager;
    }

    @Override
    public String execute() throws InvalidDataException, CommandException {
        getManager().add(getProductArg());
        return "Added element: " + getProductArg().toString();
    }
}
