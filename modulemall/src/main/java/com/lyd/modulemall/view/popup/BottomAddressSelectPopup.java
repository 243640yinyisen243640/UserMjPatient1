package com.lyd.modulemall.view.popup;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luwei.checkhelper.CheckHelper;
import com.luwei.checkhelper.SingleCheckHelper;
import com.lyd.modulemall.R;
import com.lyd.modulemall.adapter.BottomSelectAddressAdapter;
import com.lyd.modulemall.bean.AddressBean;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.net.OnError;
import com.rxjava.rxlife.RxLife;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;
import razerdp.basepopup.BasePopupWindow;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  省市县三级选择
 * 作者: LYD
 * 创建日期: 2021/1/28 9:45
 */
public class BottomAddressSelectPopup extends BasePopupWindow implements View.OnClickListener {
    private ImageView imgClose;
    private TextView tvProvince;
    private TextView tvCity;
    private TextView tvArea;
    private View lineSelected;
    private RecyclerView rvAddressList;
    private Context context;

    //adcode 自编码（获取省为空）
    private String adCode = "";
    //type 获取类型 0 获取省 1获取市/区
    private String type = "0";
    //获取一级二级三级
    private int tabIndex = 0;
    private SingleCheckHelper singleCheckHelper = new SingleCheckHelper();
    private BottomSelectAddressAdapter adapter;
    private View popupView;

    private AddressBean provinceBean;
    private AddressBean cityBean;
    private AddressBean areaBean;

    private OnCityItemClickListener callBackListener;

    public BottomAddressSelectPopup(Context context) {
        super(context);
        this.context = context;
        setPopupGravity(Gravity.BOTTOM);
        initView();
        popupView = getContentView();
        getAddressList();
        updateIndicator();
        updateCallBackBean();
    }

    public void setOnCityItemClickListener(OnCityItemClickListener listener) {
        callBackListener = listener;
    }

    private void updateCallBackBean() {
        provinceBean = new AddressBean();
        cityBean = new AddressBean();
        areaBean = new AddressBean();
    }

    /**
     *
     */
    private void getAddressList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("adcode", adCode);
        RxHttp.postForm(MallUrl.GET_AREAS)
                .addAll(map)
                .asResponseList(AddressBean.class)
                .to(RxLife.toMain((LifecycleOwner) context))
                .subscribe(new Consumer<List<AddressBean>>() {
                    @Override
                    public void accept(List<AddressBean> list) throws Exception {
                        //注册选择器
                        singleCheckHelper.register(AddressBean.class, new CheckHelper.Checker<AddressBean, BaseViewHolder>() {
                            @Override
                            public void check(AddressBean bean, BaseViewHolder holder) {
                                TextView tvName = holder.getView(R.id.tv_name);
                                ImageView imgSelect = holder.getView(R.id.img_select);
                                tvName.setTextColor(ColorUtils.getColor(R.color.main_green));
                                imgSelect.setVisibility(View.VISIBLE);
                                String cityname = bean.getCityname();
                                adCode = bean.getAdcode();
                                switch (tabIndex) {
                                    case 0:
                                        tvProvince.setText(cityname);
                                        tvCity.setText("请选择");
                                        provinceBean.setAdcode(adCode);
                                        provinceBean.setCityname(cityname);
                                        break;
                                    case 1:
                                        tvCity.setText(cityname);
                                        tvArea.setText("请选择");
                                        cityBean.setAdcode(adCode);
                                        cityBean.setCityname(cityname);
                                        break;
                                    case 2:
                                        tvArea.setText(cityname);
                                        areaBean.setAdcode(adCode);
                                        areaBean.setCityname(cityname);
                                        break;
                                }

                                updateIndicator();

                                type = "1";
                                tabIndex++;

                                getAddressList();
                            }

                            @Override
                            public void unCheck(AddressBean bean, BaseViewHolder holder) {
                            }
                        });
                        adapter = new BottomSelectAddressAdapter(list, singleCheckHelper);
                        rvAddressList.setLayoutManager(new LinearLayoutManager(context));
                        rvAddressList.setAdapter(adapter);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {
                        ToastUtils.cancel();
                        //回调
                        dismiss();
                        callBackListener.onSelected(provinceBean, cityBean, areaBean);
                    }
                });
    }

    private void initView() {
        imgClose = findViewById(R.id.img_close);

        tvProvince = findViewById(R.id.tv_province);
        tvCity = findViewById(R.id.tv_city);
        tvArea = findViewById(R.id.tv_area);
        lineSelected = findViewById(R.id.line_selected);
        rvAddressList = findViewById(R.id.rv_address_list);
        imgClose.setOnClickListener(this);
        tvProvince.setOnClickListener(this);
        tvCity.setOnClickListener(this);
        tvArea.setOnClickListener(this);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_bottom_address_select);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.img_close) {
            dismiss();
        } else if (id == R.id.tv_province) {
            adCode = "";
            type = "0";
            tabIndex = 0;
            getAddressList();
            tvProvince.setText("请选择");
            tvCity.setText("");
            tvArea.setText("");
            updateIndicator();
            updateCallBackBean();
        } else if (id == R.id.tv_city) {
            adCode = provinceBean.getAdcode();
            type = "1";
            tabIndex = 1;
            getAddressList();
            tvCity.setText("请选择");
            tvArea.setText("");
            updateIndicator();
        } else if (id == R.id.tv_area) {
            adCode = cityBean.getAdcode();
            type = "1";
            tabIndex = 2;
            getAddressList();

            tvArea.setText("请选择");
            updateIndicator();
        }
    }


    /**
     * 更新选中城市下面的红色横线指示器
     */
    private void updateIndicator() {
        popupView.post(new Runnable() {
            @Override
            public void run() {
                switch (tabIndex) {
                    case 0:
                        tabSelectedIndicatorAnimation(tvProvince).start();
                        break;
                    case 1:
                        tabSelectedIndicatorAnimation(tvCity).start();
                        break;
                    case 2:
                        tabSelectedIndicatorAnimation(tvArea).start();
                        break;
                }
            }
        });

    }

    /**
     * tab 选中的红色下划线动画
     *
     * @param tvTab
     * @return
     */
    private AnimatorSet tabSelectedIndicatorAnimation(TextView tvTab) {
        ObjectAnimator xAnimator = ObjectAnimator.ofFloat(lineSelected, "X", lineSelected.getX(), tvTab.getX());
        final ViewGroup.LayoutParams params = lineSelected.getLayoutParams();
        ValueAnimator widthAnimator = ValueAnimator.ofInt(params.width, tvTab.getMeasuredWidth());
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.width = (int) animation.getAnimatedValue();
                lineSelected.setLayoutParams(params);
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new FastOutSlowInInterpolator());
        set.playTogether(xAnimator, widthAnimator);
        return set;
    }


    public interface OnCityItemClickListener {
        /**
         * 当选择省市区三级选择器时，需要覆盖此方法
         *
         * @param provinceBean
         * @param cityBean
         * @param districtBean
         */
        void onSelected(AddressBean provinceBean, AddressBean cityBean, AddressBean districtBean);
    }

}
