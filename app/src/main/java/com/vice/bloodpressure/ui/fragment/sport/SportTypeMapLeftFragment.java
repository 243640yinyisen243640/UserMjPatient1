package com.vice.bloodpressure.ui.fragment.sport;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.ui.activity.sport.HomeSportCountDownActivity;
import com.wei.android.lib.colorview.view.ColorRelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 运动类型之地图
 * 作者: LYD
 * 创建日期: 2020/9/27 16:51
 */
public class SportTypeMapLeftFragment extends BaseFragment {
    @BindView(R.id.img_sport_type)
    ImageView imgSportType;
    @BindView(R.id.tv_sport_type_text)
    TextView tvSportTypeText;
    @BindView(R.id.rl_center)
    ColorRelativeLayout rlCenter;
    //    @BindView(R.id.bmapView)
    //    MapView mMapView;
    //
    //    private BaiduMap mBaiduMap;
    //    //定位相关
    //    private LocationClient mLocClient;
    //    private MyLocationListener myListener = new MyLocationListener();
    //    //是否首次定位
    //    private boolean isFirstLoc = true;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sport_type_map_left;
    }

    @Override
    protected void init(View rootView) {
        //initLocation();
    }

//    private void initLocation() {
//        mBaiduMap = mMapView.getMap();
//        //开启定位图层
//        mBaiduMap.setMyLocationEnabled(true);
//        //定位初始化
//        mLocClient = new LocationClient(getPageContext());
//        mLocClient.registerLocationListener(myListener);
//        LocationClientOption option = new LocationClientOption();
//        //打开gps
//        option.setOpenGps(true);
//        //设置坐标类型
//        option.setCoorType("bd09ll");
//        option.setScanSpan(1000);
//        mLocClient.setLocOption(option);
//        mLocClient.start();
//    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick(R.id.rl_center)
    public void onViewClicked() {
        startActivity(new Intent(getPageContext(), HomeSportCountDownActivity.class));
    }

//    @Override
    //    public void onResume() {
    //        super.onResume();
    //        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
    //        mMapView.onResume();
    //    }
    //
    //    @Override
    //    public void onPause() {
    //        super.onPause();
    //        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
    //        mMapView.onPause();
    //    }
    //
    //    @Override
    //    public void onDestroy() {
    //        mLocClient.stop();
    //        mBaiduMap.setMyLocationEnabled(false);
    //        mMapView.onDestroy();
    //        mMapView = null;
    //        super.onDestroy();
    //    }
    //
    //    /**
    //     * 定位SDK监听函数
    //     */
    //    public class MyLocationListener extends BDAbstractLocationListener {
    //        @Override
    //        public void onReceiveLocation(BDLocation location) {
    //            // MapView 销毁后不在处理新接收的位置
    //            if (location == null || mMapView == null) {
    //                return;
    //            }
    //            double mCurrentLat = location.getLatitude();
    //            double mCurrentLon = location.getLongitude();
    //            float mCurrentAccracy = location.getRadius();
    //            int mCurrentDirection = 0;
    //            //设置定位数据的精度信息，单位：米
    //            //此处设置开发者获取到的方向信息，顺时针0-360
    //            MyLocationData myLocationData = new MyLocationData.Builder()
    //                    //设置定位数据的精度信息，单位：米
    //                    .accuracy(location.getRadius())
    //                    //此处设置开发者获取到的方向信息，顺时针0-360
    //                    .direction(mCurrentDirection)
    //                    .latitude(location.getLatitude())
    //                    .longitude(location.getLongitude())
    //                    .build();
    //            mBaiduMap.setMyLocationData(myLocationData);
    //            if (isFirstLoc) {
    //                isFirstLoc = false;
    //                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
    //                MapStatus.Builder builder = new MapStatus.Builder();
    //                builder.target(ll).zoom(18.0f);
    //                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    //            }
    //        }
    //    }
}
