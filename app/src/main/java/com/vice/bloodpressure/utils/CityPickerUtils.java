package com.vice.bloodpressure.utils;

import android.content.Context;

import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.vice.bloodpressure.R;

/**
 * 描述: 地址选择
 * 作者: LYD
 * 创建日期: 2020/1/8 17:40
 */
public class CityPickerUtils {
    private static final String TAG = "CityPickerUtils";

    private CityPickerUtils() {
    }

    /**
     * 显示省市县三级联动
     *
     * @param context
     */
    public static void show(Context context, CityPickerCallBack callBack) {
        //初始化城市选择
        CityPickerView mCityPickerView = new CityPickerView();
        mCityPickerView.init(context);
        //基本配置
        String textColor = "#057dff";
        CityConfig.WheelType mWheelType = CityConfig.WheelType.PRO_CITY_DIS;
        CityConfig cityConfig = new CityConfig.Builder()
                .title("选择城市")
                .cancelTextColor(textColor)
                .confirTextColor(textColor)
                .visibleItemsCount(5)
                .province("河南")
                .city("郑州市")
                .district("金水区")
                .provinceCyclic(false)
                .cityCyclic(false)
                .districtCyclic(false)
                .setCityWheelType(mWheelType)
                .setCustomItemLayout(R.layout.item_city)
                .setCustomItemTextViewId(R.id.item_city_name_tv)
                .setShowGAT(false)
                .build();
        mCityPickerView.setConfig(cityConfig);

        mCityPickerView.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                if (province != null && city != null && district != null) {
                    String pName = province.getName();
                    String pId = province.getId();
                    String cName = city.getName();
                    String cId = city.getId();
                    String dName = district.getName();
                    String dId = district.getId();
                    callBack.execEvent(
                            pName, pId,
                            cName, cId,
                            dName, dId);
                }
            }

            @Override
            public void onCancel() {

            }
        });
        mCityPickerView.showCityPicker();
    }

    /**
     * 回调接口:用接口的实例去调用方法
     */
    public interface CityPickerCallBack {
        void execEvent(String pName, String pId,
                       String cName, String cId,
                       String dName, String did);
    }
}
