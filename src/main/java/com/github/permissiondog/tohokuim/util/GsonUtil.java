package com.github.permissiondog.tohokuim.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.UUID;

/**
 * Gson 相关
 * @author PermissionDog
 */
public class GsonUtil {
    public static final Gson gson = new GsonBuilder()
            .setPrettyPrinting().registerTypeAdapter(UUID.class, new TypeAdapter<UUID>() {
                @Override
                public void write(JsonWriter out, UUID value) throws IOException {
                    out.value(value.toString());
                }

                @Override
                public UUID read(JsonReader in) throws IOException {
                    var t = in.nextString();
                    if ("".equals(t) || t == null) {
                        return null;
                    }
                    return UUID.fromString(t);
                }
            }).create();
}
