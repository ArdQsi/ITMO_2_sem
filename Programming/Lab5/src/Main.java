import Commands.CommandManager;
import collection.*;
import file.FileManager;
import io.ConsoleInputManager;
import io.InputManager;

public class Main {
    public static void main(String[] args) throws Exception {
        ProductCollectionManager collectionManager = new ProductCollectionManager();
        FileManager fileManager = new FileManager();
        InputManager consoleManager = new ConsoleInputManager();
        CommandManager commandManager = new CommandManager(collectionManager, consoleManager, fileManager);
        commandManager.consoleMode();
    }
}
