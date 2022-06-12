package com.github.permissiondog.tohokuim.util;

import com.github.permissiondog.tohokuim.Constant;
import com.github.permissiondog.tohokuim.entity.enumeration.MessageDirection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Gson 相关
 * @author PermissionDog
 */
public class GsonUtil {
    public static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .setLenient()
            .registerTypeAdapter(UUID.class, new TypeAdapter<UUID>() {
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
            })
            .registerTypeAdapter(LocalDateTime.class, new TypeAdapter<LocalDateTime>() {
                @Override
                public void write(JsonWriter out, LocalDateTime value) throws IOException {
                    out.value(value.format(Constant.DATE_TIME_FORMATTER));
                }

                @Override
                public LocalDateTime read(JsonReader in) throws IOException {
                    return LocalDateTime.parse(in.nextString(), Constant.DATE_TIME_FORMATTER);
                }
            })
            .registerTypeHierarchyAdapter(MessageDirection.class, new TypeAdapter<MessageDirection>() {

                @Override
                public void write(JsonWriter out, MessageDirection value) throws IOException {
                    out.value(value.stringValue());
                }

                @Override
                public MessageDirection read(JsonReader in) throws IOException {
                    return MessageDirection.of(in.nextString());
                }
            }).create();
}
