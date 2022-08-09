package com.vms.ykt.TestActivity

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vms.ykt.R
import kotlin.IllegalArgumentException

class SlideMenu @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    lateinit var contView: View;
    lateinit var view: View;

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMeasure = MeasureSpec.getSize(widthMeasureSpec)
        val heightMeasure = MeasureSpec.getSize(heightMeasureSpec)
        val mod = MeasureSpec.getMode(widthMeasureSpec)

        val layoutParams = contView.layoutParams;
        val width = layoutParams.width;
        val height = layoutParams.height;

        var widthSpec = MeasureSpec.makeMeasureSpec(widthMeasure, MeasureSpec.AT_MOST)
        var heightSpec = MeasureSpec.makeMeasureSpec(heightMeasure, MeasureSpec.AT_MOST)

        if (width != LayoutParams.MATCH_PARENT && width != LayoutParams.WRAP_CONTENT) {
            widthSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY)
        } else if (width == LayoutParams.MATCH_PARENT) {
            widthSpec = MeasureSpec.makeMeasureSpec(widthMeasure, MeasureSpec.EXACTLY)
        }

        if (height != LayoutParams.MATCH_PARENT && height != LayoutParams.WRAP_CONTENT) {
            heightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        } else if (width == LayoutParams.MATCH_PARENT) {
            heightSpec = MeasureSpec.makeMeasureSpec(heightMeasure, MeasureSpec.EXACTLY)
        }
        contView.measure(widthSpec, heightSpec)

        val contViewWidthMeasure = contView.measuredWidth
        val contViewHeightMeasure = contView.measuredHeight


        view.measure(
            MeasureSpec.makeMeasureSpec(width / 4 * 2, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(contViewHeightMeasure, MeasureSpec.EXACTLY)
        );

        setMeasuredDimension(contViewWidthMeasure + view.measuredWidth, contViewHeightMeasure)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        TODO("Not yet implemented")
    }

    fun initAttrs(attrs: AttributeSet? = null) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.FlowLayout, 0, 0).apply {
            getInt(R.styleable.SlideMenu_sliderStyles, 0)
            recycle();
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initView()
    }

    fun initView() {
        if (childCount > 1) throw IllegalArgumentException("1111");
        contView = getChildAt(0);
        view = LayoutInflater.from(context).inflate(R.layout.test_page_view, this, false);
        addView(view)
    }
}