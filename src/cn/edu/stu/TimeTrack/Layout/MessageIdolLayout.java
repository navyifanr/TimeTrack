package cn.edu.stu.TimeTrack.Layout;

import java.util.ArrayList;
import java.util.List;

import cn.edu.stu.TimeTrack.MainActivity;
import cn.edu.stu.TimeTrack.R;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

public class MessageIdolLayout extends LinearLayout{
	
	private MainActivity mContext;
	private ViewPager mViewPager;
	private ViewPagerAdapter mAdapter;
	private List<View> mListViews;
	
	public MessageIdolLayout(MainActivity context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		mListViews = new ArrayList<View>();
//		mListViews.add(View.inflate(mContext,R.layout.message_idol, null));
//		mListViews.add(View.inflate(mContext,R.layout.message_idol2, null));

		mAdapter = new ViewPagerAdapter(mListViews);
		View llMessage =   View.inflate(mContext,R.layout.message, null);
//		mViewPager = (ViewPager) llMessage.findViewById(R.id.mViewPager);
		mViewPager.setAdapter(mAdapter);
	}
	private class ViewPagerAdapter extends PagerAdapter {

		private List<View> list;

		public ViewPagerAdapter(List<View> list) {
			// TODO Auto-generated constructor stub
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object instantiateItem(View container, int position) {
			// TODO Auto-generated method stub
			((ViewPager) container).addView(list.get(position), 0);
			if (position == 0) {
//				ImageView download_btn = (ImageView) container.findViewById(R.id.download_btn);
//				download_btn.setOnClickListener(new View.OnClickListener() {
//
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						new AlertDialog.Builder(TActivity.this)
//								.setTitle("说明")
//								.setMessage("单个页卡内按钮事件测试")
//								.setNegativeButton("确定",
//										new DialogInterface.OnClickListener() {
//											public void onClick(
//													DialogInterface dialog,
//													int which) {
//											}
//										}).show();
//					}
//				});
			}
			return list.get(position);
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager) container).removeView(list.get(position));
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}
	}
}
