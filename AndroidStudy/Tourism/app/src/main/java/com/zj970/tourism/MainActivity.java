package com.zj970.tourism;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zj970.tourism.adapter.MyFragmentStateAdapter;
import com.zj970.tourism.base.BaseActivity;
import com.zj970.tourism.fragment.CustomizedTravelFragment;
import com.zj970.tourism.fragment.MapFragment;
import com.zj970.tourism.fragment.TravelsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zj970
 */
public class MainActivity extends BaseActivity {

    private CustomizedTravelFragment customizedTravelFragment;
    private MapFragment mapFragment;
    private TravelsFragment travelsFragment;
    private List<Fragment> mData = new ArrayList<>();
    private ViewPager2 mViewPager2;
    private BottomNavigationView bottomNavigationView;
    private ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            int itemID = R.id.map_fragment;
            switch (position) {
                case 0:
                    itemID = R.id.custom_travel_fragment;
                    break;
                case 1:
                    itemID = R.id.map_fragment;
                    break;
                case 2:
                    itemID = R.id.travels_fragment;
                    break;
                default:
                    break;
            }
            bottomNavigationView.setSelectedItemId(itemID);
        }
    };
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelected = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.custom_travel_fragment:
                    mViewPager2.setCurrentItem(0, true);
                    return true;
                case R.id.map_fragment:
                    mViewPager2.setCurrentItem(1, true);
                    return true;
                case R.id.travels_fragment:
                    mViewPager2.setCurrentItem(2, true);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewPager();

    }

    private void initViewPager() {
        //TODO:初始化界面
        mViewPager2 = findViewById(R.id.layout_content);
        bottomNavigationView = findViewById(R.id.main_bottomNavigation);
        customizedTravelFragment = new CustomizedTravelFragment();
        mapFragment = new MapFragment();
        travelsFragment = new TravelsFragment();
        mData.add(customizedTravelFragment);
        mData.add(mapFragment);
        mData.add(travelsFragment);
        mViewPager2.setAdapter(new MyFragmentStateAdapter(this, mData));
        mViewPager2.setOffscreenPageLimit(1);
        mViewPager2.registerOnPageChangeCallback(onPageChangeCallback);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelected);
    }
}