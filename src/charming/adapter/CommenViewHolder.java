package charming.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class CommenViewHolder {

	private SparseArray<View> mViews;
	private View mConvertView;
	private int mPosition;

	private CommenViewHolder(Context context, int layout, ViewGroup parent) {
		// TODO Auto-generated constructor stub
		mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layout, parent, false);
		mConvertView.setTag(this);
	}

	public static CommenViewHolder getViewHolder(Context context, View convertView, int layoutId, ViewGroup parent,
			int position) {
		CommenViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new CommenViewHolder(context, layoutId, parent);
		} else {
			viewHolder = (CommenViewHolder) convertView.getTag();
		}
		viewHolder.mPosition = position;
		return viewHolder;
	}

	public View getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return view;
	}

	public View getConvertView() {
		return mConvertView;
	}

	public CommenViewHolder setText(int viewId, String text) {
		((TextView) getView(viewId)).setText(text);
		return this;
	}

	public CommenViewHolder setImageResource(int viewId, int resId) {
		setImageResourceAndListener(viewId, resId, null);
		return this;
	}

	public CommenViewHolder setImageResourceAndListener(int viewId, int resId, OnClickListener listener) {
		((ImageView) getView(viewId)).setImageResource(resId);
		((ImageView) getView(viewId)).setOnClickListener(listener);
		return this;
	}
}
