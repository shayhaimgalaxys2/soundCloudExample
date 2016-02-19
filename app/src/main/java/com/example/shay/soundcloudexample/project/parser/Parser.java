package com.example.shay.soundcloudexample.project.parser;

import android.util.Pair;

import com.example.shay.soundcloudexample.project.utils.LoggingHelper;
import com.example.shay.soundcloudexample.project.utils.NumberUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by shay on 17/02/2016.
 */
public class Parser {

    @SuppressWarnings("unchecked")
    public static <T> T jsonParse(JSONObject jsonObject, String key, T ret) {

        if (jsonObject.has(key)) {
            try {
                Object obj = jsonObject.get(key);

                if (ret.getClass().getSimpleName()
                        .equals(obj.getClass().getSimpleName())) {
                    ret = (T) jsonObject.get(key);
                } else if (ret instanceof JSONObject && obj instanceof JSONArray) {

                    // try {
                    ret = (T) convertJsonArrayToJsonObject((JSONArray) obj);
                    // } catch (Exception e) {
                    // ret = (T) convertJsonArrayToJsonObject((JSONArray) obj,
                    // new Integer(0), createTempJsonObject());
                    // }

                }
                // not in use
                else if (ret instanceof JSONArray && obj instanceof JSONObject) {
                    ret = (T) convertJsonObjectToJsonArray((JSONObject) obj);
                } else {
                    ret = convertStringToNumber(ret, obj);
                }

            } catch (Exception e) {
                LoggingHelper.e("exception type:"
                        + e.getClass().getSimpleName() + " message:"
                        + e.getMessage() + " was thrown by " + key);
            }
        }

        return ret;
    }

    @SuppressWarnings("unchecked")
    private static <T> T convertStringToNumber(T ret, Object obj) {
        if (NumberUtils.stringIsnumber(obj.toString())) {

            if (ret instanceof Integer && obj instanceof String) {
                ret = (T) new Integer(Integer.parseInt((String) obj));

            } else if (ret instanceof Long && obj instanceof String) {

                ret = (T) new Long(Long.parseLong((String) obj));

            } else if (ret instanceof Double && obj instanceof String) {

                ret = (T) new Double(Double.parseDouble((String) obj));
            } else if (ret instanceof Long && obj instanceof Integer) {

                ret = (T) new Long((Integer) obj);
            } else if (ret instanceof Integer && obj instanceof Double) {

                double myDouble = (Double) obj;
                int temp = (int) myDouble;
                ret = (T) new Integer(temp);
            } else if (ret instanceof Double && obj instanceof Integer) {

                int myInteger = (Integer) obj;
                int temp = (int) myInteger;
                ret = (T) new Double(temp);
            }

        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    private static JSONArray convertJsonObjectToJsonArray(JSONObject jsonObject)
            throws JSONException {
        Iterator<String> iterator = (Iterator<String>) jsonObject.keys();
        JSONArray jsonArray = new JSONArray();

        while (iterator.hasNext()) {
            String key2 = (String) iterator.next();
            jsonArray.put(jsonObject.get(key2));
        }

        return jsonArray;
    }

    private static JSONObject convertJsonArrayToJsonObject(JSONArray array)
            throws JSONException {

        JSONObject ret = new JSONObject();

        for (int i = 0; i < array.length(); i++) {

            ret.put(i + "", array.get(i));
        }
        return ret;

    }


    public static HashMap<String, String> getHashMapFromJson(
            HashMap<String, String> hashMap, JSONObject jsonObject) {

        LoggingHelper.entering();
        hashMap = new HashMap<String, String>();
        @SuppressWarnings("unchecked")
        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            try {
                hashMap.put(key, jsonObject.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return hashMap;

    }

    public static HashMap<String, String> getLinkedHashMapFromJson(
            LinkedHashMap<String, String> linkedHashMap, JSONObject jsonObject) {

        LoggingHelper.entering();
        linkedHashMap = new LinkedHashMap<String, String>();
        @SuppressWarnings("unchecked")
        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            try {
                linkedHashMap.put(key, jsonObject.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return linkedHashMap;

    }


    public static HashMap<String, Integer> getHashMapIntegetFromJson(
            HashMap<String, Integer> hashMap, JSONObject jsonObject) {

        LoggingHelper.entering();
        hashMap = new HashMap<String, Integer>();
        @SuppressWarnings("unchecked")
        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            LoggingHelper.d("key:" + key);
            try {

                hashMap.put(key, jsonObject.getInt(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return hashMap;

    }

    public static JSONObject createTempJsonObject() {
        return new JSONObject();
    }

    public static String createTempString() {
        return new String();
    }

    public static String getValueFromHashMap(HashMap<String, String> hashMap,
                                             String key) {

        if (hashMap != null) {

            if (hashMap.containsKey(key)) {
                return hashMap.get(key);
            }
        }
        return key;

    }

    public static int getIntValueFromHashMap(HashMap<String, Integer> hashMap,
                                             String key) {

        if (hashMap.containsKey(key)) {
            return hashMap.get(key);
        }
        return 0;

    }

    public static List<String> convertHashMapToList(
            HashMap<String, String> hashMap) {
        return new ArrayList<String>(hashMap.values());
    }

    public static List<Pair<String, String>> converHashMapToPair(
            HashMap<String, String> hashMap) {
        List<Pair<String, String>> pairList = new ArrayList<Pair<String, String>>();
        for (String key : hashMap.keySet()) {
            pairList.add(new Pair<String, String>(key, hashMap.get(key)));
        }
        return pairList;
    }

    public static boolean isJsonObjectEmpty(JSONObject jsonObject) {

        return jsonObject.length() == 0;
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }

        return null;
    }


    public static Map sortByKey(Map unsortedMap) {
        Map sortedMap = new TreeMap();
        sortedMap.putAll(unsortedMap);
        return sortedMap;
    }

}