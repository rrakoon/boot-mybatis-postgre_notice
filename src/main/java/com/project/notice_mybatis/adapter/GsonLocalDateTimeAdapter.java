package com.project.notice_mybatis.adapter;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
     REST 방식에서 처리는 JavaScript로 되어야되므로
     LocalDateTime 처리는 Thymeleaf 클래스 메서드를 활용할 수 없으므로 JavaScript로 처리되어야된다.
*/

public class GsonLocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return LocalDateTime.parse(json.getAsString(),DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(DateTimeFormatter.ISO_DATE_TIME.format(src));
    }
}
