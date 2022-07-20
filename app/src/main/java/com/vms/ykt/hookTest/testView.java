package com.vms.ykt.hookTest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.vms.ykt.R;

@SuppressLint("AppCompatCustomView")
public class testView extends TextView {

    public testView(Context context) {
        super(context);
    }

    public testView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.test2);
    }

    public testView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray vTypedArray =context.obtainStyledAttributes(attrs,R.styleable.test1,defStyleAttr,R.style.testS_df);
    }


}
