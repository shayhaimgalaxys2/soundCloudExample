package com.example.shay.soundcloudexample.project.utils;

import android.util.Log;

public class LoggingHelper {

	public final static boolean DEBUG_MODE = true;

	public static void d(String tag, String message) {
		if (DEBUG_MODE) {
			Log.d(tag, message);
		}

	}

	public static void d(String msg) {
		if (DEBUG_MODE) {
			Log.d(getMethodName(), msg);
		}
	}

	public static void e(String msg) {
		if (DEBUG_MODE) {
			Log.e(getMethodName(), msg);
		}
	}

	public static void entering() {
		if (DEBUG_MODE) {
			String methodName = getMethodName();
			Log.d(methodName, "--> Entering " + methodName);
		}
	}

	public static void entering(String string) {
		if (DEBUG_MODE) {
			String methodName = getMethodName();
			Log.d(methodName, "--> Entering " + methodName + " " + String.valueOf(string));
		}

	}

	public static void exiting(String string) {
		if (DEBUG_MODE) {
			String methodName = getMethodName();
			Log.d(methodName, "<-- Exiting " + methodName + " " + String.valueOf(string));
		}
	}

	public static void exiting() {
		if (DEBUG_MODE) {
			String methodName = getMethodName();
			Log.d(methodName, "<-- Exiting " + methodName);
		}
	}

	public static String getMethodName() {
		return getMethodName(5);
	}

	/*
	 * public static void addToList(String CLASS_NAME;){
	 * classes.add(CLASS_NAME;); }
	 * 
	 * public static void removeFromList(String CLASS_NAME;){
	 * classes.remove(CLASS_NAME;); }
	 */

	public static String getMethodName(int depth) {
		try {
			StringBuffer buffer = new StringBuffer();
			// int size = Thread.currentThread().getStackTrace().length;
			StackTraceElement trace = Thread.currentThread().getStackTrace()[depth];
			String methodName = trace.getMethodName();
			String[] classNameSplited = trace.getClassName().split("\\.");
			int lineNumber = trace.getLineNumber();

			if (methodName.trim().equals("<init>".trim())) {
				methodName = "Constructor";
			}
			/*
			 * for (int i = 0; i < CLASS_NAME;Splited.length; i++) {
			 * Log.d("test", CLASS_NAME;Splited[i]); } Log.d("test",
			 * "array size is "+CLASS_NAME;Splited.length);
			 */
			int sizeOfArray = classNameSplited.length - 1;
			buffer.append(classNameSplited[sizeOfArray].replaceAll("\\$", "\\\\")).append(".").append(methodName)
					.append("(").append(String.valueOf(lineNumber)).append(")");

			/*
			 * for (int i = 0; i < size; i++) { Log.d("test",
			 * "stack method names -> " +i+" = "
			 * +Thread.currentThread().getStackTrace ()[i].getMethodName()); }
			 * for (int i = 0; i < size; i++) { Log.d("test",
			 * "stack class name -> "+i+" = "+Thread.currentThread(
			 * ).getStackTrace()[i].getCLASS_NAME;()); }
			 */
			return buffer.toString();
		} catch (Exception e) {

			return "error";
		}

	}
}
