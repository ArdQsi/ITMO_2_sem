package commands;

import collection.CollectionManager;
import command.CommandImplements;
import command.CommandType;
import data.Product;
import exceptions.*;

public class RemoveByIdCommand extends CommandImplements {
    private CollectionManager<Product> collectionManager;
    public RemoveByIdCommand(CollectionManager<Product> cm) {
        super("remove_by_id", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() throws InvalidDataException {
        if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        if (!hasStringArg()) throw new MissedCommandArgumentException();
        try {
            long id = Long.parseLong(getStringArg());
            if (!collectionManager.chekID(id)) throw new InvalidCommandException("no such id");

            boolean success = collectionManager.removeById(id);
            if (success) return "element with id \"" + Long.toString(id) + "\" removed";
            else throw new CommandException("cannot removed");
        } catch (NumberFormatException e) {
            throw new InvalidDataException("id must be long");
        }
    }

}
