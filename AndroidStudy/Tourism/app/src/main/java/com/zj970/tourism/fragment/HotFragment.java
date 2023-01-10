package com.zj970.tourism.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.zj970.tourism.R;
import com.zj970.tourism.adapter.HotAdapter;
import com.zj970.tourism.entity.HotItem;

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
public class HotFragment extends Fragment {

    View rootView;
    ListView hot_list_view;
    List<HotItem> hotItems = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_hot,container,false);
        findByAll();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        hotItems = initData();
        if (!hotItems.isEmpty()){
            hot_list_view.setAdapter(new HotAdapter(getContext(),R.layout.hot_list_view_item,hotItems));
        }
    }

    private void findByAll(){
        //TODO:获取所有的控件实例
        hot_list_view = rootView.findViewById(R.id.hot_list_view);
    }

    private List<HotItem> initData(){
        //TODO:获取热门景点数据
        //模拟数据
        List<HotItem> tmp = new ArrayList<>();
        String TEST_URL = "https://th.bing.com/th/id/R.b4f6e6466d5160e3cad7c1ef81b3c92a?rik=gNRmTiLyxIvupA&riu=http%3a%2f%2fimage.hnol.net%2fc%2f2020-08%2f08%2f19%2f20200808194758711-4605672.jpg&ehk=Sw1IpsOjR%2fkjdoBykQgOt9mZmY72e3uiJ23zPtCo%2fG0%3d&risl=&pid=ImgRaw&r=0";
        String TEST_CONTENT = "测试数据Content测试数据Content测试数据Content测试数据Content测试数据Content测试数据Content";
        String TEST_PRICE = "￥0-9999";
        for (int i = 0; i < 10; i++){
            HotItem hotItem = new HotItem.Builder(TEST_URL,TEST_CONTENT,TEST_PRICE)
                    .mHotLabel1("标签1").mHotLabel2("标签2").mHotLabel3("标签3").mHotLabel4("标签4").build();
            tmp.add(hotItem);
        }
        return tmp;
    }
}
