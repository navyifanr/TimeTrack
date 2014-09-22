package cn.edu.stu.TimeTrack;

import cn.edu.stu.TimeTrack.bean.Articles;
import cn.edu.stu.TimeTrack.utils.FileManager;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class ArticleActivity extends Activity {

	private ViewFlipper vfArticle;
	private TextView tvType, tvTitle, tvContent;
	private ScrollView svArticle;
	
	private Articles articles;
	private int type;
	private String fileName = "";
	
	private int index;
	private GestureDetector gestureDetector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.article);
		
		Intent intent = getIntent();
		articles = (Articles) intent.getSerializableExtra("articles");
		index = intent.getIntExtra("index", -1);
		type = intent.getIntExtra("type", -1);
		
		init();
		operate();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (gestureDetector.onTouchEvent(event)) {
			event.setAction(MotionEvent.ACTION_CANCEL);
		}
		return super.dispatchTouchEvent(event);
	}

	private void init() {
		svArticle = (ScrollView) findViewById(R.id.sv_article);
		vfArticle = (ViewFlipper) findViewById(R.id.vf_article);
		tvType = (TextView) findViewById(R.id.tv_article_type);
		tvTitle = (TextView) findViewById(R.id.tv_article_title);
		tvContent = (TextView) findViewById(R.id.tv_article_content);
		
		gestureDetector = new GestureDetector(new DefaultGestureDetector());
	}

	private void operate() {
		switch (type) {
		case 0:
			tvType.setText("时间人物");
			fileName = "famousPerson";
			break;
		case 1:
			tvType.setText("时间美文");
			fileName = "timeNovel";
			break;
		case 2:
			tvType.setText("时间故事");
			fileName = "timeStories";
			break;
		case 3:
			tvType.setText("时间管理");
			fileName = "timeManage";
			break;
		case 4:
			tvType.setText("时间书籍");
			fileName = "timeBook";
			break;
		case 5:
			tvType.setText("时间电影");
			fileName = "timeFilm";
			break;
		}
		setViews();
	}
	
	private void setViews(){
		tvTitle.setText(articles.getArticles().get(index).getTitle());
		tvContent.setText(articles.getArticles().get(index).getContent());
		if (!articles.getArticles().get(index).isRead()) {
			articles.getArticles().get(index).setRead(true);
			String strArticle = new Gson().toJson(articles);
			FileManager.saveArticle(strArticle, fileName);
		}
	}

	private class DefaultGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			final int FLING_MIN_DISTANCE = 240;// X或者y轴上移动的距离(像素)
			final int FLING_MIN_VELOCITY = 200;// x或者y轴上的移动速度(像素/秒)
			final int SCROLLING_MIN_VELOCITY = 500;// x或者y轴上的移动速度(像素/秒)
			if ((e1.getX() - e2.getX()) > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {//right
				if (index+1 < articles.getArticles().size()) {
					index++;
					setViews();
					vfArticle.setInAnimation(AnimationUtils.loadAnimation(ArticleActivity.this, R.anim.push_left_in));
					vfArticle.setOutAnimation(AnimationUtils.loadAnimation(ArticleActivity.this, R.anim.push_left_out));
					vfArticle.showNext();
					return true;
				}
			} else if ((e2.getX() - e1.getX()) > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {//left
				if (index > 0) {
					index--;
					setViews();
					vfArticle.setInAnimation(AnimationUtils.loadAnimation(ArticleActivity.this, R.anim.push_right_in));
					vfArticle.setOutAnimation(AnimationUtils.loadAnimation(ArticleActivity.this, R.anim.push_right_out));
					vfArticle.showPrevious();
					return true;
				}
			} else if (e2.getY() - e1.getY() > FLING_MIN_DISTANCE && Math.abs(velocityY) > SCROLLING_MIN_VELOCITY) {//up
				if (svArticle.getScrollY() == 0) {
					finish();
					return true;
				}
			}
			return false;
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
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
