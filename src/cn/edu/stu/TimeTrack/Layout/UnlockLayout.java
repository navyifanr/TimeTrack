package cn.edu.stu.TimeTrack.Layout;

import java.util.Calendar;

import cn.edu.stu.TimeTrack.R;
import cn.edu.stu.TimeTrack.utils.FileService;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class UnlockLayout extends RelativeLayout {

	private ImageView ivUnlocker = null;
	private ImageView[] ivEmotes = new ImageView[5];
	private Bitmap dragBitmap = null; // ��ʼ��ͼƬ��קʱ��Bitmap����
	private Context mContext = null;
	private Handler unLockHandler = null; // ����Activityͨ�ŵ�Handler����
	private int x2;
	private int y2;
	private double width;
	private double ivUnlockerWidth, ivUnlockerHeight;
	private double[] ivEmotesWidth = new double[5];
	private double[] ivEmotesHeight = new double[5];
	private LayoutParams[] params = new LayoutParams[5];

	private double marginLeftAndRight = 0;
	private double marginBottom = 5;
	private double[] betas = new double[5];
	private double r;

	private int mLastMoveX = 1000; // ��ǰbitmapӦ�û��Ƶĵط� �� ��ʼֵΪ�㹻�󣬿�����Ϊ������
	private int mLastMoveY = 1000;
	private boolean isHit;
	private boolean isFinishLoad = false;

	public UnlockLayout(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public UnlockLayout(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		mContext = context;
		init();
	}

	public UnlockLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}

	// ��ʼ��ͼƬ��קʱ��Bitmap����
	private void init() {
		dragBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.unlocker2);

		betas[0] = 18 * Math.PI / 180;
		betas[1] = 54 * Math.PI / 180;
		betas[2] = 90 * Math.PI / 180;
		betas[3] = 54 * Math.PI / 180;
		betas[4] = 18 * Math.PI / 180;
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		ivUnlocker = (ImageView) findViewById(R.id.iv_unlock_locked);
		ivEmotes[0] = (ImageView) findViewById(R.id.iv_unlock1);
		ivEmotes[1] = (ImageView) findViewById(R.id.iv_unlock2);
		ivEmotes[2] = (ImageView) findViewById(R.id.iv_unlock3);
		ivEmotes[3] = (ImageView) findViewById(R.id.iv_unlock4);
		ivEmotes[4] = (ImageView) findViewById(R.id.iv_unlock5);
		super.onFinishInflate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mLastMoveX = (int) event.getX();
		mLastMoveY = (int) event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			return handleActionDownEvenet(event);
		case MotionEvent.ACTION_MOVE:
			return handleActionMoveEvent(event);
		case MotionEvent.ACTION_UP:
			return handleActionUpEvent(event);
		}
		return super.onTouchEvent(event);
	}

	// �����϶�ʱ��ͼƬ
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (!isFinishLoad) {
			width = getWidth();
			ivUnlockerWidth = ivUnlocker.getWidth();
			ivUnlockerHeight = ivUnlocker.getHeight();

			for (int i = 0; i < ivEmotesWidth.length; i++) {
				ivEmotesWidth[i] = ivEmotes[i].getWidth();
				ivEmotesHeight[i] = ivEmotes[i].getHeight();
				params[i] = (LayoutParams) ivEmotes[i].getLayoutParams();
			}

			r = (width - ivEmotesWidth[0]) / 2 - marginLeftAndRight;

			LayoutParams unlockerParams = (LayoutParams) ivUnlocker.getLayoutParams();
			unlockerParams.setMargins(0, 0, 0, (int) marginBottom);
			ivUnlocker.setLayoutParams(unlockerParams);

			params[0].setMargins(0, 0, (int) (r * Math.cos(betas[0]) - (ivUnlockerWidth + ivEmotesWidth[0]) / 2), (int) (marginBottom + r * Math.sin(betas[0]) - ivEmotesHeight[0] / 2));
			ivEmotes[0].setLayoutParams(params[0]);

			params[1].setMargins(0, 0, (int) (r * Math.cos(betas[1]) - (ivUnlockerWidth + ivEmotesWidth[1]) / 2), (int) (r * Math.sin(betas[1]) - ivEmotesHeight[1] / 2 - ivUnlockerHeight));
			ivEmotes[1].setLayoutParams(params[1]);

			params[2].setMargins(0, 0, 0, (int) (r - ivUnlockerHeight - ivEmotesHeight[2] / 2));
			ivEmotes[2].setLayoutParams(params[2]);

			params[3].setMargins((int) (r * Math.cos(betas[3]) - (ivUnlockerWidth + ivEmotesWidth[3]) / 2), 0, 0, (int) (r * Math.sin(betas[3]) - ivEmotesHeight[3] / 2 - ivUnlockerHeight));
			ivEmotes[3].setLayoutParams(params[3]);

			params[4].setMargins((int) (r * Math.cos(betas[4]) - (ivUnlockerWidth + ivEmotesWidth[4]) / 2), 0, 0, (int) (marginBottom + r * Math.sin(betas[4]) - ivEmotesHeight[4] / 2));
			ivEmotes[4].setLayoutParams(params[4]);
			
			x2 = ivUnlocker.getLeft() + ivUnlocker.getWidth() / 2;
			y2 = ivUnlocker.getTop() + ivUnlocker.getHeight() / 2;
			isFinishLoad = true;
		}
         
		if (isHit) {
			// �Ժ��ʵ�����ֵ���Ƹ�ͼƬ
			int drawXCor = mLastMoveX - dragBitmap.getWidth() / 2;
			int drawYCor = mLastMoveY - dragBitmap.getHeight() / 2;
			canvas.drawBitmap(dragBitmap, drawXCor, drawYCor, null);
		}
	}

	// ���������ǣ��Ƿ������ͼƬ�����Ƿ���Ҫ��ʼ�ƶ�
	private boolean handleActionDownEvenet(MotionEvent event) {
		Rect rect = new Rect();

		ivUnlocker.getHitRect(rect);
		isHit = rect.contains(mLastMoveX, mLastMoveY);
		if (isHit) {// ��ʼ��ק �����ظ�ͼƬ
			ivUnlocker.setVisibility(View.INVISIBLE);
			invalidate();
		}
		return isHit;
	}

	private boolean handleActionMoveEvent(MotionEvent event) {
		getHitNum();
		invalidate();
		return false;
	}

	// �ж��ɿ���ָʱ���Ƿ�ﵽĩβ�����Կ����� , �ǣ�����������ͨ��һ�����㷨ʹ����ˡ�
	private boolean handleActionUpEvent(MotionEvent event) {
		int hitNum = getHitNum();
		if (hitNum > -1) {
			dragBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.unlocker2);
			save(hitNum + 1);
			Toast.makeText(mContext, "�����ɹ�", 1000).show();
			virbate(); // ��һ��
			Message message = new Message();
			message.obj = "FinishLockActivity";
			message.arg1 = hitNum + 1;
			unLockHandler.sendMessage(message);
			return true;
		} else {
			dragBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.unlocker2);
			resetViewState();
			return false;
		}
	}

	// �㵽ֱ�ߵ���̾�����ж� �㣨x0,y0�� ����������ɵ��߶Σ�x1,y1�� ,( x2,y2 )
	private double pointToLine(int x1, int y1, int x2, int y2, int x0, int y0) {
		double space = 0;
		double a, b, c;
		a = lineSpace(x1, y1, x2, y2);// �߶εĳ���
		b = lineSpace(x1, y1, x0, y0);// (x1,y1)����ľ���
		c = lineSpace(x2, y2, x0, y0);// (x2,y2)����ľ���
		if (c <= 0.000001 || b <= 0.000001) {
			space = 0;
			return space;
		}
		if (a <= 0.000001) {
			space = b;
			return space;
		}
		if (c * c >= a * a + b * b) {
			space = b;
			return space;
		}
		if (b * b >= a * a + c * c) {
			space = c;
			return space;
		}
		double p = (a + b + c) / 2;// ���ܳ�
		double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// ���׹�ʽ�����
		space = 2 * s / a;// ���ص㵽�ߵľ��루���������������ʽ��ߣ�
		return space;
	}

	// ��������֮��ľ���
	private double lineSpace(int x1, int y1, int x2, int y2) {
		double lineLength = 0;
		lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		return lineLength;
	}
	
	private void save(int uMood) {
//		if(uMood!=1&&!Caculator.issleep)
//		{
		  Calendar calendar = Calendar.getInstance();
		  int year = calendar.get(Calendar.YEAR);
		  int month = calendar.get(Calendar.MONDAY) + 1;
		  int day = calendar.get(Calendar.DAY_OF_MONTH);
		  int hour = calendar.get(Calendar.HOUR_OF_DAY);
		  int minute = calendar.get(Calendar.MINUTE);
		  int second = calendar.get(Calendar.SECOND);
		  FileService.saveStatusData(year + "-" + month + "-" + day, String.format("%1$02d", hour) + ":" + String.format("%1$02d", minute) + ":" + String.format("%1$02d", second), uMood);
//		  Caculator.issleep = true;
//		}
	}

	private int getHitNum() {
		int hitNum = -1;
		for (int i = 0; i < 5; i++) {
			int x0 = ivEmotes[i].getLeft() + ivEmotes[i].getWidth() / 2;
			int y0 = ivEmotes[i].getTop() + ivEmotes[i].getHeight() / 2;
			if (r * Math.sin(betas[0]) > pointToLine(mLastMoveX, mLastMoveY, x2, y2, x0, y0)) {
				hitNum = i;
				System.out.println(""+i);
			}
		}
		setHitHighlight(hitNum);
		return hitNum;
	}

	private void setHitHighlight(int i) {
		ivEmotes[0].setBackgroundResource(R.drawable.unlocker_sleep);
		ivEmotes[1].setBackgroundResource(R.drawable.unlocker_entertm);
		ivEmotes[2].setBackgroundResource(R.drawable.unlocker_study);
		ivEmotes[3].setBackgroundResource(R.drawable.unlocker_work);
		ivEmotes[4].setBackgroundResource(R.drawable.unlocker_none);
		if (i > -1) {
			switch (i) {
			case 0:
				ivEmotes[0].setBackgroundResource(R.drawable.unlocker_sleep2);
				break;
			case 1:
				ivEmotes[1].setBackgroundResource(R.drawable.unlocker_entertm2);
				break;
			case 2:
				ivEmotes[2].setBackgroundResource(R.drawable.unlocker_study2);
				break;
			case 3:
				ivEmotes[3].setBackgroundResource(R.drawable.unlocker_work2);
				break;
			case 4:
				ivEmotes[4].setBackgroundResource(R.drawable.unlocker_none2);
				break;
			default:
				break;
			}
		}
	}

	// ���ó�ʼ��״̬����ʾtv_slider_iconͼ��ʹbitmap���ɼ�
	private void resetViewState() {
		mLastMoveX = 1000;
		mLastMoveY = 1000;
		ivUnlocker.setVisibility(View.VISIBLE);
		invalidate(); // �ػ����һ��
	}

	// ��һ���¿�
	private void virbate() {
		Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(200);
	}
	
	public void setUnlockHandler(Handler handler) {
		unLockHandler = handler;// activity���ڵ�Handler����
	}
}
