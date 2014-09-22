package cn.edu.stu.TimeTrack;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class UnlockService extends Service {

	private UnlockReceiver lockReceiver;

	@Override
	public IBinder onBind(Intent arg0) {
		// System.out.println("Service onBind");
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// System.out.println("Service onCreate");

		lockReceiver = new UnlockReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.SCREEN_ON");
//		filter.addAction("android.intent.action.SCREEN_OFF");
		filter.addAction("android.intent.action.BOOT_COMPLETED");
		registerReceiver(lockReceiver, filter);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// System.out.println("Service onStartCommand");

		return super.onStartCommand(intent, flags, startId);

	}

	@Override
	public void onDestroy() {
		// System.out.println("Service onDestroy");
		super.onDestroy();
	}

}
