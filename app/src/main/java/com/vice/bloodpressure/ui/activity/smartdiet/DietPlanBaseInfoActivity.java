package com.vice.bloodpressure.ui.activity.smartdiet;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.ui.activity.MainActivity;
import com.wei.android.lib.colorview.view.ColorTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  基本情况
 * 作者: LYD
 * 创建日期: 2020/3/13 14:02
 */
public class DietPlanBaseInfoActivity extends BaseHandlerActivity {
    @BindView(R.id.img_desc)
    ImageView imgDesc;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_next)
    ColorTextView tvNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("基本情况");
        String type = getIntent().getStringExtra("type");
        if ("special".equals(type)) {
            imgDesc.setImageResource(R.drawable.base_info_diet_special);
            tvDesc.setText("因糖尿病肾病Ⅲ期及以上的饮食较为特殊，需要严格控制蛋白质的摄入，建议您咨询专业临床营养师为您设计精确的饮食方案。");
        } else {
            imgDesc.setImageResource(R.drawable.base_info_not_match);
            tvDesc.setText("非常抱歉，小慧还没有将您的身体状况纳入计算范围内，我们会尽快努力扩大计算范围，给您带来不好的体验小慧表示诚挚的歉意，您可以正常使用体验其它功能。");
        }
        getBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.finishToActivity(MainActivity.class, false);
            }
        });
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_smart_diet_base_info, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick(R.id.tv_next)
    public void onViewClicked() {
        ActivityUtils.finishToActivity(MainActivity.class, false);
    }
}
