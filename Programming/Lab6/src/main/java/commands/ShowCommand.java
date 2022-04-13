package commands;

import collection.CollectionManager;
import command.CommandImplements;
import command.CommandType;
import data.Product;
import exceptions.EmptyCollectionException;

public class ShowCommand extends CommandImplements {
    private CollectionManager<Product> collectionManager;

    public ShowCommand(CollectionManager<Product> cm) {
        super("show", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() {
        if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        //collectionManager.sort(); по хорошему добавить этот метод, но у меня проблемы с сортом(мб по умолчанию сделать)
        return collectionManager.getCollection().toString();
    }
}
