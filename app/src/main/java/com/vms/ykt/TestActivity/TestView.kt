package com.vms.ykt.TestActivity

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import androidx.core.graphics.withRotation
import androidx.core.graphics.withTranslation
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.vms.ykt.R
import kotlinx.coroutines.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

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
    private var paintCircleFill: Paint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE;
        strokeWidth = 3.0f;
        color = ContextCompat.getColor(context, R.color.teal_200);

    }

    private lateinit var SinePath: Path

    var mWidth = 0.1f;
    var mHeight = 0.1f;

    var cXPoint = 0.1f;
    var cYPoint = 0.1f;

    var sXPoint = 0f;
    var sYPoint = 0f;

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mWidth = w.toFloat();
        mHeight = h.toFloat();
        Log.d(TAG, "onSizeChanged: $w  $h")
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

            val rsx = cXPoint;
            val rsy = cYPoint / 2 * 3f;
            val rs = if (rsx > cYPoint / 2f) cYPoint / 2 - 5f else rsx - 5f;
            drawCircle(rsx, rsy, rs, paintCircle);
            drawCircleLine(this, rs);
            drawCircleFill(this, rs);
            drawSine(this, rs);
        }

    }

    private var mRota = 10f;

    private fun drawCircleLine(canvas: Canvas?, rs: Float) {
        canvas?.apply {
            withTranslation(cXPoint, cYPoint / 2 * 3f) {
                withRotation(-mRota) {
                    drawLine(0f, 0f, rs, 0f, paintCircleLine);
                }
            }

        };

    }

    val TAG = this::class.java.name

    private fun drawCircleFill(canvas: Canvas?, rs: Float) {
        canvas?.apply {
            withTranslation(cXPoint, cYPoint) {
                drawCircle(rs * cos(mRota.toHuDu()), 0f, 10f, paintCircleFill);
            }


            //  Log.d(TAG, "drawCircleFill: ${rs * cos(mRota.toHuDu())}")

            withTranslation(cXPoint, cYPoint / 2 * 3f) {
                drawCircle(rs * cos(mRota.toHuDu()), 0f, 10f, paintCircleFill);
            }


            val x = rs * cos(mRota.toHuDu())
            val y = rs * sin(mRota.toHuDu())
            withTranslation(cXPoint, cYPoint) {
                withTranslation(x, -y) {
                    drawLine(0f, 0f, 0f, y, paintCircleLine);
                    drawLine(0f, 0f, 0f, -(cYPoint / 2 * 3f - y), paintCircleLine);
                }
            }
        };

    }

    private fun drawSine(canvas: Canvas?, rs: Float) {
        canvas?.apply {
            withTranslation(cXPoint, cYPoint) {
                val count = 66
                val item = cXPoint / count / 3;
                SinePath = Path();
                SinePath.moveTo(rs * cos(mRota.toHuDu()), 0f)
                repeat(count) {
                    val x = rs * cos(it * -0.15 + mRota.toHuDu()).toFloat();
                    val y = -(it * item);
                    SinePath.quadTo(x, y, x, y);
                }

                drawPath(SinePath, paintCircleLine);
                drawTextOnPath("TestView-----", SinePath, 100f, 0f, paintText);
            }
        };

    }

    private fun Float.toHuDu() = this / 180 * PI.toFloat();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startRotationLine() {
        job = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                delay(100);
                mRota += 5f;
                invalidate();
            }
        }
    }

    private var job: Job? = null;

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopRotationLine() {
        job?.cancel();// STOPSHIP: 2022/7/26
    }
}