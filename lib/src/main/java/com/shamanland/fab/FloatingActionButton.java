package com.shamanland.fab;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

public class FloatingActionButton extends ImageView {
    public static final int SIZE_NORMAL = 0;
    public static final int SIZE_MINI = 1;

    private int mSize;
    private int mColor;

    public int getSize() {
        return mSize;
    }

    /**
     * @param size {@link #SIZE_NORMAL} or {@link #SIZE_MINI}
     */
    public void setSize(int size) {
        mSize = size;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public FloatingActionButton(Context context) {
        super(context);
        init(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FloatingActionButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a;

        try {
            if (isInEditMode()) {
                return;
            }

            if (attrs == null) {
                return;
            }

            Resources.Theme theme = context.getTheme();
            if (theme == null) {
                return;
            }

            a = theme.obtainStyledAttributes(attrs, R.styleable.FloatingActionButton, 0, 0);
            if (a == null) {
                return;
            }
        } finally {
            mSize = SIZE_NORMAL;
            mColor = Color.WHITE;
        }

        try {
            initAttrs(a);
        } finally {
            a.recycle();
        }

        initBackground();
    }

    private void initAttrs(TypedArray a) {
        setSize(a.getInteger(R.styleable.FloatingActionButton_fabSize, SIZE_NORMAL));
        setColor(a.getColor(R.styleable.FloatingActionButton_fabColor, Color.WHITE));
    }

    public void initBackground() {
        final int backgroundId;

        if (mSize == SIZE_MINI) {
            backgroundId = R.drawable.com_shamanland_fab_circle_mini;
        } else {
            backgroundId = R.drawable.com_shamanland_fab_circle_normal;
        }

        Drawable background = getResources().getDrawable(backgroundId);

        if (background instanceof LayerDrawable) {
            LayerDrawable layers = (LayerDrawable) background;
            if (layers.getNumberOfLayers() == 2) {
                Drawable shadow = layers.getDrawable(0);
                Drawable circle = layers.getDrawable(1);

                if (shadow instanceof GradientDrawable) {
                    ((GradientDrawable) shadow.mutate()).setGradientRadius(getShadowRadius(shadow, circle));
                }

                if (circle instanceof GradientDrawable) {
                    ((GradientDrawable) circle.mutate()).setColor(mColor);
                }
            }
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            //noinspection deprecation
            setBackgroundDrawable(background);
        } else {
            setBackground(background);
        }
    }

    protected static int getShadowRadius(Drawable shadow, Drawable circle) {
        int radius = 0;

        if (shadow != null && circle != null) {
            Rect rect = new Rect();
            radius = (circle.getIntrinsicWidth() + (shadow.getPadding(rect) ? rect.left + rect.right : 0)) / 2;
        }

        return Math.max(1, radius);
    }
}
