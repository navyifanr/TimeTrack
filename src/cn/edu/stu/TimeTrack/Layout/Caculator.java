package cn.edu.stu.TimeTrack.Layout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.R.integer;

import cn.edu.stu.TimeTrack.bean.TimeData;
import cn.edu.stu.TimeTrack.utils.FileService;

public class Caculator {
	public static boolean issleep = false;
	public static HashMap<Integer, Float> Daycaculator(String date)
	{
		
		TimeData  statusData = FileService.getStatusData(date);
		HashMap<Integer, Float> map = new HashMap<Integer, Float>();
		ArrayList<Float> list_time  = new ArrayList<Float>();
		ArrayList<String> time_s  = new ArrayList<String>();
		ArrayList<Integer> state  = new ArrayList<Integer>();
		float day_worktime = 0;
		float day_studytime = 0;
		float day_playtime = 0;

		int i = 0;
		if (statusData != null) {
			Map<String, Integer> status = statusData.getStatus();
			Iterator<String> iterator = status.keySet().iterator();
			while (iterator.hasNext()) {
				String s = (String)iterator.next();		
			    time_s.add(s);	
			    System.out.println(time_s.get(i));
				String [] time_sp =time_s.get(i).split(":");
				int m = status.get(s);
				state.add(m);
				list_time.add(Float.valueOf(time_sp[0])+Float.valueOf((float)(Float.parseFloat(time_sp[1])/60.0)));
				//System.out.println(Float.valueOf((float)(Float.parseFloat(time_sp[1])/60.0)));
				if(i!=0)
				{
					 
						float f = list_time.get(i)- list_time.get(i-1);
						//System.out.println("i am i n>>>>>>>>>>>>>>");
						//System.out.println(""+f);

						switch (state.get(i-1)) {
						case 1:

							break;
						case 2:
							day_worktime = day_worktime+f;
							break;
						case 3:
							day_playtime+=f;
							break;
						case 4:
							day_studytime+=f;
						default:
							break;
						}
					
				
				}
				i++;
			}
		}
		map.put(2, day_worktime);
		map.put(3, day_playtime);
		map.put(4, day_studytime);
		//System.out.println("sdasda"+map.get(2));
		//System.out.println("fuck"+map.get(3));
		return map;			  
	}
   public static HashMap<Integer, Float> Monthcaculator(int year,int month)
   {
	  HashMap<Integer, Float> map = new HashMap<Integer, Float>();
	  float month_worktime = 0;
	  float month_studytime = 0;
	  float month_playtime = 0;
	  boolean isyear = false;
	  if(year%4==0&&year%100!=0||year%400==0)
	  {
		  isyear =true;
	  }
	  switch (month) {
	  case 1:
		  for(int i=1;i<=31;i++)
		  {
			  String date = year + "-"+ month + "-"+ i;
			  HashMap<Integer, Float> map1 = Daycaculator(date);
			  month_worktime+=map1.get(2);
			  month_playtime+=map1.get(3);
			  month_studytime+=map1.get(4);
		  }
		  map.put(2, month_worktime);
		  map.put(3, month_playtime);
		  map.put(4, month_studytime);
		  return map;
	  case 2:
		  if(isyear)
		  {
		  for(int i=1;i<=29;i++)
		  {
			  String date = year + "-"+ month + "-"+ i;
			  HashMap<Integer, Float> map1 = Daycaculator(date);
			  month_worktime+=map1.get(2);
			  month_playtime+=map1.get(3);
			  month_studytime+=map1.get(4);
		  }
		  map.put(2, month_worktime);
		  map.put(3, month_playtime);
		  map.put(4, month_studytime);
		  }
		  else
		  {
			  for(int i=1;i<=28;i++)
			  {
				  String date = year + "-"+ month + "-"+ i;
				  HashMap<Integer, Float> map1 = Daycaculator(date);
				  month_worktime+=map1.get(2);
				  month_playtime+=map1.get(3);
				  month_studytime+=map1.get(4);
			  }
			  map.put(2, month_worktime);
			  map.put(3, month_playtime);
			  map.put(4, month_studytime);  
		  }
		  return map;
	  case 3:
		  for(int i=1;i<=31;i++)
		  {
			  String date = year + "-"+ month + "-"+ i;
			  HashMap<Integer, Float> map1 = Daycaculator(date);
			  month_worktime+=map1.get(2);
			  month_playtime+=map1.get(3);
			  month_studytime+=map1.get(4);
		  }
		  map.put(2, month_worktime);
		  map.put(3, month_playtime);
		  map.put(4, month_studytime);
		  return map;
	  case 4:
		  for(int i=1;i<=30;i++)
		  {
			  String date = year + "-"+ month + "-"+ i;
			  HashMap<Integer, Float> map1 = Daycaculator(date);
			  month_worktime+=map1.get(2);
			  month_playtime+=map1.get(3);
			  month_studytime+=map1.get(4);
		  }
		  map.put(2, month_worktime);
		  map.put(3, month_playtime);
		  map.put(4, month_studytime);
		  return map;
	  case 5:
		  for(int i=1;i<=31;i++)
		  {
			  String date = year + "-"+ month + "-"+ i;
			  HashMap<Integer, Float> map1 = Daycaculator(date);
			  month_worktime+=map1.get(2);
			  month_playtime+=map1.get(3);
			  month_studytime+=map1.get(4);
		  }
		  map.put(2, month_worktime);
		  map.put(3, month_playtime);
		  map.put(4, month_studytime);
		  return map;
	  case 6:
		  for(int i=1;i<=30;i++)
		  {
			  String date = year + "-"+ month + "-"+ i;
			  HashMap<Integer, Float> map1 = Daycaculator(date);
			  month_worktime+=map1.get(2);
			  month_playtime+=map1.get(3);
			  month_studytime+=map1.get(4);
		  }
		  map.put(2, month_worktime);
		  map.put(3, month_playtime);
		  map.put(4, month_studytime);
		  return map;
	  case 7:
		  for(int i=1;i<=31;i++)
		  {
			  String date = year + "-"+ month + "-"+ i;
			  HashMap<Integer, Float> map1 = Daycaculator(date);
			  month_worktime+=map1.get(2);
			  month_playtime+=map1.get(3);
			  month_studytime+=map1.get(4);
		  }
		  map.put(2, month_worktime);
		  map.put(3, month_playtime);
		  map.put(4, month_studytime);
		  return map;
	  case 8:
		  for(int i=1;i<=31;i++)
		  {
			  String date = year + "-"+ month + "-"+ i;
			  HashMap<Integer, Float> map1 = Daycaculator(date);
			  month_worktime+=map1.get(2);
			  month_playtime+=map1.get(3);
			  month_studytime+=map1.get(4);
		  }
		  map.put(2, month_worktime);
		  map.put(3, month_playtime);
		  map.put(4, month_studytime);
		  return map;
	  case 9:
		  for(int i=1;i<=30;i++)
		  {
			  String date = year + "-"+ month + "-"+ i;
			  HashMap<Integer, Float> map1 = Daycaculator(date);
			  month_worktime+=map1.get(2);
			  month_playtime+=map1.get(3);
			  month_studytime+=map1.get(4);
		  }
		  map.put(2, month_worktime);
		  map.put(3, month_playtime);
		  map.put(4, month_studytime);
		  return map;
	  case 10:
		  for(int i=1;i<=31;i++)
		  {
			  String date = year + "-"+ month + "-"+ i;
			  HashMap<Integer, Float> map1 = Daycaculator(date);
			  month_worktime+=map1.get(2);
			  month_playtime+=map1.get(3);
			  month_studytime+=map1.get(4);
		  }
		  map.put(2, month_worktime);
		  map.put(3, month_playtime);
		  map.put(4, month_studytime);
		  return map;
	  case 11:
		  
		  for(int i=1;i<=30;i++)
		  {
			  String date = year + "-"+ month + "-"+ i;
			  HashMap<Integer, Float> map1 = Daycaculator(date);
			  month_worktime+=map1.get(2);
			  month_playtime+=map1.get(3);
			  month_studytime+=map1.get(4);
			  System.out.println("i am in");
		  }
		  map.put(2, month_worktime);
		  map.put(3, month_playtime);
		  map.put(4, month_studytime);
		  System.out.println(month_worktime);
		  return map;
	  case 12:
		  for(int i=1;i<=31;i++)
		  {
			  String date = year + "-"+ month + "-"+ i;
			  HashMap<Integer, Float> map1 = Daycaculator(date);
			  month_worktime+=map1.get(2);
			  month_playtime+=map1.get(3);
			  month_studytime+=map1.get(4);
		  }
		  map.put(2, month_worktime);
		  map.put(3, month_playtime);
		  map.put(4, month_studytime);
		  return map;
	default:
		break;
	}	
	  return null;	   
   }
}
