package com.vice.bloodpressure.ui.activity.homesign;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.PersonalRecordBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.vice.bloodpressure.utils.ZXingUtils;
import com.vice.bloodpressure.view.popu.SlideFromBottomPopupSaveToAlbum;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 描述:  我的二维码
 * 作者: LYD
 * 创建日期: 2019/12/30 11:11
 */
public class MyQRCodeActivity extends BaseHandlerActivity {
    private static final int GET_BITMAP_FROM_URL = 10010;
    private static final int GET_USER_INFO = 10011;
    @BindView(R.id.img_head)
    QMUIRadiusImageView imgHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.img_sex)
    ImageView imgSex;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.img_qr_code)
    ImageView imgQrCode;
    private String guid;
    private Bitmap codeBmp;
    private SlideFromBottomPopupSaveToAlbum saveToAlbumPopup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPopup();
        initTitle();
        getUserInfo();
    }

    private void initPopup() {
        saveToAlbumPopup = new SlideFromBottomPopupSaveToAlbum(getPageContext());
        TextView tvSaveToAlbum = saveToAlbumPopup.findViewById(R.id.tv_save_to_album);
        tvSaveToAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtils
                        .permission(PermissionConstants.STORAGE)
                        .callback(new PermissionUtils.SimpleCallback() {
                            @Override
                            public void onGranted() {
                                String dcimPath = PathUtils.getExternalDcimPath() + "/" + "HuiJianKang" + "/" + "MyQrCode.jpg";
                                ImageUtils.save(codeBmp, dcimPath, Bitmap.CompressFormat.JPEG);
                                FileUtils.notifySystemToScan(dcimPath);
                                //ImageUtils.save2Album(codeBmp, Bitmap.CompressFormat.JPEG);
                                ToastUtils.showShort("图片已保存至相册");
                                saveToAlbumPopup.dismiss();
                            }

                            @Override
                            public void onDenied() {
                                ToastUtils.showShort("请允许使用存储权限");
                            }
                        }).request();
            }
        });
    }

    private void initTitle() {
        setTitle("我的二维码");
        getLlMore().removeAllViews();
        ImageView imgMore = new ImageView(getPageContext());
        imgMore.setImageResource(R.drawable.home_scan_top_more);
        imgMore.setPadding(20, 5, 20, 5);
        getLlMore().addView(imgMore);
        getLlMore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToAlbumPopup.showPopupWindow();
            }
        });
    }


    private void getUserInfo() {
        LoginBean userLogin = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", userLogin.getToken());
        XyUrl.okPost(XyUrl.PERSONAL_RECORD, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                PersonalRecordBean personalRecordBean = JSONObject.parseObject(value, PersonalRecordBean.class);
                Message message = getHandlerMessage();
                message.what = GET_USER_INFO;
                message.obj = personalRecordBean;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    /**
     * 请求图片
     *
     * @param urlStr
     */
    private void getBitmapFromUrl(String urlStr) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //创建Url对象
                    URL url = new URL(urlStr);
                    //根据url发送Http请求
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    //设置请求方式
                    urlConnection.setRequestMethod("GET");
                    //设置连接超时时间
                    urlConnection.setConnectTimeout(10 * 1000);
                    //得到服务器返回的相应码
                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        //获取输入流
                        InputStream is = urlConnection.getInputStream();
                        //将输入流转化成Bitmap
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        //通知主线程
                        Message message = new Message();
                        message.what = GET_BITMAP_FROM_URL;
                        message.obj = bitmap;
                        sendHandlerMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_my_qrcode, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_USER_INFO:
                PersonalRecordBean userBean = (PersonalRecordBean) msg.obj;
                guid = userBean.getGuid();
                LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
                String picUrl = user.getPicture();
                getBitmapFromUrl(picUrl);
                Glide.with(Utils.getApp())
                        .load(picUrl)
                        .placeholder(R.drawable.default_head)
                        .error(R.drawable.default_head)
                        .into(imgHead);
                String nickname = userBean.getNickname();
                tvName.setText(nickname);
                int sex = userBean.getSex();
                if (1 == sex) {
                    imgSex.setImageResource(R.drawable.my_qrcode_sex_male);
                } else {
                    imgSex.setImageResource(R.drawable.my_qrcode_sex_female);
                }
                String tel = userBean.getTel();
                tvTel.setText(tel);
                break;
            case GET_BITMAP_FROM_URL:
                Bitmap logoBmp = (Bitmap) msg.obj;
                codeBmp = ZXingUtils.createQRImage(getPageContext(), "guid" + guid, logoBmp);
                imgQrCode.setImageBitmap(codeBmp);
                break;
        }
    }
}
