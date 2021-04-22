package com.vice.bloodpressure.ui.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.PersonalRecordBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.homesign.MyQRCodeActivity;
import com.vice.bloodpressure.utils.DialogUtils;
import com.lyd.baselib.utils.GifSizeFilter;
import com.vice.bloodpressure.utils.PickerUtils;
import com.vice.bloodpressure.utils.TimeFormatUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 编辑个人资料
 * 作者: LYD
 * 创建日期: 2019/3/27 16:11
 */

public class MeActivity extends BaseHandlerActivity {
    private static final String TAG = "MeActivity";
    private static final int GET_USER_INFO = 10010;
    private static final int REQUEST_CODE_CHOOSE = 10011;
    private static final int CODE_RESULT_REQUEST = 10012;
    private static final int CHANGE_IMG_HEAD = 10014;

    @BindView(R.id.iv_head)
    QMUIRadiusImageView imgHead;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.ll_sex)
    LinearLayout llSex;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.ll_birthday)
    LinearLayout llBirthday;
    @BindView(R.id.tv_tel)
    TextView tvTel;

    private Uri cropImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("编辑个人资料");
        setUserInfo();
        getUserInfo();
    }

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_me, contentLayout, false);
        return layout;
    }

    /**
     * 设置用户信息
     */
    private void setUserInfo() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        Glide.with(Utils.getApp()).
                load(user.getPicture())
                .error(R.drawable.default_head)
                .placeholder(R.drawable.default_head)
                .into(imgHead);
        tvTel.setText(user.getUsername());
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


    @OnClick({R.id.ll_head, R.id.ll_name, R.id.ll_sex, R.id.ll_birthday, R.id.ll_my_qrcode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_head://头像 修改头像设置
                toSelectPhoto();
                break;
            case R.id.ll_name://昵称
                toUpdateNickName();
                break;
            case R.id.ll_sex://性别
                toUpdateSex();
                break;
            case R.id.ll_birthday://生日
                toUpdateBirthday();
                break;
            case R.id.ll_my_qrcode://生日
                //toUpdateBirthday();
                startActivity(new Intent(getPageContext(), MyQRCodeActivity.class));
                break;
        }
    }

    private void toUpdateBirthday() {
        PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
            @Override
            public void execEvent(String content) {
                tvBirthday.setText(content);
                long birthdayTime = TimeUtils.string2Millis(content, TimeFormatUtils.getDefaultFormat());
                long timeS = birthdayTime / 1000;
                toSave("birthtime", timeS + "");
            }
        });
    }

    private void toUpdateSex() {
        ArrayList<String> sexList = new ArrayList<>();
        sexList.add("男");
        sexList.add("女");
        PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
            @Override
            public void execEvent(String content) {
                tvSex.setText(content);
                if ("男".equals(content)) {
                    toSave("sex", "1");
                } else {
                    toSave("sex", "2");
                }
            }
        }, sexList);
    }

    private void toUpdateNickName() {
        DialogUtils.getInstance().showEditTextDialog(getPageContext(), "昵称", "请输入昵称", new DialogUtils.DialogInputCallBack() {
            @Override
            public void execEvent(String text) {
                tvName.setText(text);
                toSave("petname", text);
            }
        });
    }


    /**
     * 知乎选择照片
     */
    private void toSelectPhoto() {
        PermissionUtils
                .permission(PermissionConstants.CAMERA, PermissionConstants.STORAGE)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        selectPhoto();
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.showShort("请允许使用相机权限和存储权限");
                    }
                }).request();
    }

    /**
     * 选择照片
     */
    private void selectPhoto() {
        Matisse.from(MeActivity.this)
                .choose(MimeType.ofImage(), false)
                .countable(true)
                .maxSelectable(1)//最多选一张
                //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, "com.vice.bloodpressure.FileProvider", "Test"))
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    /**
     * 保存生日 性别 昵称
     *
     * @param fieldName
     * @param fieldValue
     */
    private void toSave(String fieldName, String fieldValue) {
        Map<String, Object> map = new HashMap<>();
        map.put("fieldname", fieldName);
        map.put("fieldvalue", fieldValue);
        XyUrl.okPostSave(XyUrl.PERSONAL_SAVE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showShort(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    private void uploadPhoto(String url) {
        LoginBean loginBean = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        XyUrl.okHead(XyUrl.UPLOAD_PHOTO_PIC, loginBean.getToken(), url, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                try {
                    org.json.JSONObject jsonObject = new org.json.JSONObject(value);
                    String url = jsonObject.getString("picture");
                    Message message = getHandlerMessage();
                    message.what = CHANGE_IMG_HEAD;
                    message.obj = url;
                    sendHandlerMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            File fileCropUri = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
            switch (requestCode) {
                case REQUEST_CODE_CHOOSE:
                    //path uri file 三者互相转换
                    cropImageUri = Uri.fromFile(fileCropUri);
                    List<String> pathList = Matisse.obtainPathResult(data);
                    File file = new File(pathList.get(0));
                    Uri newUri = Uri.fromFile(file);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        newUri = FileProvider.getUriForFile(this, "com.vice.bloodpressure.FileProvider", new File(newUri.getPath()));
                    }
                    cropImageUri(this, newUri, cropImageUri, 1, 1, 300, 300, CODE_RESULT_REQUEST);//裁剪
                    break;
                case CODE_RESULT_REQUEST:
                    String url = cropImageUri.getPath();
                    uploadPhoto(url);
                    break;
            }
        }
    }

    private void cropImageUri(Activity activity, Uri orgUri, Uri desUri, int aspectX, int aspectY, int width, int height, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(orgUri, "image/*");
        //发送裁剪信号
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", true);
        //将剪切的图片保存到目标Uri中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, desUri);
        //1-false用uri返回图片
        //2-true直接用bitmap返回图片（此种只适用于小图片，返回图片过大会报错）
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_USER_INFO:
                PersonalRecordBean user = (PersonalRecordBean) msg.obj;
                int sex = user.getSex();
                if (1 == sex) {
                    tvSex.setText("男");
                } else {
                    tvSex.setText("女");
                }
                String birthday = TimeUtils.millis2String(user.getBirthtime() * 1000L, TimeFormatUtils.getDefaultFormat());
                tvBirthday.setText(birthday);
                tvName.setText(user.getPetname());
                break;
            case CHANGE_IMG_HEAD:
                String headUrl = (String) msg.obj;
                LoginBean loginBean = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
                loginBean.setPicture(headUrl);
                SharedPreferencesUtils.putBean(getPageContext(), SharedPreferencesUtils.USER_INFO, loginBean);
                EventBusUtils.post(new EventMessage(ConstantParam.RONG_HEAD_REFRESH));
                Glide.with(Utils.getApp()).
                        load(headUrl)
                        .error(R.drawable.default_head)
                        .placeholder(R.drawable.default_head)
                        .into(imgHead);
                break;
            default:
                break;
        }
    }
}
