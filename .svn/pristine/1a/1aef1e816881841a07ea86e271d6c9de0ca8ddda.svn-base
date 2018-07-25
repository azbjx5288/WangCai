package com.wangcai.lottery.base.net;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by liujc on 2015/5/15.
 */
public class Formater2 {
    private static final String DEPTH_PLACEHOLDER = "  ";
    private static final char OFFSET = ' ';

    public static void println(String tag, String str) {
        if (str.length() > 4000) {
            for (String tmp: str.split("\n")){
                Log.i(tag, tmp);
            }
        } else {
            Log.i(tag, str);
        }
    }

    public static void println(String tag, StringBuffer buffer, String str) {
        if (buffer != null) {
            buffer.append(str);
            buffer.append("\n");
        } else {
            Log.i(tag, str);
        }
    }

    public static void outJsonObject(String tag, String placeholder, String myName, String JsonObjectString) {
        JsonParser jsonParser = new JsonParser();
        try {
            JsonElement element = jsonParser.parse(JsonObjectString);
            if (element instanceof JsonObject) {
                outJsonObject(tag, placeholder, myName, (JsonObject)element);
            } else if (element instanceof JsonArray) {
                outJsonObject(tag, placeholder, myName, (JsonArray)element);
            } else {
                println(tag, "not json in: " + placeholder + myName + JsonObjectString);
            }
        } catch (Exception e) {
            println(tag, "not json in: " + placeholder + myName + JsonObjectString);
        }
    }

    public static void outJsonObject(String tag, String placeholder, String myName, JsonObject JsonObject) {
        StringBuffer buffer = new  StringBuffer();
        outJsonObject(tag, placeholder, myName, JsonObject, buffer);
        println(tag, buffer.toString());
    }

    public static void outJsonObject(String tag, String placeholder, String myName, JsonObject jsonObject, StringBuffer buffer) {
        if (jsonObject == null) {
            return;
        }
        if (placeholder == null) {
            placeholder = "";
        }
        if (myName == null) {
            myName = "";
        }

        if (jsonObject.isJsonNull()) {
            println(tag, buffer, placeholder + myName + ":" + "{}");
            return;
        }

        Iterator<Map.Entry<String, JsonElement>> iterator = jsonObject.entrySet().iterator();
        String head = null;
        String trail = null;
        String myNameBlankspace = "";
        if (myName.length() > 0) {
            char[] tmpName = new char[myName.length()];
            Arrays.fill(tmpName, OFFSET);
            tmpName[myName.length() - 1] = '|';
            myNameBlankspace = new String(tmpName);
            head = placeholder + myName + ":" + "{";
            trail = placeholder + myNameBlankspace +OFFSET+ "}";
        } else {
            head = placeholder + "{";
            trail = placeholder + "}";
        }
        println(tag, buffer, head);

        placeholder += myNameBlankspace + DEPTH_PLACEHOLDER;
        while (iterator.hasNext()) {
            String name = iterator.next().getKey();
            Object object = jsonObject.get(name);
            if (object instanceof JsonObject) {
                outJsonObject(tag, placeholder, name, (JsonObject) object, buffer);
            } else if (object instanceof JsonArray) {
                outJsonObject(tag, placeholder, name, (JsonArray) object, buffer);
            } else if (object instanceof String){
                println(tag, buffer, placeholder + name + ":" + ((String)object).replaceAll("\n",
                        "\n" + placeholder + new String(new byte[name.length() + 1])));
            } else {
                println(tag, buffer, placeholder + name + ":" +  object);
            }
        }
        println(tag, buffer, trail);
    }

    public static void outJsonObject(String tag, String placeholder, String myName, JsonArray JsonArray) {
        StringBuffer buffer = new StringBuffer();
        outJsonObject(tag, placeholder, myName, JsonArray, buffer);
        println(tag, buffer.toString());
    }

    public static void outJsonObject(String tag, String placeholder, String myName, JsonArray JsonArray, StringBuffer buffer) {
        if (JsonArray == null) {
            return;
        }
        if (placeholder == null) {
            placeholder = "";
        }
        if (myName == null) {
            myName = "";
        }

        if (JsonArray.size() == 0) {
            println(tag, buffer, placeholder + myName + ":" + "[]");
            return;
        }

        String head = null;
        String trail = null;
        String myNameBlankspace = "";
        if (myName.length() > 0) {
            char[] tmpName = new char[myName.length()];
            Arrays.fill(tmpName, OFFSET);
            tmpName[myName.length() - 1] = '|';
            myNameBlankspace = new String(tmpName);
            head = placeholder + myName + ":" + "[<size=" + JsonArray.size() + ">";
            trail = placeholder + myNameBlankspace + OFFSET + "]";
        } else {
            head = placeholder + "[<size=" + JsonArray.size() + ">";
            trail = placeholder + "]";
        }
        println(tag, buffer, head);

        placeholder += myNameBlankspace + DEPTH_PLACEHOLDER;
        try {
            for (int i = 0, length = JsonArray.size(); i < length; i++) {
                Object object = JsonArray.get(i);
                if (object instanceof JsonObject) {
                    outJsonObject(tag, placeholder, "", (JsonObject) object, buffer);
                } else if (object instanceof JsonArray) {
                    outJsonObject(tag, placeholder, "", (JsonArray) object, buffer);
                } else {
                    println(tag, buffer, placeholder + object.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        println(tag, buffer, trail);
    }
}
