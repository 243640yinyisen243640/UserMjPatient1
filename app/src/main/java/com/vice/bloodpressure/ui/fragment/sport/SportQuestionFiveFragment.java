package com.vice.bloodpressure.ui.fragment.sport;

import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.HomeSportQuestionAddBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.MainActivity;
import com.vice.bloodpressure.utils.SPUtils;
import com.vice.bloodpressure.utils.TurnsUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 运动答题之 第五道
 * 作者: LYD
 * 创建日期: 2020/11/14 13:02
 */
public class SportQuestionFiveFragment extends BaseFragment {
    private static final String TAG = "SportQuestionFiveFragment";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_desc)
    TextView tvDesc;

    @BindView(R.id.img_yes)
    ImageView imgYes;
    @BindView(R.id.ll_yes)
    LinearLayout llYes;

    @BindView(R.id.img_no)
    ImageView imgNo;
    @BindView(R.id.ll_no)
    LinearLayout llNo;

    @BindView(R.id.img_yes_empty)
    ImageView imgYesEmpty;
    @BindView(R.id.ll_yes_empty)
    LinearLayout llYesEmpty;

    @BindView(R.id.img_no_empty)
    ImageView imgNoEmpty;
    @BindView(R.id.ll_no_empty)
    LinearLayout llNoEmpty;

    @BindView(R.id.ll_is_show_have)
    LinearLayout llIsShowHave;

    @BindView(R.id.et_time)
    EditText etTime;
    @BindView(R.id.et_frequency)
    EditText etFrequency;

    @BindView(R.id.tv_next)
    ColorTextView tvNext;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sport_question_five;
    }

    @Override
    protected void init(View rootView) {
        EventBusUtils.post(new EventMessage<>(ConstantParam.HOME_SPORT_QUESTION_TITLE, "5"));
        setTitleAndDesc();
        setInitialTag();
    }

    private void setInitialTag() {
        llYes.setTag(0);
        llNo.setTag(0);
        llYesEmpty.setTag(0);
        llNoEmpty.setTag(0);
    }

    private void setTitleAndDesc() {
        SpanUtils.with(tvTitle)
                .append("5").setForegroundColor(ColorUtils.getColor(R.color.main_home))
                .append("/5").setForegroundColor(ColorUtils.getColor(R.color.color_666))
                .create();
        tvDesc.setText("是否有运动习惯？");
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick({R.id.ll_yes, R.id.ll_no, R.id.ll_yes_empty, R.id.ll_no_empty, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_yes:
                llIsShowHave.setVisibility(View.VISIBLE);
                llYes.setTag(1);
                llNo.setTag(0);
                imgYes.setImageResource(R.drawable.sport_level_checked);
                imgNo.setImageResource(R.drawable.sport_level_uncheck);
                //设置选中颜色
                tvNext.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
                tvNext.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
                break;
            case R.id.ll_no:
                llIsShowHave.setVisibility(View.GONE);
                llYes.setTag(0);
                llNo.setTag(1);
                imgYes.setImageResource(R.drawable.sport_level_uncheck);
                imgNo.setImageResource(R.drawable.sport_level_checked);
                //设置选中颜色
                tvNext.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
                tvNext.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
                break;
            case R.id.ll_yes_empty:
                llYesEmpty.setTag(1);
                llNoEmpty.setTag(0);
                imgYesEmpty.setImageResource(R.drawable.sport_level_checked);
                imgNoEmpty.setImageResource(R.drawable.sport_level_uncheck);
                break;
            case R.id.ll_no_empty:
                llYesEmpty.setTag(0);
                llNoEmpty.setTag(1);
                imgYesEmpty.setImageResource(R.drawable.sport_level_uncheck);
                imgNoEmpty.setImageResource(R.drawable.sport_level_checked);
                break;
            case R.id.tv_next:
                toSubmit();
                break;
        }
    }

    private void toSubmit() {
        int tagYes = (int) llYes.getTag();
        int tagNo = (int) llNo.getTag();
        if (0 == tagYes && 0 == tagNo) {
            ToastUtils.showShort("请选择是否有运动习惯");
            return;
        }
        if (1 == tagYes) {
            int tagYesEmpty = (int) llYesEmpty.getTag();
            int tagNoEmpty = (int) llNoEmpty.getTag();
            String time = etTime.getText().toString().trim();
            String frequency = etFrequency.getText().toString().trim();
            int frequencyInt = TurnsUtils.getInt(frequency, 0);
            if (0 == tagYesEmpty && 0 == tagNoEmpty) {
                ToastUtils.showShort("请选择是否有空腹运动");
                return;
            }
            if (TextUtils.isEmpty(time)) {
                ToastUtils.showShort("请输入运动时间");
                return;
            }
            if (TextUtils.isEmpty(frequency)) {
                ToastUtils.showShort("请输入运动频率");
                return;
            }
            if (frequencyInt > 7) {
                ToastUtils.showShort("一周运动频率不能大于7");
                return;
            }
        }
        toDoSubmit();
    }


    /**
     * 最终的答题提交
     */
    private void toDoSubmit() {
        int tagYes = (int) llYes.getTag();
        int tagYesEmpty = (int) llYesEmpty.getTag();
        int habit = 0 == tagYes ? 0 : 1;
        int empty = 0 == tagYesEmpty ? 0 : 1;
        HomeSportQuestionAddBean questionBean = (HomeSportQuestionAddBean) SPUtils.getBean("sportQuestion");
        Map<String, Object> map = new HashMap<>();
        map.put("issport", "1");
        map.put("age", questionBean.getAge());
        map.put("height", questionBean.getHeight());
        map.put("weight", questionBean.getWeight());
        map.put("complications", getCommlications(questionBean.getComplications()));
        map.put("habit", habit);
        if (1 == habit) {
            map.put("empty", empty);
            map.put("sportTime", etTime.getText().toString().trim());
            map.put("sportFrequency", etFrequency.getText().toString().trim());
        }
        XyUrl.okPostSave(XyUrl.INDEX_ADD_SPORT_QUESTION, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ToastUtils.showShort(value);
                ActivityUtils.finishToActivity(MainActivity.class, false);
                EventBusUtils.post(new EventMessage<>(ConstantParam.HOME_SPORT_QUESTION_SUBMIT));
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {

            }
        });
    }

    private String getCommlications(List<String> list) {
        Log.e(TAG, "getCommlications==" + list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            sb.append(s);
            if (i != list.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
