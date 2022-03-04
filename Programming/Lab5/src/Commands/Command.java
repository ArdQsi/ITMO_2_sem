package Commands;

import exceptions.CommandException;
import exceptions.InvalidDataException;

/**
 * Command interface
 */
public interface Command {
    public void run(String arg) throws CommandException, InvalidDataException;
}
