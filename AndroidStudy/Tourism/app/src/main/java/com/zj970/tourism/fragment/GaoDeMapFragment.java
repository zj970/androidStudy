package com.zj970.tourism.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.*;
import com.amap.api.maps.model.*;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.RotateAnimation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.*;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zj970.tourism.R;
import com.zj970.tourism.RouteActivity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author: zj970
 * @date: 2023/1/11
 */
public class GaoDeMapFragment extends Fragment implements
        AMapLocationListener, LocationSource, PoiSearch.OnPoiSearchListener,
        AMap.OnMapClickListener, AMap.OnMapLongClickListener,
        GeocodeSearch.OnGeocodeSearchListener, EditText.OnKeyListener,
        AMap.OnMarkerClickListener, AMap.OnMarkerDragListener,
        AMap.InfoWindowAdapter, AMap.OnInfoWindowClickListener {

    View rootView;
    private static final String TAG = "GaoDeMapFragment";
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private MapView mapView;

    //地图控制器
    private AMap aMap = null;
    //位置更改监听
    private LocationSource.OnLocationChangedListener mListener;
    //定位样式
    private MyLocationStyle myLocationStyle = new MyLocationStyle();

    //定义一个UiSettings对象
    private UiSettings mUiSettings;

    //POI查询对象
    private PoiSearch.Query query;
    //POI搜索对象
    private PoiSearch poiSearch;
    //城市码
    private String cityCode = null;
    //浮动按钮
    private FloatingActionButton fabPOI;

    //地理编码搜索
    private GeocodeSearch geocodeSearch;
    //解析成功标识码
    private static final int PARSE_SUCCESS_CODE = 1000;

    //输入框
    private EditText etAddress;

    //浮动按钮  清空地图标点
    private FloatingActionButton fabClearMarker;
    private FloatingActionButton fabRoute;

    //标点列表
    private List<Marker> markerList = new ArrayList<>();

    //城市
    private String city;



    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.gaode_map,container,false);

        fabPOI = rootView.findViewById(R.id.fab_poi);
        fabClearMarker = rootView.findViewById(R.id.fab_clear_marker);
        fabRoute = rootView.findViewById(R.id.fab_route);
        etAddress = rootView.findViewById(R.id.et_address);
        //键盘按键监听
        etAddress.setOnKeyListener(this);
        fabClearMarker.setOnClickListener(this::clearAllMarker);
        fabRoute.setOnClickListener(this::jumpRouteActivity);
        //初始化定位
        initLocation();

        //初始化地图
        initMap(savedInstanceState);

        //检查Android版本
        checkingAndroidVersion();
        return rootView;
    }



    /**
     * 初始化定位
     */
    private void initLocation() {
        //初始化定位
        try {
            mLocationClient = new AMapLocationClient(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mLocationClient != null) {
            //设置定位回调监听
            mLocationClient.setLocationListener(this);
            //初始化AMapLocationClientOption对象
            mLocationOption = new AMapLocationClientOption();
            //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //获取最近3s内精度最高的一次定位结果：
            //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
            mLocationOption.setOnceLocationLatest(true);
            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            //设置定位请求超时时间，单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
            mLocationOption.setHttpTimeOut(20000);
            //关闭缓存机制，高精度定位会产生缓存。
            mLocationOption.setLocationCacheEnable(false);
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
        }
    }

    /**
     * 初始化地图
     *
     * @param savedInstanceState
     */
    private void initMap(Bundle savedInstanceState) {
        mapView = rootView.findViewById(R.id.map_view);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);
        //初始化地图控制器对象
        aMap = mapView.getMap();

        //设置最小缩放等级为16 ，缩放级别范围为[3, 20]
        aMap.setMinZoomLevel(16);
        //开启室内地图
        aMap.showIndoorMap(true);

        //实例化UiSettings类对象
        mUiSettings = aMap.getUiSettings();
        //隐藏缩放按钮 默认显示
        mUiSettings.setZoomControlsEnabled(false);
        //显示比例尺 默认不显示
        mUiSettings.setScaleControlsEnabled(true);

        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.gps_point));
        // 自定义精度范围的圆形边框颜色  都为0则透明
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        // 自定义精度范围的圆形边框宽度  0 无宽度
        myLocationStyle.strokeWidth(0);
        // 设置圆形的填充颜色  都为0则透明
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));

        //设置定位蓝点的Style
        aMap.setMyLocationStyle(myLocationStyle);

        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);

        //设置地图点击事件
        aMap.setOnMapClickListener(this);
        //设置地图长按事件
        aMap.setOnMapLongClickListener(this);
        //设置地图Marker点击事件
        aMap.setOnMarkerClickListener(this);

        //设置地图Marker拖拽事件
        aMap.setOnMarkerDragListener(this);

        //设置InfoWindowAdapter监听
        aMap.setInfoWindowAdapter(this);

        //设置InfoWindow点击事件
        aMap.setOnInfoWindowClickListener(this);


        //构造 GeocodeSearch 对象
        try {
            geocodeSearch = new GeocodeSearch(getContext());
        } catch (AMapException e) {
            e.printStackTrace();
        }
        //设置监听
        geocodeSearch.setOnGeocodeSearchListener(this);
    }

    /**
     * 检查Android版本
     */
    private void checkingAndroidVersion() {
/*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Android6.0及以上先获取权限再定位
            requestPermission();
        } else {*/
            //Android6.0以下直接定位
            //启动定位
            mLocationClient.startLocation();
        //}
    }

    /**
     * Toast提示
     *
     * @param msg 提示内容
     */
    private void showMsg(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 接收异步返回的定位结果
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //地址
                String address = aMapLocation.getAddress();
                //城市赋值
                city = aMapLocation.getCity();
                //获取纬度
                double latitude = aMapLocation.getLatitude();
                //获取经度
                double longitude = aMapLocation.getLongitude();

                Log.d("MainActivity", aMapLocation.getCity());
                showMsg(address);

                //停止定位后，本地定位服务并不会被销毁
                mLocationClient.stopLocation();

                //显示地图定位结果
                if (mListener != null) {
                    // 显示系统图标
                    mListener.onLocationChanged(aMapLocation);
                }

                //显示浮动按钮
                fabPOI.show();

                fabPOI.setOnClickListener(this::queryPOI);

                //赋值
                cityCode = aMapLocation.getCityCode();
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mLocationClient == null) {
            mLocationClient.startLocation();//启动定位
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //销毁定位客户端，同时销毁本地定位服务。
        if (mLocationClient != null) {
            mLocationClient.onDestroy();
        }
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    /**
     * POI搜索返回
     *
     * @param poiResult POI所有数据
     * @param i
     */
    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        //解析result获取POI信息

        //获取POI组数列表
        ArrayList<PoiItem> poiItems = poiResult.getPois();
        for (PoiItem poiItem : poiItems) {
            Log.d("MainActivity", " Title：" + poiItem.getTitle() + " Snippet：" + poiItem.getSnippet());
        }
    }

    /**
     * POI中的项目搜索返回
     *
     * @param poiItem 获取POI item
     * @param i
     */
    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * 浮动按钮点击查询附近POI
     *
     * @param view
     */
    public void queryPOI(View view) {
        //构造query对象
        query = new PoiSearch.Query("购物", "", cityCode);
        // 设置每页最多返回多少条poiitem
        query.setPageSize(10);
        //设置查询页码
        query.setPageNum(1);
        //构造 PoiSearch 对象
        try {
            poiSearch = new PoiSearch(getContext(), query);
        } catch (AMapException e) {
            e.printStackTrace();
        }
        //设置搜索回调监听
        poiSearch.setOnPoiSearchListener(this);
        //发起搜索附近POI异步请求
        poiSearch.searchPOIAsyn();
    }

    /**
     * 地图单击事件
     *
     * @param latLng
     */
    @Override
    public void onMapClick(LatLng latLng) {
        //通过经纬度获取地址
        //latlonToAddress(latLng);
        //添加标点
        addMarker(latLng);
        //改变地图中心点
        updateMapCenter(latLng);
    }

    /**
     * 改变地图中心位置
     * @param latLng 位置
     */
    private void updateMapCenter(LatLng latLng) {
        // CameraPosition 第一个参数： 目标位置的屏幕中心点经纬度坐标。
        // CameraPosition 第二个参数： 目标可视区域的缩放级别
        // CameraPosition 第三个参数： 目标可视区域的倾斜度，以角度为单位。
        // CameraPosition 第四个参数： 可视区域指向的方向，以角度为单位，从正北向顺时针方向计算，从0度到360度
        CameraPosition cameraPosition = new CameraPosition(latLng, 16, 30, 0);
        //位置变更
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        //改变位置
        //aMap.moveCamera(cameraUpdate);
        //带动画的移动
        aMap.animateCamera(cameraUpdate);

    }

    /**
     * 地图长按事件
     *
     * @param latLng
     */
    @Override
    public void onMapLongClick(LatLng latLng) {
        //通过经纬度获取地址
        latlonToAddress(latLng);
    }

    /**
     * 添加地图标点
     *
     * @param latLng
     */
    private void addMarker(LatLng latLng) {
        //显示浮动按钮
        fabClearMarker.show();
        //标题
        String title;
        String Content;
        Log.d(TAG, "addMarker: "+latLng.latitude);
        Log.d(TAG, "addMarker: "+latLng.longitude);
        //添加标点
        Marker marker = aMap.addMarker(new MarkerOptions()
                .draggable(true)//可拖动
                .position(latLng)
                .title("标题")
                .snippet("详细信息"));

        //绘制Marker时显示InfoWindow
        //marker.showInfoWindow();

        //设置标点的绘制动画效果
        Animation animation = new RotateAnimation(marker.getRotateAngle(), marker.getRotateAngle() + 180, 0, 0, 0);
        long duration = 1000L;
        animation.setDuration(duration);
        animation.setInterpolator(new LinearInterpolator());

        marker.setAnimation(animation);
        marker.startAnimation();

        markerList.add(marker);
    }

    /**
     * 通过经纬度获取地址
     *
     * @param latLng
     */
    private void latlonToAddress(LatLng latLng) {
        //位置点  通过经纬度进行构建
        LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
        //逆编码查询  第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 20, GeocodeSearch.AMAP);
        //异步获取地址信息
        geocodeSearch.getFromLocationAsyn(query);
    }

    /**
     * 坐标转地址
     *
     * @param regeocodeResult
     * @param rCode
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
        //解析result获取地址描述信息
        if (rCode == PARSE_SUCCESS_CODE) {
            RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
            //显示解析后的地址
            Log.d("MainActivity", regeocodeAddress.getFormatAddress());
            //showMsg("地址：" + regeocodeAddress.getFormatAddress());

            LatLonPoint latLonPoint = regeocodeResult.getRegeocodeQuery().getPoint();
            LatLng latLng = new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
            addMarker(latLng);
        } else {
            showMsg("获取地址失败");
        }

    }

    /**
     * 地址转坐标
     *
     * @param geocodeResult
     * @param rCode
     */
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int rCode) {
        if (rCode == PARSE_SUCCESS_CODE) {
            List<GeocodeAddress> geocodeAddressList = geocodeResult.getGeocodeAddressList();
            if (geocodeAddressList != null && geocodeAddressList.size() > 0) {
                LatLonPoint latLonPoint = geocodeAddressList.get(0).getLatLonPoint();
                //显示解析后的坐标
                showMsg("坐标：" + latLonPoint.getLongitude() + "，" + latLonPoint.getLatitude());
            }

        } else {
            showMsg("获取坐标失败");
        }
    }

    /**
     * 键盘点击
     *
     * @param v
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
            //获取输入框的值
            String address = etAddress.getText().toString().trim();
            if (address == null || address.isEmpty()) {
                showMsg("请输入地址");
            } else {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                //隐藏软键盘
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);

                // name表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode
                GeocodeQuery query = new GeocodeQuery(address, city);
                geocodeSearch.getFromLocationNameAsyn(query);
            }
            return true;
        }
        return false;
    }

    /**
     * 清空地图Marker
     *
     * @param view
     */
    public void clearAllMarker(View view) {
        if (markerList != null && markerList.size() > 0) {
            for (Marker markerItem : markerList) {
                markerItem.remove();
            }
        }
        fabClearMarker.hide();
    }

    /**
     * Marker点击事件
     *
     * @param marker
     * @return
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        //showMsg("点击了标点");
        //显示InfoWindow
        if (!marker.isInfoWindowShown()) {
            //显示
            marker.showInfoWindow();
        } else {
            //隐藏
            marker.hideInfoWindow();
        }
        return true;
    }

    /**
     * 开始拖动
     *
     * @param marker
     */
    @Override
    public void onMarkerDragStart(Marker marker) {
        Log.d(TAG, "开始拖动");
    }

    /**
     * 拖动中
     *
     * @param marker
     */
    @Override
    public void onMarkerDrag(Marker marker) {
        Log.d(TAG, "拖动中");
    }

    /**
     * 拖动完成
     *
     * @param marker
     */
    @Override
    public void onMarkerDragEnd(Marker marker) {
        Log.d(TAG, "拖动完成");
    }

    /**
     * 修改内容
     *
     * @param marker
     * @return
     */
    @Override
    public View getInfoContents(Marker marker) {
        View infoContent = getLayoutInflater().inflate(
                R.layout.custom_info_contents, null);
        render(marker, infoContent);
        return infoContent;
    }

    /**
     * 修改背景
     *
     * @param marker
     */
    @Override
    public View getInfoWindow(Marker marker) {
        View infoWindow = getLayoutInflater().inflate(
                R.layout.custom_info_window, null);

        render(marker, infoWindow);
        return infoWindow;
    }

    /**
     * 渲染
     *
     * @param marker
     * @param view
     */
    private void render(Marker marker, View view) {
        ((ImageView) view.findViewById(R.id.badge))
                .setImageResource(R.drawable.icon_yuan);

        //修改InfoWindow标题内容样式
        String title = marker.getTitle();
        TextView titleUi = ((TextView) view.findViewById(R.id.title));
        if (title != null) {
            SpannableString titleText = new SpannableString(title);
            titleText.setSpan(new ForegroundColorSpan(Color.RED), 0,
                    titleText.length(), 0);
            titleUi.setTextSize(15);
            titleUi.setText(titleText);

        } else {
            titleUi.setText("");
        }
        //修改InfoWindow片段内容样式
        String snippet = marker.getSnippet();
        TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
        if (snippet != null) {
            SpannableString snippetText = new SpannableString(snippet);
            snippetText.setSpan(new ForegroundColorSpan(Color.GREEN), 0,
                    snippetText.length(), 0);
            snippetUi.setTextSize(20);
            snippetUi.setText(snippetText);
        } else {
            snippetUi.setText("");
        }
    }

    /**
     * InfoWindow点击事件
     *
     * @param marker
     */
    @Override
    public void onInfoWindowClick(Marker marker) {
        showMsg("弹窗内容：标题：" + marker.getTitle() + "\n片段：" + marker.getSnippet());
    }

    /**
     * 进入路线规划
     * @param view
     */
    public void jumpRouteActivity(View view) {
        startActivity(new Intent(getActivity(), RouteActivity.class));
    }
}

