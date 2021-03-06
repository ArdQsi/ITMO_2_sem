package io;

import exceptions.InvalidDataException;

import static io.OutPutManager.printErr;

/**
 *
 * @param <T>
 */

public class Question<T> {
    private Askable<T> askable;
    private T answer;
    public Question(String msg, Askable<T> askable) {
        this.askable = askable;
        while (true) {
            try {
                System.out.println(msg + " ");
                T ans = this.askable.ask();
                answer = ans;
                break;
            } catch (InvalidDataException e){
                printErr(e.getMessage());
            }
        }
    }
    public T getAnswer() {
        return answer;
    }
}
