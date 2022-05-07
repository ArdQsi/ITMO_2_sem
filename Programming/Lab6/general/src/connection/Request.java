package connection;

import data.Product;

import java.io.Serializable;

public interface Request extends Serializable {
    String getStringArg();
    Product getProduct();
    String getCommandName();

    //Request getMsgBytes();
}
