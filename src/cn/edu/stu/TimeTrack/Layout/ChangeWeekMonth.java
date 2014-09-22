package cn.edu.stu.TimeTrack.Layout;

import java.util.LinkedList;
import java.util.List;

public class ChangeWeekMonth {
	boolean isWeek = true;
//	String[] strdate;  // ��������Ҫ����Ϊ���飬���Ȳ���ȷ�������趨Ϊ������ʱ���������û��ֵ������ֿ�ָ���쳣
//	List<String> strdate;     // ������Ҫ����������Ȼweek���ȡmonth�趨���ַ���  
	List<String> strWdate;     
	List<String> strMdate;        
	List<String> strWdays = new LinkedList<String>();
	List<String> strMdays = new LinkedList<String>();
	String year="", month="", day="";
	String startWday="", endWday="", startMday="", endMday="";

	//  �Խ������������ƣ�����
	public void change(String date) {
		strWdate = new LinkedList<String>();
		strMdate = new LinkedList<String>();
		String[] ymd = date.split("-");
		year = ymd[0];
		month = ymd[1];
		day = ymd[2];
		endWday = month + "��" + day + "��";
		endMday = month + "��" + day + "��";
		System.out.println("����" + endMday);
		int mdays1 = Integer.parseInt(day);
		int i;
		int j = 0;
		if (isWeek) {
			if (Integer.parseInt(day) >= 7) {
				for (i = 0; i < 7; i++) {
					strWdate.add(year + "-" + month + "-"+ (Integer.parseInt(day) - i));      
					strWdays.add((Integer.parseInt(day) - i)+"");
				}
				startWday = month+ "��" + (Integer.parseInt(day) - i+1) + "��";                                                 //  ע��ֲ�ͬ���
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
						startWday = (Integer.parseInt(month) - 1) + "��" + j + "��";         //  ע��ֲ�ͬ��� ,�����ļ���
					}else{
						strWdate.add((Integer.parseInt(year) - 1) + "-" + 12 + "-" + j);                    //  ������ ע��һ�º�ʮ���£����仯
						startWday = 12 + "��" + j + "��";   
					}
					strWdays.add(j+"");
				}
			}
			System.out.println("��ʼ" + startWday);
		} else {
			//   �������л��µ�
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
			startMday = (Integer.parseInt(month) - 1) + "��" + (j+1) + "��";
		}
	}

	private void monthHavedays(String months) {
		switch (Integer.parseInt(months)) {
		case 1:
//		������	ע���ǵ��µ�ǰһ����
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
	// ��ǰһ��ʱ����ȡ�������ڣ���ǰ��ʼ���ڵ�ǰһ�죩
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
//			if(month!=12+"")	{               //  PS: �����ֶ��Ǵ���ģ� //   ������ע���ж��ַ���(��һ������)�Ƿ���ȣ�һ��Ҫ��equals,������==��һֱ��false��
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
