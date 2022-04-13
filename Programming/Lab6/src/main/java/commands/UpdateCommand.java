package commands;

import collection.CollectionManager;
import command.CommandImplements;
import command.CommandType;
import data.Product;
import exceptions.*;

public class UpdateCommand extends CommandImplements {
    private CollectionManager<Product> collectionManager;

    public UpdateCommand(CollectionManager<Product> cm) {
        super("update", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() throws InvalidDataException {
        if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        if (!hasStringArg()) throw new MissedCommandArgumentException();
        try {
            long id = Long.parseLong(getStringArg());
            if (!collectionManager.chekID(id)) throw new InvalidCommandException("no such id");

            boolean success = collectionManager.updateById(id, getProductArg());
            if (success) return "element with id: " + Long.toString(id) + " updated";
            else throw new CommandException("cannot update");
        } catch (NumberFormatException e) {
            throw new InvalidDataException("id must be number");
        }
    }
}
