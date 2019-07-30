package com.example.testbutton;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class MyAdapterHead extends RecyclerView.Adapter {
    private int totalItem = 10;


    private Context mContext;
    private Observable mObservable;
    private RecyclerView mRecyclerView;
    private ViewPager mViewPager;

    private View mFocusedView;
    private long mFocusedPosition;

    private int pageIndex1 = 0;     //页码
    private int pageSize1 = 4;      //每页显示的item数

    private final int SCROLL = 100;
    private final int UPDATE = 99;


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SCROLL:
                    break;
                case UPDATE:
                    mFocusedPosition = mFocusedPosition + 1;

                    MyHolder viewHolder = (MyHolder) mRecyclerView.findViewHolderForAdapterPosition((int) mFocusedPosition);
                    //MyHolder viewHolder = (MyHolder)mRecyclerView.getChildViewHolder(mFocusedView);
                    Log.d("operation", "right view Holder " + mFocusedPosition + " is selected.");
                    viewHolder.btn.setFocusable(true);
                    viewHolder.btn.setFocusableInTouchMode(true);
                    viewHolder.btn.requestFocus();
                    break;


            }
        }
    };

    public MyAdapterHead(Context context, final Observable observable, RecyclerView recyclerView, ViewPager viewPager) {
        mContext = context;
        mObservable = observable;
        mRecyclerView = recyclerView;
        mViewPager = viewPager;
        //CalPageSize();
//        observable.subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(String direction) {
//                switch (direction) {
//                    case "up":
//
//                        Log.d("operation", "up");
//
//                        break;
//                    case "down":
//
//                        Log.d("operation", "down");
//
//                        break;
//                    case "left":
//                        Log.d("operation", "left");
//                        Log.d("operation", "right");
//                        if (mFocusedView == null) {
//                            //无焦点时，无反应
//                            Log.d("operation", "left mFocusedView is null");
//                        } else {
//                            /*
//                            有焦点时，点击<-，左移
//                             */
//                            //下一个item存在
//                            if ((int) mFocusedPosition > 0) {
//
//                                //焦点在界面上第一个完全显示的item， 向左滑动界面
//                                RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
//                                //判断是当前layoutManager是否为LinearLayoutManager
//                                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
//                                if (layoutManager instanceof LinearLayoutManager) {
//                                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
//                                    //焦点在界面上第一个完全显示的item，左滑
//                                    if (linearManager.findFirstCompletelyVisibleItemPosition() == mFocusedPosition && linearManager.findFirstCompletelyVisibleItemPosition() != linearManager.findFirstVisibleItemPosition()) {
//                                        if ((int) mFocusedPosition - pageSize1 >= 0) {
//                                            mRecyclerView.smoothScrollToPosition((int) mFocusedPosition - pageSize1);
//                                        } else {
//                                            mRecyclerView.smoothScrollToPosition(0);
//                                        }
//                                    }
//
//                                    if (linearManager.findFirstCompletelyVisibleItemPosition() == mFocusedPosition && linearManager.findFirstCompletelyVisibleItemPosition() == linearManager.findFirstVisibleItemPosition()) {
//                                        Log.d("operation", "Scroll ");
//
//                                        if ((int) mFocusedPosition - pageSize1 >= 0) {
//                                            mRecyclerView.smoothScrollToPosition((int) mFocusedPosition - pageSize1);
//                                        } else {
//                                            mRecyclerView.smoothScrollToPosition(0);
//                                        }
//                                        return;
//                                    }
//
//                                } else {
//                                    throw new RuntimeException();
//                                }
//
//                                //mFocusedView = mRecyclerView.getChildAt((int)mFocusedPosition+1%pageSize1);
//                                mFocusedPosition = mFocusedPosition - 1;
//
//                                MyHolder viewHolder = (MyHolder) mRecyclerView.findViewHolderForAdapterPosition((int) mFocusedPosition);
//                                //MyHolder viewHolder = (MyHolder)mRecyclerView.getChildViewHolder(mFocusedView);
//                                Log.d("operation", "right view Holder " + mFocusedPosition + " is selected.");
//                                viewHolder.btn.setFocusable(true);
//                                viewHolder.btn.setFocusableInTouchMode(true);
//                                viewHolder.btn.requestFocus();
//                            }
//
//                        }
//                        break;
//                    case "right":
//                        if (mFocusedView == null) {
//                            //无焦点时，点击->，自动获取第一个为焦点
//                            Log.d("operation", "right mFocusedView is null");
//
//                            pageIndex1 = 0;
//                            mFocusedView = mRecyclerView.getChildAt(0);
//                            mFocusedPosition = 0;
//                            if (mRecyclerView.getChildViewHolder(mFocusedView) != null) {
//                                MyHolder viewHolder = (MyHolder) mRecyclerView.getChildViewHolder(mFocusedView);
//                                Log.d("operation", "right view Holder " + mFocusedPosition + " is selected.");
//                                viewHolder.btn.setFocusable(true);
//                                viewHolder.btn.setFocusableInTouchMode(true);
//                                viewHolder.btn.requestFocus();
//                            }
//                        } else {
//
//                            /*
//                            有焦点时，点击->，右移
//                             */
//                            //下一个item存在
//                            if ((int) mFocusedPosition < totalItem - 1) {
//
//
//                                RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
//                                //判断是当前layoutManager是否为LinearLayoutManager
//                                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
//                                if (layoutManager instanceof LinearLayoutManager) {
//                                    Log.d("operation", "right mFocusedView is not null");
//                                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
//                                    //焦点在界面上最后一个完全显示的item，右滑
//                                    if (linearManager.findLastCompletelyVisibleItemPosition() == mFocusedPosition) {
//                                        Log.d("operation", "右滑");
//                                        if ((int) mFocusedPosition + pageSize1 < totalItem) {
//
//                                            mRecyclerView.smoothScrollToPosition((int) mFocusedPosition + pageSize1);
//                                            //notifyItemMoved((int) mFocusedPosition, (int) mFocusedPosition + pageSize1);
//                                        } else {
//
//                                            mRecyclerView.smoothScrollToPosition(totalItem - 1);
//                                        }
//                                    }
//
//                                    mFocusedPosition = mFocusedPosition + 1;
//
//                                    MyHolder viewHolder = (MyHolder) mRecyclerView.findViewHolderForAdapterPosition((int) mFocusedPosition);
//                                    //MyHolder viewHolder = (MyHolder)mRecyclerView.getChildViewHolder(mFocusedView);
//                                    Log.d("operation", "right view Holder " + mFocusedPosition + " is selected.");
//                                    viewHolder.btn.setFocusable(true);
//                                    viewHolder.btn.setFocusableInTouchMode(true);
//                                    viewHolder.btn.requestFocus();
//
//                                } else {
//                                    throw new RuntimeException();
//                                }
//
//
//                            }
//
//                        }
//                        break;
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
    }

//    private void CalPageSize() {
//        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
//        //判断是当前layoutManager是否为LinearLayoutManager
//        // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
//        if (layoutManager instanceof LinearLayoutManager) {
//            LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
//            //获取最后一个可见view的位置
//            pageSize1 = linearManager.findLastCompletelyVisibleItemPosition();
//        } else {
//            throw new RuntimeException();
//        }
//    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_button, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final MyHolder myHolder = (MyHolder) holder;

        //myHolder.button.requestFocus();
        myHolder.tv.setText("" + position);
        myHolder.btn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                //判断点击
                myHolder.clicked = myHolder.clicked * -1;
                //奇数次获取焦点，显示动画
                if (myHolder.clicked == 1) {

                    ObjectAnimator animator_2 = ObjectAnimator.ofFloat(
                            myHolder.btn,
                            "rotation",
                            0,
                            360);
                    animator_2.setDuration(2000);
                    animator_2.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator_2.start();
                    Log.d("operation", "Viewpager position: "+ position);
                    mViewPager.setCurrentItem(position);
                }

            }
        });
        myHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("operation", "Click");
                ObjectAnimator animator_2 = ObjectAnimator.ofFloat(
                        myHolder.btn,
                        "rotation",
                        0,
                        360);
                animator_2.setDuration(2000);
                animator_2.setInterpolator(new AccelerateDecelerateInterpolator());
                animator_2.start();
            }
        });


    }

    @Override
    public int getItemCount() {
        return totalItem;
    }


    public static class MyHolder extends RecyclerView.ViewHolder {
        public Button btn;
        public TextView tv;

        //根据获取焦点次数处理事件
        //奇数次（1）显示动画，偶数（-1）次跳过
        private int clicked = -1;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.test1);
            tv = itemView.findViewById(R.id.testt1);
        }


    }
}
