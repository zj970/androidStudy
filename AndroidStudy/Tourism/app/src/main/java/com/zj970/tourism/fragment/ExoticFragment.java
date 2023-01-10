package com.zj970.tourism.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.zj970.tourism.R;
import com.zj970.tourism.adapter.ExoticAdapter;
import com.zj970.tourism.entity.ExoticItem;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author: zj970
 * @date: 2023/1/7
 */
public class ExoticFragment extends Fragment {
    View rootView;
    RecyclerView exoticRecyclerView;
    Spinner arrangement,exoticType;
    List<ExoticItem> exoticItemList = new ArrayList<>();

    public void findByAll(){
        exoticRecyclerView = rootView.findViewById(R.id.exotic_recycler_view);
        arrangement = rootView.findViewById(R.id.arrangement);
        exoticType = rootView.findViewById(R.id.exotic_type);
    }

    public List<ExoticItem> initData(){
        //TODO:获取异域风情景点数据
        //模拟数据
        List<ExoticItem> tmp = new ArrayList<>();
        String TEST_URL = "https://th.bing.com/th/id/OIP.t6mekkDTT8O4TL5xu5iQtgHaE7?pid=ImgDet&rs=1";
        String TEST_COUNTRY = "中国";
        for (int i = 0; i < 10; i++){
            ExoticItem exoticItem = new ExoticItem(TEST_COUNTRY,TEST_URL,String.valueOf(i));
            tmp.add(exoticItem);
        }
        return tmp;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_exotic,container,false);
        findByAll();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        exoticItemList = initData();
        if (!exoticItemList.isEmpty()){
            exoticRecyclerView.setAdapter(new ExoticAdapter(exoticItemList));
            arrangement.setSelection(1);
            arrangement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position)
                    {
                        case 0:
                            /*LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                            exoticRecyclerView.setLayoutManager(layoutManager);*/
                            GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(),1);
                            exoticRecyclerView.setLayoutManager(layoutManager2);
                            break;
                        case 1:
                            GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(),2);
                            exoticRecyclerView.setLayoutManager(layoutManager1);
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
}
