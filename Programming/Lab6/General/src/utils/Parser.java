package utils;

import java.io.*;

public class Parser {
    public static Object parseFrom(byte[] data) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
             ObjectInputStream in = new ObjectInputStream(byteArrayInputStream)) {
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> byte[] parseTo(T object) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(6400); ObjectOutput out =
                new ObjectOutputStream(byteArrayOutputStream)) {
            out.writeObject(object);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


