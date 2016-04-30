package charming.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CommenAdapter<T> extends BaseAdapter{
	
	protected Context mContext;
	protected List<T> mDatas;
	protected int mLayoutId;
	public CommenAdapter(Context context, List<T> datas, int layoutId) {
		mContext=context;
		mDatas=datas;
		mLayoutId=layoutId;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		CommenViewHolder holder=CommenViewHolder.getViewHolder(mContext, convertView, mLayoutId, parent, position);
		convert(holder,getItem(position));
		return holder.getConvertView();
	}

	protected abstract void convert(CommenViewHolder holder, T t);
		
}
