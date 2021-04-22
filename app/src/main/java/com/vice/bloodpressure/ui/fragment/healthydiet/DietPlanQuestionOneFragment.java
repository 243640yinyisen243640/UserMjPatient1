package com.vice.bloodpressure.ui.fragment.healthydiet;

import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.DietPlanQuestionBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.utils.SPUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 描述:  答题第一道(选择男女)
 * 作者: LYD
 * 创建日期: 2019/12/17 11:04
 */
public class DietPlanQuestionOneFragment extends BaseFragment {
    private static final String TAG = "DietPlanQuestionOneFragment";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.img_man)
    ImageView imgMan;
    @BindView(R.id.img_women)
    ImageView imgWomen;
    @BindView(R.id.tv_next)
    ColorTextView tvNext;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_diet_plan_question_one;
    }

    @Override
    protected void init(View rootView) {
        EventBusUtils.post(new EventMessage<>(ConstantParam.DIET_PLAN_QUESTION_TITLE, "1"));
        setTag();
        SpanUtils.with(tvTitle)
                .append("1").setForegroundColor(ColorUtils.getColor(R.color.main_home))
                .append("/4").setForegroundColor(ColorUtils.getColor(R.color.color_666))
                .create();
    }

    private void setTag() {
        imgMan.setTag(0);
        imgWomen.setTag(0);
    }

    @OnClick({R.id.img_man, R.id.img_women, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_man:
                imgMan.setTag(1);
                imgWomen.setTag(0);
                imgMan.setImageResource(R.drawable.dit_plan_man_checked);
                imgWomen.setImageResource(R.drawable.dit_plan_woman_uncheck);
                //设置选中颜色
                tvNext.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
                tvNext.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
                break;
            case R.id.img_women:
                imgMan.setTag(0);
                imgWomen.setTag(1);
                imgMan.setImageResource(R.drawable.dit_plan_man_un_check);
                imgWomen.setImageResource(R.drawable.dit_plan_woman_checked);
                //设置选中颜色
                tvNext.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
                tvNext.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
                break;
            case R.id.tv_next:
                int tagMan = (int) imgMan.getTag();
                int tagWomen = (int) imgWomen.getTag();
                if (0 == tagMan && 0 == tagWomen) {
                    ToastUtils.showShort("请选择性别");
                    return;
                }
                DietPlanQuestionBean questionBean = new DietPlanQuestionBean();
                if (1 == tagMan) {
                    questionBean.setSex("1");
                } else if (1 == tagWomen) {
                    questionBean.setSex("2");
                }
                SPUtils.putBean("Diet_Question", questionBean);
                //跳转答题第二道
                DietPlanQuestionTwoFragment questionTwo = new DietPlanQuestionTwoFragment();
                FragmentUtils.replace(getParentFragmentManager(), questionTwo, R.id.fl_question, true);
                break;
        }
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }
}
