package Sim;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TimeWriter {
    public static void logTime(double time, String name) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(name + ".txt", true));
            writer.append(time + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
