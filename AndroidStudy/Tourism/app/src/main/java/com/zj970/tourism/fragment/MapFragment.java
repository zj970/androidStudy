package com.zj970.tourism.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.baidu.mapapi.map.MapView;
import com.zj970.tourism.R;

import java.util.Calendar;

/**
 * <p>
 * 地图页面
 * </p>
 *
 * @author: zj970
 * @date: 2023/1/3
 */
public class MapFragment extends Fragment implements View.OnClickListener {
    View rootView;

    MapView mapView;
    LinearLayout layout_2_1;
    LinearLayout layout_2_2;
    LinearLayout layout_3_1;
    LinearLayout layout_3_2;
    LinearLayout layout_3_3;

    Button btn_menu_2;
    Button btn_menu_2_1;
    Button btn_menu_2_2;
    Button btn_menu_3;
    Button btn_menu_3_1;
    Button btn_menu_3_2;
    Button btn_menu_3_3;

    ImageView menu_2_icon;
    ImageView menu_3_icon;

    boolean isMenu2 = false;
    boolean isMenu3 = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.map, container, false);
        findByAll();
        return rootView;
    }

    private void findByAll() {
        //TODO:初始化控件
        if (rootView != null) {
            mapView = rootView.findViewById(R.id.map_view);
            layout_2_1 = rootView.findViewById(R.id.layout_2_1);
            layout_2_2 = rootView.findViewById(R.id.layout_2_2);
            layout_3_1 = rootView.findViewById(R.id.layout_3_1);
            layout_3_2 = rootView.findViewById(R.id.layout_3_2);
            layout_3_3 = rootView.findViewById(R.id.layout_3_3);

            btn_menu_2 = rootView.findViewById(R.id.btn_menu_2);
            btn_menu_2_1 = rootView.findViewById(R.id.btn_menu_2_1);
            btn_menu_2_2 = rootView.findViewById(R.id.btn_menu_2_2);
            btn_menu_3 = rootView.findViewById(R.id.btn_menu_3);
            btn_menu_3_1 = rootView.findViewById(R.id.btn_menu_3_1);
            btn_menu_3_2 = rootView.findViewById(R.id.btn_menu_3_2);
            btn_menu_3_3 = rootView.findViewById(R.id.btn_menu_3_3);

            menu_2_icon = rootView.findViewById(R.id.menu_2_icon);
            menu_3_icon = rootView.findViewById(R.id.menu_3_icon);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        btn_menu_2.setOnClickListener(this::onClick);
        btn_menu_3.setOnClickListener(this::onClick);
        menu_2_icon.setImageResource(R.drawable.arrow_right);
        menu_3_icon.setImageResource(R.drawable.arrow_right);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_menu_2:
                if (isMenu2){
                    isMenu2 = false;
                    layout_2_1.setVisibility(View.GONE);
                    layout_2_2.setVisibility(View.GONE);
                    menu_2_icon.setImageResource(R.drawable.arrow_right);
                }else {
                    isMenu2 = true;
                    layout_2_1.setVisibility(View.VISIBLE);
                    layout_2_2.setVisibility(View.VISIBLE);
                    menu_2_icon.setImageResource(R.drawable.arrow_down);
                }
                break;
            case R.id.btn_menu_3:

                if (isMenu3){
                    isMenu3 = false;
                    layout_3_1.setVisibility(View.GONE);
                    layout_3_2.setVisibility(View.GONE);
                    layout_3_3.setVisibility(View.GONE);
                    menu_3_icon.setImageResource(R.drawable.arrow_right);
                }else {
                    isMenu3 = true;
                    layout_3_1.setVisibility(View.VISIBLE);
                    layout_3_2.setVisibility(View.VISIBLE);
                    layout_3_3.setVisibility(View.VISIBLE);
                    menu_3_icon.setImageResource(R.drawable.arrow_down);
                }
                break;
        }
    }
}
