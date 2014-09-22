package cn.edu.stu.TimeTrack.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.os.Environment;

public class FileManager {

	public static String FilePath = Environment.getExternalStorageDirectory().toString() + "/" + "TimeTrack" + "/"; // »ñÈ¡SD¿¨Â·¾¶

	public static boolean create(String folderName) {
		File dir = new File(FilePath + folderName);
		try {
			if (!dir.exists()) {
				dir.mkdirs();
			}
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	public static String[] getFileList(String folderName) {
		String[] list = null;
		if (create(folderName)) {
			File dir = new File(FilePath + folderName);
			list = dir.list();
		}
		return list;
	}

	public static void saveAccessToken(String data, String folderName, String fileName) {
		saveData(data, folderName, fileName);
	}

	public static String getAccessToken(String folderName, String fileName) {
		return getData(folderName, fileName);
	}
	
	public static void saveExpiresIn(String data, String folderName, String fileName) {
		saveData(data, folderName, fileName);
	}

	public static String getExpiresIn(String folderName, String fileName) {
		return getData(folderName, fileName);
	}
	
	public static void saveSetting(String data, String fileName) {
		saveData(data, "setting", fileName);
	}

	public static String getSetting(String fileName) {
		return getData("setting", fileName);
	}
	
	public static void saveArticle(String data, String fileName) {
		saveData(data, "article", fileName);
	}

	public static String getArticle(String fileName) {
		return getData("article", fileName);
	}
	
	public static String getArticlePath() {
		if (create("article")) {
			return FilePath + "article" + "/";
		}
		return "";
	}
	
	
	public static void saveData(String data, String folderName, String fileName) {
		if (create("data/" + folderName)) {
			File file = new File(FilePath + "data/" + folderName + "/" + fileName + ".txt");
			try {
				file.createNewFile();
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(data.getBytes());
				fos.flush();
				fos.close();
				System.out.println(data);
				System.out.println("dsadsaffdasds");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}
	
	public static String getData(String folderName, String fileName) {
		String data = "";
		if (create("data/" + folderName)) {
			File file = new File(FilePath + "data/" + folderName + "/" + fileName + ".txt");
			String line = "";
			try {
				FileInputStream fis = new FileInputStream(file);
				InputStreamReader isb = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isb);
				while ((line = br.readLine()) != null) {
					data = data + line;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}


}
