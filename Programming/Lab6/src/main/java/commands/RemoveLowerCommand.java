package commands;

import collection.CollectionManager;
import command.CommandImplements;
import command.CommandType;
import data.Product;
import exceptions.EmptyCollectionException;
import exceptions.InvalidDataException;
import exceptions.MissedCommandArgumentException;

public class RemoveLowerCommand extends CommandImplements {
    private CollectionManager<Product> collectionManager;

    public RemoveLowerCommand(CollectionManager<Product> cm) {
        super("remove_lower", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() throws InvalidDataException {
        if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        if (!hasStringArg()) throw new MissedCommandArgumentException();
        try {
            long id = Long.parseLong(getStringArg());
            collectionManager.removeLower(id);
            return "Elements with id lower than " + Long.toString(id) + " was successfully deleted";
        } catch (NumberFormatException e) {
            throw new InvalidDataException("id must be number");
        }
    }
}
