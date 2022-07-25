package com.vms.ykt.TestActivity

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.vms.ykt.R


class TestEditor @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {
    private val icon: Drawable? =
        ContextCompat.getDrawable(context, R.drawable.ic_baseline_link_24);
    private val icon_link: Drawable? =
        ContextCompat.getDrawable(context, R.drawable.ic_baseline_link_24);
    private val icon_link_off: Drawable? =
        ContextCompat.getDrawable(context, R.drawable.ic_baseline_link_off_24);
    private var ic: Drawable? = null;
    private var link_click: Boolean = false;
    var icon_type = 1;


    init {

    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        updataInco(text, icon_type);

        super.onTextChanged(text, start, lengthBefore, lengthAfter)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        event?.let { et ->
            this.ic?.let {
                if (et.x > width - it.intrinsicWidth
                    && et.x < width
                    && et.y > height / 2 - it.intrinsicHeight / 2
                    && et.y < height / 2 + it.intrinsicHeight / 2
                    && et.action == MotionEvent.ACTION_UP
                    && icon_type == 1
                ) text?.clear() else updataInco("", 2);
            }
        };

        performClick();
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    private fun updataInco(text: CharSequence?, type: Int) {

        when (type) {
            1 -> {
                ic = if (text?.trim()?.isNotEmpty() == true) icon else null;
            };
            2 -> {
                ic = if (link_click) icon_link_off else icon_link;
                isShowPassW(link_click)
                link_click = !link_click

            }

        }
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, ic, null);
    }

    private fun isShowPassW(boolean: Boolean) {
        if (boolean)
            transformationMethod = HideReturnsTransformationMethod.getInstance()
        else
            transformationMethod = PasswordTransformationMethod.getInstance();
    }
    public fun setEditType(type: Int){
        this.icon_type=type;
        updataInco("",type);
    }
}