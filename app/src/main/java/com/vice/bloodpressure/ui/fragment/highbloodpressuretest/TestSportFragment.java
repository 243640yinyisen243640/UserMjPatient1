package com.vice.bloodpressure.ui.fragment.highbloodpressuretest;

import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.NonDrugSportAdapter;
import com.vice.bloodpressure.adapter.NonDrugSportMultiAdapter;
import com.vice.bloodpressure.adapter.NonDrugSportMultiAdapterForSportBottom;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.NonDrugSportMultiBean;
import com.vice.bloodpressure.bean.highbloodpressuretest.TestOfDietBean;
import com.vice.bloodpressure.bean.highbloodpressuretest.TestOfHbpAddBean;
import com.vice.bloodpressure.bean.highbloodpressuretest.TestOfSportBean;
import com.vice.bloodpressure.imp.OnItemClickListener;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.MainActivity;
import com.vice.bloodpressure.utils.DialogUtils;
import com.vice.bloodpressure.utils.PickerUtils;
import com.vice.bloodpressure.utils.SPUtils;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TestSportFragment extends BaseFragment {
    private static final String TAG = "TestSportFragment";
    private static final int ADD_SUCCESS = 10010;
    @BindView(R.id.rv_one)
    RecyclerView rvOne;
    @BindView(R.id.rv_two)
    RecyclerView rvTwo;
    @BindView(R.id.cb_yes)
    CheckBox cbYes;
    @BindView(R.id.ll_yes)
    LinearLayout llYes;
    @BindView(R.id.cb_no)
    CheckBox cbNo;
    @BindView(R.id.ll_no)
    LinearLayout llNo;
    @BindView(R.id.cb_yes_empty)
    CheckBox cbYesEmpty;
    @BindView(R.id.ll_yes_empty)
    LinearLayout llYesEmpty;
    @BindView(R.id.cb_no_empty)
    CheckBox cbNoEmpty;
    @BindView(R.id.ll_no_empty)
    LinearLayout llNoEmpty;
    @BindView(R.id.et_sport_time)
    EditText etSportTime;
    @BindView(R.id.et_sport_rate)
    EditText etSportRate;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.rv_three)
    RecyclerView rvThree;
    @BindView(R.id.rv_four)
    RecyclerView rvFour;

    private NonDrugSportAdapter oneAdapter;
    private NonDrugSportAdapter twoAdapter;
    private NonDrugSportMultiAdapter threeAdapter;
    private NonDrugSportMultiAdapterForSportBottom fourAdapter;

    private List<String> selectDatasFirst = new ArrayList<>();
    private List<String> selectDatasSecond = new ArrayList<>();
    private TestOfSportBean sportsBean;
    //足部病变等级
    private int bf10 = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test_sport;
    }

    @Override
    protected void init(View rootView) {
        sportsBean = new TestOfSportBean();
        initRvOne();
        initRvTwo();
        initRvThree();
        initRvFour();
    }

    private void initRvOne() {
        String[] listString = Utils.getApp().getResources().getStringArray(R.array.sport_prescription_one);
        List<String> list = Arrays.asList(listString);
        oneAdapter = new NonDrugSportAdapter(list);

        rvOne.setLayoutManager(new LinearLayoutManager(getPageContext()));
        rvOne.setAdapter(oneAdapter);

        oneAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                oneAdapter.setSelection(position);
                sportsBean.setYdbu(position + 1 + "");
            }
        });
    }

    private void initRvTwo() {
        String[] listString = Utils.getApp().getResources().getStringArray(R.array.sport_prescription_two);
        List<String> list = Arrays.asList(listString);
        twoAdapter = new NonDrugSportAdapter(list);

        rvTwo.setLayoutManager(new LinearLayoutManager(getPageContext()));
        rvTwo.setAdapter(twoAdapter);

        twoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                twoAdapter.setSelection(position);
                sportsBean.setSteps(position + 1 + "");
            }
        });

    }

    private void initRvThree() {
        String[] listString = Utils.getApp().getResources().getStringArray(R.array.sport_prescription_three);
        ArrayList<NonDrugSportMultiBean> list = new ArrayList<>();
        for (int i = 0; i < listString.length; i++) {
            list.add(new NonDrugSportMultiBean(listString[i], NonDrugSportMultiBean.Type.TypeOne));
        }
        list.add(5, new NonDrugSportMultiBean("健康,无并发症▼", NonDrugSportMultiBean.Type.TypeTwo));
        selectDatasFirst.add("5");
        threeAdapter = new NonDrugSportMultiAdapter(list);
        rvThree.setLayoutManager(new LinearLayoutManager(getPageContext()));
        rvThree.setAdapter(threeAdapter);
        threeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!threeAdapter.isSelected.get(position)) {
                    threeAdapter.isSelected.put(position, true);//修改map的值保存状态
                    threeAdapter.notifyItemChanged(position);
                    selectDatasFirst.add(position + 1 + "");
                } else {
                    threeAdapter.isSelected.put(position, false);//修改map的值保存状态
                    threeAdapter.notifyItemChanged(position);
                    selectDatasFirst.remove(position + 1 + "");
                }
            }
        });


        String[] stringYesOrNo = {"健康，无并发症", "感觉迟钝", "感觉丧失", "破溃"};
        List<String> yesOrNoList = Arrays.asList(stringYesOrNo);
        threeAdapter.setNewListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                PickerUtils.showOptionPosition(getPageContext(), new PickerUtils.PositionCallBack() {
                    @Override
                    public void execEvent(String content, int position) {
                        String format = String.format("%s▼", content);
                        TextView tv = (TextView) view;
                        tv.setText(format);
                        //足部病变等级
                        bf10 = position + 1;
                    }
                }, yesOrNoList);
            }
        });
    }

    private void initRvFour() {
        String[] listString = Utils.getApp().getResources().getStringArray(R.array.sport_prescription_four);
        ArrayList<NonDrugSportMultiBean> list = new ArrayList<>();
        for (int i = 0; i < listString.length; i++) {
            list.add(new NonDrugSportMultiBean(listString[i], NonDrugSportMultiBean.Type.TypeOne));
        }
        fourAdapter = new NonDrugSportMultiAdapterForSportBottom(list);
        rvFour.setLayoutManager(new LinearLayoutManager(getPageContext()));
        rvFour.setAdapter(fourAdapter);
        fourAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!fourAdapter.isSelected.get(position)) {
                    //修改map的值保存状态
                    fourAdapter.isSelected.put(position, true);
                    fourAdapter.notifyItemChanged(position);
                    selectDatasSecond.add(position + 1 + "");
                } else {
                    //修改map的值保存状态
                    fourAdapter.isSelected.put(position, false);
                    fourAdapter.notifyItemChanged(position);
                    selectDatasSecond.remove(position + 1 + "");
                }
            }
        });
    }

    @OnClick({R.id.ll_yes, R.id.ll_no, R.id.ll_yes_empty, R.id.ll_no_empty, R.id.bt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_yes:
                cbYes.setChecked(true);
                cbNo.setChecked(false);
                llEmpty.setVisibility(View.VISIBLE);
                sportsBean.setYdzhuan("1");
                break;
            case R.id.ll_no:
                cbYes.setChecked(false);
                cbNo.setChecked(true);
                llEmpty.setVisibility(View.GONE);
                sportsBean.setYdzhuan("2");
                break;
            case R.id.ll_yes_empty:
                cbYesEmpty.setChecked(true);
                cbNoEmpty.setChecked(false);
                sportsBean.setKong("1");
                break;
            case R.id.ll_no_empty:
                cbYesEmpty.setChecked(false);
                cbNoEmpty.setChecked(true);
                sportsBean.setKong("2");
                break;
            case R.id.bt_submit:
                if (selectDatasFirst.size() == 9) {
                    toDoSubmit();
                } else {
                    //有禁忌症
                    DialogUtils.getInstance().showDialog(getPageContext(), "温馨提示", "您有运动禁忌症，不适合运动，无法生成运动处方！", false, new DialogUtils.DialogCallBack() {
                        @Override
                        public void execEvent() {
                        }
                    });
                }
                break;
        }
    }

    private void toDoSubmit() {
        String time = etSportTime.getText().toString().trim();
        String rate = etSportRate.getText().toString().trim();
        sportsBean.setYdcitime(time);
        sportsBean.setYdzhouci(rate);
        sportsBean.setYdls1("1");
        sportsBean.setYdls2("1");
        sportsBean.setYdls3("1");
        sportsBean.setYdls4("1");
        sportsBean.setYdls5("1");
        sportsBean.setYdls6("1");
        sportsBean.setYdls7("1");
        sportsBean.setYdls8("1");
        sportsBean.setYdls9("1");
        sportsBean.setYdls10("1");
        sportsBean.setBf10(bf10 + "");
        //0否1是
        for (int i = 0; i < selectDatasSecond.size(); i++) {
            switch (selectDatasSecond.get(i)) {
                case "1":
                    sportsBean.setYdls11("1");
                    break;
                case "2":
                    sportsBean.setYdls13("1");
                    break;
                case "3":
                    sportsBean.setYdls14("1");
                    break;
                case "4":
                    sportsBean.setYdls15("1");
                    break;
            }
        }

        TestOfHbpAddBean addBean = new TestOfHbpAddBean();
        String hbpId = SPStaticUtils.getString("hbpId");
        String prlId = SPStaticUtils.getString("prlId");
        addBean.setId(hbpId);
        addBean.setPrlid(prlId);
        TestOfDietBean dietBean = (TestOfDietBean) SPUtils.getBean("hbpDietBean");
        addBean.setDietary(dietBean);
        addBean.setSports(sportsBean);
        LoginBean loginBean = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        String token = loginBean.getToken();
        String userid = loginBean.getUserid();
        addBean.setAccess_token(token);
        addBean.setUserid(userid);
        String jsonResult = JSON.toJSONString(addBean);
        XyUrl.okPostJson(XyUrl.ADD_HBP, jsonResult, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                sendHandlerMessage(ADD_SUCCESS);
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {

            }
        });
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case ADD_SUCCESS:
                DialogUtils.getInstance().showDialog(getPageContext(), "温馨提示", "您的信息已提交,请等待医生为你生成报告!", false, new DialogUtils.DialogCallBack() {
                    @Override
                    public void execEvent() {
                        ActivityUtils.finishToActivity(MainActivity.class, false);
                    }
                });
                break;
        }
    }
}
