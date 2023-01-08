package com.zj970.tourism.fragment;

import android.content.Intent;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.zj970.tourism.MoreActivity;
import com.zj970.tourism.R;
import com.zj970.tourism.SearchActivity;

/**
 * <p>
 * 游记分享
 * </p>
 *
 * @author: zj970
 * @date: 2023/1/3
 */
public class TravelsFragment extends Fragment implements View.OnClickListener{

    EditText searchEdit;
    ImageView searchIcon;
    Button btn_more_1;
    ImageView top_pick_image_0;
    ImageView top_pick_image_1;
    TextView top_pick_title_0;
    TextView top_pick_title_1;
    TextView top_pick_text_0;
    TextView top_pick_text_1;

    Button btn_more_2;
    ImageView free_travel_image_0;
    ImageView free_travel_image_1;
    TextView free_travel_title_0;
    TextView free_travel_title_1;
    TextView free_travel_text_0;
    TextView free_travel_text_1;

    Button btn_more_3;
    ImageView exotic_image_0;
    ImageView exotic_image_1;
    ImageView exotic_image_2;
    ImageView exotic_image_3;

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.travels,container,false);
        findByAll();
        return rootView;
    }

    private void findByAll(){
        //TODO:初始化所有控件实例
        searchEdit = rootView.findViewById(R.id.search_edit);
        searchIcon = rootView.findViewById(R.id.search_icon);
        btn_more_1 = rootView.findViewById(R.id.btn_more_1);
        btn_more_2 = rootView.findViewById(R.id.btn_more_2);
        top_pick_image_0 = rootView.findViewById(R.id.top_pick_image_0);
        top_pick_image_1 = rootView.findViewById(R.id.top_pick_image_1);
        top_pick_title_0 = rootView.findViewById(R.id.top_pick_title_0);
        top_pick_title_1 = rootView.findViewById(R.id.top_pick_title_1);
        top_pick_text_0 = rootView.findViewById(R.id.top_pick_text_0);
        top_pick_text_1 = rootView.findViewById(R.id.top_pick_text_1);

        free_travel_image_0 = rootView.findViewById(R.id.free_travel_image_0);
        free_travel_image_1 = rootView.findViewById(R.id.free_travel_image_1);
        free_travel_title_0 = rootView.findViewById(R.id.free_travel_title_0);
        free_travel_title_1 = rootView.findViewById(R.id.free_travel_title_1);
        free_travel_text_0 = rootView.findViewById(R.id.free_travel_text_0);
        free_travel_text_1 = rootView.findViewById(R.id.free_travel_text_1);

        btn_more_3 = rootView.findViewById(R.id.btn_more_3);
        exotic_image_0 = rootView.findViewById(R.id.exotic_image_0);
        exotic_image_1 = rootView.findViewById(R.id.exotic_image_1);
        exotic_image_2 = rootView.findViewById(R.id.exotic_image_2);
        exotic_image_3 = rootView.findViewById(R.id.exotic_image_3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_edit:
            case R.id.search_icon:
                Intent search = new Intent(this.getContext(), SearchActivity.class);
                startActivity(search);
                break;
            case R.id.btn_more_1:
                MoreActivity.actionStart(this.getContext(),R.string.search_hint_hot,R.layout.fragment_hot);
                break;
            case R.id.btn_more_2:
                MoreActivity.actionStart(this.getContext(),R.string.search_hint_free,R.layout.fragment_free);
                break;
            case R.id.btn_more_3:
                MoreActivity.actionStart(this.getContext(),R.string.search_hint_exotic,R.layout.fragment_exotic);
                break;
            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        searchIcon.setOnClickListener(this::onClick);
        searchEdit.setOnClickListener(this::onClick);
        btn_more_1.setOnClickListener(this::onClick);
        btn_more_2.setOnClickListener(this::onClick);
        btn_more_3.setOnClickListener(this::onClick);
        searchEdit.setInputType(InputType.TYPE_NULL);
        setTextToImg("错误",exotic_image_0);
        setTextToImg("错误",exotic_image_1);
        setTextToImg("错误",exotic_image_2);
        setTextToImg("错误",exotic_image_3);
    }

        /**
         * 文字绘制在图片上，并返回bitmap对象
         */
        private void setTextToImg(String text,ImageView srcImage) {
            BitmapDrawable icon = (BitmapDrawable) getResources().getDrawable(R.drawable.background);;

            Bitmap bitmap = icon.getBitmap().copy(Bitmap.Config.ARGB_8888, true);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            // 抗锯齿
            paint.setAntiAlias(true);
            // 防抖动
            paint.setDither(true);
            paint.setTextSize(300);
            Typeface font = Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD);
            paint.setTypeface(font);
            paint.setColor(Color.parseColor("#000000"));
            canvas.drawText(text,(bitmap.getWidth() / 5),(bitmap.getHeight() / 2), paint);
            srcImage.setImageBitmap(bitmap);
        }


}
