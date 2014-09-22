package cn.edu.stu.TimeTrack.Layout;

import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.edu.stu.TimeTrack.View.DrawCircle;
import cn.edu.stu.TimeTrack.View.PieView;
import cn.edu.stu.TimeTrack.bean.*;
import cn.edu.stu.TimeTrack.utils.FileService;

import cn.edu.stu.TimeTrack.MainActivity;
import cn.edu.stu.TimeTrack.R;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class TodayLayout extends LinearLayout {

	private MainActivity mContext;
	private View today;
	public TextView tvToday;
	public ImageButton ibPerson, ibCircle, ibPieChart, ibList;
	private LinearLayout llTodayChange;
	private View llTodayPerson, llTodayCircle, llTodayList,llTodayPie;
	public TextView todayDate;
	public String date,mdate;
//	private LinearLayout llTipsContent;
//	private View vTipsContent;
//	private PopupWindow popupWindow;
	private DrawCircle drawCircle;
	private PieView pieView;
	private LinearLayout llAddPie;
	private LinearLayout llTodayListMain;
	
	List<Integer> colors ;
	List<Paint> mpaints;
	Paint paint;
	List<Float> startAngle;
	List<Float> sweepAngle;
	
	float rest = 0;
	float relax = 0;
	float study = 0;
	float work = 0;
	private int restper,relaxper,studyper,workper; 
	
	int[] color =new int[]{R.color.relax,R.color.entertm,R.color.study,R.color.work};
    int[] shade_colors = new int[]{Color.rgb(20, 181, 181),Color.rgb(134,105,183),Color.rgb(221, 195, 86),Color.rgb(15,150,197)};
    int[] percent;
    
    private static TimeData statusData;
    
	public TodayLayout(MainActivity context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		
		LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		setLayoutParams(layoutParams);
	    today = View.inflate(mContext, R.layout.today, null);
		today.setLayoutParams(layoutParams);
		addView(today);
		
		Calendar calendar = Calendar.getInstance();
		date = calendar.get(Calendar.YEAR) + "-"
				+ (calendar.get(Calendar.MONTH) + 1) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH);
		mdate = new String(date);
		init();
	}

	public void init() {
		// ������ע���������õĲ���today. , (�����addView�������ã����Բ����������õĲ��֣��˴�������Բ�����)
		tvToday = (TextView)findViewById(R.id.tv_today);
//		ibPerson = (ImageButton)today.findViewById(R.id.ib_show_state);
		ibCircle = (ImageButton) today.findViewById(R.id.ib_circle);
		ibPieChart = (ImageButton) today.findViewById(R.id.ib_pie);
		ibList = (ImageButton) today.findViewById(R.id.ib_list);

		llTodayChange = (LinearLayout) today.findViewById(R.id.ll_change);
//		llTodayPerson = LinearLayout.inflate(mContext, R.layout.today_person,null);
//		// ��ʼ���������ӵ�Ĭ�ϲ���
//		ibPerson.setBackgroundResource(R.drawable.today_figure2);
		llTodayCircle = View.inflate(mContext,R.layout.today_circle, null);
		drawCircle = (DrawCircle)llTodayCircle.findViewById(R.id.drawcirle);
		setCircle();
		drawCircle.setContentRatio(mpaints,startAngle,sweepAngle);
		llTodayChange.addView(llTodayCircle);
		
//		llTipsContent = (LinearLayout)llTodayPerson.findViewById(R.id.ll_tips);
//		vTipsContent = View.inflate(mContext, R.layout.today_tips, null);
//		llTodayPerson.setOnClickListener(new TipsOnClickListener());
		
		llTodayPie = LinearLayout.inflate(mContext, R.layout.today_pie_chart, null);
		llAddPie = (LinearLayout)llTodayPie.findViewById(R.id.ll_add_pie);
		llTodayList = LinearLayout.inflate(mContext, R.layout.today_list, null);
		llTodayListMain = (LinearLayout)llTodayList.findViewById(R.id.ll_today_list);
		
		// ע��������ӵĲ��ֵķ�ʽ��Ӧ��View����ʽ��ӣ����»�����쳣
		// llTodayList = (LinearLayout)findViewById(R.layout.today_list);
		todayDate = (TextView) findViewById(R.id.tv_today_date);
		todayDate.setText(date);

//		ibPerson.setOnClickListener(new topMenuClickListener());
		ibCircle.setOnClickListener(new topMenuClickListener());
		ibPieChart.setOnClickListener(new topMenuClickListener());
		ibList.setOnClickListener(new topMenuClickListener());
		
		ibCircle.performClick();
	}
	
	private class topMenuClickListener implements OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			resetView();
			switch (v.getId()) {
//			case R.id.ib_show_state:
//				ibPerson.setBackgroundResource(R.drawable.today_figure2);
//				llTodayChange.addView(llTodayPerson);
//				break;
			case R.id.ib_circle:
				setCircle();
				drawCircle.setContentRatio(mpaints,startAngle,sweepAngle);
				ibCircle.setBackgroundResource(R.drawable.today_circle2);
				llTodayChange.addView(llTodayCircle);
				break;
			case R.id.ib_pie:
				setPieChart();
				ibPieChart.setBackgroundResource(R.drawable.today_pie_chart2);
//				llTodayChange.setBackgroundColor(R.color.main_bg);
			    llTodayChange.addView(llTodayPie);
				break;
			case R.id.ib_list:
				setListData();
				ibList.setBackgroundResource(R.drawable.today_list2);
				llTodayChange.addView(llTodayList);
				break;
			default:
				break;
			}
		}
	}

//	private class TipsOnClickListener implements OnClickListener {
//		public void onClick(View v) {
//			popupWindow = new PopupWindow(vTipsContent, llTipsContent.getWidth(), llTipsContent.getHeight(), true);
//			popupWindow.setAnimationStyle(R.style.PopupAnimation);
//			popupWindow.setOutsideTouchable(true);
//			popupWindow.setFocusable(true);
//			popupWindow.setBackgroundDrawable(new BitmapDrawable());
//			popupWindow.setOnDismissListener(new OnDismissListener() {
//				public void onDismiss() {
////					resetView();
//				}
//			});
//			int[] location = new int[2];
//			llTipsContent.getLocationOnScreen(location);
//			popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1]);
////			TextView tipsContent = (TextView)llTipsContent.findViewById(R.id.tv_tips);
////			tipsContent.setText(R.string.tips_content);              // ע���������õ���id�ţ������ַ���������� ����������Ϊ�ַ���Ҳ�����
//		}
//	}
	
	private void resetView() {
		llTodayChange.removeAllViews();
		
//		ibPerson.setBackgroundResource(R.drawable.today_figure);
		ibCircle.setBackgroundResource(R.drawable.today_circle);
		ibPieChart.setBackgroundResource(R.drawable.today_pie_chart);
		ibList.setBackgroundResource(R.drawable.today_list);
	}
	
	public void setCircle(){
		statusData = FileService.getStatusData(date);
		colors = new LinkedList<Integer>();      
		mpaints = new LinkedList<Paint>();            // ������ע��λ��
		startAngle = new LinkedList<Float>();
		sweepAngle = new LinkedList<Float>();
		if (statusData != null) {
			Map<String, Integer> status = statusData.getStatus();
			Iterator<String> iterator = status.keySet().iterator();
			int i = 1;
			float[] mHour = new float[100];   // �����������ʼ���ĳ��Ȳ���Ϊ��������������ʾ���󣬵�����ʱ����ֿ�ָ���쳣��
			startAngle.add((float) -90);
			paint = new Paint(Paint.ANTI_ALIAS_FLAG);    
			paint.setARGB(255,20, 181, 181);
			mpaints.add(paint);
			mHour[0] = 0;
			while (iterator.hasNext()) {
				String time = (String) iterator.next();
				
		        String[] my =time.split(":");
		        float hour =(float) Integer.parseInt(my[0]);   //   ������
		        float min =Integer.parseInt(my[1]);

		        mHour[i] =(float) (hour+min/60);
		        
		        startAngle.add(15*mHour[i] - 90);
		        sweepAngle.add((mHour[i] - mHour[i-1])*15);
		        paint = new Paint(Paint.ANTI_ALIAS_FLAG);                 // ע��λ�ã�ÿaddһ�Σ�����newһ��
				int j = status.get(time);
				switch (j) {
				case 1:
					paint.setARGB(255,20, 181, 181);
					mpaints.add(paint);
					break;
				case 2:
					paint.setARGB(255,134, 105, 183);
					mpaints.add(paint);
					break;
				case 3:
					paint.setARGB(255,221, 195, 86);
					mpaints.add(paint);
					break;
				case 4:
					paint.setARGB(255,16, 150, 220);
					mpaints.add(paint);
					break;
				case 5:
					paint.setARGB(255,107, 105, 107);
					mpaints.add(paint);
					break;
				default:
					break;
				}
				i++;
			}
			if(date.equals(mdate)){                   //ע�⣡����
			}else{
				sweepAngle.add((24-mHour[i-1])*15);
			}
//			colors.set(i-1,Color.WHITE);                                 //  �޸���������ɫ��ע����set������add
		}else{
			paint = new Paint(Paint.ANTI_ALIAS_FLAG);    
			paint.setARGB(255,107, 107, 107);
			mpaints.add(paint);
			startAngle.add((float) -90);
			sweepAngle.add((float) 360);
		}
	}
	
	public void setPieChart(){
		rest = 0;
		relax = 0;
		study = 0;
		work = 0;
		statusData = FileService.getStatusData(date);
		if (statusData != null) {
			Map<String, Integer> status = statusData.getStatus();
			Iterator<String> iterator = status.keySet().iterator();
			float[] mHour = new float[100]; 
			mHour[0] = 0;
			int i = 1;
			List<Integer> kind = new LinkedList<Integer>();
			kind.add(1);
			while (iterator.hasNext()) {
				String time = (String) iterator.next();
				
		        String[] my =time.split(":");
		        float hour =(float) Integer.parseInt(my[0]);   //   ������
		        float min =Integer.parseInt(my[1]);

		        mHour[i] =(float)(hour+min/60);    //  ����������
		        kind.add(status.get(time));
				i++;
			}
			if(date.equals(mdate)){     
				kind.remove(kind.size() - 1);
			}else{
				mHour[i] = 24;
			}
			for(int j = 0,k=1;j<kind.size();j++,k++){
				switch (kind.get(j)) {
				case 1:
					rest += mHour[k]-mHour[k-1];
					break;
				case 2:
					relax += mHour[k]-mHour[k-1];
					break;
				case 3:
					study += mHour[k]-mHour[k-1];
					break;
				case 4:
					work += mHour[k]-mHour[k-1];
					break;
				case 5:
					break;
				default:
					break;
				}
			}
			float allTime = rest+relax+study+work;
			restper = (int) (360*rest/allTime);
			relaxper = (int)(360*relax/allTime);
			studyper = (int)(360*study/allTime);
			workper = (int)(360*work/allTime);
		}else{
		}
		System.out.println("rest:"+restper+"   realx:"+relaxper+"   study:"+studyper+"   work:"+workper);
	    percent = new int[]{restper,relaxper,studyper,workper};
		pieView = new PieView(mContext,color,shade_colors,percent);
		if (statusData != null) {
		    llAddPie.removeAllViews();
		    llAddPie.addView(pieView);
		}
	}
	
	public void setListData(){
		statusData = FileService.getStatusData(date);
		List<String> times = new LinkedList<String>();
		List<String> state = new LinkedList<String>();
		if (statusData != null) {
			Map<String, Integer> status = statusData.getStatus();
			Iterator<String> iterator = status.keySet().iterator();
			times.add("00:00:00");
			state.add("��Ϣ");
			while (iterator.hasNext()) {
				String time = (String) iterator.next();
			    times.add(time);
			    int j = status.get(time);
				switch (j) {
				case 1:
					state.add("��Ϣ");
					break;
				case 2:
					state.add("����");
					break;
				case 3:
					state.add("����");
					break;
				case 4:
					state.add("����");
					break;
				case 5:
					state.add("�޼�¼");
					break;
				default:
					break;
				}
				if(!date.equals(mdate)&&!iterator.hasNext()){    
					times.add("24:00:00");
				}else{
				}
				if(times.size()>1){
					llTodayListMain.removeAllViews();
					for(int i = 0; i <times.size()-1;i++){
						View llTodayListOne = LinearLayout.inflate(mContext,R.layout.today_list_one, null);
						TextView tvListTime1 = (TextView)llTodayListOne.findViewById(R.id.tv_list_time1);
						TextView tvListTime2 = (TextView)llTodayListOne.findViewById(R.id.tv_list_time2);
						TextView tvListStatus = (TextView)llTodayListOne.findViewById(R.id.tv_list_status);
						tvListTime1.setText(times.get(i));
						tvListTime2.setText(times.get(i+1));
						tvListStatus.setText(state.get(i));
						llTodayListMain.addView(llTodayListOne);
					}	
				}
			}
		}
	}
}
