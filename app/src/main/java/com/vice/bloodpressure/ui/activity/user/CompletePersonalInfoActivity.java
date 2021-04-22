package com.vice.bloodpressure.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lsp.RulerView;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.MainActivity;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  完善个人信息
 * 作者: LYD
 * 创建日期: 2020/10/30 15:16
 */
public class CompletePersonalInfoActivity extends BaseActivity {
    @BindView(R.id.tv_jump)
    TextView tvJump;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.img_man)
    ImageView imgMan;
    @BindView(R.id.img_women)
    ImageView imgWomen;
    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.ruler_height)
    RulerView rulerHeight;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.ruler_weight)
    RulerView rulerWeight;
    @BindView(R.id.tv_next)
    ColorTextView tvNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgMan.setTag(0);
        imgWomen.setTag(0);
        hideTitleBar();
        initRuler();
    }

    private void initRuler() {
        rulerWeight.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {

            }

            @Override
            public void onScrollResult(String result) {
                tvWeight.setText(floatStringToIntString(result));
            }
        });
        rulerHeight.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {

            }

            @Override
            public void onScrollResult(String result) {
                tvHeight.setText(floatStringToIntString(result));
            }
        });
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_complete_personal_info, contentLayout, false);
        return view;
    }

    @OnClick({R.id.tv_jump, R.id.img_man, R.id.img_women, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_jump:
                ActivityUtils.finishAllActivities();
                startActivity(new Intent(getPageContext(), MainActivity.class));
                break;
            case R.id.img_man:
                imgMan.setTag(1);
                imgWomen.setTag(0);
                imgMan.setImageResource(R.drawable.dit_plan_man_checked);
                imgWomen.setImageResource(R.drawable.dit_plan_woman_uncheck);
                break;
            case R.id.img_women:
                imgMan.setTag(0);
                imgWomen.setTag(1);
                imgMan.setImageResource(R.drawable.dit_plan_man_un_check);
                imgWomen.setImageResource(R.drawable.dit_plan_woman_checked);
                break;
            case R.id.tv_next:
                toDoSubmit();
                break;
        }
    }


    /**
     * 提交全部数据
     */
    private void toDoSubmit() {
        String name = etName.getText().toString().trim();
        String weight = tvWeight.getText().toString().trim();
        String height = tvHeight.getText().toString().trim();
        int tagMan = (int) imgMan.getTag();
        int tagWomen = (int) imgWomen.getTag();
        int postSex = 0;
        if (1 == tagMan) {
            postSex = 1;
        } else if (1 == tagWomen) {
            postSex = 2;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("nickname", name);
        map.put("sex", postSex + "");
        map.put("weight", weight);
        map.put("height", height);
        XyUrl.okPostSave(XyUrl.COMPLETE_PERSONAL_INFO, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ToastUtils.showShort(value);
                ActivityUtils.finishAllActivities();
                startActivity(new Intent(getPageContext(), MainActivity.class));
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {

            }
        });
    }


    private String floatStringToIntString(String floatString) {
        int a = (int) Float.parseFloat(floatString);
        return String.valueOf(a);
    }
}