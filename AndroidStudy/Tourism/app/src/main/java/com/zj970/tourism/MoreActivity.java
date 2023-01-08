package com.zj970.tourism;

import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.zj970.tourism.base.BaseActivity;
import com.zj970.tourism.fragment.ExoticFragment;
import com.zj970.tourism.fragment.FreeFragment;
import com.zj970.tourism.fragment.HotFragment;

/**
 * @author zj970
 */
public class MoreActivity extends BaseActivity implements View.OnClickListener{
    public static final String TIPS = "TIPS";
    public static final String FRAME_LAYOUT = "frame_layout";
    View more_top_bar;
    ImageView travels_return;
    EditText more_search;
    FrameLayout moreFragment;
    FragmentManager manager;
    private int hintId,fragmentId;

    public static void actionStart(Context context, int tipsId,int frameLayoutId){
        Intent intent = new Intent(context,MoreActivity.class);
        intent.putExtra(TIPS,tipsId);
        intent.putExtra(FRAME_LAYOUT,frameLayoutId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        findByAll();
        hintId = getIntent().getIntExtra(TIPS,R.string.search_hint_hot);
        fragmentId = getIntent().getIntExtra(FRAME_LAYOUT,R.layout.fragment_hot);
        more_search.setHint(hintId);
        manager = getSupportFragmentManager();
        initFragment(fragmentId);
        travels_return.setOnClickListener(this::onClick);
    }

    private void initFragment(int fragmentId){
        switch (fragmentId){
            case R.layout.fragment_free:
                manager.beginTransaction().replace(R.id.more_fragment,new FreeFragment()).commit();
                break;
            case R.layout.fragment_exotic:
                manager.beginTransaction().replace(R.id.more_fragment,new ExoticFragment()).commit();
                break;
            case R.layout.fragment_hot:
                manager.beginTransaction().replace(R.id.more_fragment,new HotFragment()).commit();
                break;
            default:
        }
    }

    @Override
    protected void findByAll() {
        more_top_bar = findViewById(R.id.more_top_bar);
        more_search = findViewById(R.id.more_search);
        moreFragment = findViewById(R.id.more_fragment);
        travels_return = more_top_bar.findViewById(R.id.travels_return);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.travels_return:
                finish();
                break;
            default:
                break;
        }
    }
}