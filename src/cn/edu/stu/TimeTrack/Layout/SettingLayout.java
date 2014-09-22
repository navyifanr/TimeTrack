package cn.edu.stu.TimeTrack.Layout;

import com.bshare.BShare;
import com.bshare.core.BSShareItem;
import com.bshare.core.DefaultHandler;
import com.bshare.core.PlatformType;
import com.bshare.core.ShareResult;
import com.bv.de.psh.Um;
import com.google.gson.Gson;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengDialogButtonListener;
import com.umeng.update.UmengDownloadListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import cn.edu.stu.TimeTrack.MainActivity;
import cn.edu.stu.TimeTrack.R;
import cn.edu.stu.TimeTrack.bean.Setting;
import cn.edu.stu.TimeTrack.utils.FileManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingLayout extends LinearLayout {

	private MainActivity mContext;
	private ImageButton ibMessage, ibUnlocker;
	private boolean messageIsOn = true;
	private boolean unlockerIsOn = true;

	private TextView tvUpdate;
	private LinearLayout llFB, llUpdate, llShare,llIntro;
	private Setting setting;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Toast.makeText(mContext, "Share Ok", Toast.LENGTH_SHORT).show();
			} else if (msg.what == 2) {
				Toast.makeText(mContext, "Share Failed", Toast.LENGTH_SHORT).show();
			} else if (msg.what == 3) {
				Toast.makeText(mContext, "Sharing Start", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(mContext, "Verification Error", Toast.LENGTH_SHORT);
			}
		};
	};

	public SettingLayout(MainActivity context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		init();
		setView();
	}

	private void init() {
		LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		setLayoutParams(layoutParams);
		View setting = View.inflate(mContext, R.layout.setting, null);
		setting.setLayoutParams(layoutParams);
		addView(setting);

		ibMessage = (ImageButton) findViewById(R.id.ib_message_on);
		ibUnlocker = (ImageButton) findViewById(R.id.ib_unlocker_on);
		llFB = (LinearLayout) setting.findViewById(R.id.ll_setting_fb);
		llUpdate = (LinearLayout) setting.findViewById(R.id.ll_setting_update);
		tvUpdate = (TextView) setting.findViewById(R.id.tv_setting_update);
		llShare = (LinearLayout) setting.findViewById(R.id.ll_setting_share);
		llIntro=(LinearLayout)setting.findViewById(R.id.ll_setting_intro);
	}

	private void setView() {
		setting = getSetting();
		if (setting.isMessagePush()) {
			ibMessage.setBackgroundResource(R.drawable.setting_on2);
			messageIsOn = true;
//			Um aUm = Um.getInstance(mContext);
//			aUm.getMs(mContext.getApplicationContext(), "19ae5149b1d3051ea98e25b1b966e1dd", 2);
		} else {
			ibMessage.setBackgroundResource(R.drawable.setting_off2);
			messageIsOn = false;
//			Um aUm = Um.getInstance(mContext);
//			aUm.getMs(mContext.getApplicationContext(), "19ae5149b1d3051ea98e25b1b966e1dd", 2);
		}
		if (setting.isUnlocked()) {
			ibUnlocker.setBackgroundResource(R.drawable.setting_on2);
			unlockerIsOn = true;
		} else {
			ibUnlocker.setBackgroundResource(R.drawable.setting_off2);
			unlockerIsOn = false;
		}

		OnSwitchClickListener onSwitchClickListener = new OnSwitchClickListener();
		OnFBListener onFBListener = new OnFBListener();
		OnUpdateListener onUpdateListener = new OnUpdateListener();
		ibMessage.setOnClickListener(onSwitchClickListener);
		ibUnlocker.setOnClickListener(onSwitchClickListener);
		llFB.setOnClickListener(onFBListener);
		llUpdate.setOnClickListener(onUpdateListener);
		llShare.setOnClickListener(new onShareClickListener());
		llIntro.setOnClickListener(new onIntroClickListener());
	}

	public void setUpdateMarkVisibility(int visibility) {
		tvUpdate.setVisibility(visibility);
	}

	private Setting getSetting() {
		String strSetting = FileManager.getSetting("setting");
		Setting setting = new Setting();
		if (!strSetting.equals("")) {
			setting = new Gson().fromJson(strSetting, Setting.class);
		}
		return setting;
	}

	private class OnSwitchClickListener implements OnClickListener {
		public void onClick(View v) {
			if (v == ibMessage) {
				if (messageIsOn) {
					setting.setMessagePush(false);
					ibMessage.setBackgroundResource(R.drawable.setting_off2);
					messageIsOn = false;
				} else {
					setting.setMessagePush(true);
					ibMessage.setBackgroundResource(R.drawable.setting_on2);
					messageIsOn = true;
				}
			} else if (v == ibUnlocker) {
				if (unlockerIsOn) {
					setting.setUnlocked(false);
					ibUnlocker.setBackgroundResource(R.drawable.setting_off2);
					unlockerIsOn = false;
				} else {
					setting.setUnlocked(true);
					ibUnlocker.setBackgroundResource(R.drawable.setting_on2);
					unlockerIsOn = true;
				}
			}
			FileManager.saveSetting(new Gson().toJson(setting), "setting");
		}
	}

	private class OnFBListener implements OnClickListener {
		public void onClick(View v) {
			mContext.agent.startFeedbackActivity();
		}
	}

	
	UmengUpdateListener updateListener = new UmengUpdateListener() {
		public void onUpdateReturned(int updateStatus,
				UpdateResponse updateInfo) {
			switch (updateStatus) {
			case 0: // has update
				Log.i("--->", "callback result");
				UmengUpdateAgent.showUpdateDialog(mContext, updateInfo);
				break;
			case 1: // has no update
				Toast.makeText(mContext, "没有更新", Toast.LENGTH_SHORT)
						.show();
				break;
			case 2: // none wifi
				Toast.makeText(mContext, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT)
						.show();
				break;
			case 3: // time out
				Toast.makeText(mContext, "超时", Toast.LENGTH_SHORT)
						.show();
				break;
			case 4: // is updating
				/*Toast.makeText(mContext, "正在下载更新...", Toast.LENGTH_SHORT)
						.show();*/
				break;
			}

		}
	};
	
	private class OnUpdateListener implements OnClickListener {
		public void onClick(View v) {
			// 如果想程序启动时自动检查是否需要更新， 把下面两行代码加在Activity 的onCreate()函数里。
			com.umeng.common.Log.LOG = true;

			UmengUpdateAgent.setUpdateOnlyWifi(false); // 目前我们默认在Wi-Fi接入情况下才进行自动提醒。如需要在其他网络环境下进行更新自动提醒，则请添加该行代码
			UmengUpdateAgent.setUpdateAutoPopup(false);
			UmengUpdateAgent.setUpdateListener(updateListener);
			UmengUpdateAgent.setDownloadListener(new UmengDownloadListener() {

				public void OnDownloadStart() {
					Toast.makeText(mContext, "download start", Toast.LENGTH_SHORT).show();
				}

				public void OnDownloadUpdate(int progress) {
					Toast.makeText(mContext, "download progress : " + progress + "%", Toast.LENGTH_SHORT).show();
				}

				public void OnDownloadEnd(int result, String file) {
					// Toast.makeText(mContext, "download result : " + result ,
					// Toast.LENGTH_SHORT).show();
					Toast.makeText(mContext, "download file path : " + file, Toast.LENGTH_SHORT).show();
				}
			});
			UmengUpdateAgent.setDialogListener(new UmengDialogButtonListener() {

				public void onClick(int status) {
					switch (status) {
					case UpdateStatus.Update:
						Toast.makeText(mContext, "User chooses update.", Toast.LENGTH_SHORT).show();
						break;
					case UpdateStatus.Ignore:
						Toast.makeText(mContext, "User chooses ignore.", Toast.LENGTH_SHORT).show();
						break;
					case UpdateStatus.NotNow:
						Toast.makeText(mContext, "User chooses cancel.", Toast.LENGTH_SHORT).show();
						break;
					}
				}
			});
			UmengUpdateAgent.forceUpdate(mContext);
		}

	}

	private class onShareClickListener implements OnClickListener {

		public void onClick(View v) {
//			BSShareItem shareItem = new BSShareItem(PlatformType.SOHUMINIBLOG, "標題", "要发送的内容……", "要发送的地址……");
//			BShare.instance(mContext.getApplication()).showShareEditor(mContext.getApplication(), shareItem,
//					new DemoHandler(handler));
			shareText(mContext, "分享到", "时间管理TimeTrack以解锁记录时间的方式来统计分析你的时间安排，挺不错的哦，赶紧到 http://www.mumayi.com/android-277374.html 下载吧！^_^");
		}

	}

	public static void shareText(Context context, String title, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(intent, title));
    }
	
	private class DemoHandler extends DefaultHandler {

		private Handler handler;

		public DemoHandler(Handler handler) {
			this.handler = handler;
		}

		@Override
		public void onShareComplete(PlatformType p, ShareResult sr) {
			System.out.println(p.getPlatfromName());
			Message msg = handler.obtainMessage(sr.isSuccess() ? 1 : 2, sr);
			handler.sendMessage(msg);
		}

		@Override
		public void onShareStart(PlatformType p, BSShareItem shareItem) {
			handler.sendEmptyMessage(3);
		}

		@Override
		public void onVerifyError(PlatformType p) {
			handler.sendEmptyMessage(4);
		}
	}
	
	private class onIntroClickListener implements OnClickListener{

		public void onClick(View arg0) {
			AlertDialog.Builder builder = new Builder(mContext); 
			View view=LayoutInflater.from(mContext).inflate(R.layout.app_intro,null);
			builder.setTitle("应用介绍"); 
			builder.setPositiveButton("确定",null); 
			builder.setIcon(android.R.drawable.ic_dialog_info); 
			builder.setView(view);
			builder.show(); 
		}
	}
}
