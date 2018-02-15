package utils;

import attributes.IAttribute;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;

/**
 * Created by astha.a on 15/02/18.
 */
public class AdapterFactory {
    static JsonDeserializer<IAttribute> getAttributeMapper() throws Exception {
        return (jsonElement, typeOfT, context) -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Class attributeName = null;
            try {
                attributeName = Class.forName("attributes." + jsonObject.get("name").getAsString());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return (IAttribute) new Gson().fromJson(jsonObject.get("properties"), attributeName);
        };
    }
}
