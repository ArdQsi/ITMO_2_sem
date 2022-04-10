package exceptions;

public class ClosedConnectionException extends ConnectionException{
    public ClosedConnectionException() {
        super("server channel closed");
    }
}
