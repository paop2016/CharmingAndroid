package charming.views;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

public class HistorgramLine extends LinearLayout{
	private List<List<Integer>> mData;
	private List<String> mTitles;
	private Paint mPaint;
	private float mItemWidth;
	private float mItemMaxHight;
	private float mHistogramWidth;
	private float mRatioX=0.666666f;
	private float mRatioY=0.8f;
	public HistorgramLine(Context context){
		this(context,null);
	}
	public HistorgramLine(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mPaint=new Paint();
		mPaint.setColor(0xccD64541);
		//区别
		mPaint.setStyle(Style.FILL);
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
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
		Log.v("jay", "dispatchDraw");
		float lineHeight = 1.0f*mItemMaxHight * mRatioY / 99;
		mPaint.setStrokeWidth(lineHeight-0.5f);
		int count = 0;
		for (int i = 0; i < mData.size(); i++) {
			mPaint.setTextSize(lineHeight);
			float fontSize1=mPaint.measureText(1+"");
			float fontSize2=mPaint.measureText(10+"");
			count=0;
			for (int j = 0; j < 99; j++) {
				if(mData.get(i).contains(j)){
					count+=j+1;
					mPaint.setColor(0xccD64541);
				}else {
					mPaint.setColor(0x77f98740);
				}
				canvas.drawLine(mItemWidth * i + (mItemWidth - mHistogramWidth) / 2,
						(float) ((1.0f - mRatioY) * mItemMaxHight + (j + 0.5f) * lineHeight),
						mItemWidth * (i + 1) - (mItemWidth - mHistogramWidth) / 2,
						(float) ((1.0f - mRatioY) * mItemMaxHight + (j + 0.5f ) * lineHeight), mPaint);
				mPaint.setColor(0xff000000);
				if(j<9){
					canvas.drawText(j+1+"", (float)(mItemWidth * (i+0.5)-fontSize1/2), (float) ((1 - mRatioY) * mItemMaxHight + (j + 1) * lineHeight), mPaint);
				}else {
					canvas.drawText(j+1+"", (float)(mItemWidth * (i+0.5)-fontSize2/2), (float) ((1 - mRatioY) * mItemMaxHight + (j + 1) * lineHeight), mPaint);
				}
			}
			mPaint.setTextSize(40);
//			FontMetrics fm = mPaint.getFontMetrics();   
//			fm.
			float fontSize3=mPaint.measureText(count+"");
			canvas.drawText(count+"", (float)(mItemWidth * (i+0.5)-fontSize3/2), (float) ((1 - mRatioY) * mItemMaxHight-24), mPaint);
			if(mTitles!=null&&mTitles.size()>i){
				mPaint.setTextSize(64);
				float fontSize4=mPaint.measureText(mTitles.get(i));
				canvas.drawText(mTitles.get(i), (float)(mItemWidth * (i+0.5)-fontSize4/2), (float) ((1 - mRatioY) * mItemMaxHight-120), mPaint);
			}
		}
		super.dispatchDraw(canvas);
	}

	public HistorgramLine setDatas(List<List<Integer>> datas){
		mData=datas;
		return this;
	}
	public HistorgramLine setTitles(List<String> titles){
		mTitles=titles;
		return this;
	}
}
