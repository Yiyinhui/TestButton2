package com.example.testbutton.Fragment.Fragment_H0;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.testbutton.Fragment.Fragment_H0.FragmentH0L0.FragmentH0L0;
import com.example.testbutton.Fragment.Fragment_H0.FragmentH0L1.FragmentH0L1;
import com.example.testbutton.Fragment.Fragment_H0.FragmentH0L2.FragmentH0L2;

public class MyFragmentPaperAdapterH0 extends FragmentPagerAdapter {
    private int mCountH0;

    Fragment fragmentH0L0 = new FragmentH0L0();
    Fragment fragmentH0L1 = new FragmentH0L1();
    Fragment fragmentH0L2 = new FragmentH0L2();

    public MyFragmentPaperAdapterH0(FragmentManager fm, int count) {
        super(fm);
        mCountH0 = count;
    }

    @Override
    public Fragment getItem(int position) {
        if(position %3 == 0){
            return fragmentH0L0;
        }else if (position %3  == 1){
            return  fragmentH0L1;
        }
        return fragmentH0L2;
        //return new FragmentH0L0();
    }

    @Override
    public int getCount() {
        return mCountH0;
    }
}
