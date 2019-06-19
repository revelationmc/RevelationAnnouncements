package net.savagedev.ra.common;

import java.io.*;

public class DataStreamUtils {
    private DataStreamUtils() {
    }

    public static byte[] toByteArray(Object... objects) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            ObjectOutputStream output = new ObjectOutputStream(outputStream);

            for (Object object : objects) {
                output.writeObject(object);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }

    public static ObjectInputStream newInputStream(byte[] bytes) {
        try {
            return new ObjectInputStream(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
