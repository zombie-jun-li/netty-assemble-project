package framework.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Created by jun.
 */
public abstract class Json {
    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    static {
        OBJECTMAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECTMAPPER.configure(SerializationFeature.INDENT_OUTPUT, false);
        OBJECTMAPPER.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        OBJECTMAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public static String toJson(Object object) {
        try {
            return OBJECTMAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String jsonText, Class<T> targetType) {
        try {
            return OBJECTMAPPER.readValue(jsonText, targetType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
