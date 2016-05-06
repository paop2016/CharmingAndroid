package charming.views;

import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class HistorgramLine extends LinearLayout{
	private List<List<Integer>> mData;
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
			float lineHeight = mItemMaxHight * mRatioY / 99;
			mPaint.setStrokeWidth(lineHeight);
			for (int j = 0; j < 99; j++) {
				if(mData.get(i).contains(j)){
					mPaint.setColor(0xccD64541);
				}else {
					mPaint.setColor(0xfff8f8f8);
				}
				canvas.drawLine(mItemWidth * i + (mItemWidth - mHistogramWidth) / 2,
						(1 - mRatioY) * mItemMaxHight + (j + 1 / 2) * lineHeight,
						mItemWidth * (i + 1) - (mItemWidth - mHistogramWidth) / 2,
						(1 - mRatioY) * mItemMaxHight + (j + 1 / 2) * lineHeight, mPaint);
			}
		}
		super.dispatchDraw(canvas);
	}

	public void setData(List<List<Integer>> datas){
		mData=datas;
	}
}
