package Commands;

/**
 * command wrapper class for command parsing
 */
public class CommandWrapper {
    private final String argument;
    private final String command;

    /**
     * Parsing a string into a command and an argument
     * @param cmd
     * @param arg
     */
    public CommandWrapper(String cmd, String arg) {
        this.argument = arg;
        this.command = cmd;
    }

    /**
     * parsing a string into a command(without argument)
     * @param cmd
     */
    public CommandWrapper(String cmd) {
        argument = null;
        this.command = cmd;
    }

    /**
     * get command from string
     * @return command
     */
    public String getCommand() {
        return command;
    }

    /**
     * get argument from string
     * @return argument
     */
    public String getArgument() {
        return argument;
    }
}
