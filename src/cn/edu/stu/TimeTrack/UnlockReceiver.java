package cn.edu.stu.TimeTrack;

import com.google.gson.Gson;

import cn.edu.stu.TimeTrack.bean.Setting;
import cn.edu.stu.TimeTrack.utils.FileManager;
import android.app.KeyguardManager;
import android.app.Service;
import android.app.KeyguardManager.KeyguardLock;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

public class UnlockReceiver extends BroadcastReceiver {
	

	public void onReceive(Context context, Intent intent) {
		
		String strSetting = FileManager.getSetting("setting");
		Setting setting = new Setting();
		if (!strSetting.equals("")) {
			setting = new Gson().fromJson(strSetting, Setting.class);
		}
//		//,设置通话时或后，不弹出解锁界面
		if (setting.isUnlocked()) {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
			if (tm.getCallState() != TelephonyManager.CALL_STATE_RINGING && tm.getCallState() != TelephonyManager.CALL_STATE_OFFHOOK) {
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setClass(context, UnlockActivity.class);
				context.startActivity(intent);
			}
		}
		
//		KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Service.KEYGUARD_SERVICE);
//		KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("");
//		keyguardLock.disableKeyguard();
//
//		TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
//		if (tm.getCallState() != TelephonyManager.CALL_STATE_RINGING && tm.getCallState() != TelephonyManager.CALL_STATE_OFFHOOK) {
//			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			intent.setClass(context, UnlockActivity.class);
//			context.startActivity(intent);
//		}
//		}
	}
}