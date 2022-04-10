package connection;

public class AnswerMsg implements Response{
    private String msg;
    private Status status;

    public AnswerMsg() {
        msg = "";
        status=Status.FINE;
    }

    public AnswerMsg info(Object str) {
        msg = str.toString();
        return this;
    }

    public AnswerMsg error(Object str) {
        msg = str.toString();
        setStatus(Status.ERROR);
        return this;
    }

    public AnswerMsg setStatus(Status st) {
        status = st;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return msg;
    }

    @Override
    public String toString() {
        switch (getStatus()) {
            case ERROR:
                return "Err: " + getMessage();
            default:
                return getMessage();
        }
    }
}
