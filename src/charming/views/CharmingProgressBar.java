package charming.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import charming.utils.DensityUtils;

public class CharmingProgressBar extends ProgressBar {
	private int mReachHeight = DensityUtils.dp2px(getContext(), 12);
	private int mUnReachHeight = DensityUtils.dp2px(getContext(), 4);
	private int mReachColor = 0xffF2784B;
	private int mUnReachColor = 0xffFDE3A7;
	private int mTextSize = DensityUtils.sp2dp(getContext(), 8);
	private int mTextColor = 0xff000000;
	private int mTextOffset = DensityUtils.dp2px(getContext(), 4);
	private Paint mPaint;
	private int mRealWidth;

	public CharmingProgressBar(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public CharmingProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CharmingProgressBar);
		mReachHeight = (int) ta.getDimension(R.styleable.CharmingProgressBar_reach_height, mReachHeight);
		mReachColor = ta.getColor(R.styleable.CharmingProgressBar_reach_color, mReachColor);
		mUnReachHeight = (int) ta.getDimension(R.styleable.CharmingProgressBar_unreach_height, mUnReachHeight);
		mUnReachColor = ta.getColor(R.styleable.CharmingProgressBar_unreach_color, mUnReachColor);
		mTextColor = ta.getColor(R.styleable.CharmingProgressBar_text_color, mTextColor);
		mTextSize = (int) ta.getDimension(R.styleable.CharmingProgressBar_text_size, mTextSize);
		mTextOffset = (int) ta.getDimension(R.styleable.CharmingProgressBar_text_offset, mTextOffset);
		ta.recycle();

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setTextSize(mTextSize);
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int result = heightSize;
		if (heightMode != MeasureSpec.AT_MOST) {
			int textHeight = (int) Math.abs(mPaint.descent() - mPaint.ascent());
			result = Math.max(Math.max(textHeight, mUnReachHeight), mReachHeight) + getPaddingBottom()
					+ getPaddingTop();
			if (heightMode == MeasureSpec.EXACTLY) {
				result = Math.min(heightSize, result);
			}
		}
		setMeasuredDimension(widthSize, result);
		mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.save();
		canvas.translate(getPaddingLeft(), getHeight() / 2);
		boolean unReach = true;
		String text = getProgress() + "%";
		float radio = getProgress() * 1.0f / getMax();
		float progressX = radio * mRealWidth;
		float textWidth = mPaint.measureText(text);
		if (progressX + textWidth > mRealWidth) {
			progressX = mRealWidth - textWidth;
			unReach = false;
		}
		// ReachBar
		float reachLine = progressX - mTextOffset / 2;
		if (reachLine > 0) {
			mPaint.setColor(mReachColor);
			mPaint.setStrokeWidth(mReachHeight);
			canvas.drawLine(0, 0, reachLine, 0, mPaint);
		}
		// Text
		mPaint.setColor(mTextColor);
		canvas.drawText(text, progressX, -(mPaint.descent() + mPaint.ascent()) / 2, mPaint);

		// UnReachBar
		if (unReach) {
			mPaint.setColor(mUnReachColor);
			mPaint.setStrokeWidth(mUnReachHeight);
			canvas.drawLine(progressX + textWidth + mTextOffset / 2, 0, mRealWidth, 0, mPaint);
		}
		canvas.restore();
	}

}
