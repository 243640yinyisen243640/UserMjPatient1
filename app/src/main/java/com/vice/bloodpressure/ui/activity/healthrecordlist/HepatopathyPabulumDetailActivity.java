package com.vice.bloodpressure.ui.activity.healthrecordlist;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.GalleryListBean;
import com.vice.bloodpressure.bean.HepatopathyPabulumDetailBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.engine.GlideImageEngine;
import com.vice.bloodpressure.view.MyGridView;
import com.wei.android.lib.colorview.view.ColorEditText;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述:   详情
 * 作者: LYD
 * 创建日期: 2019/9/7 10:37
 */
public class HepatopathyPabulumDetailActivity extends BaseHandlerActivity {
    private static final int GET_DETAIL = 10010;
    @BindView(R.id.et_alt)
    ColorEditText etAlt;
    @BindView(R.id.et_ast)
    ColorEditText etAst;
    @BindView(R.id.et_alp)
    ColorEditText etAlp;
    @BindView(R.id.et_ggt)
    ColorEditText etGgt;
    @BindView(R.id.et_tp_total)
    ColorEditText etTpTotal;
    @BindView(R.id.et_tp_white)
    ColorEditText etTpWhite;
    @BindView(R.id.et_tp_egg)
    ColorEditText etTpEgg;
    @BindView(R.id.et_ag)
    ColorEditText etAg;
    @BindView(R.id.et_bil_total)
    ColorEditText etBilTotal;
    @BindView(R.id.et_bil_indirect)
    ColorEditText etBilIndirect;
    @BindView(R.id.et_bil_direct)
    ColorEditText etBilDirect;
    @BindView(R.id.et_afp)
    ColorEditText etAfp;
    @BindView(R.id.tv_left)
    ColorTextView tvLeft;
    @BindView(R.id.tv_center)
    ColorTextView tvCenter;
    @BindView(R.id.tv_right)
    ColorTextView tvRight;
    @BindView(R.id.tv_left_second)
    ColorTextView tvLeftSecond;
    @BindView(R.id.tv_center_second)
    ColorTextView tvCenterSecond;
    @BindView(R.id.tv_right_second)
    ColorTextView tvRightSecond;
    @BindView(R.id.gv_pic)
    MyGridView gvPic;
    @BindView(R.id.rv_pic_add)
    RecyclerView rvPicAdd;
    @BindView(R.id.et_blood_ammonia)
    EditText etBloodAmmonia;
    @BindView(R.id.et_forward)
    EditText etForward;
    @BindView(R.id.et_blood_red)
    EditText etBloodRed;
    @BindView(R.id.et_blood_clotting)
    EditText etBloodClotting;
    private List<String> sourceImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("肝病营养指标");
        getDetail();
    }


    /**
     * 获取详情
     */
    private void getDetail() {
        String id = getIntent().getStringExtra("id");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        XyUrl.okPost(XyUrl.HEPATOPATHY_PABULUM_DETAIL, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                HepatopathyPabulumDetailBean data = JSONObject.parseObject(value, HepatopathyPabulumDetailBean.class);
                Message msg = getHandlerMessage();
                msg.what = GET_DETAIL;
                msg.obj = data;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });

    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_hepatopathy_pabulum_add, contentLayout, false);
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DETAIL:
                HepatopathyPabulumDetailBean data = (HepatopathyPabulumDetailBean) msg.obj;
                setDetail(data);
                break;
        }
    }


    /***
     * 设置详情
     * @param data
     */
    private void setDetail(HepatopathyPabulumDetailBean data) {
        setEtUnEdited();
        setEtContent(data);
        setEdemaAndNutrition(data);
        setGvImg(data.getLiverimg());
    }

    private void setGvImg(List<String> list) {
        rvPicAdd.setVisibility(View.GONE);
        gvPic.setVisibility(View.VISIBLE);
        sourceImageList = list;
        List<GalleryListBean> listBean = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                GalleryListBean model = new GalleryListBean();
                model.setThumb_img("add");
                listBean.add(model);
            }
            gvPic.setAdapter(new NineGridAdapter());
        }
    }

    /**
     * 设置水肿
     *
     * @param data
     */
    private void setEdemaAndNutrition(HepatopathyPabulumDetailBean data) {
        String edema = data.getEdema();
        String nutrition = data.getNutrition();
        //0未选择 1 轻度  2中度  3重度
        switch (edema) {
            case "0":
                break;
            case "1":
                setTvChecked(tvLeft);
                break;
            case "2":
                setTvChecked(tvCenter);
                break;
            case "3":
                setTvChecked(tvRight);
                break;
        }

        switch (nutrition) {
            case "0":
                break;
            case "1":
                setTvChecked(tvLeftSecond);
                break;
            case "2":
                setTvChecked(tvCenterSecond);
                break;
            case "3":
                setTvChecked(tvRightSecond);
                break;
        }
    }

    /**
     * 设置选中
     *
     * @param tv
     */
    private void setTvChecked(ColorTextView tv) {
        tv.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white_text));
        tv.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
        tv.getColorHelper().setBorderColorNormal(ColorUtils.getColor(R.color.main_home));
    }

    /**
     * 设置ET内容
     *
     * @param data
     */
    private void setEtContent(HepatopathyPabulumDetailBean data) {
        //"transaminase":"1",谷丙转氨酶
        String transaminase = data.getTransaminase();
        etAlt.setText(transaminase);
        //"aspertate":"2",谷草转氨酶
        String aspertate = data.getAspertate();
        etAst.setText(aspertate);
        //"phosphatase":"3",碱性磷酸酶
        String phosphatase = data.getPhosphatase();
        etAlp.setText(phosphatase);
        //"transpeptidase":"4",谷氨酰转肽酶
        String transpeptidase = data.getTranspeptidase();
        etGgt.setText(transpeptidase);
        //"totalprotein":"5",总蛋白
        String totalprotein = data.getTotalprotein();
        etTpTotal.setText(totalprotein);
        //"albumin":"6",白蛋白
        String albumin = data.getAlbumin();
        etTpWhite.setText(albumin);
        //"globulin":"7",球蛋白
        String globulin = data.getGlobulin();
        etTpEgg.setText(globulin);
        //"albuminvs":"8",白蛋白/球蛋白
        String albuminvs = data.getAlbuminvs();
        etAg.setText(albuminvs);
        //"bravery":"9",总胆红
        String bravery = data.getBravery();
        etBilTotal.setText(bravery);
        //"bilered":"10",间接胆红
        String bilered = data.getBilered();
        etBilIndirect.setText(bilered);
        //"bilirubin":"11",直接胆红素
        String bilirubin = data.getBilirubin();
        etBilDirect.setText(bilirubin);
        //"afp":"12",甲胎蛋白
        String afp = data.getAfp();
        etAfp.setText(afp);
        String blood_ammonia = data.getBlood_ammonia();
        etBloodAmmonia.setText(blood_ammonia);
        String prealbumin = data.getPrealbumin();
        etForward.setText(prealbumin);
        String haemoglobin = data.getHaemoglobin();
        etBloodRed.setText(haemoglobin);
        String prothrombin = data.getProthrombin();
        etBloodClotting.setText(prothrombin);
    }

    /**
     * 设置ET不可编辑
     */
    private void setEtUnEdited() {
        etAlt.setEnabled(false);
        etAst.setEnabled(false);
        etAlp.setEnabled(false);
        etGgt.setEnabled(false);
        etTpTotal.setEnabled(false);
        etTpWhite.setEnabled(false);
        etTpEgg.setEnabled(false);
        etAg.setEnabled(false);
        etBilTotal.setEnabled(false);
        etBilIndirect.setEnabled(false);
        etBilDirect.setEnabled(false);
        etAfp.setEnabled(false);
    }

    private class NineGridAdapter extends CommonAdapter<String> {

        private NineGridAdapter() {
            super(HepatopathyPabulumDetailActivity.this, R.layout.item_pic, sourceImageList);
        }

        @Override
        protected void convert(ViewHolder viewHolder, String item, final int position) {
            ImageView imageView = viewHolder.getView(R.id.iv_pic);
            //Glide加载图片
            int screenWidth = ScreenUtils.getScreenWidth();
            int imgWidth = (screenWidth - SizeUtils.dp2px(3 * 5 + 12 * 2 + 2 * 15)) / 4;
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
                    MNImageBrowser.with(HepatopathyPabulumDetailActivity.this)
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
