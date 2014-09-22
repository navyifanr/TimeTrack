package cn.edu.stu.TimeTrack.Layout;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.edu.stu.TimeTrack.MainActivity;
import cn.edu.stu.TimeTrack.R;
import cn.edu.stu.TimeTrack.View.LineChartView;
import cn.edu.stu.TimeTrack.View.PieView;
import cn.edu.stu.TimeTrack.bean.TimeData;
import cn.edu.stu.TimeTrack.utils.FileService;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HistoryLayout extends LinearLayout {

	private MainActivity mContext;
	private TextView tvHistoryDay, tvHistoryWeek, tvHistoryMonth;
	private View llHistoryDay;
	private LinearLayout llHistoryContent;
	// private ImageButton imMonthLeft, imMonthRight;
	private CalendarView calendarView;
	// private TextView tvDayDate;
	private View llHistoryWeek, llHistoryMonth;
	private LinearLayout llWMContent;
	private ImageButton imWeekPieChart, imWeekLineChart;
	private View llWMItem;
	private LinearLayout llChange;
	private ImageButton imMonthPieChart, imMonthLineChart;
	private ImageButton ibWLast, ibWNext;
	private boolean isPieClicked = true;
	private TextView tvWLdate, tvWRdate, tvMLdate, tvMRdate;
	private View llMonthLine, llMonthItem;
	private LinearLayout llChangeMonth;
	private LinearLayout llMonthContent;
	private LinearLayout llScrollMonth;
	private LineChartView lineChartView;
	private LineChartView lineChartView2;

	private ChangeWeekMonth chgeWM;
	String clickdate;
	int clickmonth, clickday;
	String date, mdate, ldate;
	private TimeData statusData;
	float rest;
	float relax;
	float study;
	float work;
	float allTime;
	private int restper, relaxper, studyper, workper;
	private List<Float> mrest, mrelax, mstudy, mwork;
	private List<List<Float>> allItem;
	// private List<String> wDays,mDays;

	List<Integer> color = new LinkedList<Integer>();
	int[] colors = new int[] { R.color.relax, R.color.entertm, R.color.study,
			R.color.work };
	int[] shade_colors = new int[] { Color.rgb(20, 181, 181),
			Color.rgb(134, 105, 183), Color.rgb(221, 195, 86),
			Color.rgb(15, 150, 197) };

	public HistoryLayout(MainActivity context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		// intent = mContext.getIntent(); // ！！！注意引用的是主Activity的，
		Calendar calendar = Calendar.getInstance();
		mdate = calendar.get(Calendar.YEAR) + "-"
				+ (calendar.get(Calendar.MONTH) + 1) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH);
		date = new String(mdate); // 注意赋值顺序，还有深浅复制
		ldate = new String(mdate);
		color.add(R.color.entertm);
		color.add(R.color.work);
		color.add(Color.YELLOW);
		color.add(Color.GREEN);

		chgeWM = new ChangeWeekMonth();
		init();

	}

	private void init() {
		LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		setLayoutParams(layoutParams);
		View history = View.inflate(mContext, R.layout.history, null);
		history.setLayoutParams(layoutParams);
		addView(history);

		tvHistoryDay = (TextView) findViewById(R.id.tv_history_day);
		tvHistoryWeek = (TextView) findViewById(R.id.tv_history_week);
		tvHistoryMonth = (TextView) findViewById(R.id.tv_history_month);

		llHistoryContent = (LinearLayout) findViewById(R.id.ll_history_main_content);
		llHistoryDay = LinearLayout.inflate(mContext, R.layout.calendar, null);
		llHistoryContent.addView(llHistoryDay);

		calendarView = (CalendarView) llHistoryDay.findViewById(R.id.myCalendarView);
		
		calendarView.setDate(calendarView.getDate());
//		calendarView.setOnDateChangeListener(new OnDateClickListener());
		// calendarView.setOnCellTouchListener(this); // ,

		// 此方法是自定義的監聽事件，與OnClickListener方法的引用相識，
		// tvDayDate = (TextView) findViewById(R.id.tv_date);
		// tvDayDate.setText(calendarView.getYear() + "-" +
		// String.format("%1$02d", calendarView.getMonth() + 1));
		// imMonthLeft = (ImageButton) findViewById(R.id.ib_left);
		// imMonthRight = (ImageButton) findViewById(R.id.ib_right);
		// ！！！注意不是重新再find id,应直接引用原来已声明的
		// llContent =
		// (LinearLayout)llMainContent.findViewById(R.id.ll_main_content);

		llHistoryWeek = LinearLayout.inflate(mContext, R.layout.history_week,null);
		imWeekPieChart = (ImageButton) llHistoryWeek.findViewById(R.id.im_week_piechart);
		imWeekLineChart = (ImageButton) llHistoryWeek.findViewById(R.id.im_week_linechart);
		llChange = (LinearLayout) llHistoryWeek.findViewById(R.id.ll_change);
		ibWLast = (ImageButton) llHistoryWeek.findViewById(R.id.ib_w_last);
		ibWNext = (ImageButton) llHistoryWeek.findViewById(R.id.ib_w_next);
		tvWLdate = (TextView) llHistoryWeek.findViewById(R.id.tv_w_ldate);
		tvWRdate = (TextView) llHistoryWeek.findViewById(R.id.tv_w_rdate);
		llWMItem = View.inflate(mContext, R.layout.history_week_item, null);
		llWMContent = (LinearLayout) llWMItem.findViewById(R.id.ll_wm_content);
		llChange.addView(llWMItem);
		chgeWM.isWeek = true;
		chgeWM.change(date);
		setWeekPieChart(chgeWM.strWdate);

		llHistoryMonth = LinearLayout.inflate(mContext, R.layout.history_month,
				null);
		imMonthPieChart = (ImageButton) llHistoryMonth
				.findViewById(R.id.im_month_piechart);
		imMonthLineChart = (ImageButton) llHistoryMonth
				.findViewById(R.id.im_month_linechart);
		llChangeMonth = (LinearLayout) llHistoryMonth
				.findViewById(R.id.ll_change_month);
		// llMonthContent = (LinearLayout) llHistoryMonth
		// .findViewById(R.id.ll_month_content);
		llMonthLine = View.inflate(mContext, R.layout.history_month_linechart,
				null);
		// lineChartView2 =
		// (LineChartView)llMonthLine.findViewById(R.id.linechart);
		llScrollMonth = (LinearLayout) llMonthLine
				.findViewById(R.id.ll_scroll_month);

		llMonthItem = View.inflate(mContext, R.layout.history_month_item, null);
		llMonthContent = (LinearLayout) llMonthItem
				.findViewById(R.id.ll_month_content);
		tvMLdate = (TextView) llHistoryMonth.findViewById(R.id.tv_m_ldate);
		tvMRdate = (TextView) llHistoryMonth.findViewById(R.id.tv_m_rdate);
		llChangeMonth.addView(llMonthItem);
		setMonthPieChart();

		tvHistoryDay.setOnClickListener(new DWMOnClickListener());
		tvHistoryWeek.setOnClickListener(new DWMOnClickListener());
		tvHistoryMonth.setOnClickListener(new DWMOnClickListener());

		// imMonthLeft.setOnClickListener(new
		// CalendarMonthBntOnClickListener());
		// imMonthRight.setOnClickListener(new
		// CalendarMonthBntOnClickListener());

		imWeekPieChart.setOnClickListener(new WeekOnClickListener());
		imWeekLineChart.setOnClickListener(new WeekOnClickListener());
		ibWLast.setOnClickListener(new ChangeWeekOnClickListener());
		ibWNext.setOnClickListener(new ChangeWeekOnClickListener());

		imMonthPieChart.setOnClickListener(new MonthOnClickListener());
		imMonthLineChart.setOnClickListener(new MonthOnClickListener());

		tvHistoryDay.performClick();
		// tvHistoryWeek.performClick();
		// tvHistoryMonth.performClick();
	}

	private class OnDateClickListener implements OnDateChangeListener{

		public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
			// TODO Auto-generated method stub
			clickdate = (year + "-" + (month + 1) + "-" + day);
			clickmonth = month + 1;
			clickday = day;

			Calendar calendar = Calendar.getInstance();
			if (calendar.get(Calendar.YEAR) < year
					|| (calendar.get(Calendar.MONTH) + 1 < clickmonth)
					|| (calendar.get(Calendar.DAY_OF_MONTH) < clickday && calendar
							.get(Calendar.MONTH) + 1 == clickmonth)) {
			}else{
				mContext.content.removeAllViews();
				mContext.resetView();
				mContext.llToday.setBackgroundResource(R.drawable.menu_today2);
				mContext.todayLayout.todayDate.setText(clickdate);
				mContext.todayLayout.tvToday.setText("");
				mContext.todayLayout.date = clickdate;
				mContext.todayLayout.setCircle();
				mContext.todayLayout.setPieChart();
				mContext.todayLayout.setListData();
				mContext.todayLayout.init();
				System.out.println(mContext.todayLayout.date + "   a");
				mContext.content.addView(mContext.todayLayout);

			}
		}
		
	}
	
	
	private class DWMOnClickListener implements OnClickListener {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.tv_history_day:
				resetViewbg();
				tvHistoryDay.setBackgroundResource(R.drawable.day2);
				llHistoryContent.removeAllViews();
				llHistoryContent.addView(llHistoryDay);
				break;
			case R.id.tv_history_week:
				resetViewbg();
				tvHistoryWeek.setBackgroundResource(R.drawable.week2);
				llHistoryContent.removeAllViews();
				llHistoryContent.addView(llHistoryWeek);
				imWeekPieChart.performClick();
				break;
			case R.id.tv_history_month:
				resetViewbg();
				tvHistoryMonth.setBackgroundResource(R.drawable.month2);
				// tvMLdate.setText(chgeWM.startMday); //！！！ 不用再赋值，否则会获取到week调用的
				// tvMRdate.setText(chgeWM.endMday);
				llHistoryContent.removeAllViews();
				llHistoryContent.addView(llHistoryMonth);
				break;
			default:
				break;
			}
		}
	}

	private void resetViewbg(){
		tvHistoryDay.setBackgroundResource(R.drawable.day);
		tvHistoryWeek.setBackgroundResource(R.drawable.week);
		tvHistoryMonth.setBackgroundResource(R.drawable.month);
	}
	// private class CalendarMonthBntOnClickListener implements OnClickListener
	// {
	//
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// if (imMonthLeft == v) {
	// calendarView.previousMonth();
	// tvDayDate.setText(calendarView.getYear() + "-" + String.format("%1$02d",
	// calendarView.getMonth() + 1));
	// } else {
	// calendarView.nextMonth();
	// tvDayDate.setText(calendarView.getYear() + "-" + String.format("%1$02d",
	// calendarView.getMonth() + 1));
	// }
	// }
	// }
	//
	// // ，或者定义一个类来监听
	// public void onTouch(Cell cell) {
	// if (cell.mPaint.getColor() == Color.GRAY) {
	// // 这是上月的
	// calendarView.previousMonth();
	// tvDayDate.setText(calendarView.getYear() + "-" + String.format("%1$02d",
	// calendarView.getMonth() + 1));
	// } else if (cell.mPaint.getColor() == Color.LTGRAY) {
	// // 下月的
	// calendarView.nextMonth();
	// tvDayDate.setText(calendarView.getYear() + "-" + String.format("%1$02d",
	// calendarView.getMonth() + 1));
	// } else { // 本月的
	// // ，监听Test中pick被点击的事件！！！
	// Intent ret = new Intent();
	// ret.putExtra("year", calendarView.getYear());
	// ret.putExtra("month", calendarView.getMonth());
	// ret.putExtra("day", cell.getDayOfMonth());
	// // 在此让当前的View 重绘一次
	// clickdate = (calendarView.getYear() + "-" + (1 + calendarView.getMonth())
	// + "-" + cell.getDayOfMonth());
	// clickmonth = 1 + calendarView.getMonth();
	// clickday = cell.getDayOfMonth();
	// Rect ecBounds;
	// ecBounds = cell.getBound();
	// calendarView.getDate();
	// calendarView.mDecoraClick.setBounds(ecBounds);
	// calendarView.invalidate();
	//
	// Calendar calendar = Calendar.getInstance();
	// if (calendar.get(Calendar.YEAR) < calendarView.getYear()
	// || (calendar.get(Calendar.MONTH) + 1 < clickmonth)
	// || (calendar.get(Calendar.DAY_OF_MONTH) < clickday &&
	// calendar.get(Calendar.MONTH) + 1 == clickmonth)) {
	// } else {
	// mContext.content.removeAllViews();
	// mContext.resetView();
	// mContext.llToday.setBackgroundResource(R.drawable.menu_today2);
	// mContext.todayLayout.todayDate.setText(clickdate);
	// mContext.todayLayout.tvToday.setText("");
	// mContext.todayLayout.date = clickdate;
	// mContext.todayLayout.setCircle();
	// mContext.todayLayout.setPieChart();
	// mContext.todayLayout.setListData();
	// mContext.todayLayout.init();
	// System.out.println(mContext.todayLayout.date + "   a");
	// mContext.content.addView(mContext.todayLayout);
	// }
	//
	// return;
	// }
	// Handler mHandler = new Handler();
	// mHandler.post(new Runnable() {
	// public void run() {
	// tvDayDate.setText(calendarView.getYear() + "-" + String.format("%1$02d",
	// calendarView.getMonth() + 1));
	// }
	// });
	// }

	private class WeekOnClickListener implements OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (imWeekPieChart == v) {
				imWeekPieChart
						.setBackgroundResource(R.drawable.today_pie_chart3);
				imWeekLineChart
						.setBackgroundResource(R.drawable.today_line_chart);
				isPieClicked = true;
				llChange.removeAllViews();
				llChange.addView(llWMItem);
				chgeWM.isWeek = true; // ,注意要重新设
				chgeWM.change(date);
				setWeekPieChart(chgeWM.strWdate);
			} else {
				imWeekPieChart
						.setBackgroundResource(R.drawable.today_pie_chart);
				imWeekLineChart
						.setBackgroundResource(R.drawable.today_line_chart2);
				isPieClicked = false;
				chgeWM.strWdays.clear();
				chgeWM.isWeek = true;
				chgeWM.change(date);
				setLineChart(chgeWM.strWdate);
				lineChartView = new LineChartView(mContext);
				lineChartView.setItem(allItem, color, chgeWM.strWdays);
				lineChartView.invalidate();
				llChange.removeAllViews();
				llChange.addView(lineChartView);
			}
		}
	}

	private class MonthOnClickListener implements OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (imMonthPieChart == v) {
				imMonthPieChart
						.setBackgroundResource(R.drawable.today_pie_chart3);
				imMonthLineChart
						.setBackgroundResource(R.drawable.today_line_chart);
				llChangeMonth.removeAllViews();
				llChangeMonth.addView(llMonthItem);
				chgeWM.change(mdate);
				chgeWM.isWeek = false;
				setWeekPieChart(chgeWM.strMdate);
			} else if (v == imMonthLineChart) {
				imMonthPieChart
						.setBackgroundResource(R.drawable.today_pie_chart);
				imMonthLineChart
						.setBackgroundResource(R.drawable.today_line_chart2);
				chgeWM.isWeek = false;
				chgeWM.strMdays.clear();
				chgeWM.change(mdate);
				setLineChart(chgeWM.strMdate);
				System.out.println(chgeWM.strMdays);
				lineChartView2 = new LineChartView(mContext);
				lineChartView2.invalidate();
				int minHeight = (getMeasuredWidth() - 60) / 7;
				lineChartView2.setMinimumWidth(minHeight * 31);
				lineChartView2.setMinimumHeight(getMeasuredHeight());
				lineChartView2.setItem(allItem, color, chgeWM.strMdays);
				lineChartView2.invalidate();
				llChangeMonth.removeAllViews();
				llScrollMonth.removeAllViews();
				llChangeMonth.addView(llMonthLine);
				llScrollMonth.addView(lineChartView2);
				lineChartView2.invalidate();
				// tvHistoryMonth.performClick();
			}
		}
	}

	private class ChangeWeekOnClickListener implements OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (ibWLast == v) {
				chgeWM.isWeek = true;
				chgeWM.change(date); // 注意要重新调用，否则会获取到Month调用的数据
				// 判断当前的开始日期是否与四周前的相同
				if (chgeWM.strWdate.get(chgeWM.strWdate.size() - 1).equals(
						chgeWM.preFourWeeks(ldate))) {
				} else {
					date = chgeWM.datePreDay(chgeWM.strWdate
							.get(chgeWM.strWdate.size() - 1));
					System.out.println("lastdate:" + date);
					llChange.removeAllViews();
					llChange.addView(llWMItem);
					if (isPieClicked) {
						chgeWM.isWeek = true; // ,注意要重新设
						chgeWM.change(date);
						setWeekPieChart(chgeWM.strWdate);
					} else {
						chgeWM.isWeek = true;
						chgeWM.strWdays.clear();
						chgeWM.change(date);
						setLineChart(chgeWM.strWdate);
						lineChartView = new LineChartView(mContext);
						lineChartView.setItem(allItem, color, chgeWM.strWdays);
						lineChartView.invalidate();
						llChange.removeAllViews();
						llChange.addView(lineChartView);
						// tvHistoryWeek.performClick();
						// tvHistoryWeek.performClick();
					}
				}
			} else if (ibWNext == v) {
				chgeWM.isWeek = true;
				chgeWM.change(date);
				// ！！！注意判断字符串(是一个对象)是否相等，一定要用equals,不能用==（一直是false）
				if (chgeWM.strWdate.get(0).equals(mdate)) {
				} else {
					date = chgeWM.nextSevendays(chgeWM.strWdate.get(0));
					System.out.println(date + "beDone");
					llChange.removeAllViews();
					llChange.addView(llWMItem);
					if (isPieClicked) {
						chgeWM.isWeek = true; // ,注意要重新设
						chgeWM.change(date);
						setWeekPieChart(chgeWM.strWdate);
					} else {
						chgeWM.isWeek = true;
						chgeWM.strWdays.clear();
						chgeWM.change(date);
						setLineChart(chgeWM.strWdate);
						lineChartView = new LineChartView(mContext);
						lineChartView.setItem(allItem, color, chgeWM.strWdays);
						lineChartView.invalidate();
						llChange.removeAllViews();
						llChange.addView(lineChartView);
						// tvHistoryWeek.performClick();
						// tvHistoryWeek.performClick();
					}
				}
			}
		}
	}

	private void setWeekPieChart(List<String> strdate) {
		rest = 0;
		relax = 0;
		study = 0;
		work = 0;
		// chgeWM.isWeek = true; // ,注意要重新设
		// chgeWM.change(date);
		for (int i = 0; i < strdate.size(); i++) {
			statusData = FileService.getStatusData(strdate.get(i));
			System.out.println("No" + i + "  " + strdate.get(i));
			if (statusData != null) {
				Map<String, Integer> status = statusData.getStatus();
				Iterator<String> iterator = status.keySet().iterator();
				float[] mHour = new float[100];
				mHour[0] = 0;
				int t = 1;
				List<Integer> kind = new LinkedList<Integer>();
				kind.add(1);
				while (iterator.hasNext()) {
					String time = (String) iterator.next();
					String[] my = time.split(":");
					float hour = (float) Integer.parseInt(my[0]); // ！！！
					float min = Integer.parseInt(my[1]);
					mHour[t] = (float) (hour + min / 60); // ！！！括号
					kind.add(status.get(time));
					t++;
				}
				mHour[t] = 24;
				for (int j = 0, k = 1; j < kind.size(); j++, k++) {
					switch (kind.get(j)) {
					case 1:
						rest += mHour[k] - mHour[k - 1];
						break;
					case 2:
						relax += mHour[k] - mHour[k - 1];
						break;
					case 3:
						study += mHour[k] - mHour[k - 1];
						break;
					case 4:
						work += mHour[k] - mHour[k - 1];
						break;
					case 5:
						break;
					default:
						break;
					}
				}
			} else {
			}
		}
		allTime = rest + relax + study + work;
		restper = (int) (360 * rest / allTime);
		relaxper = (int) (360 * relax / allTime);
		studyper = (int) (360 * study / allTime);
		workper = (int) (360 * work / allTime);
		int[] percent = new int[] { restper, relaxper, studyper, workper };
		PieView pieView = new PieView(mContext, colors, shade_colors, percent);
		llWMContent.removeAllViews();
		llWMContent.addView(pieView);

		tvWLdate.setText(chgeWM.startWday);
		tvWRdate.setText(chgeWM.endWday);
		System.out.println(chgeWM.startWday + "___" + chgeWM.endWday);
	}

	private void setMonthPieChart() {
		rest = 0;
		relax = 0;
		study = 0;
		work = 0;
		chgeWM.isWeek = false;
		chgeWM.change(mdate);
		for (int i = 0; i < chgeWM.strMdate.size(); i++) {
			statusData = FileService.getStatusData(chgeWM.strMdate.get(i));
			System.out.println("No" + i + "  " + chgeWM.strMdate.get(i));
			if (statusData != null) {
				Map<String, Integer> status = statusData.getStatus();
				Iterator<String> iterator = status.keySet().iterator();
				float[] mHour = new float[100];
				mHour[0] = 0;
				int t = 1;
				List<Integer> kind = new LinkedList<Integer>();
				kind.add(1);
				while (iterator.hasNext()) {
					String time = (String) iterator.next();
					String[] my = time.split(":");
					float hour = (float) Integer.parseInt(my[0]); // ！！！
					float min = Integer.parseInt(my[1]);
					mHour[t] = (float) (hour + min / 60); // ！！！括号
					kind.add(status.get(time));
					t++;
				}
				mHour[t] = 24;
				for (int j = 0, k = 1; j < kind.size(); j++, k++) {
					switch (kind.get(j)) {
					case 1:
						rest += mHour[k] - mHour[k - 1];
						break;
					case 2:
						relax += mHour[k] - mHour[k - 1];
						break;
					case 3:
						study += mHour[k] - mHour[k - 1];
						break;
					case 4:
						work += mHour[k] - mHour[k - 1];
						break;
					case 5:
						break;
					default:
						break;
					}
				}
			} else {
			}
		}
		allTime = rest + relax + study + work;
		restper = (int) (360 * rest / allTime);
		relaxper = (int) (360 * relax / allTime);
		studyper = (int) (360 * study / allTime);
		workper = (int) (360 * work / allTime);
		int[] percent = new int[] { restper, relaxper, studyper, workper };
		PieView pieView = new PieView(mContext, colors, shade_colors, percent);
		llMonthContent.removeAllViews();
		llMonthContent.addView(pieView);

		tvMLdate.setText(chgeWM.startMday);
		tvMRdate.setText(chgeWM.endMday);
		System.out.println(chgeWM.startMday + "___" + chgeWM.endMday);
	}

	private void setLineChart(List<String> strdate) {
		mrelax = new LinkedList<Float>();
		mrest = new LinkedList<Float>();
		mstudy = new LinkedList<Float>();
		mwork = new LinkedList<Float>();
		allItem = new LinkedList<List<Float>>();
		for (int i = 0; i < strdate.size(); i++) {
			rest = 0;
			relax = 0;
			study = 0;
			work = 0;
			statusData = FileService.getStatusData(strdate.get(i));
			if (statusData != null) {
				Map<String, Integer> status = statusData.getStatus();
				Iterator<String> iterator = status.keySet().iterator();
				float[] mHour = new float[100];
				mHour[0] = 0;
				int t = 1;
				List<Integer> kind = new LinkedList<Integer>();
				kind.add(1);
				while (iterator.hasNext()) {
					String time = (String) iterator.next();
					String[] my = time.split(":");
					float hour = (float) Integer.parseInt(my[0]); // ！！！
					float min = Integer.parseInt(my[1]);
					mHour[t] = (float) (hour + min / 60); // ！！！括号
					kind.add(status.get(time));
					t++;
				}
				if (strdate.get(i).equals(ldate)) {
					kind.remove(kind.size() - 1);
				} else {
					mHour[t] = 24;
				}
				for (int j = 0, k = 1; j < kind.size(); j++, k++) {
					switch (kind.get(j)) {
					case 1:
						rest += mHour[k] - mHour[k - 1];
						break;
					case 2:
						relax += mHour[k] - mHour[k - 1];
						break;
					case 3:
						study += mHour[k] - mHour[k - 1];
						break;
					case 4:
						work += mHour[k] - mHour[k - 1];
						break;
					case 5:
						break;
					default:
						break;
					}
				}
			} else {
			}
			mrest.add(rest);
			mrelax.add(relax);
			mstudy.add(study);
			mwork.add(work);
		}
		// ，注意位置！！！
		allItem.add(mrest);
		allItem.add(mrelax);
		allItem.add(mstudy);
		allItem.add(mwork);
		tvWLdate.setText(chgeWM.startWday);
		tvWRdate.setText(chgeWM.endWday);
	}
}
