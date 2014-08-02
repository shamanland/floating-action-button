package com.shamanland.fab;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FloatingActionButton extends ImageView {
    public static final int SIZE_NORMAL = 0;
    public static final int SIZE_MINI = 1;

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
    }

    private void initAttrs(TypedArray a) {
        setSize(a.getInteger(R.styleable.FloatingActionButton_fabSize, SIZE_NORMAL));
    }

    /**
     * @param size one of {@link #SIZE_NORMAL} or {@link #SIZE_MINI}
     */
    public void setSize(int size) {
        int value = getSizeOf(size);

        if (getWidth() == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMinimumWidth(value);
            setMaxWidth(value);
        }

        if (getHeight() == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMinimumHeight(value);
            setMaxHeight(value);
        }
    }

    /**
     * @param size one of {@link #SIZE_NORMAL} or {@link #SIZE_MINI}
     * @return size in pixels
     */
    public int getSizeOf(int size) {
        switch (size) {
            case SIZE_NORMAL:
                return getResources().getDimensionPixelSize(R.dimen.com_shamanland_fab_size);
            case SIZE_MINI:
                return getResources().getDimensionPixelSize(R.dimen.com_shamanland_fab_size_mini);
        }

        throw new IllegalArgumentException(String.valueOf(size));
    }
}
