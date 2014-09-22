package cn.edu.stu.TimeTrack.View;

import java.util.Calendar;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import cn.edu.stu.TimeTrack.Layout.*;

public class LineView extends View
{
	public static  int width;
	public static  int height; 
	private static int every_chart = (height-80-height/6)/4;
	private Paint paint =null;
    private ImageView images[]= new ImageView[2];
	private LayoutParams[] params = new LayoutParams[5];
	public LineView(Context context,int w,int h) {
		super(context);
		// TODO Auto-generated constructor stub
		width = w;
		height = h;
		 paint = new Paint(); 
         paint.setColor(Color.YELLOW);
         paint.setStrokeJoin(Paint.Join.ROUND);
         paint.setStrokeCap(Paint.Cap.ROUND);
         paint.setStrokeWidth(3);
	}  
	/* (non-Javadoc)
	 * @see android.view.View#onFinishInflate()
	 */
	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		
		super.onFinishInflate();
	}

	public LineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

	}

	/* (non-Javadoc)
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
		
	}

	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		System.out.println("weiksdsjjkdskk");
		Paint p = new Paint();
		p.setColor(Color.WHITE);
		p.setStrokeJoin(Paint.Join.ROUND);
		p.setStrokeCap(Paint.Cap.ROUND);
		p.setStrokeWidth(9);
		p.setTextSize(25);
		canvas.drawLine(50, height-80, 50, height/6, paint);
//		System.out.println(MainActivity.height/6);
//		System.out.println(MainActivity.width-100);
		canvas.drawLine(50, height-80, width, height-80, paint);
		Paint p3 = new Paint();
        p3.setColor(Color.GRAY);
        p3.setStrokeJoin(Paint.Join.ROUND);
		p3.setStrokeCap(Paint.Cap.ROUND);
		p3.setStrokeWidth(3);
		System.out.println("DSADADASASDSAD");
		for(int i = 1; i < 5;i++)
		{
			canvas.drawLine(50+i*100, height-80, 50+i*100, height/6, p3);
			canvas.drawLine(50, height-80-i*100, width, height-80-i*100, p3);
			canvas.drawText(""+i*5, 20, height-80-i*100, p);
		}
		Calendar calendar = Calendar.getInstance();
	
	    for(int i= 0;i<=1;i++)
	    {
	    	int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH)+i+1;
			
			HashMap<Integer, Float> map = Caculator.Monthcaculator(year,month);
			float month_worktime = map.get(2);
			System.out.println("DASDA"+month_worktime);
			float month_studytime = map.get(4);
			float month_playtime = map.get(3);
	        canvas.drawPoint(50+i*100,height-80-every_chart*month_worktime/5,paint); 
	    }
	}
} 
