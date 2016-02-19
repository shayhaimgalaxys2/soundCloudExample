package com.example.shay.soundcloudexample.project.utils;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHandler {

	private HashMap<String, HashMap<String, String>> cache = new HashMap<String, HashMap<String, String>>();
	private Context context;

	public SharedPreferencesHandler(Context context) {
		this.context = context;
	}

	public boolean writeToDisk(String filename, String key, String value) {
		inseretIntoCache(filename, key, value);
		SharedPreferences prefs = context.getSharedPreferences(filename, 0);
		return prefs.edit().putString(key, value).commit();
	}

	public boolean removeFromDisk(String filename, String key) {
		removeFromCache(filename, key);
		SharedPreferences prefs = context.getSharedPreferences(filename, 0);
		return prefs.edit().remove(key).commit();
	}

	private void inseretIntoCache(String filename, String key, String text) {
		HashMap<String, String> diskMap = cache.get(filename);
		if (diskMap == null) {
			HashMap<String, String> newMap = new HashMap<String, String>();
			newMap.put(key, text);
			cache.put(filename, newMap);
		} else {
			diskMap.put(key, text);
		}
	}

	private void removeFromCache(String filename, String key) {
		HashMap<String, String> diskMap = cache.get(filename);
		if (diskMap == null) {
			return;
		} else {
			diskMap.remove(key);
		}
	}

	public String readFromDisk(String filename, String key) {
		String toReturn = getFromDiskCache(filename, key);
		if (toReturn == null) {
			SharedPreferences prefs = context.getSharedPreferences(filename, 0);
			String value = prefs.getString(key, null);
			if (value != null) {
				inseretIntoCache(filename, key, value);
			}
			return value;
		}
		LoggingHelper.d("toReturn = " + toReturn);
		return toReturn;
	}

	private String getFromDiskCache(String filename, String key) {
		HashMap<String, String> map = cache.get(filename);
		if (map != null) {
			return map.get(key);
		}
		return null;
	}

}
