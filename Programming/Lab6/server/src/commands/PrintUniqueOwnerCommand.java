package commands;

import collection.CollectionManager;
import command.CommandImplements;
import command.CommandType;
import data.Product;
import exceptions.EmptyCollectionException;

import java.util.List;

public class PrintUniqueOwnerCommand extends CommandImplements {
    private CollectionManager<Product> collectionManager;
    public PrintUniqueOwnerCommand(CollectionManager<Product> cm) {
        super("print_unique_owner", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() {
        if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        List<String> list = collectionManager.getUniqueOwner();
        return list.stream().reduce("", (a,b) -> a + b +"\n");
        //return list.toString();
    }
}
