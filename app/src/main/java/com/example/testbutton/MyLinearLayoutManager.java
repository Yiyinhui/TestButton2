package com.example.testbutton;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

public class MyLinearLayoutManager extends LinearLayoutManager {
    public MyLinearLayoutManager(Context context) {
        super(context);
    }

    public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {//reverseLayout: Defines if layout should be calculated from end to start.
        super(context, orientation, reverseLayout);
    }

    public MyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /*
    重写smoothScrollToPosition()
     */
    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        RecyclerView.SmoothScroller smoothScroller = new CenterSmoothScroller(recyclerView.getContext());
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    private class CenterSmoothScroller extends LinearSmoothScroller {
        CenterSmoothScroller(Context context) {
            super(context);
        }

        @Nullable
        @Override
        public PointF computeScrollVectorForPosition(int targetPosition) {
            return MyLinearLayoutManager.this.computeScrollVectorForPosition(targetPosition);
        }

        //控制滑动的位置
        @Override
        public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
            switch (snapPreference) {
                case SNAP_TO_START:
                    return boxStart - viewStart;
                case SNAP_TO_END:
                    return boxEnd - viewEnd;
                case SNAP_TO_ANY:
                    final int dtStart = boxStart - viewStart;
                    if (dtStart > 0) {
                        return dtStart;
                    }
                    final int dtEnd = boxEnd - viewEnd;
                    if (dtEnd < 0) {
                        return dtEnd;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("snap preference should be one of the"
                            + " constants defined in SmoothScroller, starting with SNAP_");
            }
            return 0;


            //return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2);
        }

        //控制滑动的速度
        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return 0.1f;
        }

        //控制停止的位置
        @Override
        protected int getVerticalSnapPreference() {
            //Align child view's left or top with parent view's left or top，类似的还有SNAP_TO_END，SNAP_TO_ANY
            return SNAP_TO_ANY;
        }
    }












}
