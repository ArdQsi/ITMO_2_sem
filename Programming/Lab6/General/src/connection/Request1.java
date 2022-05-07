package connection;

import data.Product;
import utils.Parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Request1 extends CommandMsg implements Request, Serializable {

    private byte[] msgBytes;

    public Request1(final String msg) {
        //super();
        //this.requestCode = requestCode;
        msgBytes = msg.getBytes();
    }

    public Request1(byte[] msg, boolean isBoolean) {
        this.msgBytes = msg;
        this.isBoolean = isBoolean;
    }

    public Request1() {

    }

    private Request request;
    private boolean isBoolean;

    public Request1(Request request, boolean isBoolean) {
        this.request = request;
        this.isBoolean = isBoolean;
    }

    public boolean getIsBoolean() {
        return isBoolean;
    }

    public Request getRequest() {
        return request;
    }

    CommandMsg commandMsg = new CommandMsg();

    public String getStringArg() {
        return commandMsg.getStringArg();
    }

    ;

    public Product getProduct() {
        return commandMsg.getProduct();
    }

    ;

    public String getCommandName() {
        return commandMsg.getCommandName();
    }

    ;

    AnswerMsg answerMsg = new AnswerMsg();

    public Status getStatus() {
        return answerMsg.getStatus();
    }

    public String getMsg() {
        return new String((msgBytes));
    }

    public byte[] getMsgBytes() {
        return msgBytes;
    }


    public Object parseFrom(byte[] bytes) throws IOException, ClassNotFoundException {
        /*List<Integer> mulist = new LinkedList<>();
        Stream<Integer> integerStream = mulist.stream().filter(x -> x == 1).filter(x -> x == 2);*/
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objStream = new ObjectInputStream(byteStream);
        return objStream.readObject();
    }

}
