package charming.utils;

import java.lang.reflect.Field;

import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.util.Log;

public class ConfigUtils {
	public static void setDrawerLayoutSize(DrawerLayout drawerLayout, int dpValue){
		Field leftDraggerField;
		Field leftCallback;
		try {
			leftDraggerField = drawerLayout.getClass().getDeclaredField("mLeftDragger");
			leftDraggerField.setAccessible(true);
			leftCallback = drawerLayout.getClass().getDeclaredField("mLeftCallback");
			leftCallback.setAccessible(true);
			Object object = leftCallback.get(drawerLayout);
			Class<?>[] declaredClasses = drawerLayout.getClass().getDeclaredClasses();
			Field declaredField = declaredClasses[3].getDeclaredField("mPeekRunnable");
			declaredField.setAccessible(true);
			declaredField.set(object, new Runnable() {
	            @Override public void run() {
	            }
	        });
			ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout); 
			Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");        
			edgeSizeField.setAccessible(true);           
			edgeSizeField.setInt(leftDragger, dpValue); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
}
