package utils;

import attributes.IAttribute;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import mappings.Mapping;
import strategies.IStrategy;

/**
 * Created by astha.a on 15/02/18.
 */
public class GsonFactory {
    private static GsonFactory instance;
    private Gson gson;

    public static GsonFactory getInstance() {
        if (instance == null) {
            instance = new GsonFactory();
        }
        return instance;
    }

    public GsonFactory() {
        GsonBuilder builder = new GsonBuilder().disableHtmlEscaping().serializeNulls();
        registerAdapters(builder);
        gson = builder.create();
    }

    private void registerAdapters(GsonBuilder gsonBuilder) {
        gsonBuilder.registerTypeAdapter(IAttribute.class, getAttributeMapper());
        gsonBuilder.registerTypeAdapter(IStrategy.class, getStrategyMapper());
        gsonBuilder.registerTypeAdapter(Mapping.class, getMappingMapper());
    }

    public Gson getGson() {
        return gson;
    }

    private static JsonDeserializer<IAttribute> getAttributeMapper() {
        return (jsonElement, typeOfT, context) -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            return (IAttribute) getObjectFromClassName("attributes." + jsonObject.get("name").getAsString(),
                    jsonObject);
        };
    }

    private static JsonDeserializer<IStrategy> getStrategyMapper() {
        return (jsonElement, typeOfT, context) -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            return (IStrategy) getObjectFromClassName("strategies." + jsonObject.get("name").getAsString(),
                    jsonObject);
        };
    }

    private static JsonDeserializer<Mapping> getMappingMapper() {
        return (jsonElement, typeOfT, context) -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            return (Mapping) getObjectFromClassName("mappings." + jsonObject.get("implementation").getAsString(),
                    jsonObject);
        };
    }

    private static Object getObjectFromClassName(String className, JsonObject jsonObject) {
        Class objClass = null;
        try {
            objClass = Class.forName(className);
        } catch (ClassNotFoundException ignored) {
        }
        assert objClass != null;
        return GsonFactory.getInstance().getGson()
                .fromJson(jsonObject.get("properties"), objClass);
    }
}
