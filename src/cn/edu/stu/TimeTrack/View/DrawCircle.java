package cn.edu.stu.TimeTrack.View;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;

public class DrawCircle extends View {

	private float bearing;

	private Paint markerPaint;
	private Paint textPaint;
	private Paint circlePaint;
	private int textHeight;

	private List<Integer> color;
	private List<Paint> paint;
	private List<Float> startAngle;
	private List<Float> sweepAngle;
	
	public DrawCircle(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public DrawCircle(Context context, AttributeSet attrs) {
		super(context, attrs);
		initCompassView();
	}

	public DrawCircle(Context context, AttributeSet ats, int defaultStyle) {
		super(context, ats, defaultStyle);
		initCompassView();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		int px = getMeasuredWidth() / 2;
		int py = getMeasuredHeight() / 2;
//		int radius = Math.min(px, py) - 30;
		int radius = px - 30;
		// 绘制圆
		canvas.drawPoint(px, py, markerPaint);
		canvas.drawCircle(px, py, radius, circlePaint);
		// 旋转视图，这样上方就面对当前的方向
		canvas.save();
		//，设置旋转
		canvas.rotate(-bearing, px, py);
		
		for(int i = 0; i < sweepAngle.size(); i++){
//			Paint mpaint = new Paint();
//			mpaint.setColor(color.get(i));
			RectF rectf = new RectF(px-radius, py-radius, px+radius, py+radius);
			canvas.drawArc(rectf, startAngle.get(i) ,sweepAngle.get(i) ,true, paint.get(i));
		}
				
		// 绘制标记，每15度画一个
		for (int i = 0; i < 24; i++) {
			// 绘制标记
			canvas.drawLine(px, py - radius, px, py - radius + 10, markerPaint);

			canvas.save();
			canvas.translate(0, textHeight);
			//，绘制时间数字
			String angle = String.valueOf(i);
			float angleTextWidth = textPaint.measureText(angle);

			int angleTextX = (int) (px - angleTextWidth / 2);
			int angleTextY = py - radius -2*textHeight;
			canvas.drawText(angle, angleTextX, angleTextY, textPaint);
			canvas.restore();
			//，旋转15度
			canvas.rotate(15, px, py);
			
		}
		canvas.restore();
	}

	protected void initCompassView() {
		setFocusable(true);

		circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		circlePaint.setColor(Color.GRAY);
//		circlePaint.setStrokeWidth(1);
//		circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);

		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setColor(Color.WHITE);

		textHeight = (int) textPaint.measureText("yY");

		markerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		markerPaint.setColor(Color.WHITE);
	}
	
	public void setContentRatio(List<Paint> paint,List<Float> startAngle, List<Float> sweepAngle){
		this.paint = paint;
		this.startAngle = startAngle;
		this.sweepAngle = sweepAngle;
	}
}
