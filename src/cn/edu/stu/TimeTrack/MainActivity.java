package cn.edu.stu.TimeTrack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.stu.TimeTrack.Layout.HistoryLayout;
import cn.edu.stu.TimeTrack.Layout.MessageLayout;
import cn.edu.stu.TimeTrack.Layout.SettingLayout;
import cn.edu.stu.TimeTrack.Layout.TodayLayout;
import cn.edu.stu.TimeTrack.utils.FileManager;

public class MainActivity extends Activity {
	public LinearLayout content;
	public LinearLayout llToday;
	private LinearLayout llHistory;
	private LinearLayout llActicles;
	private LinearLayout llSetting;
	private TextView tvUpdate;
	public TodayLayout todayLayout;
	private HistoryLayout historyLayout;
	private MessageLayout messageLayout;
	private SettingLayout settingLayout;

	int[] colors;
	float[] startAngle;
	float[] sweepAngle;

	private long exitTime = 0;
	
	public FeedbackAgent agent;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		// 隐藏通知栏
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);

		Intent intent = new Intent();
		intent.setClass(this, UnlockService.class);
		startService(intent);

		init();

		com.umeng.common.Log.LOG = true;
		
		 agent = new FeedbackAgent(this);
	     agent.sync();  //设置回复信息后提醒
	     //	如果想程序启动时自动检查是否需要更新， 把下面代码加在Activity 的onCreate()函数里。
	     UmengUpdateAgent.update(this);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// 一般可以在OptionsMenu中添加一个Item,用于作为反馈界面的入口
		menu.add(0, 0, 0, "退出");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0: // 调用反馈提供的接口，进入反馈界面
			finish();
			return true;
		default:
		}
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void init() {
		content = (LinearLayout) findViewById(R.id.ll_main_content);
		todayLayout = new TodayLayout(this);
		content.addView(todayLayout);

		llToday = (LinearLayout) this.findViewById(R.id.ll_menu1);
		llToday.setBackgroundResource(R.drawable.menu_today2);

		llHistory = (LinearLayout) findViewById(R.id.ll_menu2);
		llActicles = (LinearLayout) findViewById(R.id.ll_menu3);
		llSetting = (LinearLayout) findViewById(R.id.ll_menu4);
		tvUpdate = (TextView) findViewById(R.id.tv_main_update);

		historyLayout = new HistoryLayout(this);
		messageLayout = new MessageLayout(this);
		settingLayout = new SettingLayout(this);

		llToday.setOnClickListener(new onMenuClickListener());
		llHistory.setOnClickListener(new onMenuClickListener());
		llActicles.setOnClickListener(new onMenuClickListener());
		llSetting.setOnClickListener(new onMenuClickListener());
		
		copy();
	}

	public void copy() {
		String[] fileList = FileManager.getFileList("article");
		if (fileList.length < 1) {
			AssetManager assetManager = getAssets();
			try {
				String[] list = assetManager.list("article");
				for (int i = 0; i < list.length; i++) {
					InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open("article/" + list[i]));
					BufferedReader bufReader = new BufferedReader(inputReader);
					String line = "";
					String result = "";
					while ((line = bufReader.readLine()) != null)
						result += line;
					FileManager.saveArticle(result, list[i].substring(0, list[i].length() - 4));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setUpdateMarkVisibility(int visibility) {
		tvUpdate.setVisibility(visibility);
	}

	private class UpdateListener implements UmengUpdateListener {
		public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
			switch (updateStatus) {
			case 0:
				setUpdateMarkVisibility(View.VISIBLE);
				settingLayout.setUpdateMarkVisibility(View.VISIBLE);
				break;
			}
		}
	}

	private class onMenuClickListener implements OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			resetView();
			switch (v.getId()) {
			case R.id.ll_menu1:
//				// 设置当点击历史的某天时，再点击今天，返回到今天的数据
//				Calendar calendar = Calendar.getInstance();
//				todayLayout.date = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
//						+ calendar.get(Calendar.DAY_OF_MONTH);
//				todayLayout.todayDate.setText(todayLayout.date);
//				todayLayout.tvToday.setText("今天");
//				todayLayout.setCircle();
//				todayLayout.setPieChart();
//				todayLayout.setListData();
//				content.removeAllViews();
				
				llToday.setBackgroundResource(R.drawable.menu_today2);
				content.addView(todayLayout);
				todayLayout.ibCircle.performClick();
				break;
			case R.id.ll_menu2:
				llHistory.setBackgroundResource(R.drawable.menu_history2);
				content.addView(historyLayout);
				break;
			case R.id.ll_menu3:
				llActicles.setBackgroundResource(R.drawable.menu_message2);
				content.addView(messageLayout);
				break;
			case R.id.ll_menu4:
				llSetting.setBackgroundResource(R.drawable.menu_setting2);
				content.addView(settingLayout);
				break;
			default:
				break;
			}
		}
	}

	public void resetView() {
		content.removeAllViews();

		llToday.setBackgroundColor(Color.TRANSPARENT);
		llHistory.setBackgroundColor(Color.TRANSPARENT);
		llActicles.setBackgroundColor(Color.TRANSPARENT);
		llSetting.setBackgroundColor(Color.TRANSPARENT);
		llToday.setBackgroundResource(R.drawable.menu_today);
		llHistory.setBackgroundResource(R.drawable.menu_history);
		llActicles.setBackgroundResource(R.drawable.menu_message);
		llSetting.setBackgroundResource(R.drawable.menu_setting);
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
