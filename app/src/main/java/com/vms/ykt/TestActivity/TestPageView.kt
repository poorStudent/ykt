package com.vms.ykt.TestActivity

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.vms.ykt.R
import com.vms.ykt.Util.Tool
import kotlinx.android.synthetic.main.page_item_view.view.*
import kotlinx.android.synthetic.main.test_page_view.view.*
import kotlinx.coroutines.*


class TestPageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var bindTitleListener: BindTitleListener? = null;
    var view: View;
    var mPagerAdapter: pagerAdapter;
    var imagCount: Int = 0;
    val TAG: String? = TestPageView::class.simpleName;
    var isFastSorll = true;
    private var pointList: MutableList<View> = mutableListOf();

    init {
        view = LayoutInflater.from(context).inflate(R.layout.test_page_view, this, true);
        mPagerAdapter = pagerAdapter();
    }

    public fun initPagerAdapter(imagCount: Int) {
        mPagerAdapter.imagCount = imagCount;
        this.imagCount = imagCount;
        initView();
        initPoint();
        initEvent();
        Log.d(TAG, "initPagerAdapter: ")
    }

    private fun initView(){
        val position = (Int.MAX_VALUE / 2 - 1) + (Int.MAX_VALUE / 2 - 1).mod(imagCount - 1);
        view.page.let {
            it.adapter = mPagerAdapter;
            it.currentItem = position;
            it.offscreenPageLimit=3
        };
    }

    private fun initPoint() {
        repeat(imagCount) {
            val v = View(context);

            v.background = ContextCompat.getDrawable(context, R.drawable.page_point_nopress)

            val layoutParams = LinearLayout.LayoutParams(
                Tool.dp2px(context, 5f),
                Tool.dp2px(context, 5f)
            );
            layoutParams.setMargins(Tool.dp2px(context, 5f), 0, Tool.dp2px(context, 5f), 0)
            v.layoutParams = layoutParams
            view.point.addView(v);
            pointList.add(v);
        }
    }

    private fun upPointColor(position: Int) {
        var pos =0;
        pointList.let {
            it.forEach { v ->
                if (pos==position) v.background =
                    ContextCompat.getDrawable(context, R.drawable.page_point_press)
                else
                    v.background =
                        ContextCompat.getDrawable(context, R.drawable.page_point_nopress)
                ++pos;
            }
        }
    }

    private fun initEvent() {
        view.page.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (isFastSorll) {
                    bindTitleListener?.let {
                        view.title.text = it.getTitle(0);

                    }
                    upPointColor(0);
                    isFastSorll = false;
                }


            }

            override fun onPageScrollStateChanged(state: Int) {

                super.onPageScrollStateChanged(state)
            }

            override fun onPageSelected(position: Int) {

                super.onPageSelected(position);
                val positions = position.mod(imagCount);
                bindTitleListener?.let {
                    view.title.text = it.getTitle(positions);
                }
                upPointColor(positions);
            }
        })
        view.page.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    when(event?.action){

                        MotionEvent.ACTION_DOWN -> {
                            Log.d(TAG, "onTouch: ACTION_DOWN")
                            stopPageSelected();
                        }
                        MotionEvent.ACTION_CANCEL->{
                            Log.d(TAG, "onTouch: ACTION_CANCEL")
                            startPageSelected();
                        }
                        MotionEvent.ACTION_UP->{
                            Log.d(TAG, "onTouch: ACTION_UP")

                            startPageSelected();
                        }

                    }
                return false
            }

        })
    }

    var cScope : CoroutineScope=CoroutineScope(Dispatchers.Main);

    private fun startPageSelected(){
        cScope.launch {
            while (true){
                delay(2000)
                view.page.apply {
                    currentItem=currentItem+1
                }

            }
        }

    }

    private fun stopPageSelected(){
        cScope.cancel()

    }

    public fun setBindImageListener(bindImageListener: BindImageListener) {
        mPagerAdapter.bindImageListener = bindImageListener;
    }

    fun setPageItemOnClick(pageItemOnClick: PageItemOnClick){
        mPagerAdapter.pageItemOnClick=pageItemOnClick;
    }

    override fun onDetachedFromWindow() {
        Log.d(TAG, "onDetachedFromWindow: ")
        stopPageSelected();
        super.onDetachedFromWindow()
    }

    override fun onAttachedToWindow() {
        Log.d(TAG, "onAttachedToWindow: ")
        startPageSelected();
        super.onAttachedToWindow()
    }

    public interface BindTitleListener {
        fun getTitle(position: Int): String
    }

    public interface BindImageListener {
        fun getImage(position: Int): Any
    }

    public interface PageItemOnClick{
        fun ItemOnClick(view: View,position: Int)

        fun ItemOnLongClick(view: View,position: Int):Boolean
        {
            return false;
        }
    }

    class pagerAdapter : RecyclerView.Adapter<pagerHold>() {
        var bindImageListener: BindImageListener? = null;
        var pageItemOnClick : PageItemOnClick?=null;
        var imagCount: Int = 0;

        val TAG = this::class.simpleName;

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): pagerHold {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.page_item_view, parent, false);
            val pagerHold = pagerHold(view);
            Log.d(TAG, "onCreateViewHolder: ")

            return pagerHold;
        }

        override fun getItemCount(): Int {
            return Int.MAX_VALUE;

        }

        override fun onBindViewHolder(holder: pagerHold, position: Int) {

            val positions = position.mod(imagCount);
            holder.apply {
                bindImageListener?.let {
                    Glide.with(imageView.context)
                        .load(bindImageListener?.getImage(positions))
                        .error(R.drawable.ic_baseline_clear_24)
                        .fitCenter()
                        .into(imageView)
                }

              /**  pageItemOnClick?.let{
                    holder.itemView.apply {
                        setOnClickListener(object : OnClickListener{

                            override fun onClick(v: View?) {
                                it.ItemOnClick( holder.itemView,positions)
                            }

                        })
                        setOnLongClickListener(object : OnLongClickListener{
                            override fun onLongClick(v: View?): Boolean {
                                return it.ItemOnLongClick(holder.itemView,positions)
                            }
                        })
              }
            }*/


            }
        }
        
        override fun getItemViewType(position: Int): Int {
            return super.getItemViewType(position)
        }

        override fun getItemId(position: Int): Long {
            return super.getItemId(position)
        }
    }

    class pagerHold(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView = itemView.imageView;

    }

}
