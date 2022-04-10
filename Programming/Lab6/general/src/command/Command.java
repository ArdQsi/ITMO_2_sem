package command;


import connection.Request;
import connection.Response;

public interface Command {
    public Response run();
    public String getName();
    public CommandType getType();
    public void setArgument(Request a);
}
