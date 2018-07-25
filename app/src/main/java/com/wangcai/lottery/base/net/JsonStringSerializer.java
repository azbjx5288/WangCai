package com.wangcai.lottery.base.net;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * 用于填充Json，避免String类型映射解析错误
 * Created by Alashi on 2016/1/28.
 */
public class JsonStringSerializer implements JsonSerializer<JsonString>, JsonDeserializer<JsonString> {
    @Override
    public JsonElement serialize(JsonString arg0, Type arg1, JsonSerializationContext arg2) {
        return new JsonPrimitive(arg0.getJson());
    }

    @Override
    public JsonString deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
        JsonString jsonString = new JsonString();
        jsonString.setJson(arg0.toString());
        return jsonString;
    }
}
