package commands;

import connection.Request;
import data.Product;

public class Argument implements Request {
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

