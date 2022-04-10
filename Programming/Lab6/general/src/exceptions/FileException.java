package exceptions;

import java.io.IOException;

/**
 * base class for all file exception
 */
public class FileException extends Exception {
    public FileException(String msg){
        super(msg);
    }
}
