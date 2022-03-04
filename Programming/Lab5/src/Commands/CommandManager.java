package Commands;

import collection.*;
import exceptions.*;
import file.FileManager;
import io.ConsoleInputManager;
import io.FileInputManager;
import io.InputManager;
import java.util.*;
import static io.OutPutManager.print;
import static io.OutPutManager.printErr;

/**
 * Class that processes commands
 */
public class CommandManager {

    private ProductCollectionManager collectionManager;
    private InputManager inputManager;
    private FileManager fileManager;
    private boolean isRunning;
    private static Stack<String> stack = new Stack<>();
    private Map<String, Command> map;

    public CommandManager(ProductCollectionManager collectionManager, InputManager inputManager, FileManager fileManager) {
        isRunning = false;
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
        this.fileManager = fileManager;

        map = new HashMap<String, Command>();
        /**
         * Add "info" command to map collection
         */
        addCommand("info", (a)->print(collectionManager.getInfo()));
        /**
         * Add "help" command to map collection
         */
        addCommand("help", (a)->print(getHelp()));
        /**
         * Add "show" command to map collection
         */
        addCommand("show", (a)-> {
            if (collectionManager.getCollection().isEmpty()) print("коллекция пуста");
            else print(collectionManager.getCollection());
        });
        /**
         * Add "add" command to map collection
         */
        addCommand("add", (a)->{
            collectionManager.add(inputManager.readProduct());
        });
        /**
         * Add "update" command to map collection
         */
        addCommand("update",(arg)->{
            long id = 0;
            if (arg == null || arg.equals("")) {
                throw new MissedCommandArgumentException();
            } try {
                id = Long.parseLong(arg);
            } catch (NumberFormatException e){
                throw new InvalidDataException("id must be long");
            }
            if (collectionManager.getCollection().isEmpty()) throw new EmptyStringException();
            if(!collectionManager.chekID(id)) throw new InvalidCommandException("no such id");
            collectionManager.updateId(id, inputManager.readProduct());
        });
        /**
         * Add "remove_by_id" command to map collection
         */
        addCommand("remove_by_id", (arg)->{
            long id = 0;
            if (arg == null || arg.equals("")) {
                throw new MissedCommandArgumentException();
            }
            try {
                id = Long.parseLong(arg);
            } catch (NumberFormatException e){
                throw new InvalidDataException("id must be long");
            }
            if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
            if (!collectionManager.chekID(id)) throw new InvalidCommandException("no such id");
            collectionManager.removeById(id);
        });
        /**
         * Add "clear" command to map collection
         */
        addCommand("clear", (a)-> {
            collectionManager.clear();
        });
        /**
         * Add "save" command to map collection
         */
        addCommand("save", (a) -> {
            if (collectionManager.getCollection().isEmpty()) print("collection is Empty");
            else {
                fileManager.writeToCSV(fileManager.getElement(collectionManager.getCollection()));
                print("collection successfully saved");
            }
        });
        /**
         * Add "execute_script" command to map collection
         */
        addCommand("execute_script", (arg) -> {
            if (arg == null || arg.equals("")) {
                throw new MissedCommandArgumentException();
            }
            if (stack.contains(arg)) throw new RecursiveScriptException();
            stack.push(arg);
            CommandManager process = new CommandManager(collectionManager,inputManager, fileManager);
            process.fileExecute(arg);
            stack.pop();
            print("script completed successfully " + arg);
        });
        /**
         * Add "exit" command to map collection
         */
        addCommand("exit", (a) ->isRunning=false);
        /**
         * Add "add_if_min" command to map collection
         */
        addCommand("add_if_min", (a) -> collectionManager.addIfMin(inputManager.readProduct()));
        /**
         * Add "remove_greater" command to map collection
         */
        addCommand("remove_greater", (arg) -> {
            if (arg == null || arg.equals("")) {
                throw new MissedCommandArgumentException();
            } else {
                if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
                collectionManager.getCollection().removeIf(product -> product.compareToId(arg)>0);
                print("Element with id greater than "+ arg + " was successfully deleted");
            }
        });
        /**
         * Add "remove_lower" command to map collection
         */
        addCommand("remove_lower", (arg) -> {
            if (arg == null || arg.equals("")) {
                throw new MissedCommandArgumentException();
            } else {
                if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
                collectionManager.getCollection().removeIf(product -> product.compareToId(arg)<0);
                print("Element with id less than "+ arg + " was successfully deleted");
            }
        });
        /**
         * Add "filter_less_than_manufacture_cost" command to map collection
         */
        addCommand("filter_less_than_manufacture_cost", (arg) -> {
            if (arg == null || arg.equals("")) {
                throw new MissedCommandArgumentException();
            } else {
                if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
                collectionManager.printLessThanManufactureCost(Float.parseFloat(arg));
            }
        });
        /**
         * Add "print_unique_owner" command to map collection
         */
        addCommand("print_unique_owner", (a) -> {
            if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
            collectionManager.printUniqueOwner();
        });
    }

    /**
     * Add command to map collection
     * @param key
     * @param command
     */
    public void addCommand(String key, Command command) {
        map.put(key, command);
    }

    /**
     * Print help string
     * @return
     */
    public static String getHelp() {
        return "help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n"+
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "save : сохранить коллекцию в файл\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\n" +
                "count_less_than_owner owner : вывести количество элементов, значение поля owner которых меньше заданного\n" +
                "filter_less_than_manufacture_cost manufactureCost : вывести элементы, значение поля manufactureCost которых меньше заданного\n" +
                "print_unique_owner : вывести уникальные значения поля owner всех элементов в коллекции";
    }

    /**
     * Method that takes console input and executes commands
     */
    public void consoleMode() {
        inputManager = new ConsoleInputManager();
        isRunning = true;
        while(isRunning) {
            System.out.println("введите \"help\", чтобы вывести справку по доступным командам ");
            CommandWrapper pair = inputManager.readCommand();
            runCommand(pair.getCommand(), pair.getArgument());
        }
    }

    /**
     * Execute commands
     * @param s
     * @param arg
     */
    public void runCommand(String s, String arg) {
        try {
            if (! hasCommand(s)) throw new NoSuchCommandException();
            map.get(s).run(arg);
        } catch (CommandException | InvalidDataException e) {
            printErr(e.getMessage());
        }
    }

    /**
     * Сommand check
     * @param s
     * @return true/false
     */
    public boolean hasCommand(String s) {
        return map.containsKey(s);
    }

    /**
     * Execute commands in script file
     * @param path1
     */
    public void fileExecute(String path1) {
        inputManager = new FileInputManager(path1);
        isRunning = true;
        while(isRunning && inputManager.getScanner().hasNextLine()) {
            CommandWrapper pair = inputManager.readCommand();
            runCommand(pair.getCommand(),pair.getArgument());
        }
    }

}
