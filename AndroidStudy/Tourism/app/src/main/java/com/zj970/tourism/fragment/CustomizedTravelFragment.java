package com.zj970.tourism.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.zj970.tourism.R;

/**
 * <p>
 *  定制旅行页面
 * </p>
 *
 * @author: zj970
 * @date: 2023/1/3
 */
public class CustomizedTravelFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_travel,container,false);
        return view;
    }
}
