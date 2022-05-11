package connection;

import auth.User;
import data.Product;

import java.io.Serializable;

public interface Request extends Serializable {
    String getStringArg();

    Product getProduct();

    String getCommandName();

    User getUser();

    void setUser(User user);

    Status getStatus();

    void setStatus(Status s);

    enum Status {
        DEFAULT,
        SENT_FROM_CLIENT,
        SENT_FROM_CLIENT_PART,
        RECEIVED_BY_SERVER
    }
}