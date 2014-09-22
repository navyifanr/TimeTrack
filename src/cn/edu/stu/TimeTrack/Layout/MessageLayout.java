package cn.edu.stu.TimeTrack.Layout;

import cn.edu.stu.TimeTrack.AdviseActivity;
import cn.edu.stu.TimeTrack.ArticleActivity;
import cn.edu.stu.TimeTrack.MainActivity;
import cn.edu.stu.TimeTrack.R;
import android.widget.LinearLayout;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class MessageLayout extends LinearLayout {

	private MainActivity mContext;
//	private LinearLayout llMessageArticle, llIdol, llEfficiency;
//	private View llMessageIdol, llMessageEfficiency;
	private LinearLayout  mPerson,mActicle,mStory,mManage,mBook,mMovie;
//	private LinearLayout llEff1, llEff2, llEff3, llEff4, llEff5, llEff6;

	public MessageLayout(MainActivity context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;

		init();

	}

	private void init() {
		LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		setLayoutParams(layoutParams);
		View message = View.inflate(mContext, R.layout.message, null);
		message.setLayoutParams(layoutParams);
		addView(message);
		// ，监听的布局控件
//		llIdol = (LinearLayout) findViewById(R.id.ll_message_idol);
//		llEfficiency = (LinearLayout) findViewById(R.id.ll_message_efficiency);
//		// ，添加的布局文件
//		llMessageIdol = View.inflate(mContext, R.layout.message_idol, null);
//		llMessageEfficiency = View.inflate(mContext, R.layout.message_efficiency, null);
//		llEff1 = (LinearLayout) llMessageEfficiency.findViewById(R.id.ll_eff1);
//		llEff2 = (LinearLayout) llMessageEfficiency.findViewById(R.id.ll_eff2);
//		llEff3 = (LinearLayout) llMessageEfficiency.findViewById(R.id.ll_eff3);
//		llEff4 = (LinearLayout) llMessageEfficiency.findViewById(R.id.ll_eff4);
//		llEff5 = (LinearLayout) llMessageEfficiency.findViewById(R.id.ll_eff5);
//		llEff6 = (LinearLayout) llMessageEfficiency.findViewById(R.id.ll_eff6);
//		// ，初始化进入的默认布局
//		llMessageArticle = (LinearLayout) findViewById(R.id.ll_message_article);
//		 llMessageArticle.addView(llMessageIdol);
		mActicle = (LinearLayout) findViewById(R.id.message_aticle);
		mBook = (LinearLayout) findViewById(R.id.message_book);
		mManage = (LinearLayout) findViewById(R.id.message_manage);
		mMovie = (LinearLayout) findViewById(R.id.message_movie);
		mPerson = (LinearLayout) findViewById(R.id.message_person);
		mStory = (LinearLayout) findViewById(R.id.message_story);

//		llIdol.setOnClickListener(this);
//		llEfficiency.setOnClickListener(this);

		 mActicle.setOnClickListener(new idolOnClickListener());
		 mBook.setOnClickListener(new idolOnClickListener());
		 mManage.setOnClickListener(new idolOnClickListener());
		 mMovie.setOnClickListener(new idolOnClickListener());
		 mPerson.setOnClickListener(new idolOnClickListener());
		 mStory.setOnClickListener(new idolOnClickListener());
		
//		llEff1.setOnClickListener(new efficiencyOnClickListener());
//		llEff2.setOnClickListener(new efficiencyOnClickListener());
//		llEff3.setOnClickListener(new efficiencyOnClickListener());
//		llEff4.setOnClickListener(new efficiencyOnClickListener());
//		llEff5.setOnClickListener(new efficiencyOnClickListener());
//		llEff6.setOnClickListener(new efficiencyOnClickListener());
	}

//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.ll_message_idol:
//			 resetView();
//			 llMessageIdol.setBackgroundResource(R.drawable.message_read);
//			 llMessageArticle.addView(llMessageIdol);
//			break;
//		case R.id.ll_message_efficiency:
//			resetView();
//			llMessageEfficiency.setBackgroundResource(R.drawable.message_read);
//			llMessageArticle.addView(llMessageEfficiency);
//			break;
//		default:
//			break;
//		}
//	}

//	private void resetView() {
//		llMessageArticle.removeAllViews();
//
//		llMessageIdol.setBackgroundResource(R.drawable.message_read2);
//		llMessageEfficiency.setBackgroundResource(R.drawable.message_read2);
//	}

	private class idolOnClickListener implements OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.message_person:
				intent.putExtra("type", 0);
				break;
			case R.id.message_aticle:
				intent.putExtra("type", 1);
				break;
			case R.id.message_story:
				intent.putExtra("type", 2);
				break;
			case R.id.message_manage:
				intent.putExtra("type", 3);
				break;
			case R.id.message_book:
				intent.putExtra("type", 4);
				break;
			case R.id.message_movie:
				intent.putExtra("type", 5);
				break;
			default:
				break;
			}
			intent.setClass(mContext, AdviseActivity.class);
			mContext.startActivity(intent);
		}
	}

//	private class efficiencyOnClickListener implements OnClickListener {
//
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			Intent intent = new Intent();
//			if (v == llEff1) {
//				intent.putExtra("type", 0);
//			} else if (v == llEff2) {
//				intent.putExtra("type", 1);
//			} else if (v == llEff3) {
//				intent.putExtra("type", 2);
//			} else if (v == llEff4) {
//				intent.putExtra("type", 3);
//			} else if (v == llEff5) {
//				intent.putExtra("type", 4);
//			} else if (v == llEff6) {
//				intent.putExtra("type", 5);
//			}
//			intent.setClass(mContext, AdviseActivity.class);
//			mContext.startActivity(intent);
//		}
//	}
}
