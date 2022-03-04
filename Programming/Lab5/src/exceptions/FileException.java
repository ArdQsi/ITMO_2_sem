package exceptions;

import java.io.IOException;

/**
 * base class for all file exception
 */
public class FileException extends IOException {
    public FileException(String msg){
        super(msg);
    }
}
