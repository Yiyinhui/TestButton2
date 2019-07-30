package com.example.testbutton.Fragment.Fragment_H0.FragmentH0L0;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testbutton.MyAdapterBody;
import com.example.testbutton.OperationControl;
import com.example.testbutton.R;

public class FragmentH0L0 extends Fragment {
    private RecyclerView mRecyclerViewH0L0Body;
    private View currentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        currentView = inflater.inflate(R.layout.fragment_00, container, false);
        initLeftButton();
        return currentView;
    }

    private void initLeftButton() {
        mRecyclerViewH0L0Body = currentView.findViewById(R.id.rv_body);

        mRecyclerViewH0L0Body.setAdapter(new MyAdapterBody(getActivity(),20));
        mRecyclerViewH0L0Body.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mRecyclerViewH0L0Body.setFocusable(false);

        OperationControl.recyclerViewMap.put("H0L0",mRecyclerViewH0L0Body);
    }
}
