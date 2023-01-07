package com.zj970.tourism;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.zj970.tourism.base.BaseActivity;

/**
 * @author zj970
 */
public class MoreActivity extends BaseActivity implements View.OnClickListener{
    public static final String TIPS = "TIPS";
    View more_top_bar;
    ImageView travels_return;
    EditText more_search;
    FrameLayout moreFragment;
    private int hintId;

    public static void actionStart(Context context, int tipsId){
        Intent intent = new Intent(context,MoreActivity.class);
        intent.putExtra(TIPS,tipsId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        findByAll();
        hintId = getIntent().getIntExtra(TIPS,R.string.search_hint_hot);
        more_search.setHint(hintId);
        travels_return.setOnClickListener(this::onClick);
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