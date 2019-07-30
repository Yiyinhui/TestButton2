package com.example.testbutton;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class MyAdapterLeft extends RecyclerView.Adapter {
    private Context mContext;
    private ViewPager mViewPagerLeft;


    public MyAdapterLeft(Context context, ViewPager viewPager){
        mContext = context;
        mViewPagerLeft = viewPager;
    }


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
                    mViewPagerLeft.setCurrentItem(position,false);
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
        return 3;
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
