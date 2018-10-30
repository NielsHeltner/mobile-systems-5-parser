package dk.sdu.mmmi.mobile.systems.parser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public class Parser {
    
    public final static String PATH = "data/activityrecognition-42b2c-export.json";
    public final static String ACTIVITY_RECOGNITION = "activityRecognitions";
    public final static String ACTIVITY_TRANSITION = "activityTransitions";
    
    private static Map<Integer, String> activities = new HashMap();
    private static Map<Integer, String> transitions = new HashMap();

    public static void main(String[] args) throws FileNotFoundException {
        activities.put(0, "In vehicle");
        activities.put(1, "On bicycle");
        activities.put(2, "On foot");
        activities.put(3, "Still");
        activities.put(4, "Unknown");
        activities.put(5, "Tilting");
        activities.put(7, "Walking");
        activities.put(8, "Running");
        
        transitions.put(0, "Start");
        transitions.put(1, "Stop");
        
        
        
        Gson gson = new Gson();
        JsonObject root = gson.fromJson(new FileReader(new File(PATH)), JsonObject.class);
        
        PrintWriter transitionWriter = new PrintWriter(new File("parsed-data-transition.txt"));
        JsonObject activityTransitions = root.getAsJsonObject(ACTIVITY_TRANSITION);
        for (Entry<String, JsonElement> entry : activityTransitions.entrySet()) {
            entry.getValue().getAsJsonArray().forEach(e -> {
                transitionWriter.println(getTimeFormatted(Long.parseLong(entry.getKey())) + " " 
                        + transitions.get(e.getAsJsonObject().get("transitionType").getAsInt()) + " "
                        + activities.get(e.getAsJsonObject().get("activityType").getAsInt()));
            });
        }
        transitionWriter.close();
        
        PrintWriter recognitionWriter = new PrintWriter(new File("parsed-data-recognition.txt"));
        JsonObject activityRecognitions = root.getAsJsonObject(ACTIVITY_RECOGNITION);
        for (Entry<String, JsonElement> entry : activityRecognitions.entrySet()) {
            recognitionWriter.println(getTimeFormatted(Long.parseLong(entry.getKey())) + " " 
                        + activities.get(entry.getValue().getAsJsonArray().get(0).getAsJsonObject().get("type").getAsInt()));
        }
        recognitionWriter.close();
    }
    
    private static String getTimeFormatted(long timeMs) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
        return simpleDateFormat.format(new Date(timeMs));
    }

}
