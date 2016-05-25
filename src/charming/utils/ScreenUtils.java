package charming.utils;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ScreenUtils {
	public static int getScreenHight(Context context){
//		activity.getWindowManager();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int hight = wm.getDefaultDisplay().getHeight();
		return hight;
	}
	
	public static int getScreenWidth(Context context){
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		return width;
	}
	
	public static void ignoreStutasBar(Activity activity){
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// 透明状态栏
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 透明通知栏
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}

	public static void tranStutasBar(Activity activity, int viewId){
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			View view = activity.findViewById(viewId);
			// 透明状态栏
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 透明通知栏
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			params.height = getStatusBarHeight(activity);
			view.setLayoutParams(params);
		}
	}

	public static int getStatusBarHeight(Activity activity) {
		// TODO Auto-generated method stub
		int result = 0;
		int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = activity.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}
	
	
}

