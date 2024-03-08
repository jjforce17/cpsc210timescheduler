package persistence;

import org.json.JSONObject;

public interface Writable {
    //CODE MOSSTLY FROM https://github.com/stleary/JSON-java
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
