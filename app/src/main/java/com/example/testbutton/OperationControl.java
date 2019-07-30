package com.example.testbutton;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class OperationControl {
    private Observable mObservable;
    private ViewPager mViewPagerHead;

    private int currentRecyclerView;
    private final int RECYCLERVIEW_HEAD = 0;
    private final int RECYCLERVIEW_LEFT = 1;
    private final int RECYCLERVIEW_BODY = 2;


    public static View mFocusedView;
    private int mFocusedPositionHead = -1;
    private RecyclerView mRecyclerViewHead;
    private int totalItemHead = 10;
    private int pageSizeHead = 4;
    private int mFocusedPositionLeft = -1;
    private RecyclerView mRecyclerViewLeft;
    private int totalItemLeft = 3;
    private int mFocusedPositionBody = -1;
    private RecyclerView mRecyclerViewBody;
    private int totalItemBody;
    private int pageSizeBodyX = 3;
    private int pageSizeBodyY = 4;


    //类常量，用于存储所有RecyclerView。
    public static Map<String, RecyclerView> recyclerViewMap = new HashMap<String, RecyclerView>();
    public static Map<String, ViewPager> viewPagerMap = new HashMap<String, ViewPager>();


    public OperationControl(Observable observable) {
        mObservable = observable;
    }

    public void setRecyclerViewHead(RecyclerView recyclerView) {
        mRecyclerViewHead = recyclerView;
    }


    public void Control() {
        mObservable
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String direction) throws Exception {
                        switch (currentRecyclerView) {
                            case RECYCLERVIEW_HEAD:
                                switch (direction) {
                                    case "up"://无操作
                                        break;
                                    case "down"://doOnNext（）更换currentRecyclerView

                                        //1.修改Head的标记位置
                                        if (mFocusedPositionHead < 0) {
                                            mFocusedPositionHead = 0;
                                        }

                                        //2.获取mRecyclerViewLeft
                                        try {
                                            mRecyclerViewLeft = recyclerViewMap.get("H" + mFocusedPositionHead);
                                        } catch (Exception e) {
                                            Log.d("operation", "No RecyclerView Exist");
                                        }

                                        //3.修改状态
                                        if (mRecyclerViewLeft != null) {
                                            currentRecyclerView = RECYCLERVIEW_LEFT;
                                        }

                                        //mRecyclerViewLeft = mViewPagerHead.getCurrentItem();
                                        break;
                                    case "left":

                                        /*
                                        有焦点时，点击<-，左移
                                        */
                                        //下一个item存在
                                        if ((int) mFocusedPositionHead > 0) {
                                            //焦点在界面上第一个完全显示的item， 向左滑动界面

                                            RecyclerView.LayoutManager layoutManager = mRecyclerViewHead.getLayoutManager();
                                            //判断是当前layoutManager是否为LinearLayoutManager
                                            // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                                            if (layoutManager instanceof LinearLayoutManager) {
                                                LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                                                //焦点在界面上第一个完全显示的item，左滑
                                                if (linearManager.findFirstCompletelyVisibleItemPosition() == mFocusedPositionHead) {
                                                    if ((int) mFocusedPositionHead - pageSizeHead >= 0) {
                                                        mRecyclerViewHead.smoothScrollToPosition((int) mFocusedPositionHead - pageSizeHead);
                                                    } else {
                                                        mRecyclerViewHead.smoothScrollToPosition(0);
                                                    }
                                                }

                                            } else {
                                                throw new RuntimeException();
                                            }
                                        }

                                        break;
                                    case "right"://右滑前判断下一个item是否显示，如不显示，右滑

                                        //下一个item存在
                                        if ((int) mFocusedPositionHead < totalItemHead - 1) {
                                            RecyclerView.LayoutManager layoutManager = mRecyclerViewHead.getLayoutManager();
                                            //判断是当前layoutManager是否为LinearLayoutManager
                                            // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                                            if (layoutManager instanceof LinearLayoutManager) {
                                                LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                                                //焦点在界面上最后一个完全显示的item，右滑
                                                if (linearManager.findLastCompletelyVisibleItemPosition() == mFocusedPositionHead) {
                                                    if ((int) mFocusedPositionHead + pageSizeHead < totalItemHead) {
                                                        Log.d("operation","scroll");
                                                        mRecyclerViewHead.smoothScrollToPosition((int) mFocusedPositionHead + pageSizeHead);
                                                        //notifyItemMoved((int) mFocusedPosition, (int) mFocusedPosition + pageSize1);
                                                    } else {
                                                        Log.d("operation","scroll");
                                                        mRecyclerViewHead.smoothScrollToPosition(totalItemHead - 1);
                                                    }
                                                }

                                            } else {
                                                throw new RuntimeException();
                                            }
                                        }
                                }
                                break;
                            case RECYCLERVIEW_LEFT:
                                switch (direction) {
                                    case "up":
                                        //已经在顶端依然选择往上按
                                        if (mFocusedPositionLeft == 0) {
                                            //焦点回到原先位置,当前recyclerView变更
                                            currentRecyclerView = RECYCLERVIEW_HEAD;
                                        }

                                        break;
                                    case "down":
                                        break;
                                    case "left":
                                        break;
                                    case "right":
                                        //进入body
                                        //1.获取recyclerView.
                                        //mRecyclerViewBody = mRecyclerViewLeft.getList(mFocusedPositionLeft);
                                        try {
                                            mRecyclerViewBody = recyclerViewMap.get("H" + mFocusedPositionHead + "L" + mFocusedPositionLeft);
                                            totalItemBody = mRecyclerViewBody.getAdapter().getItemCount();
                                            Log.d("operation", "totalItemBody: "+totalItemBody);

                                            //修改参数
                                            if(totalItemBody<=pageSizeBodyX){
                                                pageSizeBodyX = totalItemBody;
                                                pageSizeBodyY = 1;
                                            }else if(totalItemBody/pageSizeBodyX<pageSizeBodyY){
                                                pageSizeBodyY = totalItemBody/pageSizeBodyX;
                                            }else{
                                                //重置
                                                 pageSizeBodyX = 3;
                                                 pageSizeBodyY = 4;
                                            }
                                                                                        //mRecyclerViewBody.
                                        } catch (Exception e) {
                                            Log.d("operation", "No RecyclerView Exist");
                                        }
                                        //获取body内item数量

                                        //修改状态
                                        if (mRecyclerViewBody != null) {
                                            currentRecyclerView = RECYCLERVIEW_BODY;
                                        }
                                        break;
                                }
                                break;
                            case RECYCLERVIEW_BODY:
                                switch (direction) {
                                    case "up":
                                        //上一行存在
                                        if (mFocusedPositionBody / pageSizeBodyX > 0) {
                                            RecyclerView.LayoutManager layoutManager = mRecyclerViewBody.getLayoutManager();
                                            if (layoutManager instanceof GridLayoutManager) {
                                                GridLayoutManager GridManager = (GridLayoutManager) layoutManager;
                                                //焦点在界面上第一个完全显示的一行，上滑
                                                if (GridManager.findFirstCompletelyVisibleItemPosition()/pageSizeBodyX == mFocusedPositionBody/pageSizeBodyX) {
                                                    if ((int) mFocusedPositionBody - pageSizeBodyX*pageSizeBodyY >= 0) {
                                                        mRecyclerViewBody.smoothScrollToPosition((int) mFocusedPositionBody - pageSizeBodyX*pageSizeBodyY);
                                                    } else {
                                                        mRecyclerViewBody.smoothScrollToPosition(0);
                                                    }
                                                }
                                            }
                                        }

                                        if (mFocusedPositionBody / pageSizeBodyX == 0){//焦点在第一行，继续上滑进入Head
                                            //修改状态
                                            currentRecyclerView = RECYCLERVIEW_HEAD;

                                        }



                                        break;
                                    case "down":
                                        //下一行存在
                                        if (mFocusedPositionBody / pageSizeBodyX < (totalItemBody-1) / pageSizeBodyX) {
                                            RecyclerView.LayoutManager layoutManager = mRecyclerViewBody.getLayoutManager();
                                            if (layoutManager instanceof GridLayoutManager) {
                                                GridLayoutManager GridManager = (GridLayoutManager) layoutManager;
                                                if (GridManager.findLastCompletelyVisibleItemPosition()/pageSizeBodyX == mFocusedPositionBody/pageSizeBodyX) {
                                                    if ((int) mFocusedPositionBody + pageSizeBodyX*pageSizeBodyY <= totalItemBody) {
                                                        mRecyclerViewBody.smoothScrollToPosition((int) mFocusedPositionBody + pageSizeBodyX*pageSizeBodyY);
                                                    } else {
                                                        mRecyclerViewBody.smoothScrollToPosition(totalItemBody-1);
                                                    }
                                                }

                                            } else {
                                                throw new RuntimeException();
                                            }
                                        }
                                        break;
                                    case "left":
                                        if (mFocusedPositionBody % pageSizeBodyX ==0) {
                                            //回到顶部
                                            mRecyclerViewBody.smoothScrollToPosition(0);

                                            currentRecyclerView = RECYCLERVIEW_LEFT;
                                        }



                                        break;
                                    case "right":
                                        Log.d("operation","doOnNext Body right");
                                        //Toast.makeText(MainActivity,'要显示的内容',Toast.LENGTH_SHORT).show();

                                        //下一行存在
                                        if (mFocusedPositionBody / pageSizeBodyX < (totalItemBody-1) / pageSizeBodyX) {
                                            RecyclerView.LayoutManager layoutManager = mRecyclerViewBody.getLayoutManager();
                                            //判断是当前layoutManager是否为LinearLayoutManager
                                            if (layoutManager instanceof GridLayoutManager) {
                                                GridLayoutManager GridManager = (GridLayoutManager) layoutManager;
                                                //焦点在界面上最后一个完全显示的点
                                                if (GridManager.findLastCompletelyVisibleItemPosition() == mFocusedPositionBody) {
                                                    if ((int) mFocusedPositionBody + pageSizeBodyX*pageSizeBodyY <= totalItemBody) {
                                                        mRecyclerViewBody.smoothScrollToPosition((int) mFocusedPositionBody + pageSizeBodyX*pageSizeBodyY);
                                                    } else {
                                                        mRecyclerViewBody.smoothScrollToPosition(totalItemBody-1);
                                                    }
                                                }

                                            } else {
                                                throw new RuntimeException();
                                            }
                                        }
                                        break;
                                }
                                break;
                        }
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                //接收到指令后先判断焦点是否存在
//                //焦点不存在,设置head的第一个为焦点
//                if (mFocusedView == null) {
//                    Log.d("operation", "mRecyclerViewHead: "+(mRecyclerViewHead==null));
//                    mFocusedView = mRecyclerViewHead.getChildAt(0);
//                    mFocusedPositionHead = 0;
//                    currentRecyclerView = RECYCLERVIEW_HEAD;
//                    Log.d("operation", "mFocusedView: "+(mFocusedView==null));
//                    if (mRecyclerViewHead.getChildViewHolder(mFocusedView) != null) {
//                        MyAdapterHead.MyHolder viewHolder = (MyAdapterHead.MyHolder) mRecyclerViewHead.getChildViewHolder(mFocusedView);
//                        viewHolder.btn.setFocusable(true);
//                        viewHolder.btn.setFocusableInTouchMode(true);
//                        viewHolder.btn.requestFocus();
//                    } else {
//                        Log.d("operation", "There is no view in rv1.");
//                    }
//                }
                    }

                    @Override
                    public void onNext(String direction) {
                        Log.d("operation","currentRecyclerView  "+currentRecyclerView+"  *************************************************************");
                        switch (currentRecyclerView) {
                            case RECYCLERVIEW_HEAD:
                                switch (direction) {
                                    case "up":
                                        if (mFocusedPositionLeft == 0) {
                                            //其他位置返回Head
                                            //mFocusedPositionHead不变
                                            //mFocusedPositionHead = mFocusedPositionHead ;
                                            //获取View设置焦点
                                            MyAdapterHead.MyHolder viewHolderUp = (MyAdapterHead.MyHolder) mRecyclerViewHead.findViewHolderForAdapterPosition((int) mFocusedPositionHead);
                                            //MyHolder viewHolder = (MyHolder)mRecyclerView.getChildViewHolder(mFocusedView);
                                            //Log.d("operation", "right view Holder " + mFocusedPositionHead + " is selected.");
                                            viewHolderUp.btn.setFocusable(true);
                                            viewHolderUp.btn.setFocusableInTouchMode(true);
                                            viewHolderUp.btn.requestFocus();
                                            mFocusedPositionLeft = -1;
                                            mFocusedPositionBody = -1;
                                        }else if(mFocusedPositionBody/pageSizeBodyX==0){
                                            MyAdapterHead.MyHolder viewHolderUp = (MyAdapterHead.MyHolder) mRecyclerViewHead.findViewHolderForAdapterPosition((int) mFocusedPositionHead);
                                            //MyHolder viewHolder = (MyHolder)mRecyclerView.getChildViewHolder(mFocusedView);
                                            //Log.d("operation", "right view Holder " + mFocusedPositionHead + " is selected.");
                                            viewHolderUp.btn.setFocusable(true);
                                            viewHolderUp.btn.setFocusableInTouchMode(true);
                                            viewHolderUp.btn.requestFocus();
                                            mFocusedPositionLeft = -1;
                                            mFocusedPositionBody = -1;

                                        }

                                        break;
                                    case "down"://doOnNext（）更换currentRecyclerView
                                        break;
                                    case "left":
                                        //下一个item存在
                                        if (mFocusedPositionHead > 0) {
                                            mFocusedPositionHead = mFocusedPositionHead - 1;

                                            MyAdapterHead.MyHolder viewHolderLeft = (MyAdapterHead.MyHolder) mRecyclerViewHead.findViewHolderForAdapterPosition((int) mFocusedPositionHead);
                                            //MyHolder viewHolder = (MyHolder)mRecyclerView.getChildViewHolder(mFocusedView);
                                            //Log.d("operation", "right view Holder " + mFocusedPositionHead + " is selected.");
                                            viewHolderLeft.btn.setFocusable(true);
                                            viewHolderLeft.btn.setFocusableInTouchMode(true);
                                            viewHolderLeft.btn.requestFocus();

                                            //获取Head焦点下的第一个ViewPager
                                            Log.d("Error","获取Head焦点下的第一个ViewPager");
                                            try{

                                                ViewPager tempViewPager = viewPagerMap.get("H"+mFocusedPositionHead);
                                                Log.d("Error","tempViewPager"+(tempViewPager==null));
                                                tempViewPager.setCurrentItem(0);
                                                Log.d("Error","tempViewPager whether shown"+(tempViewPager.isShown()));


                                            }catch (Exception e){
                                                Log.d("Error","No ViewPager Exist");
                                            }


                                        }

                                        break;
                                    case "right":
                                        Log.d("operation","doOnNext Body right");

                                        //下一个item存在
                                        if (mFocusedPositionHead < totalItemHead - 1) {
                                            mFocusedPositionHead = mFocusedPositionHead + 1;

                                            MyAdapterHead.MyHolder viewHolderRight = (MyAdapterHead.MyHolder) mRecyclerViewHead.findViewHolderForAdapterPosition((int) mFocusedPositionHead);
                                            //MyHolder viewHolder = (MyHolder)mRecyclerView.getChildViewHolder(mFocusedView);
                                            //Log.d("operation", "right view Holder " + mFocusedPositionHead + " is selected.");
                                            viewHolderRight.btn.setFocusable(true);
                                            viewHolderRight.btn.setFocusableInTouchMode(true);
                                            viewHolderRight.btn.requestFocus();


                                            //获取Head焦点下的第一个ViewPager
                                            Log.d("Error","获取Head焦点下的第一个ViewPager");
                                            try{

                                                ViewPager tempViewPager = viewPagerMap.get("H"+mFocusedPositionHead);
                                                Log.d("Error","tempViewPager"+(tempViewPager==null));
                                                tempViewPager.setCurrentItem(0);
                                                Log.d("Error","tempViewPager whether shown"+(tempViewPager.isShown()));

                                            }catch (Exception e){
                                                Log.d("Error","No ViewPager Exist");
                                            }
                                        }
                                        break;
                                }


                                break;
                            case RECYCLERVIEW_LEFT:
                                switch (direction) {
                                    case "up":
                                        //下一个item存在
                                        if (mFocusedPositionLeft > 0) {
                                            mFocusedPositionLeft = mFocusedPositionLeft - 1;

                                            MyAdapterLeft.MyHolder viewHolderLeft = (MyAdapterLeft.MyHolder) mRecyclerViewLeft.findViewHolderForAdapterPosition((int) mFocusedPositionLeft);
                                            //MyHolder viewHolder = (MyHolder)mRecyclerView.getChildViewHolder(mFocusedView);
                                            //Log.d("operation", "right view Holder " + mFocusedPositionLeft+ " is selected.");
                                            viewHolderLeft.btn.setFocusable(true);
                                            viewHolderLeft.btn.setFocusableInTouchMode(true);
                                            viewHolderLeft.btn.requestFocus();
                                        }
                                        break;
                                    case "down":
                                        //下一个item存在
                                        if (mFocusedPositionLeft < totalItemLeft - 1) {
                                            mFocusedPositionLeft = mFocusedPositionLeft + 1;

                                            MyAdapterLeft.MyHolder viewHolderRight = (MyAdapterLeft.MyHolder) mRecyclerViewLeft.findViewHolderForAdapterPosition((int) mFocusedPositionLeft);
                                            //MyHolder viewHolder = (MyHolder)mRecyclerView.getChildViewHolder(mFocusedView);
                                            //Log.d("operation", "right view Holder " + mFocusedPositionLeft + " is selected.");
                                            viewHolderRight.btn.setFocusable(true);
                                            viewHolderRight.btn.setFocusableInTouchMode(true);
                                            viewHolderRight.btn.requestFocus();
                                        }
                                        break;
                                    case "left":
                                        if(mFocusedPositionBody%pageSizeBodyX==0){
                                            MyAdapterLeft.MyHolder viewHolderLeft = (MyAdapterLeft.MyHolder) mRecyclerViewLeft.findViewHolderForAdapterPosition((int) mFocusedPositionLeft);
                                            //MyHolder viewHolder = (MyHolder)mRecyclerView.getChildViewHolder(mFocusedView);
                                            viewHolderLeft.btn.setFocusable(true);
                                            viewHolderLeft.btn.setFocusableInTouchMode(true);
                                            viewHolderLeft.btn.requestFocus();
                                            mFocusedPositionBody = -1;

                                        }

                                        break;
                                    case "right":
                                        break;
                                }
                                break;
                            case RECYCLERVIEW_BODY:
                                Log.d("Error","mFocusedPositionBody：  "+mFocusedPositionBody);
                                switch (direction) {
                                    case "up":
                                        if (mFocusedPositionBody / pageSizeBodyX > 0) {
                                            mFocusedPositionBody = mFocusedPositionBody - pageSizeBodyX;
                                            MyAdapterBody.MyHolder viewHolderUp = (MyAdapterBody.MyHolder) mRecyclerViewBody.findViewHolderForAdapterPosition((int) mFocusedPositionBody);
                                            //MyHolder viewHolder = (MyHolder)mRecyclerView.getChildViewHolder(mFocusedView);
                                            viewHolderUp.btn.setFocusable(true);
                                            viewHolderUp.btn.setFocusableInTouchMode(true);
                                            viewHolderUp.btn.requestFocus();
                                        }

                                        break;
                                    case "down":
                                        if (mFocusedPositionBody / pageSizeBodyX < (totalItemBody-1) / pageSizeBodyX) {

                                            mFocusedPositionBody = mFocusedPositionBody + pageSizeBodyX < totalItemBody-1 ? mFocusedPositionBody + pageSizeBodyX : totalItemBody-1;
                                            MyAdapterBody.MyHolder viewHolderDown = (MyAdapterBody.MyHolder) mRecyclerViewBody.findViewHolderForAdapterPosition((int) mFocusedPositionBody);
                                            //MyHolder viewHolder = (MyHolder)mRecyclerView.getChildViewHolder(mFocusedView);
                                            viewHolderDown.btn.setFocusable(true);
                                            viewHolderDown.btn.setFocusableInTouchMode(true);
                                            viewHolderDown.btn.requestFocus();
                                        }
                                        break;
                                    case "left":
                                        //下一个item存在
                                        if (mFocusedPositionBody % pageSizeBodyX > 0) {
                                            mFocusedPositionBody = mFocusedPositionBody - 1;
                                            MyAdapterBody.MyHolder viewHolderLeft = (MyAdapterBody.MyHolder) mRecyclerViewBody.findViewHolderForAdapterPosition((int) mFocusedPositionBody);
                                            //MyHolder viewHolder = (MyHolder)mRecyclerView.getChildViewHolder(mFocusedView);
                                            viewHolderLeft.btn.setFocusable(true);
                                            viewHolderLeft.btn.setFocusableInTouchMode(true);
                                            viewHolderLeft.btn.requestFocus();
                                        }
                                        break;
                                    case "right":
                                        //下一个item存在

//                                        if (mFocusedPositionBody % pageSizeBodyX < pageSizeBodyX - 1&&(mFocusedPositionBody + 1)<totalItemBody) {
                                        if ((mFocusedPositionBody + 1)<totalItemBody) {
                                            mFocusedPositionBody = mFocusedPositionBody + 1;

                                            MyAdapterBody.MyHolder viewHolderRight = (MyAdapterBody.MyHolder) mRecyclerViewBody.findViewHolderForAdapterPosition((int) mFocusedPositionBody);
                                            //MyHolder viewHolder = (MyHolder)mRecyclerView.getChildViewHolder(mFocusedView);
                                            viewHolderRight.btn.setFocusable(true);
                                            viewHolderRight.btn.setFocusableInTouchMode(true);
                                            viewHolderRight.btn.requestFocus();
                                        }


//                                        if(totalItemBody==1){
//                                            mFocusedPositionBody = mFocusedPositionBody + 1;
//
//                                            MyAdapterBody.MyHolder viewHolderRight = (MyAdapterBody.MyHolder) mRecyclerViewBody.findViewHolderForAdapterPosition((int) mFocusedPositionBody);
//                                            //MyHolder viewHolder = (MyHolder)mRecyclerView.getChildViewHolder(mFocusedView);
//                                            viewHolderRight.btn.setFocusable(true);
//                                            viewHolderRight.btn.setFocusableInTouchMode(true);
//                                            viewHolderRight.btn.requestFocus();
//
//                                        }
                                        break;
                                }
                                break;
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("operation", "Error" + e.getMessage().toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
