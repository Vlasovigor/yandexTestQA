package com.gmail.vlasovigoor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSON_TEST {

    public static void mainFunction(String mainFileName, String secondaryFileName) {

        String firstFileName = "./" + mainFileName;
        String secondFileName = "./" + secondaryFileName;
        JsonParser parser = new JsonParser();
        JsonObject mainJsonObject = null;
        try {
            mainJsonObject = (JsonObject) parser.parse(new FileReader(firstFileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonObject secondaryJsonObject = null;
        try {
            secondaryJsonObject = (JsonObject) parser.parse(new FileReader(secondFileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        JsonObject mainJsonObjectB = (JsonObject) mainJsonObject.get("b");
        JsonObject secondaryJsonObjectB = (JsonObject) secondaryJsonObject.get("b");

        copyJsonElement(mainJsonObjectB, secondaryJsonObjectB);
        mainJsonObject.add("b", mainJsonObjectB);

        FileWriter writer = null;
        try {
            writer = new FileWriter("./result.json");
            writer.write(mainJsonObject.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyJsonElement(JsonObject mainJsonObject, JsonObject secondaryJsonObject) {

        for (String keyInSecondary : secondaryJsonObject.keySet()) {
            for (String keyInMain : mainJsonObject.keySet()) {
                if (keyInMain.equals(keyInSecondary)) {
                    copyJsonElement((JsonObject) mainJsonObject.get(keyInMain),
                            (JsonObject) secondaryJsonObject.get(keyInSecondary));
                }
            }
            mainJsonObject.add(keyInSecondary, secondaryJsonObject.get(keyInSecondary));
        }
    }

    public static void main(String[] argv) {

        mainFunction("mainJson.json",
                "secondary.json");
    }
}
