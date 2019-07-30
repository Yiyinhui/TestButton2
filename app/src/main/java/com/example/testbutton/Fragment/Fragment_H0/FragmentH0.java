package com.example.testbutton.Fragment.Fragment_H0;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.testbutton.MyAdapterLeft;
import com.example.testbutton.MyLinearLayoutManager;
import com.example.testbutton.OperationControl;
import com.example.testbutton.R;

public class FragmentH0 extends Fragment {
    private RecyclerView mRecyclerViewLeft;
    private ViewPager mViewPagerLeft;
    private View currentView;

    private int count ;

    public FragmentH0(int count){
        this.count = count;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        currentView = inflater.inflate(R.layout.fragment_h0, container, false);
        initLeftButton();
        return currentView;
    }

    private void initLeftButton() {
        mRecyclerViewLeft = currentView.findViewById(R.id.rv_h0_left);
        mViewPagerLeft = currentView.findViewById(R.id.viewpager_h0);

        mViewPagerLeft.setAdapter(new MyFragmentPaperAdapterH0(getChildFragmentManager(), count));
        mViewPagerLeft.setFocusable(false);
//        mViewPagerLeft.setPageTransformer(true, new ViewPager.PageTransformer() {
//            private static final float MIN_SCALE = 0.75f;
//
//            /*
//            position的可能性的值有：
//            [-Infinity,-1)  已经看不到了
//            (1,+Infinity] 已经看不到了
//            [-1,1]
//
//            假设现在ViewPager在A页现在滑出B页，则:
//            A页的position变化就是( -1, 0]
//            B页的position变化就是[ 0 , 1 ]
//             */
//            @Override
//            public void transformPage(@NonNull View page, float position) {
//                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
//                float rotate = 10 * Math.abs(position);
//                //position小于等于1的时候，代表page已经位于中心item的最左边，
//                //此时设置为最小的缩放率以及最大的旋转度数
//                if (position <= -1) {
//                    page.setScaleX(MIN_SCALE);
//                    page.setScaleY(MIN_SCALE);
//                    page.setRotationY(rotate);
//                }
//
//                if (position > -1 && position < 0) {//A页的动画
//                    page.setScaleX(scaleFactor);
//                    page.setScaleY(scaleFactor);
//                    page.setRotationY(rotate);
//                }
//
//                if (position >= 0 && position < 1) {//B页的动画
//                    page.setScaleX(scaleFactor);
//                    page.setScaleY(scaleFactor);
//                    page.setRotationY(-rotate);
//                }
//
//                if (position >= 1) {//position大于等于1的时候，代表page已经位于中心item的最右边
//                    page.setScaleX(scaleFactor);
//                    page.setScaleY(scaleFactor);
//                    page.setRotationY(-rotate);
//                }
//            }
//        });



        mRecyclerViewLeft.setAdapter(new MyAdapterLeft(getActivity(),mViewPagerLeft));
        mRecyclerViewLeft.setLayoutManager(new MyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerViewLeft.setFocusable(false);

        OperationControl.recyclerViewMap.put("H0",mRecyclerViewLeft);
        OperationControl.viewPagerMap.put("H0",mViewPagerLeft);
    }



}
