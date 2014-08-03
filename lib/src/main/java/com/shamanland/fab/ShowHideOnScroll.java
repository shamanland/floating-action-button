package com.shamanland.fab;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Implementation of {@link View.OnTouchListener} which shows/hides specified view
 * when {@link #onScrollUp()} and {@link #onScrollDown()} events recognized.
 */
public class ShowHideOnScroll extends ScrollDetector implements Animation.AnimationListener {
    private final View mView;
    private final int mShow;
    private final int mHide;

    /**
     * Construct object with defaults animations {@link R.anim#floating_action_button_show} and {@link R.anim#floating_action_button_hide}.
     *
     * @param view null not allowed
     */
    public ShowHideOnScroll(View view) {
        this(view, R.anim.floating_action_button_show, R.anim.floating_action_button_hide);
    }

    /**
     * Construct object with custom animations.
     *
     * @param view     null not allowed
     * @param animShow anim resource id
     * @param animHide anim resource id
     */
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
