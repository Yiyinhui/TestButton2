package com.example.testbutton.Fragment.Fragment_H0.FragmentH0L2;

import android.os.Bundle;
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

public class FragmentH0L2 extends Fragment {
    private RecyclerView mRecyclerViewH0L2Body;
    private View currentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        currentView = inflater.inflate(R.layout.fragment_02, container, false);
        initLeftButton();
        return currentView;
    }

    private void initLeftButton() {
        mRecyclerViewH0L2Body = currentView.findViewById(R.id.rv_body);

        mRecyclerViewH0L2Body.setAdapter(new MyAdapterBody(getActivity(),1));
        mRecyclerViewH0L2Body.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mRecyclerViewH0L2Body.setFocusable(false);

        OperationControl.recyclerViewMap.put("H0L2",mRecyclerViewH0L2Body);
    }

}
