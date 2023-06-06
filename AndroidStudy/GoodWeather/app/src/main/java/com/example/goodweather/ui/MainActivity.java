package com.example.goodweather.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.baidu.location.BDLocation;
import com.example.goodweather.R;
import com.example.goodweather.ViewModel.MainViewModel;
import com.example.goodweather.bean.*;
import com.example.goodweather.bean.adapter.DailyAdapter;
import com.example.goodweather.bean.adapter.HourlyAdapter;
import com.example.goodweather.bean.adapter.LifestyleAdapter;
import com.example.goodweather.bean.adapter.OnClickItemCallback;
import com.example.goodweather.databinding.ActivityMainBinding;
import com.example.goodweather.databinding.DialogDailyDetailBinding;
import com.example.goodweather.databinding.DialogHourlyDetailBinding;
import com.example.goodweather.location.GoodLocation;
import com.example.goodweather.location.LocationCallback;
import com.example.goodweather.repository.CityRepository;
import com.example.goodweather.util.*;
import com.example.mylibrary.base.NetworkActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends NetworkActivity<ActivityMainBinding> implements LocationCallback, CityDialog.SelectedCityCallback {
    private static final String TAG = MainActivity.class.getSimpleName();

    //权限数组
    private final String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //请求权限意图
    private ActivityResultLauncher<String[]> requestPermissionIntent;

    private MainViewModel viewModel;


    private final List<DailyResponse.DailyBean> dailyBeanList = new ArrayList<>();
    private final DailyAdapter dailyAdapter = new DailyAdapter(dailyBeanList);
    private final List<LifestyleResponse.DailyBean> lifestyleList = new ArrayList<>();
    private final LifestyleAdapter lifestyleAdapter = new LifestyleAdapter(lifestyleList);
    private final List<HourlyResponse.HourlyBean> hourlyBeanList = new ArrayList<>();
    private final HourlyAdapter hourlyAdapter = new HourlyAdapter(hourlyBeanList);
    //城市弹窗
    private CityDialog cityDialog;

    private GoodLocation goodLocation;

    //菜单
    private Menu mMenu;
    //城市信息来源标识  0: 定位， 1: 切换城市
    private int cityFlag = 0;


    //城市名称，定位和切换城市都会重新赋值。
    private String mCityName;
    //是否正在刷新
    private boolean isRefresh;

    private ActivityResultLauncher<Intent> jumpActivityIntent;

    /**
     * 注册意图
     */
    @Override
    public void onRegister() {
        //请求权限意图
        requestPermissionIntent = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
            boolean fineLocation = Boolean.TRUE.equals(result.get(Manifest.permission.ACCESS_FINE_LOCATION));
            boolean writeStorage = Boolean.TRUE.equals(result.get(Manifest.permission.WRITE_EXTERNAL_STORAGE));
            if (fineLocation && writeStorage) {
                //权限已经获取到，开始定位
                startLocation();
            }
        });

        jumpActivityIntent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                //获取上个页面返回的数据
                String city = result.getData().getStringExtra(Constant.CITY_RESULT);
                //检查返回的城市 , 如果返回的城市是当前定位城市，并且当前定位标志为0，则不需要请求
                if (city.equals(MVUtils.getString(Constant.LOCATION_CITY)) && cityFlag == 0) {
                    LogUtil.d(TAG, "onRegister: 管理城市页面返回不需要进行天气查询");
                    return;
                }
                //反之就直接调用选中城市的方法进行城市天气搜索
                LogUtil.d(TAG, "onRegister: 管理城市页面返回进行天气查询");
                selectedCity(city);
            }
        });

    }

    /**
     * 初始化
     */
    @Override
    protected void onCreate() {
        //沉浸式
        setFullScreenImmersion();
        //初始化定位
        initLocation();
        //请求权限
        requestPermission();
        //初始化视图
        initView();
        //绑定ViewModel
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        //获取城市数据
        viewModel.getAllCity();
    }

    /**
     * 数据观察
     */
    @Override
    protected void onObserveData() {
        if (viewModel != null) {
            //城市数据返回
            viewModel.searchCityResponseMutableLiveData.observe(this, searchCityResponse -> {
                List<SearchCityResponse.LocationBean> location = searchCityResponse.getLocationBeanList();
                if (location != null && location.size() > 0) {
                    String id = location.get(0).getId();
                    //根据cityFlag设置重新定位菜单项是否显示
                    mMenu.findItem(R.id.item_relocation).setVisible(cityFlag == 1);

                    //检查正在刷新
                    if (isRefresh) {
                        showMsg("刷新完成");
                        binding.layRefresh.setRefreshing(false);
                        isRefresh = false;
                    }

                    //获取到城市的ID
                    if (id != null) {
                        //通过城市ID查询城市实时天气
                        viewModel.nowWeather(id);
                        //通过城市ID查询天气预报
                        viewModel.dailyWeather(id);
                        //通过城市ID查询生活指数
                        viewModel.lifestyle(id);
                        //通过城市ID查询逐小时天气预报
                        viewModel.hourlyWeather(id);
                        //通过城市ID查询空气质量
                        viewModel.airWeather(id);
                    }
                }
            });
            //实况天气返回
            viewModel.nowResponseMutableLiveData.observe(this, nowResponse -> {
                NowResponse.NowBean now = nowResponse.getNow();
                if (now != null) {
                    binding.tvWeek.setText(EasyDate.getTodayOfWeek());
                    binding.tvWeatherInfo.setText(now.getText());
                    binding.tvTemp.setText(now.getTemp());
                    binding.tvUpdateTime.setText("最近更新时间：" + EasyDate.greenwichupToSimpleTime(nowResponse.getUpdateTime()));

                    binding.tvWindDirection.setText("风向     " + now.getWindDir());//风向
                    binding.tvWindPower.setText("风力     " + now.getWindScale() + "级");//风力
                    binding.wwBig.startRotate();//大风车开始转动
                    binding.wwSmall.startRotate();//小风车开始转动

                }
            });
            //天气预报返回
            viewModel.dailyResponseMutableLiveData.observe(this, dailyResponse -> {
                List<DailyResponse.DailyBean> daily = dailyResponse.getDaily();
                if (daily != null) {
                    if (dailyBeanList.size() > 0) {
                        dailyBeanList.clear();
                    }
                    dailyBeanList.addAll(daily);
                    dailyAdapter.notifyDataSetChanged();
                    //显示当天的最高温和最低温
                    binding.tvHeight.setText(String.format("%s℃", daily.get(0).getTempMax()));
                    binding.tvLow.setText(String.format(" / %s℃", daily.get(0).getTempMin()));

                }
            });
            //生活指数返回
            viewModel.lifestyleResponseMutableLiveData.observe(this, lifestyleResponse -> {
                List<LifestyleResponse.DailyBean> daily = lifestyleResponse.getDaily();
                if (daily != null) {
                    if (lifestyleList.size() > 0) {
                        lifestyleList.clear();
                    }
                    lifestyleList.addAll(daily);
                    lifestyleAdapter.notifyDataSetChanged();
                }
            });

            viewModel.cityMutableLiveData.observe(this, provinces -> {
                //城市弹窗初始化
                cityDialog = CityDialog.getInstance(MainActivity.this, provinces);
                cityDialog.setSelectedCityCallback(this);
            });

            //逐小时天气预报
            viewModel.hourlyResponseMutableLiveData.observe(this, hourlyResponse -> {
                List<HourlyResponse.HourlyBean> hourly = hourlyResponse.getHourly();
                if (hourly != null) {
                    if (hourlyBeanList.size() > 0) {
                        hourlyBeanList.clear();
                    }
                    hourlyBeanList.addAll(hourly);
                    hourlyAdapter.notifyDataSetChanged();
                }
            });

            //空气质量返回
            viewModel.airResponseMutableLiveData.observe(this, airResponse -> {
                dismissLoadingDialog();//隐藏加载弹窗
                AirResponse.NowBean now = airResponse.getNow();
                if (now == null) return;
                binding.rpbAqi.setMaxProgress(300);//最大进度，用于计算
                binding.rpbAqi.setMinText("0");//设置显示最小值
                binding.rpbAqi.setMinTextSize(32f);
                binding.rpbAqi.setMaxText("300");//设置显示最大值
                binding.rpbAqi.setMaxTextSize(32f);
                binding.rpbAqi.setProgress(Float.parseFloat(now.getAqi()));//当前进度
                binding.rpbAqi.setArcBgColor(getColor(R.color.arc_bg_color));//圆弧的颜色
                binding.rpbAqi.setProgressColor(getColor(R.color.arc_progress_color));//进度圆弧的颜色
                binding.rpbAqi.setFirstText(now.getCategory());//空气质量描述 取值范围：优，良，轻度污染，中度污染，重度污染，严重污染
                binding.rpbAqi.setFirstTextSize(44f);//第一行文本的字体大小
                binding.rpbAqi.setSecondText(now.getAqi());//空气质量值
                binding.rpbAqi.setSecondTextSize(64f);//第二行文本的字体大小
                binding.rpbAqi.setMinText("0");
                binding.rpbAqi.setMinTextColor(getColor(R.color.arc_progress_color));

                binding.tvAirInfo.setText(String.format("空气%s", now.getCategory()));

                binding.tvPm10.setText(now.getPm10());//PM10
                binding.tvPm25.setText(now.getPm2p5());//PM2.5
                binding.tvNo2.setText(now.getNo2());//二氧化氮
                binding.tvSo2.setText(now.getSo2());//二氧化硫
                binding.tvO3.setText(now.getO3());//臭氧
                binding.tvCo.setText(now.getCo());//一氧化碳
            });

            //错误信息返回
            viewModel.failed.observe(this, this::showLongMsg);
        }
    }


    /**
     * 请求权限
     */
    private void requestPermission() {
        //因为项目的最低版本API是23，所以肯定需要动态请求危险权限，只需要判断权限是否拥有即可
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //开始权限请求
            requestPermissionIntent.launch(permissions);
            return;
        }
        //开始定位
        startLocation();
    }


    /**
     * 初始化定位
     */
    private void initLocation() {
        goodLocation = GoodLocation.getInstance(this);
        goodLocation.setCallback(this);
    }


    /**
     * 开始定位
     */
    private void startLocation() {
        cityFlag = 0;
        goodLocation.startLocation();
    }


    /**
     * 接收定位信息
     *
     * @param bdLocation 定位数据
     */
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        showLoadingDialog();//显示加载弹窗
        String city = bdLocation.getCity();             //获取城市
        String district = bdLocation.getDistrict();     //获取区县

        if (viewModel != null && district != null) {
            mCityName = district;//定位后重新赋值
            //保存定位城市
            MVUtils.put(Constant.LOCATION_CITY,district);
            //保存到我的城市数据表中
            viewModel.addMyCityData(district);
            //显示当前定位城市
            binding.tvCity.setText(district);
            //搜索城市
            viewModel.searchCity(district);
        } else {
            LogUtil.e("TAG", "district: " + district);
        }
    }

    /**
     * 初始化Recycler列表
     */
    private void initView() {
        setToolbarMoreIconCustom(binding.materialToolbar);
        binding.rvDaily.setLayoutManager(new LinearLayoutManager(this));
        binding.rvDaily.setAdapter(dailyAdapter);
        binding.rvLifestyle.setLayoutManager(new LinearLayoutManager(this));
        binding.rvLifestyle.setAdapter(lifestyleAdapter);
        //逐小时天气预报列表
        LinearLayoutManager hourlyLayoutManager = new LinearLayoutManager(this);
        hourlyLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.rvHourly.setLayoutManager(hourlyLayoutManager);
        binding.rvHourly.setAdapter(hourlyAdapter);
        hourlyAdapter.setOnClickItemCallback(position -> showHourlyDetailDialog(hourlyBeanList.get(position)));

        //下拉刷新监听
        binding.layRefresh.setOnRefreshListener(() -> {
            if (mCityName == null) {
                binding.layRefresh.setRefreshing(false);
                return;
            }
            //设置正在刷新
            isRefresh = true;
            //搜索城市
            viewModel.searchCity(mCityName);
        });

        //滑动监听
        binding.layScroll.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > oldScrollY) {
                //getMeasuredHeight() 表示控件的绘制高度
                if (scrollY > binding.layScrollHeight.getMeasuredHeight()) {
                    binding.materialToolbar.setTitle(mCityName == null ? "城市天气" : mCityName);
                }
            } else if (scrollY < oldScrollY) {
                if (scrollY < binding.layScrollHeight.getMeasuredHeight()) {
                    //改回原来的
                    binding.materialToolbar.setTitle("城市天气");
                }
            }
        });


        dailyAdapter.setOnClickItemCallback(position -> showDailyDetailDialog(dailyBeanList.get(position)));
    }

    public void setToolbarMoreIconCustom(Toolbar toolbar) {
        if (toolbar == null) return;
        toolbar.setTitle("城市天气");
        Drawable moreIcon = ContextCompat.getDrawable(toolbar.getContext(), R.drawable.ic_round_add_32);
        if (moreIcon != null) toolbar.setOverflowIcon(moreIcon);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mMenu = menu;
        //根据cityFlag设置重新定位菜单项是否显示
        mMenu.findItem(R.id.item_relocation).setVisible(cityFlag == 1);
        //根据使用必应壁纸的状态，设置item项是否选中
        mMenu.findItem(R.id.item_bing).setChecked(MVUtils.getBoolean(Constant.USED_BING));
        return true;
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_switching_cities:
                if (cityDialog != null && !cityDialog.getProvinceList().isEmpty()) {
                    cityDialog.show();
                }
                break;
            case R.id.item_relocation:
                startLocation();//点击重新定位item时，再次定位一下。
                break;
            case R.id.item_manage_city:
                jumpActivityIntent.launch(new Intent(mContext,ManageCityActivity.class));
                break;
            case R.id.item_bing:
                item.setChecked(!item.isChecked());
                MVUtils.put(Constant.USED_BING, item.isChecked());
                String bingUrl = MVUtils.getString(Constant.BING_URL);
                //更新壁纸
                updateBgImage(item.isChecked(), bingUrl);
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //更新壁纸
        updateBgImage(MVUtils.getBoolean(Constant.USED_BING), MVUtils.getString(Constant.BING_URL));
    }


    @Override
    public void selectedCity(String cityName) {
        cityFlag = 1;//切换城市
        mCityName = cityName;//切换城市后赋值
        //搜索城市
        viewModel.searchCity(cityName);
        //显示所选城市
        binding.tvCity.setText(cityName);
    }

    private void updateBgImage(boolean usedBing, String bingUrl) {
        if (usedBing && !bingUrl.isEmpty()) {
            GlideUtils.loadImg(this, bingUrl, binding.layRoot);
        } else {
            binding.layRoot.setBackground(ContextCompat.getDrawable(this, R.drawable.main_bg));
        }
    }

    private void showDailyDetailDialog(DailyResponse.DailyBean dailyBean) {
        BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
        DialogDailyDetailBinding detailBinding = DialogDailyDetailBinding.inflate(LayoutInflater.from(MainActivity.this), null, false);
        //关闭弹窗
        detailBinding.ivClose.setOnClickListener(v -> dialog.dismiss());
        //设置数据显示
        detailBinding.toolbarDaily.setTitle(String.format("%s   %s", dailyBean.getFxDate(), EasyDate.getWeek(dailyBean.getFxDate())));
        detailBinding.toolbarDaily.setSubtitle("天气预报详情");
        detailBinding.tvTmpMax.setText(String.format("%s℃", dailyBean.getTempMax()));
        detailBinding.tvTmpMin.setText(String.format("%s℃", dailyBean.getTempMin()));
        detailBinding.tvUvIndex.setText(dailyBean.getUvIndex());
        detailBinding.tvCondTxtD.setText(dailyBean.getTextDay());
        detailBinding.tvCondTxtN.setText(dailyBean.getTextNight());
        detailBinding.tvWindDeg.setText(String.format("%s°", dailyBean.getWind360Day()));
        detailBinding.tvWindDir.setText(dailyBean.getWindDirDay());
        detailBinding.tvWindSc.setText(String.format("%s级", dailyBean.getWindScaleDay()));
        detailBinding.tvWindSpd.setText(String.format("%s公里/小时", dailyBean.getWindSpeedDay()));
        detailBinding.tvCloud.setText(String.format("%s%%", dailyBean.getCloud()));
        detailBinding.tvHum.setText(String.format("%s%%", dailyBean.getHumidity()));
        detailBinding.tvPres.setText(String.format("%shPa", dailyBean.getPressure()));
        detailBinding.tvPcpn.setText(String.format("%smm", dailyBean.getPrecip()));
        detailBinding.tvVis.setText(String.format("%skm", dailyBean.getVis()));
        dialog.setContentView(detailBinding.getRoot());
        dialog.show();
    }

    private void showHourlyDetailDialog(HourlyResponse.HourlyBean hourlyBean) {
        BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
        DialogHourlyDetailBinding detailBinding = DialogHourlyDetailBinding.inflate(LayoutInflater.from(MainActivity.this), null, false);
        //关闭弹窗
        detailBinding.ivClose.setOnClickListener(v -> dialog.dismiss());
        //设置数据显示
        String time = EasyDate.updateTime(hourlyBean.getFxTime());
        detailBinding.toolbarHourly.setTitle(EasyDate.showTimeInfo(time) + time);
        detailBinding.toolbarHourly.setSubtitle("逐小时预报详情");
        detailBinding.tvTmp.setText(String.format("%s℃", hourlyBean.getTemp()));
        detailBinding.tvCondTxt.setText(hourlyBean.getText());
        detailBinding.tvWindDeg.setText(String.format("%s°", hourlyBean.getWind360()));
        detailBinding.tvWindDir.setText(hourlyBean.getWindDir());
        detailBinding.tvWindSc.setText(String.format("%s级", hourlyBean.getWindScale()));
        detailBinding.tvWindSpd.setText(String.format("公里/小时%s", hourlyBean.getWindSpeed()));
        detailBinding.tvHum.setText(String.format("%s%%", hourlyBean.getHumidity()));
        detailBinding.tvPres.setText(String.format("%shPa", hourlyBean.getPressure()));
        detailBinding.tvPop.setText(String.format("%s%%", hourlyBean.getPop()));
        detailBinding.tvDew.setText(String.format("%s℃", hourlyBean.getDew()));
        detailBinding.tvCloud.setText(String.format("%s%%", hourlyBean.getCloud()));
        dialog.setContentView(detailBinding.getRoot());
        dialog.show();
    }

}
