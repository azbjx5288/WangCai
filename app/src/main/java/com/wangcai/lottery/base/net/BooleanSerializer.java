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
 * PHP服务器使用“1”表示true，“0”表示false，GSON匹配时需要特别处理
 * Created by Alashi on 2016/1/7.
 */
public class BooleanSerializer implements JsonSerializer<Boolean>, JsonDeserializer<Boolean> {
    @Override
    public JsonElement serialize(Boolean arg0, Type arg1, JsonSerializationContext arg2) {
        return new JsonPrimitive(arg0 ? 1 : 0);
    }

    @Override
    public Boolean deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
        return arg0.getAsBoolean()==true||arg0.getAsInt() == 1?true:false;
    }
}
