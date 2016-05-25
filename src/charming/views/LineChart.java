package charming.views;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class LineChart extends View {

	private int mYValue = 100;
	private List<String> mItems;
	private List<Integer> mDatas;
	private int mXItemsCount;
	private int mYItemsCount = 5;

	private int mWidth;
	private int mHeight;
	private Paint mCoordinatesPaint;
	private Paint mLinePaint;
	private Paint mLightLinePaint;
	private Paint mXwordPaint;
	private Paint mYwordPaint;

	public LineChart(Context context) {
		// TODO Auto-generated constructor stub
		this(context, null);
	}

	public LineChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mCoordinatesPaint = new Paint();
		mCoordinatesPaint.setDither(true);
		mCoordinatesPaint.setAntiAlias(true);
		mCoordinatesPaint.setStrokeWidth(5);
		mCoordinatesPaint.setColor(0xff000000);
		mLightLinePaint = new Paint();
		mLightLinePaint.setDither(true);
		mLightLinePaint.setAntiAlias(true);
		mLightLinePaint.setStrokeWidth(1);
		mLightLinePaint.setColor(0x44000000);
		mLinePaint = new Paint();
		mLinePaint.setDither(true);
		mLinePaint.setAntiAlias(true);
		mLinePaint.setStrokeWidth(6);
		mLinePaint.setColor(0xFF81CFE0);
		mYwordPaint = new Paint();
		mYwordPaint.setDither(true);
		mYwordPaint.setAntiAlias(true);
		mYwordPaint.setColor(0xff000000);
		mXwordPaint = new Paint();
		mXwordPaint.setDither(true);
		mXwordPaint.setAntiAlias(true);
		mXwordPaint.setColor(0xff000000);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		mXItemsCount = mItems.size();
		super.onSizeChanged(w, h, oldw, oldh);
		mHeight = h;
		mWidth = w;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		float mItemWidth = mWidth / (mXItemsCount + 1);
		float mItemHeight = mHeight / (mYItemsCount + 1);
		mYwordPaint.setTextSize(mItemHeight / 3);
		mXwordPaint.setTextSize(mItemWidth / 6);
		// 坐标系
		canvas.drawLine(mItemWidth / 2, mItemHeight * (0.5f + mYItemsCount), (0.5f + mXItemsCount) * mItemWidth,
				mItemHeight * (0.5f + mYItemsCount), mCoordinatesPaint);
		canvas.drawLine(mItemWidth / 2, mItemHeight * (0.5f + mYItemsCount), mItemWidth / 2, mItemHeight * (0.5f),
				mCoordinatesPaint);
		// 虚线和纵轴坐标
		FontMetrics fm = mYwordPaint.getFontMetrics();
		Log.v("jay", mYwordPaint.ascent()+"");
		float distance = -fm.ascent;
		Log.v("jay", fm.ascent+"");
		canvas.drawText(0 + "", mItemWidth / 2 - mItemHeight / 3 - mYwordPaint.measureText(0 + "") / 2,
				mItemHeight * (0.5f + mYItemsCount) + distance * 1 / 3, mYwordPaint);
		for (int i = 0; i < mYItemsCount; i++) {
			canvas.drawLine(mItemWidth / 2, mItemHeight * (0.5f + i), (0.5f + mXItemsCount) * mItemWidth,
					mItemHeight * (0.5f + i), mLightLinePaint);
			float mYWordWidth = mYwordPaint.measureText(mYValue / mYItemsCount * (mYItemsCount - i) + "");
			canvas.drawText(mYValue / mYItemsCount * (mYItemsCount - i) + "",
					mItemWidth / 2 - mItemHeight / 3 - mYWordWidth / 2, mItemHeight * (0.5f + i) + distance * 1 / 3,
					mYwordPaint);
		}
		for (int i = 0; i < mItems.size(); i++) {
			// 底部文字
			float mXWordWidth = mYwordPaint.measureText(mItems.get(i));
			canvas.drawText(mItems.get(i), mItemWidth * (i + 1) - mXWordWidth / 2, mItemHeight * (0.9f + mYItemsCount),
					mXwordPaint);
			float radio = 0;
			if (mDatas != null && mDatas.size() > i) {
				int value = mDatas.get(i);
				radio = (float) value / mYValue;
				if (i != 0) {
					// 折线
					int oldValue = mDatas.get(i - 1);
					float oldRadio = (float) oldValue / mYValue;
					canvas.drawLine(mItemWidth * i, mItemHeight * 0.5f + mItemHeight * mYItemsCount * (1 - oldRadio),
							mItemWidth * (i + 1), mItemHeight * 0.5f + mItemHeight * mYItemsCount * (1 - radio),
							mLinePaint);
				}
				// 具体数值
				float mValueWordWidth = mYwordPaint.measureText(value + "");
				canvas.drawText(value + "", mItemWidth * (i + 1) - mValueWordWidth / 2,
						mItemHeight * 0.5f + mItemHeight * mYItemsCount * (1 - radio) - distance / 2, mXwordPaint);
			}
		}
	}

	public LineChart setItem(List<String> items) {
		mItems = items;
		return this;
	}

	public LineChart setData(List<Integer> datas) {
		mDatas = datas;
		return this;
	}

	public LineChart setYvalue(int yValue) {
		mYValue = yValue;
		return this;
	}

	public LineChart setYCount(int yCount) {
		mYItemsCount = yCount;
		return this;
	}
}
