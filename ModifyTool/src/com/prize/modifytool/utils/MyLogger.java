package com.prize.modifytool.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyLogger {
	
	public static boolean isOpen=true;
	
	public static String logPath="c:\\ModifyTool.txt";
	
	public static synchronized void print(String message){
		if (!isOpen) {
			return;
		}
		Date date = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = dateFormat.format(date); 
		message=now +" :"+ message+"\n";
		
		try {
			File file = new File(logPath);
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(logPath),true));
			writer.append(message);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
