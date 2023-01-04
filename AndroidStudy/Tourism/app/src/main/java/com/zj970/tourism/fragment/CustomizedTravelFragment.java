package com.zj970.tourism.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.zj970.tourism.MainActivity;
import com.zj970.tourism.R;
import com.zj970.tourism.base.BaseApplication;

import java.util.Calendar;

/**
 * <p>
 * 定制旅行页面
 * </p>
 *
 * @author: zj970
 * @date: 2023/1/3
 */
public class CustomizedTravelFragment extends Fragment implements View.OnClickListener {
    /**
     * 人数下拉选择框
     */
    private Spinner toll;

    /**
     * 预算下拉选择框
     */
    private Spinner budget;

    private Button startDate;
    private Button endDate;
    private Calendar calendar;
    private int year, monthOfYear, dayOfMonth;
    private Button custom_travel_ok;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.custom_travel, container, false);
        findByAll();
        return rootView;
    }

    private void findByAll() {
        //TODO:初始化控件
        if (rootView != null) {
            toll = rootView.findViewById(R.id.toll);
            budget = rootView.findViewById(R.id.budget);
            startDate = rootView.findViewById(R.id.start_date);
            endDate = rootView.findViewById(R.id.end_date);
            custom_travel_ok = rootView.findViewById(R.id.custom_travel_ok);
            //通过Calendar对象来获取年、月、日信息
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            monthOfYear = calendar.get(Calendar.MONTH);
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_date:
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = year + "-" + (month + 1) + "-" + dayOfMonth;
                        startDate.setText(date);
                    }
                }, year, monthOfYear, dayOfMonth).show();
                break;
                case R.id.end_date:
                    new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String date = year + "-" + (month + 1) + "-" + dayOfMonth;
                            endDate.setText(date);
                        }
                    }, year, monthOfYear, dayOfMonth).show();
                    break;
            case R.id.custom_travel_ok:
                MainActivity.toThisMap();
                break;

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        startDate.setOnClickListener(this::onClick);
        endDate.setOnClickListener(this::onClick);
        custom_travel_ok.setOnClickListener(this::onClick);
    }
}
