package commands;

import collection.CollectionManager;
import collection.ProductCollectionManager;
import command.CommandImplements;
import command.CommandType;
import data.Product;
import exceptions.EmptyCollectionException;
import exceptions.InvalidDataException;

public class ClearCommand extends CommandImplements {
    CollectionManager<Product> collectionManager;
    public ClearCommand(CollectionManager<Product> cm){
        super("clear", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() throws InvalidDataException {
        if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        collectionManager.clear();
        return "collection cleared";
    }
}
