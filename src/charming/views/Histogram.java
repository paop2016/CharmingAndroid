package charming.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;

public class Histogram extends LinearLayout{
	private List<Integer> mData;
	private Paint mPaint;
	private float mItemWidth;
	private float mItemMaxHight;
	private float mHistogramWidth;
	private float mRatioX=0.666666f;
	private float mRatioY=0.8f;
	private int mMaxData;
	public Histogram(Context context){
		this(context,null);
	}
	public Histogram(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mPaint=new Paint();
		mPaint.setColor(0xccD64541);
		//区别
		mPaint.setStyle(Style.FILL);
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setStrokeWidth(4f);
//		mPaint.setPathEffect(new CornerPathEffect(10f));
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		mItemWidth = w / mData.size();
		mItemMaxHight = h;
		mHistogramWidth = mItemWidth*mRatioX;
	}
	@Override
	protected void dispatchDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		for (int i = 0; i < mData.size(); i++) {
			canvas.drawRect(mItemWidth*i+(mItemWidth-mHistogramWidth)/2, (1-mRatioY*mData.get(i)/mMaxData)*mItemMaxHight, mItemWidth*(i+1)-(mItemWidth-mHistogramWidth)/2, mItemMaxHight, mPaint);
		}
		super.dispatchDraw(canvas);
	}
	public void setData(List<Integer> datas){
		mData=datas;
		mMaxData=max(mData);
	}
	private int max(List<Integer> datas) {
		// TODO Auto-generated method stub
		int max=datas.get(0);
		for (int i = 1; i < datas.size(); i++) {
			if(datas.get(i)>max){
				max=datas.get(i);
			}
		}
		return max;
	}; 
}
