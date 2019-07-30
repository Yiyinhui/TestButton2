package com.example.testbutton.Fragment;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class FixedSpeedScroll extends Scroller {
    private static int mDuration = 1000;
    //private int mDuration = 2000;


    public FixedSpeedScroll(Context context) {
        super(context);
    }

    public FixedSpeedScroll(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public FixedSpeedScroll(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }


    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public static void setDuration(int time) {
        mDuration = time;
    }

    public int getmDuration() {
        return mDuration;
    }
}
