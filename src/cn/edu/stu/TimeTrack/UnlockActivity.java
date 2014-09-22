package cn.edu.stu.TimeTrack;

import java.util.Calendar;

import com.umeng.analytics.MobclickAgent;

import cn.edu.stu.TimeTrack.Layout.UnlockLayout;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.TextView;

public class UnlockActivity extends Activity {
	
	private UnlockLayout unlockLayout;
	private TextView tvHour, tvMinute, tvDate, tvWeek;
	private int hour, minute, year, month, day, week;
	
	private Handler myHandler = new Handler (){
		public void handleMessage(Message msg){
			if(msg.obj.equals("FinishLockActivity")) {
				finish(); // 锁屏成功时，结束我们的Activity界面
			} else if (msg.obj.equals("RefreshTime")) {
				setTimeView();
			}
		}
	};
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		 隐藏通知栏
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//，屏蔽系统的解锁
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		setContentView(R.layout.unlock);
		
		init();
	}

	private void init() {
		unlockLayout = (UnlockLayout) findViewById(R.id.ll_unlock);
		unlockLayout.setUnlockHandler(myHandler);
		
		tvHour = (TextView) unlockLayout.findViewById(R.id.tv_unlock_hour);
		tvMinute = (TextView) unlockLayout.findViewById(R.id.tv_unlock_minute);
		tvDate = (TextView) unlockLayout.findViewById(R.id.tv_unlock_date);
		tvWeek = (TextView) unlockLayout.findViewById(R.id.tv_unlock_week);
		
		new Thread(new UnlockThread()).start();
	}

	private void setTimeView() {
		tvHour.setText(String.format("%1$02d", hour));
		tvMinute.setText(String.format("%1$02d", minute));
		tvDate.setText(year+"-"+month+"-"+day);
		switch (week) {
		case Calendar.SUNDAY:
			tvWeek.setText("星期天");
			break;
		case Calendar.MONDAY:
			tvWeek.setText("星期一");
			break;
		case Calendar.TUESDAY:
			tvWeek.setText("星期二");
			break;
		case Calendar.WEDNESDAY:
			tvWeek.setText("星期三");
			break;
		case Calendar.THURSDAY:
			tvWeek.setText("星期四");
			break;
		case Calendar.FRIDAY:
			tvWeek.setText("星期五");
			break;
		case Calendar.SATURDAY:
			tvWeek.setText("星期六");
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onAttachedToWindow() {
		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
		super.onAttachedToWindow();
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_HOME:
			break;
		case KeyEvent.KEYCODE_BACK:
			break;
		default:
			break;
		}
		return true;
	}
	
	private class UnlockThread implements Runnable {
		public void run() {
			while (true) {
			try {
				Thread.sleep(1000);
				Calendar calendar = Calendar.getInstance();
				hour = calendar.get(Calendar.HOUR_OF_DAY);
				minute = calendar.get(Calendar.MINUTE);
				year = calendar.get(Calendar.YEAR);
				month = calendar.get(Calendar.MONTH) + 1;
				day = calendar.get(Calendar.DAY_OF_MONTH);
				week = calendar.get(Calendar.DAY_OF_WEEK);
				Message msg = new Message();
				msg.obj = "RefreshTime";
				myHandler.sendMessage(msg);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			}
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
}