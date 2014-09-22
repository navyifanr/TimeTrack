package cn.edu.stu.TimeTrack.View;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LineChartView extends View{
	
	int px;
	int py;
	float oneHeight;
	float oneWidth; 
	List<List<Float>> items;
	List<List<Float>> items2;
	private List<Float> mrest,mrelax,mstudy,mwork;
	List<Integer> colors;
	List<String> adays;
	
	public LineChartView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public LineChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public LineChartView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		int numday = adays.size();
		items2 = new LinkedList<List<Float>>();
		mrelax = new LinkedList<Float>();
		mrest = new LinkedList<Float>();
		mstudy = new LinkedList<Float>();
		mwork = new LinkedList<Float>();
		px = getMeasuredWidth();
		py = getMeasuredHeight();
		float height = py-60;
		float width = px-60;
		oneHeight = height/13;
	    oneWidth = width/numday;
		
		Paint mpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mpaint.setColor(Color.WHITE);
		mpaint.setStrokeWidth(3);
		canvas.drawText("h", 15, 20, mpaint);
		canvas.drawLine(30, 10, 30, py-30, mpaint);
		canvas.drawLine(30, py-30, px-10, py-30, mpaint);
		
		for(int i = 1;i<=12;i++){
			canvas.drawLine(30, py-30-i*height/13, 35, py-30-i*height/13, mpaint);
			canvas.drawText(i+"", 15,py-30-i*height/13 , mpaint);
		}
		for(int i = 0;i<adays.size();i++){
			canvas.drawLine(30+(i+1)*width/numday,py-30, 30+(i+1)*width/numday, py-35, mpaint);
		    canvas.drawText(adays.get(numday-1-i), 27+i*width/numday,py-10 , mpaint);
		}
	    System.out.println("item: "+items.size()+", "+"color:"+colors.size());
	    DecimalFormat fornat = new DecimalFormat("0.#");
	    int j,i = 0;
//	    for(j = 0;j<items2.size();j++){
//	    	for(i= items2.get(j).size()-1;i>=0;i--){
//	    		if(items2.get(j).get(i)>10){
//	    			float real = items2.get(j).get(i);
//	    			items2.get(j).set(i,10+(real-10)/10);
//				}
//	    	}
//	    }
	    for(j = 0;j<items.size();j++){
	    	for(i= 0;i<items.get(j).size();i++){
	    		float real = new Float(items.get(j).get(i));
	    		if(items.get(j).get(i)>12&&j==0){
	    			mrelax.add(12+(real-12)/10);
				}else if(j==0){
					mrelax.add(items.get(j).get(i));
				}
	    		if(items.get(j).get(i)>12&&j==1){
	    			mrest.add(12+(real-12)/10);
				}else if(j==1){
					mrest.add(items.get(j).get(i));
				}
	    		if(items.get(j).get(i)>12&&j==2){
	    			mstudy.add(12+(real-12)/10);
				}else if(j==2){
					mstudy.add(items.get(j).get(i));
				}
	    		if(items.get(j).get(i)>12&&j==3){
	    			mwork.add(12+(real-12)/10);
				}else if(j==3){
					mwork.add(items.get(j).get(i));
				}
	    	}
	    }
	    items2.add(mrelax);
	    items2.add(mrest);
	    items2.add(mstudy);
	    items2.add(mwork);
	    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    	paint.setStrokeWidth(3);
    	Paint numPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	    numPaint.setTextSize(15);
    	for(j = 0;j<items2.size();j++){
//			paint.setColor(colors.get(j));
    		if(j==0){
    			paint.setARGB(255,20, 181, 181);
    			numPaint.setARGB(255,20, 181, 181);
    		}else if(j==1){
    			paint.setARGB(255,134, 105, 183);
    			numPaint.setARGB(255,134, 105, 183);
    		}else if(j==2){
    			paint.setARGB(255,221, 195, 86);
    			numPaint.setARGB(255,221, 195, 86);
    		}else{
    			paint.setARGB(255,16, 150, 220);
    			numPaint.setARGB(255,16, 150, 220);
    		}
			for(i= 0;i<items2.get(j).size()-1;i++){
				canvas.drawLine(30+(numday-i-1)*oneWidth,py-30-items2.get(j).get(i)*oneHeight, 30+(numday-i-2)*oneWidth, py-30-items2.get(j).get(i+1)*oneHeight, paint);
				if(items2.get(j).get(i)<items2.get(j).get(i+1)){
					canvas.drawText(fornat.format(items.get(j).get(i)), 30+(numday-i-1)*oneWidth, py-30-items2.get(j).get(i)*oneHeight+15, numPaint);
				}else{
					canvas.drawText(fornat.format(items.get(j).get(i)), 30+(numday-i-1)*oneWidth, py-30-items2.get(j).get(i)*oneHeight-15, numPaint);
				}
			}
			canvas.drawText(fornat.format(items.get(j).get(i)), 30+(numday-i-1)*oneWidth, py-30-items2.get(j).get(i)*oneHeight-15, numPaint);
	    }
	}
//	    for(j = 0;j<items.size();j++){
//			paint.setColor(colors.get(j));
//			for(i= items.get(j).size()-1;i>0;i--){
//				real = new Float(items.get(j).get(i));
//				if(items.get(j).get(i)>10){
//					items.get(j).set(i,10+(real-10)/10);
//				}
//				canvas.drawLine(30+i*oneWidth,py-30-items.get(j).get(i)*oneHeight, 30+(i-1)*oneWidth, py-30-items.get(j).get(i-1)*oneHeight, paint);
//				if(items.get(j).get(i)<items.get(j).get(i-1)){
//					canvas.drawText(fornat.format(real), 30+i*oneWidth, py-30-items.get(j).get(i)*oneHeight+15, paint);
//				}else{
//					canvas.drawText(fornat.format(real), 30+i*oneWidth, py-30-items.get(j).get(i)*oneHeight-15, paint);
//				}
//			}
//	    }
//	    canvas.drawText(fornat.format(items.get(j-1).get(i)), 30+i*oneWidth, py-30-items.get(j-1).get(i)*oneHeight-15, paint);
//	}
	
	public void setItem(List<List<Float>> items,List<Integer> colors,List<String> adays){
		this.items = items;
		this.colors = colors;
		this.adays = adays;
	}
	
	@Override
	public void setMinimumWidth(int minWidth) {
		// TODO Auto-generated method stub
		super.setMinimumWidth(minWidth);
	}
	@Override
	public void setMinimumHeight(int minHeight) {
		// TODO Auto-generated method stub
		super.setMinimumHeight(minHeight);
	}
	@Override
	public void invalidate() {
		// TODO Auto-generated method stub
		super.invalidate();
	}
	@Override
	public void invalidate(int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.invalidate(l, t, r, b);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}
}
