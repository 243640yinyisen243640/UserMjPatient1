package com.vice.bloodpressure.ui.fragment.highbloodpressuretest;

import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.NationListBean;
import com.vice.bloodpressure.bean.highbloodpressuretest.TestOfDietBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.PickerUtils;
import com.vice.bloodpressure.utils.SPUtils;
import com.wei.android.lib.colorview.view.ColorButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 饮食信息之基本信息
 * 作者: LYD
 * 创建日期: 2020/6/18 14:56
 */
public class TestDietOneFragment extends BaseFragment {
    private static final int GET_NATION_LIST = 10010;
    @BindView(R.id.et_height)
    EditText etHeight;
    @BindView(R.id.et_weight)
    EditText etWeight;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.et_high_pressure)
    EditText etHighPressure;
    @BindView(R.id.et_low_pressure)
    EditText etLowPressure;
    @BindView(R.id.et_tc)
    EditText etTc;
    @BindView(R.id.et_tg)
    EditText etTg;
    @BindView(R.id.et_ldl)
    EditText etLdl;
    @BindView(R.id.et_hdl)
    EditText etHdl;
    @BindView(R.id.tv_labour_intensity)
    TextView tvLabourIntensity;
    @BindView(R.id.rl_labour_intensity)
    RelativeLayout rlLabourIntensity;
    @BindView(R.id.tv_smoke)
    TextView tvSmoke;
    @BindView(R.id.rl_smoke)
    RelativeLayout rlSmoke;
    @BindView(R.id.tv_drink)
    TextView tvDrink;
    @BindView(R.id.rl_drink)
    RelativeLayout rlDrink;
    @BindView(R.id.tv_nation)
    TextView tvNation;
    @BindView(R.id.rl_nation)
    RelativeLayout rlNation;
    @BindView(R.id.tv_dn)
    TextView tvDn;
    @BindView(R.id.rl_dn)
    RelativeLayout rlDn;
    @BindView(R.id.tv_dn_period)
    TextView tvDnPeriod;
    @BindView(R.id.rl_dn_period)
    RelativeLayout rlDnPeriod;

    @BindView(R.id.ll_dn_period_time)
    LinearLayout llDnPeriodTime;
    @BindView(R.id.bt_next_question)
    ColorButton btNextQuestion;


    private List<NationListBean> nationList = new ArrayList<>();
    private ArrayList<String> nationNameList = new ArrayList<>();
    private int nationId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test_diet_one;
    }

    @Override
    protected void init(View rootView) {
        getNationList();
    }


    /**
     * 获取民族列表
     */
    private void getNationList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", "2");
        XyUrl.okPost(XyUrl.GET_NATION_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<NationListBean> nationList = JSONObject.parseArray(value, NationListBean.class);
                Message message = getHandlerMessage();
                message.what = GET_NATION_LIST;
                message.obj = nationList;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_NATION_LIST:
                nationList = (List<NationListBean>) msg.obj;
                if (nationList != null && nationList.size() > 0) {
                    for (int i = 0; i < nationList.size(); i++) {
                        nationNameList.add(nationList.get(i).getRecipename());
                    }
                }
                break;
        }
    }

    @OnClick({R.id.rl_labour_intensity, R.id.rl_smoke, R.id.rl_drink, R.id.rl_nation, R.id.rl_dn, R.id.rl_dn_period, R.id.bt_next_question})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_labour_intensity:
                KeyboardUtils.hideSoftInput(getActivity());
                String[] strArray = getResources().getStringArray(R.array.liver_files_base_info_profession);
                List<String> labourIntensityList = Arrays.asList(strArray);
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String text) {
                        tvLabourIntensity.setText(text);
                    }
                }, labourIntensityList);
                break;
            case R.id.rl_smoke:
                KeyboardUtils.hideSoftInput(getActivity());
                showYesOrNo(tvSmoke);
                break;
            case R.id.rl_drink:
                KeyboardUtils.hideSoftInput(getActivity());
                showYesOrNo(tvDrink);
                break;
            case R.id.rl_nation:
                KeyboardUtils.hideSoftInput(getActivity());
                PickerUtils.showOptionPosition(getPageContext(), new PickerUtils.PositionCallBack() {
                    @Override
                    public void execEvent(String content, int position) {
                        tvNation.setText(content);
                        nationId = nationList.get(position).getId();
                    }
                }, nationNameList);
                break;
            case R.id.rl_dn:
                KeyboardUtils.hideSoftInput(getActivity());
                String[] diabeticString = {"确诊无", "确诊有"};
                List<String> diabeticList = Arrays.asList(diabeticString);
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String text) {
                        tvDn.setText(text);
                        if ("确诊有".equals(text)) {
                            llDnPeriodTime.setVisibility(View.VISIBLE);
                        } else {
                            llDnPeriodTime.setVisibility(View.GONE);
                            tvDnPeriod.setText("");
                        }
                    }
                }, diabeticList);
                break;
            case R.id.rl_dn_period:
                KeyboardUtils.hideSoftInput(getActivity());
                String[] diabeticPeriodString = {"Ⅰ期", "Ⅱ期", "Ⅲ期", "Ⅳ期", "Ⅴ期"};
                List<String> diabeticPeriodList = Arrays.asList(diabeticPeriodString);
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String text) {
                        tvDnPeriod.setText(text);
                    }
                }, diabeticPeriodList);
                break;
            case R.id.bt_next_question:
                toJumpNextQuestion();
                break;
        }
    }

    private void showYesOrNo(TextView tv) {
        String[] strArray = getResources().getStringArray(R.array.liver_files_base_info_is_or_not);
        List<String> list = Arrays.asList(strArray);
        PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
            @Override
            public void execEvent(String text) {
                tv.setText(text);
            }
        }, list);
    }


    /**
     *
     */
    private void toJumpNextQuestion() {
        String height = etHeight.getText().toString().trim();
        String weight = etWeight.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String labourIntensity = tvLabourIntensity.getText().toString().trim();
        String dn = tvDn.getText().toString().trim();
        if (TextUtils.isEmpty(height)) {
            ToastUtils.showShort("请填写身高");
            return;
        }
        if (TextUtils.isEmpty(weight)) {
            ToastUtils.showShort("请填写体重");
            return;
        }
        if (TextUtils.isEmpty(age)) {
            ToastUtils.showShort("请填写年龄");
            return;
        }
        if (TextUtils.isEmpty(labourIntensity)) {
            ToastUtils.showShort("请选择劳动强度");
            return;
        }
        if (TextUtils.isEmpty(dn)) {
            ToastUtils.showShort("请选择糖尿病肾病");
            return;
        }
        //设值
        TestOfDietBean dietBean = new TestOfDietBean();
        dietBean.setTgshengao(height);
        dietBean.setTgtizhong(weight);
        dietBean.setAge(age);
        //高压
        String hp = etHighPressure.getText().toString().trim();
        dietBean.setSystolic(hp);
        //低压
        String lp = etLowPressure.getText().toString().trim();
        dietBean.setDiastolic(lp);
        //tc
        String tc = etTc.getText().toString().trim();
        dietBean.setSytc(tc);
        //tg
        String tg = etTg.getText().toString().trim();
        dietBean.setSytg(tg);
        //ldl
        String ldl = etLdl.getText().toString().trim();
        dietBean.setSyldl(ldl);
        //hdl
        String hdl = etHdl.getText().toString().trim();
        dietBean.setSyhdl(hdl);
        //劳动强度
        String labour = tvLabourIntensity.getText().toString().trim();
        switch (labour) {
            case "轻体力":
                dietBean.setJbzhiye("1");
                break;
            case "中体力":
                dietBean.setJbzhiye("2");
                break;
            case "重体力":
                dietBean.setJbzhiye("3");
                break;
        }
        //抽烟
        String smoke = tvSmoke.getText().toString().trim();
        switch (smoke) {
            case "是":
                dietBean.setJbxiyan("1");
                break;
            case "否":
                dietBean.setJbxiyan("2");
                break;
        }
        //喝酒
        String drink = tvDrink.getText().toString().trim();
        switch (drink) {
            case "是":
                dietBean.setDrink("1");
                break;
            case "否":
                dietBean.setDrink("2");
                break;
        }
        //少数名族
        dietBean.setMinorities(nationId + "");
        //第五部分
        String dnPeriod = tvDnPeriod.getText().toString().trim();
        switch (dn) {
            case "确诊无":
                dietBean.setNephropathy("1");
                break;
            case "确诊有":
                dietBean.setNephropathy("2");
                break;
        }
        //"Ⅰ期", "Ⅱ期", "Ⅲ期", "Ⅳ期", "Ⅴ期
        switch (dnPeriod) {
            case "Ⅰ期":
                dietBean.setNephropathylei("1");
                break;
            case "Ⅱ期":
                dietBean.setNephropathylei("2");
                break;
            case "Ⅲ期":
                dietBean.setNephropathylei("3");
                break;
            case "Ⅳ期":
                dietBean.setNephropathylei("4");
                break;
            case "Ⅴ期":
                dietBean.setNephropathylei("5");
                break;
        }
        //保存饮食数据
        SPUtils.putBean("hbpDietBean", dietBean);
        TestDietTwoFragment dietTwoFragment = new TestDietTwoFragment();
        FragmentUtils.replace(getParentFragmentManager(), dietTwoFragment, R.id.ll_fragment_root, false);
    }


}
