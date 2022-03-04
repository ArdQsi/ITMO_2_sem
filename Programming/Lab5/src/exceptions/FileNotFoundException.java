package exceptions;

/**
 * thrown when cannot find file
 */
public class FileNotFoundException extends FileException{
    public FileNotFoundException() {
        super("cannot find file");
    }
}
