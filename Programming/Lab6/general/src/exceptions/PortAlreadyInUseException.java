package exceptions;

import java.net.ConnectException;

public class PortAlreadyInUseException extends ConnectionException {
    public PortAlreadyInUseException() {
        super("port already in use");
    }
}
