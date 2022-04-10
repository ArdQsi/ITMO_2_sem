package commands;

import collection.CollectionManager;
import command.Command;
import command.CommandManager;
import connection.AnswerMsg;
import connection.Request;
import data.Product;
import exceptions.CommandException;
import file.FileManager;
import file.ReaderWriter;
import io.InputManager;
import log.Log;
import server.Server;

import java.io.File;


public class ServerCommandManager extends CommandManager {
    private Server server;
    private CollectionManager<Product> collectionManager;
    private FileManager fileManager;
    //private InputManager inputManager;


    public ServerCommandManager(Server serv) {
        server = serv;
        collectionManager = server.getCollectionManager();
        fileManager = server.getFileManager();
        //inputManager = server.getInputManager();


        addCommand(new AddCommand(collectionManager));
        addCommand(new InfoCommand(collectionManager));
        addCommand(new HelpCommand());
        addCommand(new ShowCommand(collectionManager));
        addCommand(new UpdateCommand(collectionManager));
        addCommand(new RemoveByIdCommand(collectionManager));
        addCommand(new ClearCommand(collectionManager));
        addCommand(new SaveCommand(collectionManager,fileManager));
        addCommand(new AddIfMinCommand(collectionManager));
        addCommand(new RemoveGreaterCommand(collectionManager));
        addCommand(new FilterLessThanManufactureCostCommand(collectionManager));
        addCommand(new PrintUniqueOwnerCommand(collectionManager));
        addCommand(new ExecuteScriptCommand(this));
        addCommand(new RemoveLowerCommand(collectionManager));

    }

    public Server getServer() {
        return server;
    }

    @Override
    public AnswerMsg runCommand(Request msg) {
        AnswerMsg res = new AnswerMsg();
        try {
            Command cmd = getCommand(msg.getCommandName());
            cmd.setArgument(msg);
            res = (AnswerMsg) cmd.run();
        }catch (CommandException e) {
            res.error(e.getMessage());
        }
        switch (res.getStatus()) {
            case EXIT:
                //Log.logger.fatal(res.getMessage());
                server.close();
                break;
            case ERROR:
                //Log.logger.error(res.getMessage());
                break;
            default:
                //Log.logger.error(res.getMessage());
                break;
        }
        return res;
    }
}
