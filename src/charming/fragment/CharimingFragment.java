package charming.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CharimingFragment extends Fragment {
	private static String TAG = "tag";

	private CharimingFragment() {
	};

	public static CharimingFragment getInstance(String text) {
		CharimingFragment myFragment = new CharimingFragment();
		Bundle bundle = new Bundle();
		bundle.putString(TAG, text);
		myFragment.setArguments(bundle);
		return myFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		TextView textView = null;
		Bundle bundle = getArguments();
		if (bundle != null) {
			textView = new TextView(getActivity());
			textView.setText(bundle.getString(TAG));
			textView.setGravity(Gravity.CENTER);
		}
		return textView;
	}
}
