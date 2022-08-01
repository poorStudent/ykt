package com.vms.ykt.TestActivity

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.vms.ykt.R
import com.vms.ykt.Util.Tool
import kotlinx.android.synthetic.main.cqooc_recycler_item.view.*
import kotlin.math.max


class flowLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    var maxLines: Int? = null;
    var marginHorizontal: Float? = null;
    var marginVertical: Float? = null;
    var textMaxlength: Int? = null;
    var textColor: Int? = null;
    var typeface: Int? = null;
    var textStyle: Int? = null;
    var fontPath: String? = null;
    var borderColor: Int? = null;
    var borderWidth: Float? = null;
    var borderRadio: Float? = null;

    var mlines: Int? = null;

    init {
        initAttrs(attrs);
    }

    private fun initAttrs(attrs: AttributeSet? = null) {
        val attr = context.theme.obtainStyledAttributes(attrs, R.styleable.FlowLayout, 0, 0)
            .run {
                try {
                    val color = ContextCompat.getColor(context, R.color.teal_200);
                    borderColor = getColor(R.styleable.FlowLayout_borderColor, color);
                    textColor = getColor(R.styleable.FlowLayout_textColor, color);
                    borderWidth = getDimension(
                        R.styleable.FlowLayout_borderWidth,
                        Tool.dp2px(context, 1f).toFloat()
                    );
                    borderRadio = getDimension(
                        R.styleable.FlowLayout_borderRadio,
                        Tool.dp2px(context, 1f).toFloat()
                    );
                    marginHorizontal = getDimension(
                        R.styleable.FlowLayout_marginHorizontal,
                        Tool.dp2px(context, 1f).toFloat()
                    );
                    marginVertical = getDimension(
                        R.styleable.FlowLayout_marginVertical,
                        Tool.dp2px(context, 1f).toFloat()
                    );
                    typeface = getInteger(R.styleable.FlowLayout_typeface, 0);
                    textStyle = getInteger(R.styleable.FlowLayout_textStyle, 0);
                    fontPath = getString(R.styleable.FlowLayout_fontPath);
                    textMaxlength = getInteger(R.styleable.FlowLayout_textMaxlength, 2);
                    mlines = getInteger(R.styleable.FlowLayout_lines, 3);
                    maxLines = getInteger(R.styleable.FlowLayout_maxLines, 3);

                } finally {
                    recycle();
                }
            }

    }

    val lines = mutableListOf<MutableList<View>>();

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val childCounts = childCount;
        if (childCounts == 0) return;

        val width = MeasureSpec.getSize(widthMeasureSpec);
        val height = MeasureSpec.getSize(heightMeasureSpec);
        val mod = MeasureSpec.getMode(widthMeasureSpec);

        val widthMeasure = MeasureSpec.makeMeasureSpec(width, mod);
        val heightMeasure = MeasureSpec.makeMeasureSpec(height, mod);

        var line = mutableListOf<View>();

        repeat(childCounts) {
            val child = getChildAt(it);
            if (child.visibility == View.GONE) return@repeat

            measureChild(child, widthMeasure, heightMeasure)

            if (line.size == 0) {
                line.add(child);
            } else {
                if (checkAddView(line, child, width)) {
                    line.add(child)
                } else {
                    lines.addAll(listOf(line))
                    line = mutableListOf();
                    line.add(child)

                }
            }

        }
        val child = getChildAt(0);
        val childH = child.measuredHeight;
        var pdt = paddingTop
        var pdb = paddingBottom;
        var tH = childH * lines.size+ marginVertical!!.toInt() * lines.size;

        setMeasuredDimension(width, tH);
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left = 0
        var top = 0;
        var right = 0;
        var bottom = 0
        val top1 = getChildAt(0);
        for (viewList in lines) {
            top += marginVertical!!.toInt();
            bottom += top1.measuredHeight;

            for (view in viewList) {
                left += marginHorizontal!!.toInt();
                right += view.measuredWidth
                view.layout(left, top, right, bottom)
                left += view.measuredWidth
                right += marginHorizontal!!.toInt();


            }
            left = 0
            right = 0;
            top += top1.measuredHeight;
            bottom += marginVertical!!.toInt();

        }
    }

    fun checkAddView(list: List<View>, view: View, int: Int): Boolean {
        val pdl= paddingStart
        val pdr= paddingEnd
        val wt = view.measuredWidth;
        var width = 0;
        width += marginHorizontal!!.toInt();
        for (child in list) {
            width += child.measuredWidth;
            width += marginHorizontal!!.toInt();
        }
        width += wt;
        return width <= int
    }

    fun getChildMeasureSpecs(spec: Int, padding: Int, childDimension: Int): Int {
        val specMode = MeasureSpec.getMode(spec) //返回父View的测量模式
        val specSize = MeasureSpec.getSize(spec) //返回父View的测量大小
        val size =
            Math.max(0, specSize - padding) //父View的测量大小 - 父View的padding占用的大小，剩余的即是子View可用的最大空间
        var resultSize = 0
        var resultMode = 0
        when (specMode) {
            MeasureSpec.EXACTLY -> if (childDimension >= 0) {  //子View大小为具体数值的情况
                resultSize = childDimension
                resultMode = MeasureSpec.EXACTLY
            } else if (childDimension == LayoutParams.MATCH_PARENT) {   //子View大小为match_parent的情况
                // Child wants to be our size. So be it.
                resultSize = size
                resultMode = MeasureSpec.EXACTLY
            } else if (childDimension == LayoutParams.WRAP_CONTENT) {   //子View大小为wrap_content的情况
                // Child wants to determine its own size. It can't be
                // bigger than us.
                resultSize = size
                resultMode = MeasureSpec.AT_MOST
            }
            MeasureSpec.AT_MOST -> if (childDimension >= 0) {
                // Child wants a specific size... so be it
                resultSize = childDimension
                resultMode = MeasureSpec.EXACTLY
            } else if (childDimension == LayoutParams.MATCH_PARENT) {
                // Child wants to be our size, but our size is not fixed.
                // Constrain child to not be bigger than us.
                resultSize = size
                resultMode = MeasureSpec.AT_MOST
            } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                // Child wants to determine its own size. It can't be
                // bigger than us.
                resultSize = size
                resultMode = MeasureSpec.AT_MOST
            }
            MeasureSpec.UNSPECIFIED -> if (childDimension >= 0) {
                // Child wants a specific size... let him have it
                resultSize = childDimension
                resultMode = MeasureSpec.EXACTLY
            } else if (childDimension == LayoutParams.MATCH_PARENT) {
                // Child wants to be our size... find out how big it should
                // be
                resultSize = 0
                resultMode = MeasureSpec.UNSPECIFIED
            } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                // Child wants to determine its own size.... find out how
                // big it should be
                resultSize = 0
                resultMode = MeasureSpec.UNSPECIFIED
            }
        }
        return MeasureSpec.makeMeasureSpec(resultSize, resultMode)
    }

    fun getChildMaxHegith(lists: List<View>): Int {
        var max = 0;
        if (lists.size == 0) return max;
        for (child in lists) {
            max = max(child.measuredHeight, max)
        }
        return max
    }

    fun initView(layout: Int) {
        val view = LayoutInflater.from(context).inflate(R.layout.test_page_view, this, false);
        view.parent?.let {
        }
        removeAllViews();
        repeat(5) {
            val text = TextView(context);
            setFontStyle(text)
            addView(text)
        }
    }

    var mData = mutableListOf<String>();

    fun setData(data: List<String>) {
        mData.clear();
        mData.addAll(data);
    }

    fun setMaxLines() {

    }

    fun setTextMaxlength() {

    }

    fun setTextColor() {

    }


    fun setBorderRadio() {

    }

    fun setBorderColor() {

    }

    fun setBorderWidth() {

    }

    fun setMarginHorizontal() {

    }

    fun setMarginVertical() {

    }

    fun setFontStyle(text: TextView) {
        if (fontPath.isNullOrEmpty() && typeface == 0 && textStyle == 0) {
            return
        }
        var tf: Typeface;
        when (typeface) {
            0 -> {
                tf = Typeface.DEFAULT
            }
            1 -> {
                tf = Typeface.SANS_SERIF
            }
            2 -> {
                tf = Typeface.SERIF
            }
            else -> {
                tf = Typeface.MONOSPACE
            }
        }
        if (!fontPath.isNullOrEmpty()) {
            tf = Typeface.createFromFile(fontPath);
        }
        text.setTypeface(tf, textStyle!!);
    }


}