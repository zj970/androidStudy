package com.zj970.tourism;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.zj970.tourism.base.BaseActivity;

/**
 * @author zj970
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {

    ImageView returnIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findByAll();
        returnIcon.setOnClickListener(this::onClick);
    }

    @Override
    protected void findByAll() {
        returnIcon = findViewById(R.id.return_icon);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.return_icon:
                finish();
                break;
            default:
                break;
        }
    }
}