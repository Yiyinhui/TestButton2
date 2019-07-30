package com.example.testbutton.Fragment.Fragment_H2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testbutton.R;

public class FragmentH2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_h2, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("operation","H0L2 Start");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("operation","H0L2 ReSume");
    }
}
