package commands;

import collection.CollectionManager;
import command.CommandImplements;
import command.CommandType;
import data.Product;
import exceptions.FileException;
import file.FileManager;

public class SaveCommand extends CommandImplements {
    FileManager fileManager;
    CollectionManager<Product> collectionManager;

    public SaveCommand(CollectionManager<Product> cm, FileManager fm) {
        super("save", CommandType.SERVER_ONLY);
        collectionManager = cm;
        fileManager = fm;
    }

    @Override
    public String execute() throws FileException {
        fileManager.writeToCSV(fileManager.getElement(collectionManager.getCollection()));
        return "collection successfully saved";
    }
}
