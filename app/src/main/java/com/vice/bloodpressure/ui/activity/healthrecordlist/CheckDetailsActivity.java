package com.vice.bloodpressure.ui.activity.healthrecordlist;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;
import com.vice.bloodpressure.bean.ExamineBean;
import com.lyd.baselib.utils.engine.GlideImageEngine;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 描述: 检查记录详情
 * 作者: LYD
 * 创建日期: 2019/4/28 14:15
 */


public class CheckDetailsActivity extends BaseActivity {
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.gv_pic)
    GridView gvPic;
    private List<String> sourceImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMiv();
    }

    private void initMiv() {
        ExamineBean examine = (ExamineBean) getIntent().getSerializableExtra("check");
        String datetime = examine.getDatetime();
        tvTime.setText(String.format("检测时间：%s", datetime.substring(11)));
        sourceImageList = examine.getPicurl();
        gvPic.setAdapter(new NineGridAdapter());
        String hydname = examine.getHydname();
        setTitle(hydname);
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_check_details, contentLayout, false);
    }


    private class NineGridAdapter extends CommonAdapter<String> {
        private NineGridAdapter() {
            super(CheckDetailsActivity.this, R.layout.item_pic, sourceImageList);
        }

        @Override
        protected void convert(ViewHolder viewHolder, String item, final int position) {
            ImageView imageView = viewHolder.getView(R.id.iv_pic);
            //Glide加载图片
            int screenWidth = ScreenUtils.getScreenWidth();
            int imgWidth = (screenWidth - SizeUtils.dp2px(2 * 5 + 12 * 2)) / 3;
            LinearLayout.LayoutParams fp = new LinearLayout.LayoutParams(imgWidth, imgWidth);
            imageView.setLayoutParams(fp);
            Glide.with(Utils.getApp()).
                    load(item).
                    error(R.drawable.default_image)
                    .placeholder(R.drawable.default_image)
                    .into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MNImageBrowser.with(CheckDetailsActivity.this)
                            .setCurrentPosition(position)
                            .setImageEngine(new GlideImageEngine())
                            .setImageList((ArrayList<String>) sourceImageList)
                            .setIndicatorHide(false)
                            .setFullScreenMode(true)
                            .show(imageView);
                }
            });
        }
    }
}
