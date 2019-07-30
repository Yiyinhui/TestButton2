package com.example.testbutton.Fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testbutton.Fragment.Fragment_H0.FragmentH0;
import com.example.testbutton.Fragment.Fragment_H1.FragmentH1;
import com.example.testbutton.Fragment.Fragment_H2.FragmentH2;
import com.example.testbutton.OperationControl;

import java.util.Map;

public class MyFragmentPaperAdapter extends FragmentPagerAdapter {
    //private String[] mTitles = new String[]{"F1", "F2", "F3"};
    private int mCount;
    private Map<String, RecyclerView> recyclerViewMap;
    Fragment fragmentH0 = new FragmentH0(3);
    Fragment fragmentH1 = new FragmentH1();
    Fragment fragmentH2 = new FragmentH2();

    public MyFragmentPaperAdapter(FragmentManager fm, int count) {
        super(fm);
        mCount = count;
    }


    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return fragmentH0;
        }else if (position  == 1){
            return fragmentH1 ;
        }
        return new FragmentH2();
        //return new FragmentH0(10);
    }

    @Override
    public int getCount() {
        return mCount;
    }


}
