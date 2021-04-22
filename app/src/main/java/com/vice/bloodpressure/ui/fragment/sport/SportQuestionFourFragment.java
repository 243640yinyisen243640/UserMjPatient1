package com.vice.bloodpressure.ui.fragment.sport;

import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.RvSportQuestionFourAdapter;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.HomeSportQuestionAddBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.imp.OnItemClickListener;
import com.vice.bloodpressure.utils.SPUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  运动答题之 第四道
 * 作者: LYD
 * 创建日期: 2020/9/25 16:51
 */
public class SportQuestionFourFragment extends BaseFragment {
    private static final String TAG = "SportQuestionFourFragment";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.rv_sport_level)
    RecyclerView rvSportLevel;
    //无选项
    @BindView(R.id.ll_check)
    LinearLayout llCheck;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.img_check)
    ImageView imgCheck;
    //无选项
    @BindView(R.id.tv_next)
    ColorTextView tvNext;

    private RvSportQuestionFourAdapter adapter;
    private List<String> selectPositionList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sport_quesion_four;
    }

    @Override
    protected void init(View rootView) {
        EventBusUtils.post(new EventMessage<>(ConstantParam.HOME_SPORT_QUESTION_TITLE, "4"));
        setTitleAndDesc();
        setRv();
        initCheck();
    }

    private void initCheck() {
        llCheck.setTag(0);
    }

    private void setRv() {
        String[] listString = Utils.getApp().getResources().getStringArray(R.array.sport_question_four);
        List<String> list = Arrays.asList(listString);
        adapter = new RvSportQuestionFourAdapter(list);
        rvSportLevel.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSportLevel.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!adapter.isSelected.get(position)) {
                    //修改map的值保存状态
                    adapter.isSelected.put(position, true);
                    adapter.notifyItemChanged(position);
                    selectPositionList.add(position + 1 + "");
                } else {
                    //修改map的值保存状态
                    adapter.isSelected.put(position, false);
                    adapter.notifyItemChanged(position);
                    selectPositionList.remove(position + 1 + "");
                }
                //点击变色
                if (selectPositionList != null && selectPositionList.size() > 0) {
                    //设置选中颜色
                    tvNext.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
                    tvNext.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
                } else {
                    //设置选中颜色
                    tvNext.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.color_eb));
                    tvNext.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.color_666));
                }
                //反色
                llCheck.setTag(0);
                imgCheck.setImageResource(R.drawable.sport_level_uncheck);
                tvText.setTextColor(ColorUtils.getColor(R.color.black_text));

                selectPositionList.remove("0");
            }
        });
    }

    private void setTitleAndDesc() {
        SpanUtils.with(tvTitle)
                .append("4").setForegroundColor(ColorUtils.getColor(R.color.main_home))
                .append("/5").setForegroundColor(ColorUtils.getColor(R.color.color_666))
                .create();
        tvDesc.setText("是否患有下列疾病？(可多选）");
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick({R.id.tv_next, R.id.ll_check})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                if (selectPositionList.size() < 1) {
                    ToastUtils.showShort("请选择是否患有下列疾病?");
                    return;
                }
                Log.e(TAG, "selectPositionList==" + selectPositionList);
                HomeSportQuestionAddBean questionBean = (HomeSportQuestionAddBean) SPUtils.getBean("sportQuestion");
                questionBean.setComplications(selectPositionList);
                SPUtils.putBean("sportQuestion", questionBean);
                SportQuestionFiveFragment questionTwoFragment = new SportQuestionFiveFragment();
                FragmentUtils.replace(getParentFragmentManager(), questionTwoFragment, R.id.ll_fragment, true);
                break;
            case R.id.ll_check:
                int check = (int) llCheck.getTag();
                if (0 == check) {
                    llCheck.setTag(1);
                    imgCheck.setImageResource(R.drawable.sport_level_checked);
                    tvText.setTextColor(ColorUtils.getColor(R.color.main_home));
                    //设置选中颜色
                    tvNext.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
                    tvNext.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
                    selectPositionList.add("0");
                    //清空已选上五个选项
                    String[] listString = Utils.getApp().getResources().getStringArray(R.array.sport_question_four_list);
                    List<String> list = Arrays.asList(listString);
                    for (int i = 0; i < list.size(); i++) {
                        adapter.isSelected.put(i, false);
                        adapter.notifyItemChanged(i);
                        selectPositionList.remove(list.get(i));
                    }
                } else {
                    llCheck.setTag(0);
                    imgCheck.setImageResource(R.drawable.sport_level_uncheck);
                    tvText.setTextColor(ColorUtils.getColor(R.color.black_text));
                    //设置选中颜色
                    tvNext.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.color_eb));
                    tvNext.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.color_666));
                    selectPositionList.remove("0");
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.post(new EventMessage<>(ConstantParam.HOME_SPORT_QUESTION_TITLE, "3"));
    }
}
