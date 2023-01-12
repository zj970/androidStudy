package com.zj970.tourism;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.zj970.tourism.adapter.MyFragmentStateAdapter;
import com.zj970.tourism.base.BaseActivity;
import com.zj970.tourism.fragment.CustomizedTravelFragment;
import com.zj970.tourism.fragment.GaoDeMapFragment;
import com.zj970.tourism.fragment.MapFragment;
import com.zj970.tourism.fragment.TravelsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zj970
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private CustomizedTravelFragment customizedTravelFragment;
    private MapFragment mapFragment;
    private GaoDeMapFragment gaoDeMapFragment;
    private TravelsFragment travelsFragment;
    private List<Fragment> mData = new ArrayList<>();
    private static ViewPager2 mViewPager2;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private View topBar;
    private Button personal;
    private Button items;

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
                    mViewPager2.setCurrentItem(0, false);//false关闭滑动效果，true打开
                    return true;
                case R.id.map_fragment:
                    mViewPager2.setCurrentItem(1, false);
                    return true;
                case R.id.travels_fragment:
                    mViewPager2.setCurrentItem(2, false);
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
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_return:
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void findByAll() {
        //TODO:初始化所有组件
        mViewPager2 = findViewById(R.id.layout_content);
        bottomNavigationView = findViewById(R.id.main_bottomNavigation);
        navigationView = findViewById(R.id.nav);
        drawerLayout = findViewById(R.id.drawer_layout);
        topBar = findViewById(R.id.top_bar);
        personal = topBar.findViewById(R.id.personal);
        items = topBar.findViewById(R.id.items);
    }

    private void initViewPager() {
        //TODO:初始化界面
        findByAll();
        personal.setOnClickListener(this::onClick);
        customizedTravelFragment = new CustomizedTravelFragment();
        //mapFragment = new MapFragment();
        gaoDeMapFragment = new GaoDeMapFragment();
        travelsFragment = new TravelsFragment();
        mData.add(customizedTravelFragment);//0
        mData.add(gaoDeMapFragment);//1
        //mData.add(mapFragment);
        mData.add(travelsFragment);//2
        mViewPager2.setAdapter(new MyFragmentStateAdapter(this, mData));
        mViewPager2.setOffscreenPageLimit(1);
        mViewPager2.registerOnPageChangeCallback(onPageChangeCallback);
        mViewPager2.setUserInputEnabled(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelected);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
    }

    public static void toThisMap() {
        mViewPager2.setCurrentItem(1, true);
    }
}