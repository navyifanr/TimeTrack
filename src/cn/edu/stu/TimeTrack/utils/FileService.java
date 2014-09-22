package cn.edu.stu.TimeTrack.utils;

import java.util.HashMap;

import cn.edu.stu.TimeTrack.bean.TimeData;

import com.google.gson.Gson;


public class FileService {
	
	public static void saveStatusData(String date, String time, int mood) {
		String folderName = date.substring(0, date.lastIndexOf("-"));
		String fileName = date.substring(date.lastIndexOf("-") + 1, date.length());
		String statusDataString = FileManager.getData(folderName, fileName);
		TimeData statusData;
		Gson gson = new Gson();
		if (statusDataString.equals("")) {
			statusData = new TimeData();
			HashMap<String, Integer> statusMap = new HashMap<String, Integer>();
			statusMap.put(time, mood);
			statusData.setuId("");
			statusData.setmDate(date);
			statusData.setStatus(statusMap);
		} else {
			statusData = gson.fromJson(statusDataString, TimeData.class);
			statusData.getStatus().put(time, mood);
		}
		statusDataString = gson.toJson(statusData);
		FileManager.saveData(statusDataString, folderName, fileName);
		System.out.println("sucesss");
	}
	
	public static TimeData getStatusData(String date) {
		String folderName = date.substring(0, date.lastIndexOf("-"));
		String fileName = date.substring(date.lastIndexOf("-") + 1, date.length());
		String statusDataString = FileManager.getData(folderName, fileName);
		if (!statusDataString.equals("")) {
			Gson gson = new Gson();
			return gson.fromJson(statusDataString, TimeData.class);
		}
		return null;
	}
	
	public void upload () {
		
	}
	
}
