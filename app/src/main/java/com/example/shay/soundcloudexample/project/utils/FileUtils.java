package com.example.shay.soundcloudexample.project.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import android.os.Environment;

public class FileUtils {

	public static final String ENTER = "\r\n";
	private static int counterException = 0;
	public static File createFile(String name) throws IOException{
		File root = Environment.getExternalStorageDirectory();
		File file = new File(root, name);
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}
	public static void saveException(Exception exception,String methodName){
		LoggingHelper.entering();
		if(!LoggingHelper.DEBUG_MODE){
			return;
		}
		LoggingHelper.d("exception:" + exception.getMessage());
		try {
			File file = createFile("exception log.txt");
			if (file.canWrite()) {
				FileWriter filewriter = new FileWriter(file,true);
				BufferedWriter out = new BufferedWriter(filewriter);
				out.append("////////start exception////////");
				out.append(ENTER);
				out.append(ENTER);
				out.append("date:" + new Date());
				out.append(ENTER);
				out.append("counter exception:" + counterException );
				out.append(ENTER);
				out.append("method name");
				out.append(ENTER);
				out.append(methodName);
				out.append("message");
				out.append(ENTER);
				out.append(exception.getMessage());
				out.append(ENTER);
				out.append("////////end exception////////");
				out.flush();
				out.close();
				counterException++;
			}

		} catch (Exception e) {
			LoggingHelper.d("exception when save file message:" + e.getMessage());
		}
	}
	public static void saveInFile(String fileName,String sessionName,HashMap<String,String> data){
		try {
			File file = createFile(fileName + ".txt");
			if (file.canWrite()) {
				FileWriter filewriter = new FileWriter(file,true);
				BufferedWriter out = new BufferedWriter(filewriter);
				out.append("//////// start " + sessionName + "////////");

				for (String key : data.keySet()) {
					out.append(ENTER);
					out.append(key + ":");
					out.append(ENTER);
					out.append(data.get(key));
					out.append(ENTER);
				
				}
				out.append("//////// end " + sessionName + "////////");
				out.append(ENTER);
				out.flush();
				out.close();
			} 

		}catch (Exception e) {
			saveException(e, LoggingHelper.getMethodName());
		}
	}
}
