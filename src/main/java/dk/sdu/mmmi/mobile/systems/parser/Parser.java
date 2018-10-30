package dk.sdu.mmmi.mobile.systems.parser;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Parser {

    public static void main(String[] args) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonObject a = gson.fromJson(new FileReader(new File("data/activityrecognition-42b2c-export.json")), JsonObject.class);
        System.out.println(a.getAsJsonObject("activityTransitions"));
    }

}
