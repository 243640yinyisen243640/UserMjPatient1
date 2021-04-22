package com.lyd.modulemall.ui.activity.refund;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luwei.checkhelper.CheckHelper;
import com.luwei.checkhelper.SingleCheckHelper;
import com.lyd.baselib.base.activity.BaseViewBindingActivity;
import com.lyd.baselib.utils.GifSizeFilter;
import com.lyd.modulemall.R;
import com.lyd.modulemall.adapter.AddImageThreeAdapter;
import com.lyd.modulemall.adapter.BottomSingleChoiceStringAdapter;
import com.lyd.modulemall.bean.RefundOrderGoodsBean;
import com.lyd.modulemall.bean.UploadPicResultBean;
import com.lyd.modulemall.databinding.ActivityApplyRefundBinding;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.net.OnError;
import com.lyd.modulemall.view.popup.BottomSingleChoicePopup;
import com.rxjava.rxlife.RxLife;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;
import liys.click.AClickStr;
import rxhttp.wrapper.param.RxHttp;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 描述:  申请退款 或 申请退货退款(不显示货物状态)
 * 作者: LYD
 * 创建日期: 2021/1/18 14:39
 */
@SuppressWarnings("all")
public class ApplyRefundActivity extends BaseViewBindingActivity<ActivityApplyRefundBinding> implements View.OnClickListener {
    private static final int REQUEST_CODE_CHOOSE = 10086;
    private static final int COMPRESS_OVER = 10010;
    private static final String TAG = "ApplyRefundActivity";
    private BottomSingleChoicePopup bottomSingleChoicePopup;
    private String refundReason;

    private List<String> listImgUrl;

    private AddImageThreeAdapter addImageAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case COMPRESS_OVER:
                    listImgUrl = (List<String>) msg.obj;
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_refund;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        listImgUrl = new ArrayList<>();
        setPageTitle();
        getGoodsInfo();
        initPuv();
    }

    private void initPuv() {
        addImageAdapter = new AddImageThreeAdapter(this, new AddImageThreeAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                PermissionUtils
                        .permission(PermissionConstants.STORAGE)
                        .callback(new PermissionUtils.SimpleCallback() {
                            @Override
                            public void onGranted() {
                                int selectMax = 3 - (listImgUrl.size());
                                selectPhoto(selectMax);
                            }

                            @Override
                            public void onDenied() {
                                ToastUtils.showShort("请允许使用存储权限");
                            }
                        }).request();
            }
        });
        binding.rvPic.setAdapter(addImageAdapter);
        binding.rvPic.setLayoutManager(new GridLayoutManager(this, 3));
        //设置删除
        addImageAdapter.setOnItemClickListener(new AddImageThreeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                listImgUrl.remove(position);
                addImageAdapter.setList(listImgUrl);
                addImageAdapter.notifyDataSetChanged();
            }
        });
    }

    private void selectPhoto(int selectMax) {
        Matisse.from(ApplyRefundActivity.this)
                .choose(MimeType.ofImage(), false)
                .countable(true)
                .maxSelectable(selectMax)//最多选一张
                //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    private void setPageTitle() {
        String type = getIntent().getStringExtra("type");
        if (type.equals("onlyRefund")) {
            setTitle("申请退款");
        } else {
            setTitle("申请退货退款");
        }
    }

    private void getGoodsInfo() {
        int order_goods_id = getIntent().getIntExtra("order_goods_id", 0);
        HashMap<String, Object> map = new HashMap<>();
        map.put("order_goods_id", order_goods_id);
        RxHttp.postForm(MallUrl.REFUND_ORDER_GOODS)
                .addAll(map)
                .asResponse(RefundOrderGoodsBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<RefundOrderGoodsBean>() {
                    @Override
                    public void accept(RefundOrderGoodsBean data) throws Exception {
                        String goods_name = data.getGoods_name();
                        String goods_img_url = data.getGoods_img_url();
                        String sku_name = data.getSku_name();
                        double refund_real_money = data.getRefund_real_money();
                        Glide.with(Utils.getApp()).load(goods_img_url).into(binding.imgPic);
                        binding.tvProductName.setText(goods_name);
                        binding.tvSpecification.setText(sku_name);
                        binding.tvRefundMoney.setText(refund_real_money + "");
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    private void toSubmit() {
        String refundReason = binding.tvRefundReason.getText().toString().trim();
        if (TextUtils.isEmpty(refundReason)) {
            ToastUtils.showShort("请选择退款原因");
            return;
        }
        //List<PictureModel> data = binding.puv.getData();
        if (listImgUrl != null && listImgUrl.size() > 0) {
            toUpLoadPic();
        } else {
            toDoSubmit("");
        }
    }

    /**
     * 提交图片
     */
    private void toUpLoadPic() {
        RxHttp.postForm(MallUrl.UPLOAD_IMAGE)
                .addFiles("file[]", listImgUrl)
                .asResponse(UploadPicResultBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<UploadPicResultBean>() {
                    @Override
                    public void accept(UploadPicResultBean response) throws Exception {
                        String url = response.getUrl();
                        toDoSubmit(url);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    /**
     * 提交数据
     */
    private void toDoSubmit(String imgUrl) {
        String type = getIntent().getStringExtra("type");
        int serviceType = 0;
        if (type.equals("onlyRefund")) {
            serviceType = 1;
        } else {
            serviceType = 2;
        }
        int order_goods_id = getIntent().getIntExtra("order_goods_id", 0);
        String refundReason = binding.tvRefundReason.getText().toString().trim();
        String refundDesc = binding.etRefundDesc.getText().toString().trim();
        HashMap map = new HashMap<>();
        map.put("order_goods_id", order_goods_id);
        map.put("action", refundReason);
        map.put("refund_remark", refundDesc);
        map.put("service_type", serviceType);
        map.put("refund_img", imgUrl);
        RxHttp.postForm(MallUrl.REFUND_ORDER)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String data) throws Exception {
                        finish();
                        Intent intent = new Intent(getPageContext(), RefundDetailActivity.class);
                        intent.putExtra("order_goods_id", order_goods_id);
                        startActivity(intent);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    @AClickStr({"ll_select_refund_reason", "bt_submit"})
    public void click(View view, String viewId) {
        switch (viewId) {
            case "ll_select_refund_reason":
                bottomSingleChoicePopup = new BottomSingleChoicePopup(getPageContext());
                TextView tvTitle = bottomSingleChoicePopup.findViewById(R.id.tv_title);
                tvTitle.setText("退款原因");
                TextView tvDesc = bottomSingleChoicePopup.findViewById(R.id.tv_desc);
                tvDesc.setText("请选择退款原因");
                //设置Rv
                RecyclerView rvCheckList = bottomSingleChoicePopup.findViewById(R.id.rv_check_list);
                String[] str = getResources().getStringArray(R.array.bottom_popup_cancel_order_and_refund_reason);
                List<String> list = Arrays.asList(str);
                //注册选择器
                SingleCheckHelper singleCheckHelper = new SingleCheckHelper();
                singleCheckHelper.register(String.class, new CheckHelper.Checker<String, BaseViewHolder>() {
                    @Override
                    public void check(String bean, BaseViewHolder holder) {
                        refundReason = bean;
                        CheckBox cbCheck = holder.getView(R.id.cb_check);
                        cbCheck.setChecked(true);
                    }

                    @Override
                    public void unCheck(String bean, BaseViewHolder holder) {
                        CheckBox cbCheck = holder.getView(R.id.cb_check);
                        cbCheck.setChecked(false);
                    }
                });
                BottomSingleChoiceStringAdapter bottomSingleChoiceStringAdapter = new BottomSingleChoiceStringAdapter(list, singleCheckHelper);
                rvCheckList.setLayoutManager(new LinearLayoutManager(getPageContext()));
                rvCheckList.setAdapter(bottomSingleChoiceStringAdapter);
                //
                TextView tvSure = bottomSingleChoicePopup.findViewById(R.id.tv_sure);
                tvSure.setOnClickListener(this);
                bottomSingleChoicePopup.showPopupWindow();
                break;
            case "bt_submit":
                toSubmit();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_sure) {
            binding.tvRefundReason.setText(refundReason);
            bottomSingleChoicePopup.dismiss();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CHOOSE:
                    List<String> pathList = Matisse.obtainPathResult(data);
                    listImgUrl.addAll(pathList);
                    compress(listImgUrl);
                    addImageAdapter.setList(listImgUrl);
                    addImageAdapter.notifyDataSetChanged();
                    break;
                default:
            }
        }
    }

    /**
     * 压缩图片
     */
    private void compress(List<String> photos) {
        Luban.with(this)
                .load(photos)
                .ignoreBy(1024)
                .setTargetDir("")
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif") || path.contains("http"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    List<String> list = new ArrayList<>();

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        String path = file.getPath();
                        list.add(path);
                        if (photos.size() == list.size()) {
                            Message msg = Message.obtain();
                            msg.what = COMPRESS_OVER;
                            msg.obj = list;
                            handler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                }).launch();
    }
}