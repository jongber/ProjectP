package com.jongber.game.desktop;
import com.badlogic.gdx.utils.Json;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Utility {

    public static void writeJson(Object object, File file) {
        Json json = new Json();
        String txt = json.prettyPrint(object);

        Utility.write(file, txt);
    }

    public static <T> T readJson(Class<T> type, File file) {
        String txt = read(file);

        Json json = new Json();
        return json.fromJson(type, txt);
    }

    public static String read(File file) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }

        return sb.toString();
    }

    public static void write(File file, String txt) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(txt);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
