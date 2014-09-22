package cn.edu.stu.TimeTrack;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import cn.edu.stu.TimeTrack.bean.Article;
import cn.edu.stu.TimeTrack.bean.Articles;
import cn.edu.stu.TimeTrack.utils.FileManager;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

public class AdviseActivity extends Activity {

	private ViewFlipper vfAdvise;
	private TextView tvTitle, tvPage;
	private TextView tvTitle1, tvTitle2, tvTitle3, tvTitle4, tvTitle5, tvTitle6;
	private LinearLayout llTitle1, llTitle2, llTitle3, llTitle4, llTitle5, llTitle6;
	private int type;
	private String fileName = "";
	private Articles articles;
	private List<Article> articleList;
	private int pages = 0;
	private int index = 0;
	private GestureDetector gestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.advise_type);

		Intent intent = getIntent();
		type = intent.getIntExtra("type", -1);

		init();
		operate();

	}

	private void init() {
		vfAdvise = (ViewFlipper) findViewById(R.id.vf_advise_type);
		tvTitle = (TextView) findViewById(R.id.tv_advise_type);
		tvPage = (TextView) findViewById(R.id.tv_advise_type_page);
		tvTitle1 = (TextView) findViewById(R.id.tv_advise_type_title1);
		tvTitle2 = (TextView) findViewById(R.id.tv_advise_type_title2);
		tvTitle3 = (TextView) findViewById(R.id.tv_advise_type_title3);
		tvTitle4 = (TextView) findViewById(R.id.tv_advise_type_title4);
		tvTitle5 = (TextView) findViewById(R.id.tv_advise_type_title5);
		tvTitle6 = (TextView) findViewById(R.id.tv_advise_type_title6);

		llTitle1 = (LinearLayout) findViewById(R.id.ll_advise_type_title1);
		llTitle2 = (LinearLayout) findViewById(R.id.ll_advise_type_title2);
		llTitle3 = (LinearLayout) findViewById(R.id.ll_advise_type_title3);
		llTitle4 = (LinearLayout) findViewById(R.id.ll_advise_type_title4);
		llTitle5 = (LinearLayout) findViewById(R.id.ll_advise_type_title5);
		llTitle6 = (LinearLayout) findViewById(R.id.ll_advise_type_title6);
	}

	private void operate() {

		gestureDetector = new GestureDetector(new DefaultGestureDetector());

		switch (type) {
		case 0:
			tvTitle.setText("时间人物");
			fileName = "famousPerson";
			break;
		case 1:
			tvTitle.setText("时间美文");
			fileName = "timeNovel";
			break;
		case 2:
			tvTitle.setText("时间故事");
			fileName = "timeStories";
			break;
		case 3:
			tvTitle.setText("时间管理");
			fileName = "timeManage";
			break;
		case 4:
			tvTitle.setText("时间书籍");
			fileName = "timeBook";
			break;
		case 5:
			tvTitle.setText("时间电影");
			fileName = "timeFilm";
			break;
		}
		setArticles();
	}

	private void setArticles() {
		String strArticle = FileManager.getArticle(fileName);
		if (!strArticle.equals("")) {
			articles = new Gson().fromJson(strArticle, Articles.class);
			articleList = articles.getArticles();
			pages = articleList.size() / 6;
			if (pages > 0) {
				TitleOnClickListener titleOnClickListener = new TitleOnClickListener();
				TitleOnTouchListener titleOnTouchListener = new TitleOnTouchListener();

				llTitle1.setOnClickListener(titleOnClickListener);
				llTitle2.setOnClickListener(titleOnClickListener);
				llTitle3.setOnClickListener(titleOnClickListener);
				llTitle4.setOnClickListener(titleOnClickListener);
				llTitle5.setOnClickListener(titleOnClickListener);
				llTitle6.setOnClickListener(titleOnClickListener);

				// llTitle1.setOnTouchListener(titleOnTouchListener);
				// llTitle2.setOnTouchListener(titleOnTouchListener);
				// llTitle3.setOnTouchListener(titleOnTouchListener);
				// llTitle4.setOnTouchListener(titleOnTouchListener);
				// llTitle5.setOnTouchListener(titleOnTouchListener);
				// llTitle6.setOnTouchListener(titleOnTouchListener);
				setArticleTitle();
			}
		}
	}

	private void setArticleTitle() {
		tvTitle1.setText(articleList.get(index * 6 + 0).getTitle());
		if (articleList.get(index * 6 + 0).isRead()) {
			tvTitle1.setTextColor(getResources().getColor(R.color.gray));
		} else {
			tvTitle1.setTextColor(getResources().getColor(R.color.black));
		}

		tvTitle2.setText(articleList.get(index * 6 + 1).getTitle());
		if (articleList.get(index * 6 + 1).isRead()) {
			tvTitle2.setTextColor(getResources().getColor(R.color.gray));
		} else {
			tvTitle2.setTextColor(getResources().getColor(R.color.black));
		}
		tvTitle3.setText(articleList.get(index * 6 + 2).getTitle());
		if (articleList.get(index * 6 + 2).isRead()) {
			tvTitle3.setTextColor(getResources().getColor(R.color.gray));
		} else {
			tvTitle3.setTextColor(getResources().getColor(R.color.black));
		}
		tvTitle4.setText(articleList.get(index * 6 + 3).getTitle());
		if (articleList.get(index * 6 + 3).isRead()) {
			tvTitle4.setTextColor(getResources().getColor(R.color.gray));
		} else {
			tvTitle4.setTextColor(getResources().getColor(R.color.black));
		}
		tvTitle5.setText(articleList.get(index * 6 + 4).getTitle());
		if (articleList.get(index * 6 + 4).isRead()) {
			tvTitle5.setTextColor(getResources().getColor(R.color.gray));
		} else {
			tvTitle5.setTextColor(getResources().getColor(R.color.black));
		}
		tvTitle6.setText(articleList.get(index * 6 + 5).getTitle());
		if (articleList.get(index * 6 + 5).isRead()) {
			tvTitle6.setTextColor(getResources().getColor(R.color.gray));
		} else {
			tvTitle6.setTextColor(getResources().getColor(R.color.black));
		}

		tvPage.setText((index + 1) + "/" + (pages));
	}

	private class TitleOnClickListener implements OnClickListener {
		public void onClick(View v) {
			int id = v.getId();
			Intent intent = new Intent(AdviseActivity.this, ArticleActivity.class);
			intent.putExtra("type", type);
			intent.putExtra("articles", articles);
			switch (id) {
			case R.id.ll_advise_type_title1:
				intent.putExtra("index", index * 6 + 0);
				articleList.get(index * 6 + 0).setRead(true);
				break;
			case R.id.ll_advise_type_title2:
				intent.putExtra("index", index * 6 + 1);
				articleList.get(index * 6 + 1).setRead(true);
				break;
			case R.id.ll_advise_type_title3:
				intent.putExtra("index", index * 6 + 2);
				articleList.get(index * 6 + 2).setRead(true);
				break;
			case R.id.ll_advise_type_title4:
				intent.putExtra("index", index * 6 + 3);
				articleList.get(index * 6 + 3).setRead(true);
				break;
			case R.id.ll_advise_type_title5:
				intent.putExtra("index", index * 6 + 4);
				articleList.get(index * 6 + 4).setRead(true);
				break;
			case R.id.ll_advise_type_title6:
				intent.putExtra("index", index * 6 + 5);
				articleList.get(index * 6 + 5).setRead(true);
				break;
			}
			setArticleTitle();
			startActivity(intent);
		}
	}

	private class TitleOnTouchListener implements OnTouchListener {
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if (event.getAction() == MotionEvent.ACTION_DOWN) {

			}
			return false;
		}
	}

	private class MyListener implements OnClickListener {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			onBackPressed();
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (gestureDetector.onTouchEvent(event)) {
			event.setAction(MotionEvent.ACTION_CANCEL);
		}
		return super.dispatchTouchEvent(event);
	}

	private class DefaultGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			final int FLING_MIN_DISTANCE = 100;// X或者y轴上移动的距离(像素)
			final int FLING_MIN_VELOCITY = 200;// x或者y轴上的移动速度(像素/秒)
			final int SCROLLING_MIN_VELOCITY = 500;// x或者y轴上的移动速度(像素/秒)
			if ((e1.getX() - e2.getX()) > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
				if (index + 1 < pages) {
					index++;
					vfAdvise.setInAnimation(AnimationUtils.loadAnimation(AdviseActivity.this, R.anim.push_left_in));
					vfAdvise.setOutAnimation(AnimationUtils.loadAnimation(AdviseActivity.this, R.anim.push_left_out));
					vfAdvise.showNext();
					setArticleTitle();
					return true;
				}
			} else if ((e1.getX() - e2.getX()) < -FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
				if (index > 0) {
					index--;
					vfAdvise.setInAnimation(AnimationUtils.loadAnimation(AdviseActivity.this, R.anim.push_right_in));
					vfAdvise.setOutAnimation(AnimationUtils.loadAnimation(AdviseActivity.this, R.anim.push_right_out));
					vfAdvise.showPrevious();
					setArticleTitle();
					return true;
				}
			} else if (e2.getY() - e1.getY() > FLING_MIN_DISTANCE && Math.abs(velocityY) > SCROLLING_MIN_VELOCITY) {// up
				finish();
				return true;
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
		setArticles();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
