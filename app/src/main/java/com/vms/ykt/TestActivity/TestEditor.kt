package com.vms.ykt.TestActivity

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.vms.ykt.R



class TestEditor @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private var icon_clean: Drawable? = null;
    private var icon_link: Drawable? = null;
    private var icon_link_off: Drawable? = null;
    private var icon: Drawable? = null;
    private var icon_link_click: Boolean = false;
    private var icon_type : Int ? = 1;
    private val TAG : String = this::class.java.simpleName;

    init {
        icon_clean = ContextCompat.getDrawable(context, R.drawable.ic_baseline_clear_24);
        icon_link = ContextCompat.getDrawable(context, R.drawable.ic_baseline_link_24);
        icon_link_off = ContextCompat.getDrawable(context, R.drawable.ic_baseline_link_off_24);

        context.theme.obtainStyledAttributes(attrs, R.styleable.TestEditor, 0, 0).
        apply {
            try {
                (0..indexCount).forEach { i ->

                    var attr = getIndex(i)

                    when (attr) {
                        R.styleable.TestEditor_icon_type -> {
                            icon_type= getInteger(attr,1);
                        }

                        R.styleable.TestEditor_icon_clean -> {
                            icon_clean = getDrawable(attr)
                        }
                        R.styleable.TestEditor_icon_link -> {
                            icon_link = getDrawable(attr)

                        }
                        R.styleable.TestEditor_icon_link_off -> {
                            icon_link_off = getDrawable(attr)

                        }
                    }
                }
            } finally {
                recycle();
            }
        };
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        updataInco(text);
        super.onTextChanged(text, start, lengthBefore, lengthAfter)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        event?.let { et ->
            this.icon?.let {
                if (et.x > width - it.intrinsicWidth
                    && et.x < width
                    && et.y > height / 2 - it.intrinsicHeight / 2
                    && et.y < height / 2 + it.intrinsicHeight / 2
                    && et.action == MotionEvent.ACTION_UP) {
                    if (icon_type == 1) text?.clear() else setEditType(2);
                    performClick();
                }
            }
        };


        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        Log.i(TAG, "onFocusChanged: "+focused)
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
    }
    private fun updataInco(text: CharSequence?) {

        if (isFocused) {
            when (icon_type) {
                1 -> {
                    icon = if (text?.trim()?.isNotEmpty() == true) icon_clean else null;
                };
                2 -> {
                    icon = if (icon_link_click) icon_link_off else icon_link;
                    isShowPassW(icon_link_click)
                    icon_link_click = !icon_link_click

                }

            }
        } else {
            icon = null;
        }

        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, icon, null);
    }

    private fun isShowPassW(boolean: Boolean) {
        if (boolean)
            transformationMethod = HideReturnsTransformationMethod.getInstance()
        else
            transformationMethod = PasswordTransformationMethod.getInstance();
    }

    fun setEditType(type: Int) {
        this.icon_type = type;
        updataInco("");
    }
}