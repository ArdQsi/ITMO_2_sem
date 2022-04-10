package connection;

import data.Product;

import java.io.Serializable;

public interface Request extends Serializable {
    public String getStringArg();
    public Product getProduct();
    public String getCommandName();
}
