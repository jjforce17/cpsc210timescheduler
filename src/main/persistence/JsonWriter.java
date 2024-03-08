package persistence;

import model.AppUser;
import model.GroupModel;
import org.json.JSONObject;

import java.io.*;

//CODE MOSSTLY FROM https://github.com/stleary/JSON-java
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String dest) {
        this.destination = dest;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of workroom to file
    public void write(AppUser u) {
        JSONObject writeObj = u.toJson();
        saveToFile(writeObj.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    public void saveToFile(String obj) {
        writer.print(obj);
    }
}
