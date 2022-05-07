package commands;

import connection.Request;
import connection.Request1;
import data.Product;

public class Argument extends Request1 {
    private String arg;
    private Product product;
    public Argument(String s, Product p) {
        arg = s;
        product = p;
    }

    @Override
    public String getStringArg() {
        return arg;
    }

    @Override
    public Product getProduct() {
        return product;
    }

    @Override
    public String getCommandName() {
        return "";
    }
}

