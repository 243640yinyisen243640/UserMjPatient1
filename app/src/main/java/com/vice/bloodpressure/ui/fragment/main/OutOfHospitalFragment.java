package com.vice.bloodpressure.ui.fragment.main;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionOwner;
import com.gyf.immersionbar.components.SimpleImmersionProxy;
import com.imuxuan.floatingview.FloatingView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.NearHospitalAdapter;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.CityBean;
import com.vice.bloodpressure.bean.HospitalBean;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.ProvincesBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述: 院外管理 未绑定
 * 作者: LYD
 * 创建日期: 2019/3/20 10:13
 */
public class OutOfHospitalFragment extends BaseFragment implements SimpleImmersionOwner {
    private static final int GET_NO_DATA = 10010;
    @BindView(R.id.sp_left)
    Spinner spLeft;
    @BindView(R.id.sp_right)
    Spinner spRight;
    @BindView(R.id.ll_blood)
    LinearLayout llBlood;
    @BindView(R.id.lv_near_hospital)
    ListView lvNearHospital;
    @BindView(R.id.srl_near_hospital)
    SmartRefreshLayout srlNearHospital;
    private LoginBean user;

    private SpinnerAdapter<CityBean> cAdapter;

    private List<ProvincesBean> provincesList;//省份
    private List<CityBean> cityList;//城市
    private List<HospitalBean> hospitalList;//医院

    //
    private NearHospitalAdapter adapter;
    private List<HospitalBean> bloodPressureList;//血压数据
    private List<HospitalBean> bpList;//上拉加载数据
    private int i = 2;//上拉加载页数
    //
    private String proId;
    private SimpleImmersionProxy mSimpleImmersionProxy = new SimpleImmersionProxy(this);

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case 1://获取省
                final List<ProvincesBean> pList = (List<ProvincesBean>) msg.obj;
                ProvincesBean provinces = new ProvincesBean(-1, "未选择", "");
                pList.add(0, provinces);
                SpinnerAdapter<ProvincesBean> pAdapter = new SpinnerAdapter(pList, 1);
                spLeft.setAdapter(pAdapter);

                spLeft.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        int itemId = spLeft.getSelectedItemPosition();
                        String pCityCode = pList.get(itemId).getCitycode() + "";
                        int pId = pList.get(itemId).getId();
                        TextView textView = view.findViewById(R.id.tv_spinner);
                        if (!textView.getText().equals("未选择")) {
                            textView.setTextColor(Color.GREEN);
                        }
                        if (0 == itemId) {
                            //恢复初始数据
                            getHospital("", "");//获取默认的列表
                            cityList = new ArrayList<>();
                            CityBean city = new CityBean("未选择", "");
                            cityList.add(city);
                            cAdapter = new SpinnerAdapter<>(cityList, 2);
                            spRight.setAdapter(cAdapter);
                        } else {
                            //开始搜索
                            getHospital(pCityCode, "");
                            getCity(pId);
                        }
                        proId = pCityCode;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                break;
            case 2://获取城市
                final List<CityBean> cList = (List<CityBean>) msg.obj;
                CityBean city = new CityBean("未选择", "");
                cList.add(0, city);
                cAdapter = new SpinnerAdapter<>(cList, 2);
                spRight.setAdapter(cAdapter);
                spRight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        int itemCityId = spRight.getSelectedItemPosition();
                        String cityId = cList.get(itemCityId).getCitycode();
                        TextView textView = view.findViewById(R.id.tv_spinner);
                        if (!textView.getText().equals("未选择")) {
                            textView.setTextColor(Color.GREEN);
                        }
                        if (0 == itemCityId) {
                            getHospital(proId, "");
                        } else {
                            getHospital("", cityId);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                break;
            case 3://医院列表
                bloodPressureList = (List<HospitalBean>) msg.obj;
                adapter = new NearHospitalAdapter(Utils.getApp(), R.layout.item_near_hospital, bloodPressureList);
                lvNearHospital.setAdapter(adapter);
                break;
            case 4://医院列表刷新
                i++;
                adapter.notifyDataSetChanged();
                break;
            case GET_NO_DATA:
                String errorMsg = (String) msg.obj;
                ToastUtils.showShort(errorMsg);
                lvNearHospital.setAdapter(null);
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_outside;
    }

    @Override
    protected void init(View rootView) {
        FloatingView.get().remove();
        user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        initViews(rootView);
        //获取默认的列表
        getHospital("", "");
        //获取默认省份
        getData();
        //默认城市
        cityList = new ArrayList<>();
        CityBean city = new CityBean("未选择", "");
        cityList.add(city);
        cAdapter = new SpinnerAdapter<>(cityList, 2);
        spRight.setAdapter(cAdapter);
    }

    private void initViews(View view) {
        //下拉刷新
        srlNearHospital.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                srlNearHospital.finishRefresh(2000);
                HashMap map = new HashMap<>();
                map.put("page", 1);
                XyUrl.okPost(XyUrl.HOSPITAL, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        bloodPressureList = JSONObject.parseArray(value, HospitalBean.class);
                        Message message = Message.obtain();
                        message.what = 3;
                        message.obj = bloodPressureList;
                        sendHandlerMessage(message);
                    }

                    @Override
                    public void onError(int error, String errorMsg) {

                    }
                });
            }
        });

        //上拉加载
        srlNearHospital.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                srlNearHospital.finishLoadMore(2000);
                HashMap map = new HashMap<>();
                map.put("page", i);
                //判断
                XyUrl.okPost(XyUrl.HOSPITAL, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        bpList = JSONObject.parseArray(value, HospitalBean.class);
                        bloodPressureList.addAll(bpList);
                        Message message = Message.obtain();
                        message.what = 4;
                        sendHandlerMessage(message);
                    }

                    @Override
                    public void onError(int error, String errorMsg) {

                    }
                });
            }
        });

    }

    /**
     * 医院列表
     */
    private void getHospital(String proId, String cityId) {
        if (TextUtils.isEmpty(proId) && TextUtils.isEmpty(cityId)) {
            HashMap map = new HashMap<>();
            map.put("page", 1);
            XyUrl.okPost(XyUrl.HOSPITAL, map, new OkHttpCallBack<String>() {
                @Override
                public void onSuccess(String value) {
                    hospitalList = JSONObject.parseArray(value, HospitalBean.class);
                    Message msg = Message.obtain();
                    msg.obj = hospitalList;
                    msg.what = 3;
                    sendHandlerMessage(msg);
                }

                @Override
                public void onError(int errorCode, final String errorMsg) {
                    if (ConstantParam.NO_DATA == errorCode) {
                        Message message = Message.obtain();
                        message.what = GET_NO_DATA;
                        message.obj = errorMsg;
                        sendHandlerMessage(message);
                    }
                }
            });
        } else if (!TextUtils.isEmpty(proId) && TextUtils.isEmpty(cityId)) {//搜索省
            HashMap map = new HashMap<>();
            map.put("page", 1);
            map.put("citycode", proId);
            XyUrl.okPost(XyUrl.SEARCH_PROVINCE_HOSPITAL, map, new OkHttpCallBack<String>() {
                @Override
                public void onSuccess(String value) {
                    hospitalList = JSONObject.parseArray(value, HospitalBean.class);
                    Message msg = Message.obtain();
                    msg.obj = hospitalList;
                    msg.what = 3;
                    sendHandlerMessage(msg);
                }

                @Override
                public void onError(int errorCode, final String errorMsg) {
                    if (ConstantParam.NO_DATA == errorCode) {
                        Message message = Message.obtain();
                        message.what = GET_NO_DATA;
                        message.obj = errorMsg;
                        sendHandlerMessage(message);
                    }
                }
            });
        } else {//搜索市
            HashMap map = new HashMap<>();
            map.put("page", 1);
            map.put("citycode", cityId);
            XyUrl.okPost(XyUrl.SEARCH_CITY_HOSPITAL, map, new OkHttpCallBack<String>() {
                @Override
                public void onSuccess(String value) {
                    hospitalList = JSONObject.parseArray(value, HospitalBean.class);
                    Message msg = Message.obtain();
                    msg.obj = hospitalList;
                    msg.what = 3;
                    sendHandlerMessage(msg);
                }

                @Override
                public void onError(int errorCode, final String errorMsg) {
                    if (ConstantParam.NO_DATA == errorCode) {
                        Message message = Message.obtain();
                        message.what = GET_NO_DATA;
                        message.obj = errorMsg;
                        sendHandlerMessage(message);
                    }
                }
            });
        }

    }

    /**
     * 获取省份
     */
    private void getData() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.PROVINCES, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                provincesList = JSONObject.parseArray(value, ProvincesBean.class);
                Message msg = Message.obtain();
                msg.obj = provincesList;
                msg.what = 1;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    /**
     * 获取城市
     *
     * @param proId
     */
    private void getCity(final int proId) {
        HashMap map = new HashMap<>();
        map.put("pronid", proId);
        XyUrl.okPost(XyUrl.CITYS, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                cityList = JSONObject.parseArray(value, CityBean.class);
                Message msg = Message.obtain();
                msg.obj = cityList;
                msg.what = 2;
                msg.arg1 = proId;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {
            }
        });
    }

    //SimpleImmersionOwner接口实现

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mSimpleImmersionProxy.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSimpleImmersionProxy.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSimpleImmersionProxy.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mSimpleImmersionProxy.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mSimpleImmersionProxy.onConfigurationChanged(newConfig);
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true).statusBarColor(R.color.white).init();
    }

    @Override
    public boolean immersionBarEnabled() {
        return true;
    }

    class SpinnerAdapter<T> extends BaseAdapter {

        private List<T> provincesList;
        private LayoutInflater inflater;
        private int type;

        public SpinnerAdapter(List<T> provincesList, int type) {
            this.provincesList = provincesList;
            this.type = type;
            this.inflater = LayoutInflater.from(getPageContext());
        }

        @Override
        public int getCount() {
            if (provincesList == null || provincesList.size() == 0) {
                return 0;
            }
            return provincesList.size();
        }

        @Override
        public Object getItem(int i) {
            return provincesList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.item_spinner, null);
                holder.tvProName = view.findViewById(R.id.tv_spinner);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            if (type == 1) {
                List<ProvincesBean> list1 = (List<ProvincesBean>) provincesList;
                /*if (list1.get(i).getCityname().equals("未选择")){
                    holder.tvProName.setTextColor(Color.GRAY);
                }*/
                holder.tvProName.setText(list1.get(i).getCityname());
            } else {
                List<CityBean> list2 = (List<CityBean>) provincesList;
                /*if (list2.get(i).getCityname().equals("未选择")){
                    holder.tvProName.setTextColor(Color.GRAY);
                }*/
                holder.tvProName.setText(list2.get(i).getCityname());
            }
            return view;
        }

        class ViewHolder {
            TextView tvProName;
        }
    }

}
