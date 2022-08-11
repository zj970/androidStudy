package com.zj970.fragmentbestpractice.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.zj970.fragmentbestpractice.R;

public class NewsContentFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_content_frag,container,false);
        return view;
    }

    /**
     * 刷新页面
     * @param newsTitle 新闻标题
     * @param newsContent 新闻内容
     */
    public void refresh(String newsTitle,String newsContent){
        View visibilityLayout = view.findViewById(R.id.visiblity_layout);
        visibilityLayout.setVisibility(View.VISIBLE);
        TextView newsTitleText = view.findViewById(R.id.news_title);
        TextView newsContentText = view.findViewById(R.id.news_content);
        newsContentText.setText(newsContent);
        newsTitleText.setText(newsTitle);
    }
}
