package charming.utils;

import android.content.Context;

public class DensityUtils {
	public static int dp2px(Context context, int dpValue){
		float density = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue*density);
	}
	public static int sp2dp(Context context, int spValue){
		float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue*scaledDensity);
	}
}
