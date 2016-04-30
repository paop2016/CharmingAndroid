package charming.views;

import java.util.List;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VPIndicator extends LinearLayout {

	private Context context;
//	装载TextView的容器布局
	private LinearLayout ll;
//	移动的指示器
	private ImageView iv;
//	每一个item的宽度，将在onSizeChanged（）里计算得到
	private float item_width;
//	xp与dp、sp的比值，用于设置高度
	private float scale;
//  现在所选中的位置，默认为0。主要用于移动动画，在设置初始滑动位置时也有使用
	private int nowPosition=0;
	public static final int MOVE_SMOOTH=0;
	public static final int MOVE_QUICK=1;
	public static final int  MOVE_DELAY=2;
	
	//默认值
	private ViewPager mViewPager=null;
	private VPListener mListener=null;
	private int backGroundColor = 0xfff8f8f8;
	private int moveDuration=500;
	private int movePattern=MOVE_SMOOTH;
	private boolean iv_under=false;
	private int iv_height = 3;
	private int visible_item = 4;
	private float textSize = 16f;
	private int textNormalColor = 0xff000000;
	private int textLightColor = 0xffff9000;
	private int ivColor = 0x77f98740;
	
	public VPIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		setOrientation(LinearLayout.VERTICAL);
		setBackgroundColor(backGroundColor);
		scale = getResources().getDisplayMetrics().density;
		ll = new LinearLayout(context);
		iv = new ImageView(context);
		LinearLayout.LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params.weight=1;
		params.height=0;
		ll.setLayoutParams(params);
		addView(ll);
		addView(iv);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		// 计算每个标题的宽度
		item_width = w / visible_item;
		
//		设置TextView的宽度和字体大小
		for (int i = 0; i < ll.getChildCount(); i++) {
			TextView tv = (TextView) ll.getChildAt(i);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
			LinearLayout.LayoutParams tvParams = (LayoutParams) tv.getLayoutParams();
			tvParams.width = (int) item_width;
			tv.setLayoutParams(tvParams);
		}
//		由于初始时setCurrentItem(0, false)时不能触发onPageSelected(int arg0)回调，所以需要预先设置选中第一标题
		setLightText(0);
		
//		设置iv指示器的宽高、上边间距、背景颜色
		LinearLayout.LayoutParams ivParams = (LayoutParams) iv.getLayoutParams();
		ivParams.height = (int) (iv_height * scale);
		ivParams.width = (int) item_width;
		if(!iv_under)
			ivParams.topMargin=(int) (-iv_height*scale);
		iv.setLayoutParams(ivParams);
		iv.setBackgroundColor(ivColor);
//		设置mViewPager的初始页面
		mViewPager.setCurrentItem(nowPosition, false);
	}
	@SuppressWarnings("deprecation")
	public VPIndicator setViewPager(ViewPager vp, int position) {
		mViewPager = vp;
		nowPosition=position;
//		初始mViewPager.setCurrentItem(0)时，不调用onPageSelected,只调用onPageScrolled
//		初始mViewPager.setCurrentItem(1)时，都调用，如果viewpager还未加载，onPageSelected在viewpager加载前就会直接调用，onPageScrolled在加载后调用
		vp.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				setLightText(arg0);
				if(movePattern==MOVE_QUICK){
					LinearLayout.LayoutParams layoutParams = (LayoutParams) iv.getLayoutParams();
					layoutParams.leftMargin = (int) (item_width * arg0);
					iv.setLayoutParams(layoutParams);
				}else if(movePattern==MOVE_DELAY) {
					AnimationSet set=new AnimationSet(true);
					TranslateAnimation animation=new TranslateAnimation(nowPosition*item_width, arg0*item_width, 0, 0);
					set.addAnimation(animation);
					set.setFillAfter(true);
					set.setDuration(moveDuration);
					iv.startAnimation(set);
					nowPosition=arg0;
				}
				if (mListener != null)
					mListener.onPageSelected(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				if(movePattern==MOVE_SMOOTH){
					LinearLayout.LayoutParams layoutParams = (LayoutParams) iv.getLayoutParams();
					layoutParams.leftMargin = (int) (item_width * (arg0 + arg1));
					iv.setLayoutParams(layoutParams);
				}
				if (mListener != null)
					mListener.onPageScrolled(arg0, arg1, arg2);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				if (mListener != null)
					mListener.onPageScrollStateChanged(arg0);
			}
		});
		return this;
	}

	public interface VPListener {
		void onPageSelected(int arg0);

		void onPageScrolled(int arg0, float arg1, int arg2);

		void onPageScrollStateChanged(int arg0);
	}

	private void setLightText(int position) {
		// TODO Auto-generated method stub
		for (int i = 0; i < ll.getChildCount(); i++) {
			TextView tv = (TextView) ll.getChildAt(i);
			tv.setTextColor(textNormalColor);
			if (i == position)
				tv.setTextColor(textLightColor);
		}
	}

	public VPIndicator setVPListener(VPListener listener) {
		mListener = listener;
		return this;
	}

	public VPIndicator setIndicatorHeight(int dp) {
		iv_height = dp;
		return this;
	}

	public VPIndicator setVisible_item(int count) {
		visible_item = count;
		return this;
	}

	public VPIndicator setTextSize(float sp) {
		textSize = sp;
		return this;
	}

	public VPIndicator setTextNormalColor(int color) {
		textNormalColor = color;
		return this;
	}

	public VPIndicator setTextLightColor(int color) {
		textLightColor = color;
		return this;
	}

	public VPIndicator setIndicatorColor(int color) {
		ivColor = color;
		return this;
	}
	public VPIndicator setIndicatorUnder(boolean under) {
		iv_under = under;
		return this;
	}
	public VPIndicator setMovePattern(int pattern) {
		movePattern=pattern;
		return this;
	}
	public VPIndicator setMoveDuration(int duration) {
		moveDuration=duration;
		return this;
	}
	public VPIndicator setText(List<String> titles) {
		for (int i = 0; i < titles.size(); i++) {
			ll.addView(createTextView(titles.get(i)));
		}
		onTouchEvent();
		return this;
	}
	private TextView createTextView(String title) {
		// TODO Auto-generated method stub
		TextView textView = new TextView(context);
		textView.setText(title);
		textView.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		textView.setLayoutParams(params);
		return textView;
	}

	private void onTouchEvent() {
		for (int i = 0; i < ll.getChildCount(); i++) {
			final int j = i;
			ll.getChildAt(i).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mViewPager.setCurrentItem(j, false);
				}
			});
		}
	}

}
