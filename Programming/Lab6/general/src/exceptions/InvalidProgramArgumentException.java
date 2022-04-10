package exceptions;

import exceptions.InvalidDataException;

public class InvalidProgramArgumentException extends InvalidDataException {
    public InvalidProgramArgumentException(String s) {
        super(s);
    }
}
