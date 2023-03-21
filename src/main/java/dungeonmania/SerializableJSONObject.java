package dungeonmania;

import java.io.Serializable;

import org.json.JSONObject;

public class SerializableJSONObject extends JSONObject implements Serializable {
    public SerializableJSONObject(String config) {
        super(config);
    }
}
