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
        System.out.println(msg.toString());
    }

    /**
     * Static method to print error notification
     * @param msg
     */
    public static void printErr(Object msg) {
        System.out.println("Err: " + msg.toString());
    }
}
