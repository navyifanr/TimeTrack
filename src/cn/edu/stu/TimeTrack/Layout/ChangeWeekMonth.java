package cn.edu.stu.TimeTrack.Layout;

import java.util.LinkedList;
import java.util.List;

public class ChangeWeekMonth {
	boolean isWeek = true;
//	String[] strdate;  // ！！！不要声明为数组，长度不能确定，当设定为定长度时，如果部分没赋值，会出现空指针异常
//	List<String> strdate;     // ！！！要设两个，不然week会获取month设定的字符串  
	List<String> strWdate;     
	List<String> strMdate;        
	List<String> strWdays = new LinkedList<String>();
	List<String> strMdays = new LinkedList<String>();
	String year="", month="", day="";
	String startWday="", endWday="", startMday="", endMday="";

	//  以结束的日期逆推，，，
	public void change(String date) {
		strWdate = new LinkedList<String>();
		strMdate = new LinkedList<String>();
		String[] ymd = date.split("-");
		year = ymd[0];
		month = ymd[1];
		day = ymd[2];
		endWday = month + "月" + day + "日";
		endMday = month + "月" + day + "日";
		System.out.println("结束" + endMday);
		int mdays1 = Integer.parseInt(day);
		int i;
		int j = 0;
		if (isWeek) {
			if (Integer.parseInt(day) >= 7) {
				for (i = 0; i < 7; i++) {
					strWdate.add(year + "-" + month + "-"+ (Integer.parseInt(day) - i));      
					strWdays.add((Integer.parseInt(day) - i)+"");
				}
				startWday = month+ "月" + (Integer.parseInt(day) - i+1) + "日";                                                 //  注意分不同情况
			} else {
				for (i = 0; i < mdays1; i++) {
					strWdate.add(year + "-" + month + "-"+ (Integer.parseInt(day) - i));       
					strWdays.add((Integer.parseInt(day) - i)+"");
				}
				monthHavedays(month);
				int mdays2 = Integer.parseInt(day);
				for (j = mdays2; j > mdays2- 7 + mdays1; j--,i++) {
					if(Integer.parseInt(month)!=1){
						strWdate.add(year + "-" + (Integer.parseInt(month) - 1) + "-" + j);
						startWday = (Integer.parseInt(month) - 1) + "月" + j + "日";         //  注意分不同情况 ,天数的计算
					}else{
						strWdate.add((Integer.parseInt(year) - 1) + "-" + 12 + "-" + j);                    //  ！！！ 注意一月和十二月，年会变化
						startWday = 12 + "月" + j + "日";   
					}
					strWdays.add(j+"");
				}
			}
			System.out.println("开始" + startWday);
		} else {
			//   ！！！切换月的
			for (i = 0; i < mdays1; i++) {
				strMdate.add(year + "-" + month + "-"+ (Integer.parseInt(day) - i));
				strMdays.add((Integer.parseInt(day) - i)+"");
			}
			monthHavedays(month);
			int mdays2 = Integer.parseInt(day);
			for (j = mdays2; j >= mdays1; j--, i++) {
				strMdate.add(year + "-" + (Integer.parseInt(month) - 1) + "-" + j);
				strMdays.add(j+"");
			}
			startMday = (Integer.parseInt(month) - 1) + "月" + (j+1) + "日";
		}
	}

	private void monthHavedays(String months) {
		switch (Integer.parseInt(months)) {
		case 1:
//		！！！	注意是当月的前一个月
			day = 31 + "";
			break;
		case 2:
			day = 31 + "";
			break;
		case 3:
			int myear = Integer.parseInt(year);
			if (myear % 4 == 0 && myear % 100 != 0 || myear % 400 == 0) {
				day = 29 + "";
			} else
				day = 28 + "";
			break;
		case 4:
			day = 31 + "";
			break;
		case 5:
			day = 30 + "";
			break;
		case 6:
			day = 31 + "";
			break;
		case 7:
			day = 30 + "";
			break;
		case 8:
			day = 31 + "";
			break;
		case 9:
			day = 31 + "";
			break;
		case 10:
			day = 30 + "";
			break;
		case 11:
			day = 31 + "";
			break;
		case 12:
			day = 30 + "";
			break;
		default:
			break;
		}
	}
	// 按前一周时，获取结束日期（当前开始日期的前一天）
	public String datePreDay(String date){
		String preOneDay="";
		String[] ymd = date.split("-");
		year = ymd[0];
		month = ymd[1];
		day = ymd[2];
		if (Integer.parseInt(day) != 1) {
			preOneDay = year + "-" + month + "-" + (Integer.parseInt(day) - 1);
		} else {
			monthHavedays(month);
			preOneDay = year + "-" + (Integer.parseInt(month) - 1) + "-" + day;
		}
		return preOneDay;
	}
	public String nextSevendays(String date2){
		String endday="";
		int days = 0;
		String[] ymd = date2.split("-");
		year = ymd[0];
		month = ymd[1];
		day = ymd[2];
		switch (Integer.parseInt(month)) {
		case 1:
			days = 31;
			break;
		case 2:
			int myear = Integer.parseInt(year);
			if (myear % 4 == 0 && myear % 100 != 0 || myear % 400 == 0) {
				days= 29;
			} else
				days= 28;
			break;
		case 3:
			days = 31;
			break;
		case 4:
			days = 30;
			break;
		case 5:
			days = 31;
			break;
		case 6:
			days = 30;
			break;
		case 7:
			days = 31;
			break;
		case 8:
			days = 31;
			break;
		case 9:
			days = 30;
			break;
		case 10:
			days = 31;
			break;
		case 11:
			days = 30;
			break;
		case 12:
			days = 31;
			break;
		default:
			break;
		}
		int mday = days-Integer.parseInt(day);
		if(mday>=7){
			endday = year + "-" + month + "-" + (Integer.parseInt(day)+7);
		}else{
//			if(month!="12"){
//			if(month!=12+"")	{               //  PS: 这两种都是错误的， //   ！！！注意判断字符串(是一个对象)是否相等，一定要用equals,不能用==（一直是false）
			if(Integer.parseInt(month)!=12){
				endday = year + "-" + (Integer.parseInt(month)+1) + "-" + (7 - mday);
			}else{
				endday = (Integer.parseInt(year)+1) + "-" + 1 + "-" + (7 - mday);
			}
		}
		System.out.println("endday:  "+endday);
		return endday;
	}
	public String preFourWeeks(String date2){
		String pre4weekdate = "";
		String[] ymd = date2.split("-");
		year = ymd[0];
		month = ymd[1];
		day = ymd[2];
		int mday = Integer.parseInt(day);
		if (Integer.parseInt(day) >28) {
			pre4weekdate = year + "-" + month + "-" + (Integer.parseInt(day) - 28);
		} else {
			monthHavedays(month);
			pre4weekdate = year + "-" + (Integer.parseInt(month) - 1) + "-" + (Integer.parseInt(day)-28+mday +1);
		}
		return pre4weekdate;
	}
}
