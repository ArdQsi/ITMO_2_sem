package io;

/**
 * Operates with console output
 */
public class OutPutManager {
    /**
     * Static method to print message
     * @param msg
     */
    public static void print(Object msg) {
        System.out.println(msg);
    }

    /**
     * Static method to print error notification
     * @param msg
     */
    public static void printErr(String msg) {
        System.out.println("Err: " + msg);
    }
}
