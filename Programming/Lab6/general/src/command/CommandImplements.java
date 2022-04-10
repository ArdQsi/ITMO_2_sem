package command;


import connection.AnswerMsg;
import connection.Request;
import connection.Response;
import connection.Status;
import data.Product;
import exceptions.CommandException;
import exceptions.ExitException;
import exceptions.FileException;
import exceptions.InvalidDataException;

import java.net.ConnectException;

public abstract class CommandImplements implements Command {
    private CommandType type;
    private String name;
    private Request arg;

    public CommandImplements(String n, CommandType t) {
        name = n;
        type = t;
    }

    @Override
    public CommandType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public abstract String execute() throws InvalidDataException, CommandException, FileException, ConnectException;

    public Response run() {
        AnswerMsg res = new AnswerMsg();
        try {
            res.info(execute());
        } catch (ExitException e) {
            res.info(e.getMessage());
            res.setStatus(Status.EXIT);
        } catch(InvalidDataException| CommandException | FileException | ConnectException e) {
            res.error(e.getMessage());
        }
        return res;
    }

    public Request getArgument() {
        return arg;
    }

    public void setArgument(Request request) {
        arg = request;
    }

    public boolean hasStringArg() {
        return arg!=null && arg.getStringArg() != null && !arg.getStringArg().equals("");
    }

    public boolean hasProductArg() {
        return arg!=null && arg.getProduct()!=null;
    }

    public String getStringArg() {
        return getArgument().getStringArg();
    }

    public Product getProductArg() {
        return getArgument().getProduct();
    }
}
