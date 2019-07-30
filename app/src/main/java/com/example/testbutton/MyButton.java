package com.example.testbutton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class MyButton extends View {    //自定义button，继承view
    /**
     * 开关按钮的背景
     */
    private Bitmap backgroundBitmap;
    /**
     * 开关按钮的滑动部分
     */
    private Bitmap slideButton;
    /**
     * 滑动按钮的左边界
     */
    private float slideBtn_left;
    /**
     * 当前开关的状态
     */
    private boolean currentState = false;




    public MyButton(Context context) {//是在java代码创建视图的时候被调用(使用new的方式)，如果是从xml填充的视图，就不会调用这个
        super(context);
        initView();
    }

    public MyButton(Context context, AttributeSet attrs) {//这个是在xml创建但是没有指定style的时候被调用
        super(context, attrs);
        initView();
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {//这个是在第二个基础上添加style的时候被调用的
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化view
     */
    private void initView() {
        Log.d("init","initView");
        backgroundBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_jiahao);
        slideButton = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_jiantou);

        /*
         * 点击事件
         */
        setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                currentState = !currentState;
                flushState();
                flushView();
            }
        });
    }


    /**
     * 测量尺寸时的回调方法
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 设置当前view的大小 width:view的宽，单位都是像素值 heigth:view的高，单位都是像素值
        setMeasuredDimension(backgroundBitmap.getWidth(),
                backgroundBitmap.getHeight());
    }

    // 这个方法对于自定义view的时候帮助不大，因为view的位置一般由父组件来决定的
    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 画view的方法,绘制当前view的内容
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);

        Paint paint = new Paint();
        // 打开抗锯齿
        paint.setAntiAlias(true);

        // 画背景
        canvas.drawBitmap(backgroundBitmap, 0, 0, paint);
        // 画滑块
        canvas.drawBitmap(slideButton, slideBtn_left, 0, paint);
    }


    /**
     * 刷新视图
     */
    protected void flushView() {
        // 刷新当前view会导致ondraw方法的执行
        invalidate();
    }

    /**
     * 刷新当前的状态
     */
    protected void flushState() {
        if (currentState) {
            slideBtn_left = backgroundBitmap.getWidth()
                    - slideButton.getWidth();
        } else {
            slideBtn_left = 0;
        }
    }



}
