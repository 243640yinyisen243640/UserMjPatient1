package com.lyd.modulemall.ui.activity.supplier;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.lyd.baselib.base.activity.BaseViewBindingActivity;
import com.lyd.modulemall.R;
import com.lyd.modulemall.adapter.OtherQualificationPicDetailAdapter;
import com.lyd.modulemall.databinding.ActivityOtherQualificationPicDetailBinding;

import java.util.ArrayList;

/**
 * 描述:  其它资质 照片详情页
 * 作者: LYD
 * 创建日期: 2021/1/8 9:41
 */
public class OtherQualificationPicDetailActivity extends BaseViewBindingActivity<ActivityOtherQualificationPicDetailBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_other_qualification_pic_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("其他资质");
        ArrayList<String> qualification_img = getIntent().getStringArrayListExtra("qualification_img");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getPageContext());
        binding.rvOtherPicList.setLayoutManager(linearLayoutManager);
        binding.rvOtherPicList.setAdapter(new OtherQualificationPicDetailAdapter(qualification_img));
    }
}