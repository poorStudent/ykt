package com.vms.ykt.TestActivity

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.vms.ykt.R
import kotlinx.coroutines.*

class TestView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), LifecycleObserver {
    private var pant: Paint = Paint().apply {
        style = Paint.Style.STROKE;
        strokeWidth = 3.0f;
        color = ContextCompat.getColor(context, R.color.purple_200);

    }
    private var paintText: Paint = Paint().apply {
        style = Paint.Style.STROKE;
        strokeWidth = 3.0f;
        textSize = 30f;
        typeface = Typeface.DEFAULT_BOLD;
        color = ContextCompat.getColor(context, R.color.purple_200);

    }
    private var paintCircle: Paint = Paint().apply {
        style = Paint.Style.STROKE;
        strokeWidth = 3.0f;
        pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f);
        color = ContextCompat.getColor(context, R.color.purple_200);

    }
    private var paintCircleLine: Paint = Paint().apply {
        style = Paint.Style.STROKE;
        strokeWidth = 3.0f;
        color = ContextCompat.getColor(context, R.color.teal_200);

    }
    var mWidth = 0.1f;
    var mHeight = 0.1f;

    var cXPoint = 0.1f;
    var cYPoint = 0.1f;

    var sXPoint = 0f;
    var sYPoint = 0f;

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mWidth = w.toFloat();
        mHeight = h.toFloat();

        cXPoint = mWidth / 2f;
        cYPoint = mHeight / 2f;

        super.onSizeChanged(w, h, oldw, oldh)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas);
        DrawWork(canvas);
    }


    private fun DrawWork(canvas: Canvas?) {

        canvas?.apply {
            drawColor(ContextCompat.getColor(context, R.color.design_default_color_on_secondary));
            drawLine(sXPoint, cYPoint, mWidth, cYPoint, pant);
            drawLine(cXPoint, sYPoint, cXPoint, mHeight, pant);
            drawLine(sXPoint, cYPoint / 2 * 3f, mWidth, cYPoint / 2 * 3f, pant);

            drawRect(cXPoint / 2, sYPoint + 100f, cXPoint / 2 * 3, sYPoint + 200f, pant);
            drawText("Test.TestView", cXPoint / 1.3f, sYPoint + 150f, paintText);

            var rsx = cXPoint;
            var rsy = cYPoint / 2 * 3f;
            var rs = if (rsx > cYPoint / 2f) cYPoint / 2 - 5f else rsx - 5f;
            drawCircle(rsx, rsy, rs, paintCircle);
            drawLine(this, rs);
        }

    }

    private var mRota = 10f;
    private fun drawLine(canvas: Canvas?, wt: Float) {
        canvas?.apply {
            translate(cXPoint, cYPoint / 2 * 3f);
            rotate(-mRota);
            drawLine(0f, 0f, wt, 0f, paintCircleLine);
        };

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startRotationLine() {
       job= CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                delay(700);
                mRota+=5f;
                invalidate();
            }
        }
    }
    private var job : Job ?=null;
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopRotationLine() {
        job?.cancel();// STOPSHIP: 2022/7/26
    }
}