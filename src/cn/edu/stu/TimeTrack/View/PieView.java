package cn.edu.stu.TimeTrack.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;

/**
 * @author Administrator
 *	画饼图类
 */
public class PieView extends PieViewBase {
	int areaX = 1;
	int areaY = 22;
	int areaWidth;
	int areaHight;
	int colors[];
	int shade_colors[];
	int percent[];
	private int thickness=20;
	
	/**
	 * @param context 上下文
	 * @param colors 最上面颜色数组
	 * @param shade_colors 阴影颜色数组
	 * @param percent 百分比 (和必须是360)
	 */
	public PieView(Context context,int[] colors,int[] shade_colors,int[] percent) {
		super(context);
		this.colors = colors;
		this.shade_colors = shade_colors;
		this.percent = percent;
	}
	
	public void setThickness(int thickness) {
		this.thickness = thickness;
		areaY=thickness+2;
		this.invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		areaWidth=width-2;
		areaHight=height-2;
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.FILL);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(1);
		for(int i=0;i<=thickness;i++){
			int tempAngle=0;
			for(int j=0;j<percent.length;j++){
				paint.setColor(shade_colors[j]);
				canvas.drawArc(new RectF(areaX, areaY-i, areaX+areaWidth, areaHight-i), tempAngle,percent[j], true, paint);
//				Paint mpaint = new Paint();
//				mpaint.setColor(Color.BLACK);
//				canvas.drawText(percent[j]/360+"%",(float)(areaX+areaWidth- areaWidth/2*Math.cos(tempAngle/2)),(float)(areaHight-i-areaWidth/2*Math.sin(tempAngle/2)),  mpaint);
				tempAngle+=percent[j];
			}
			if(i==thickness){
				for(int j=0;j<percent.length;j++){
					paint.setColor(colors[j]);
					canvas.drawArc(new RectF(areaX, areaY-i, areaX+areaWidth, areaHight-i), tempAngle,percent[j], true, paint);
//					Paint mpaint = new Paint();
//					mpaint.setColor(Color.BLACK);
//					canvas.drawText(percent[j]/360+"%",(float)(areaX+areaWidth- areaWidth*Math.cos(tempAngle/2)),(float)(areaHight-i-areaWidth*Math.sin(tempAngle/2)),  mpaint);
					tempAngle+=percent[j];
				}
			}
		}
//		Paint mpaint = new Paint();
//		mpaint.setColor(Color.BLACK);
//		float rx = areaWidth/2,ry = (areaHight-areaY)/2;
//		float x = rx+areaX,y = ry+areaY;
//		canvas.drawText("25%", x+rx, y+ry, mpaint);
//		canvas.drawText("25%", x-rx, y+ry, mpaint);
//		canvas.drawText("25%", x-rx, y-ry, mpaint);
//		canvas.drawText("25%", x+rx, y-ry, mpaint);
	}
}
