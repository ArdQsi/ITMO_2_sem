package commands;

import collection.CollectionManager;
import command.CommandImplements;
import command.CommandType;
import data.Product;
import exceptions.*;

import java.util.List;

public class RemoveGreaterCommand extends CommandImplements {
    private CollectionManager<Product> collectionManager;
    public RemoveGreaterCommand(CollectionManager<Product> cm) {
        super("remove_greater", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() throws InvalidDataException {
        if(collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        if (!hasStringArg()) throw new MissedCommandArgumentException();
        try {
            long id = Long.parseLong(getStringArg());
            collectionManager.removeGreater(id);
            return "Elements with id greater than " + Long.toString(id) + " was successfully deleted";
        } catch (NumberFormatException e) {
            throw new InvalidDataException("id must be number");
        }
    }
}
