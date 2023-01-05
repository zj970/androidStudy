package com.zj970.tourism.fragment;

import android.graphics.BitmapFactory;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.zj970.tourism.MainActivity;
import com.zj970.tourism.R;
import com.zj970.tourism.base.BaseApplication;

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
    public LocationClient mLocationClient;
    BaiduMap mBaiduMap;
    boolean isFirstLocate = true;
    //标点的图标
    BitmapDescriptor bitmap;
    //标点纬度
    double markerLatitude = 0;
    //标点经度
    double markerLongitude = 0;
    //重置定位按钮
    ImageButton ibLocation;
    //标点也可以是说覆盖物
    Marker marker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocationClient.setAgreePrivacy(true);
        try {
            mLocationClient = new LocationClient(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mLocationClient.registerLocationListener(new MyLocationListener());
    }

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

            ibLocation = rootView.findViewById(R.id.ib_location);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        btn_menu_2.setOnClickListener(this::onClick);
        btn_menu_3.setOnClickListener(this::onClick);
        ibLocation.setOnClickListener(this::resetLocation);
        menu_2_icon.setImageResource(R.drawable.arrow_right);
        menu_3_icon.setImageResource(R.drawable.arrow_right);
        initLocation();
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

    /**
     * 定位初始化
     */
    public void initLocation() {
        //添加隐私合规政策
        LocationClient.setAgreePrivacy(true);
        mBaiduMap = mapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        if (mLocationClient == null) {
            try {
                mLocationClient = new LocationClient(getContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (mLocationClient != null) {
            MyLocationListener myListener = new MyLocationListener();
            mLocationClient.registerLocationListener(myListener);
            LocationClientOption option = new LocationClientOption();
            // 设置高精度定位
            option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
            //可选，默认gcj02，设置返回的定位结果坐标系
            option.setCoorType("bd09ll");
            //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
            option.setScanSpan(50000);
            //可选，设置是否需要地址信息，默认不需要
            option.setIsNeedAddress(true);
            //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
            option.setIsNeedLocationDescribe(true);
            //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
            option.setIsNeedLocationPoiList(true);
            //可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
            option.setIgnoreKillProcess(false);
            //可选，默认false，设置是否收集CRASH信息，默认收集
            option.SetIgnoreCacheException(false);
            mLocationClient.setLocOption(option);
            mLocationClient.start();//开始定位
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            // Toast.makeText(MapFragment.this.getContext(), location.getAddrStr(), Toast.LENGTH_SHORT).show();
            // MapView 销毁后不在处理新接收的位置
            if (mapView == null) {
                return;
            }
            double resultLatitude;
            double resultLongitude;

            if (markerLatitude == 0) {//自动定位
                resultLatitude = location.getLatitude();
                resultLongitude = location.getLongitude();
                ibLocation.setVisibility(View.GONE);
            } else {//标点定位
                resultLatitude = markerLatitude;
                resultLongitude = markerLongitude;
                ibLocation.setVisibility(View.VISIBLE);
            }

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())// 设置定位数据的精度信息，单位：米
                    .direction(location.getDirection()) // 此处设置开发者获取到的方向信息，顺时针0-360
                    .latitude(resultLatitude)
                    .longitude(resultLongitude)
                    .build();

            mBaiduMap.setMyLocationData(locData);// 设置定位数据, 只有先允许定位图层后设置数据才会生效
            LatLng latLng = new LatLng(resultLatitude, resultLongitude);
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(latLng).zoom(20.0f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }
    }


    /**
     * 点切换到其他标点位置时，重置定位显示，点击之后回到自动定位
     *
     * @param view
     */
    public void resetLocation(View view) {
        markerLatitude = 0;
        initLocation();
        marker.remove();//清除标点
    }



    /**
     * 地图点击
     */
    private void mapOnClick() {
        // 设置marker图标
        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapPoiClick(MapPoi mapPoi) {

            }

            //此方法就是点击地图监听
            @Override
            public void onMapClick(LatLng latLng) {
                //获取经纬度
                markerLatitude = latLng.latitude;
                markerLongitude = latLng.longitude;
                //先清除图层
                mBaiduMap.clear();
                // 定义Maker坐标点
                LatLng point = new LatLng(markerLatitude, markerLongitude);
                // 构建MarkerOption，用于在地图上添加Marker
                MarkerOptions options = new MarkerOptions().position(point)
                        .icon(bitmap);
                // 在地图上添加Marker，并显示
                //mBaiduMap.addOverlay(options);
                marker = (Marker) mBaiduMap.addOverlay(options);
                Bundle bundle = new Bundle();
                bundle.putSerializable("info", "纬度：" + markerLatitude + "   经度：" + markerLongitude);
                marker.setExtraInfo(bundle);//将bundle值传入marker中，给baiduMap设置监听时可以得到它

                //点击地图之后重新定位
                initLocation();
            }
        });

    }

}
