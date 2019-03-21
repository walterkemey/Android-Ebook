package com.daimajia.slider.library;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by admin on 08-08-2017.
 */

public class Width {

    public static int getScreenWidth(Context context) {
        int columnWidth;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        final Point point = new Point();
        point.x = display.getWidth();
        point.y = display.getHeight();
        columnWidth = point.x;
        return columnWidth;
    }

}
