package com.shamanland.fab;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

public class FloatingActionButton extends ImageView {
    public static final int SIZE_NORMAL = 0;
    public static final int SIZE_MINI = 1;

    private int mSize;
    private int mColor;

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
        mSize = SIZE_NORMAL;
        mColor = Color.WHITE;

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

        TypedArray a = theme.obtainStyledAttributes(attrs, R.styleable.FloatingActionButton, 0, 0);
        if (a == null) {
            return;
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

    private void initBackground() {
        if (true) {
            return;
        }

        Drawable background = null;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            //noinspection deprecation
            setBackgroundDrawable(background);
        } else {
            setBackground(background);
        }
    }

    /**
     * @param size one of {@link #SIZE_NORMAL} or {@link #SIZE_MINI}
     */
    public void setSize(int size) {
        mSize = size;
    }

    public void setColor(int color) {
        mColor = color;
    }
}
