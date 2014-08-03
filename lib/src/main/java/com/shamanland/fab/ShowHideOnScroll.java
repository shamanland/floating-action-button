package com.shamanland.fab;

import android.view.View;

public class ShowHideOnScroll extends ScrollDetector {
    private final View mView;

    public ShowHideOnScroll(View view) {
        super(view.getContext());
        mView = view;
    }

    @Override
    public void onScrollDown() {
        if (mView.getVisibility() != View.VISIBLE) {
            mView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onScrollUp() {
        if (mView.getVisibility() == View.VISIBLE) {
            mView.setVisibility(View.GONE);
        }
    }
}
