package com.shamanland.fab;

import android.content.Context;
import android.os.Build;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

public abstract class ScrollDetector extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {
    private final GestureDetector mDetector;
    private final int mSlop;

    private float mDownY;
    private boolean mDirection;
    private boolean mIgnore;

    public abstract void onScrollDown();

    public abstract void onScrollUp();

    public void setIgnore(boolean ignore) {
        mIgnore = ignore;
    }

    public ScrollDetector(Context context) {
        mDetector = new GestureDetector(context, this);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
            //noinspection deprecation
            mSlop = ViewConfiguration.getTouchSlop() * 2;
        } else {
            mSlop = ViewConfiguration.get(context).getScaledPagingTouchSlop();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mDetector.onTouchEvent(event);
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        mDownY = e.getY();
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (mIgnore) {
            return false;
        }

        if (mDirection != distanceY > 0) {
            mDirection = !mDirection;
            mDownY = e2.getY();
        }

        float distance = mDownY - e2.getY();

        if (distance < -mSlop) {
            onScrollDown();
        } else if (distance > mSlop) {
            onScrollUp();
        }

        return false;
    }
}
