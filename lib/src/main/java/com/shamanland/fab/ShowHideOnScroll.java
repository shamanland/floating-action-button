package com.shamanland.fab;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class ShowHideOnScroll extends ScrollDetector implements Animation.AnimationListener {
    private final View mView;
    private final int mShow;
    private final int mHide;

    public ShowHideOnScroll(View view) {
        this(view, R.anim.floating_action_button_show, R.anim.floating_action_button_hide);
    }

    public ShowHideOnScroll(View view, int animShow, int animHide) {
        super(view.getContext());
        mView = view;
        mShow = animShow;
        mHide = animHide;
    }

    @Override
    public void onScrollDown() {
        if (mView.getVisibility() != View.VISIBLE) {
            mView.setVisibility(View.VISIBLE);
            animate(mShow);
        }
    }

    @Override
    public void onScrollUp() {
        if (mView.getVisibility() == View.VISIBLE) {
            mView.setVisibility(View.GONE);
            animate(mHide);
        }
    }

    private void animate(int anim) {
        if (anim != 0) {
            Animation a = AnimationUtils.loadAnimation(mView.getContext(), anim);
            a.setAnimationListener(this);

            mView.startAnimation(a);
            setIgnore(true);
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {
        // empty
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        setIgnore(false);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // empty
    }
}
