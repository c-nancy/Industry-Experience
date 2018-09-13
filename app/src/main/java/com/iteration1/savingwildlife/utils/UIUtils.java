package com.iteration1.savingwildlife.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class UIUtils {
    private static Toast toast;

    // This method is to adjust the size of imageview
    // @Return: the layout params of the viewgroup
    public static ViewGroup.LayoutParams adjustImageSize(Drawable r, View v) {
        // First get width and height of this drawable
        int width = r.getIntrinsicWidth();
        int height = r.getIntrinsicHeight();
        // Calculate the scale ratio of this drawable
        double ratio = (double) width / height;

        // Get layout params of this imageview
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        // Calculate the actual width in device first, and the height accordingly
        layoutParams.width = (int) Math.floor(Resources.getSystem().getDisplayMetrics().widthPixels * 0.95);
        layoutParams.height = (int) Math.floor(Resources.getSystem().getDisplayMetrics().widthPixels / ratio + 0.5);
        // Set the according height/weight to this imageview

        return layoutParams;
    }


<<<<<<< HEAD

    public static void showCenterToast(Context context,
                                       String content) {
=======
    public static void showCenterToast(Context context,
                                 String content) {
>>>>>>> origin/master
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
