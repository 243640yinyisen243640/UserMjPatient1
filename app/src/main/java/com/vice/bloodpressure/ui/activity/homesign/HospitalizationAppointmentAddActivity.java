package com.vice.bloodpressure.ui.activity.homesign;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.allen.library.RxHttpUtils;
import com.allen.library.bean.BaseData;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.Service;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.DialogUtils;
import com.lyd.baselib.utils.GifSizeFilter;
import com.vice.bloodpressure.utils.PickerUtils;
import com.vice.bloodpressure.view.popu.SlideFromBottomPopup;
import com.wei.android.lib.colorview.view.ColorButton;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import razerdp.basepopup.BasePopupWindow;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @ProjectName:
 * @Package: com.vice.bloodpressure.ui.activity.familydoctor
 * @ClassName: HospitalizationAppointmentAddActivity
 * @Description: 预约住院添加
 * @Author: zwk
 * @CreateDate: 2020/1/2 9:12
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/1/2 9:12
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class HospitalizationAppointmentAddActivity extends BaseHandlerActivity {

    private static final String TAG = "HospitalizationAppointmentAddActivity";
    private static final int REQUEST_CAMERA = 0x0086;
    private static final int REQUEST_CODE_CHOOSE = 0x0085;
    private static final int COMPRESS_OVER = 8888;
    @BindView(R.id.et_one)
    EditText etOne;
    @BindView(R.id.tv_two)
    TextView tvTwo;
    @BindView(R.id.et_three)
    EditText etThree;
    @BindView(R.id.tv_four)
    TextView tvFour;
    @BindView(R.id.et_five)
    EditText etFive;
    @BindView(R.id.tv_six)
    TextView tvSix;
    @BindView(R.id.tv_describe)
    EditText etDescribe;
    @BindView(R.id.iv_one)
    ImageView ivOne;
    @BindView(R.id.iv_two)
    ImageView ivTwo;
    @BindView(R.id.iv_three)
    ImageView ivThree;
    @BindView(R.id.btn_submit)
    ColorButton btnSubmit;
    private ListPopupWindow listPopupWindow;
    private SlideFromBottomPopup bottomPopup;
    private int imgPosition;
    private File file;
    private SuccessPopup successPopup;
    private List<String> paths = new ArrayList<>();

    private String sex;
    private String time;
    private String name;
    private String type;
    private String age;
    private String phoneNumber;
    private String describe;

    private LoginBean user;
    private List<String> phpList;
    private int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("预约住院");
        user = (LoginBean) SharedPreferencesUtils.getBean(Utils.getApp(), SharedPreferencesUtils.USER_INFO);
        initListPopup();
        initBottomPopup();
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_hospitalization_appointment_add, contentLayout, false);
    }

    private void initBottomPopup() {
        bottomPopup = new SlideFromBottomPopup(getPageContext());
        bottomPopup.findViewById(R.id.tv_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PermissionUtils
                        .permission(PermissionConstants.STORAGE)
                        .callback(new PermissionUtils.SimpleCallback() {
                            @Override
                            public void onGranted() {
                                selectPhoto();
                            }

                            @Override
                            public void onDenied() {
                                ToastUtils.showShort("请允许使用存储权限");
                            }
                        }).request();
            }
        });
        bottomPopup.findViewById(R.id.tv_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtils
                        .permission(PermissionConstants.CAMERA)
                        .callback(new PermissionUtils.SimpleCallback() {
                            @Override
                            public void onGranted() {
                                takePhoto();
                            }

                            @Override
                            public void onDenied() {
                                ToastUtils.showShort("请允许使用相机权限");
                            }
                        }).request();
            }
        });
    }

    private void initListPopup() {
        List<String> strings = new ArrayList<>();
        strings.add("初次住院");
        strings.add("再次住院");
        listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.shape_bg_list_popup));
        listPopupWindow.setAnimationStyle(R.style.QMUI_Animation_PopDownMenu_Center);
        listPopupWindow.setAdapter(new MyListPopupWindowAdapter(this, strings, new MyListPopupWindowAdapter.OnItemChildCheckListener() {
            @Override
            public void OnItemChildChecked(View v, String str, int position) {
                x = position + 1;
                tvTwo.setTextColor(Color.BLACK);
                tvTwo.setText(str);
                type = str;
                listPopupWindow.dismiss();
            }
        }));
        listPopupWindow.setAnchorView(tvTwo);
        listPopupWindow.setVerticalOffset(ConvertUtils.dp2px(1));
        listPopupWindow.setWidth(ConvertUtils.dp2px(215));
        listPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        listPopupWindow.setModal(true);
    }

    private void selectPhoto() {
        Matisse.from(HospitalizationAppointmentAddActivity.this)
                .choose(MimeType.ofImage(), false)
                .countable(true)
                .maxSelectable(1)//最多选一张
                //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    /**
     * 拍照
     *
     * @param
     */
    private void takePhoto() {
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Test/" + System.currentTimeMillis() + ".jpg");
        file.getParentFile().mkdirs();
        //改变Uri
        Uri uri = FileProvider.getUriForFile(this, "com.vice.bloodpressure.FileProvider", file);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //添加权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case COMPRESS_OVER:
                paths = (List<String>) msg.obj;
                break;
        }
    }

    @OnClick({R.id.tv_two, R.id.tv_six, R.id.btn_submit, R.id.iv_one, R.id.iv_two, R.id.iv_three, R.id.tv_four})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_two:
                listPopupWindow.show();
                break;
            case R.id.tv_six:
                PickerUtils.showTime(this, new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvSix.setText(content);
                        tvSix.setTextColor(Color.BLACK);
                        time = content;
                    }
                });
                break;
            case R.id.tv_four:
                DialogUtils.getInstance().showCommonDialog(this, "请选择性别",
                        "", "男", "女", new DialogUtils.DialogCallBack() {
                            @Override
                            public void execEvent() {
                                tvFour.setTextColor(Color.BLACK);
                                tvFour.setText("男");
                                sex = "1";
                            }
                        }, new DialogUtils.DialogCallBack() {
                            @Override
                            public void execEvent() {
                                tvFour.setTextColor(Color.BLACK);
                                tvFour.setText("女");
                                sex = "2";
                            }
                        });
                break;
            case R.id.btn_submit:
                toSubmit();
                break;
            case R.id.iv_one:
                imgPosition = 1;
                bottomPopup.showPopupWindow();
                break;
            case R.id.iv_two:
                imgPosition = 2;
                bottomPopup.showPopupWindow();
                break;
            case R.id.iv_three:
                imgPosition = 3;
                bottomPopup.showPopupWindow();
                break;
        }
    }

    private void toSubmit() {
        name = etOne.getText().toString();
        age = etThree.getText().toString();
        phoneNumber = etFive.getText().toString();
        describe = etDescribe.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort("请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(type)) {
            ToastUtils.showShort("请选择住院类型");
            return;
        }
        if (TextUtils.isEmpty(age)) {
            ToastUtils.showShort("请输入年龄");
            return;
        }
        if (TextUtils.isEmpty(sex)) {
            ToastUtils.showShort("请选择性别");
            return;
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            ToastUtils.showShort("请输入联系电话");
            return;
        }
        if (TextUtils.isEmpty(time)) {
            ToastUtils.showShort("请选择预约时间");
            return;
        }
        if (TextUtils.isEmpty(describe)) {
            ToastUtils.showShort("请输入疾病诊断");
            return;
        }

        phpList = new ArrayList<>();
        for (int i = 0; i < paths.size(); i++) {
            uploadPhoto(paths.get(i));
        }
        if (paths.size() == 0) {
            submitContent("");
        }

    }

    private void submitContent(String url) {
        LogUtils.e(url);
        String token = user.getToken();
        String signid = user.getSignid();
        RxHttpUtils.createApi(Service.class)
                .familyInhospitalAdd(token, String.valueOf(x), name, age, sex, phoneNumber, describe, url, time, Integer.valueOf(signid))
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BaseData>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(BaseData baseData) {
                        //清除压缩文件
                        FileUtils.deleteAllInDir("/storage/emulated/0/Android/data/com.vice.bloodpressure/cache/luban_disk_cache/");

                        successPopup = new SuccessPopup(HospitalizationAppointmentAddActivity.this);
                        successPopup.showPopupWindow();
                        EventBusUtils.post(new EventMessage<>(123));
                        getHandler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                successPopup.dismiss();
                                finish();
                            }
                        }, 500);
                    }
                });
    }

    /**
     * 上传图片
     */
    private void uploadPhoto(String url) {
        XyUrl.okUpload(XyUrl.UPLOAD_PHOTO, url, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                try {
                    LogUtils.e(value);
                    org.json.JSONObject jsonObject = new org.json.JSONObject(value);
                    String url = jsonObject.getString("url");
                    phpList.add(url);
                    LogUtils.e(paths);
                    LogUtils.e(phpList);
                    if (paths.size() == phpList.size()) {

                        submitContent(phpList.toString().substring(1, phpList.toString().length() - 1).replace(" ", ""));
                    }
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
            switch (requestCode) {
                //拍照完成回调
                case REQUEST_CAMERA:
                    bottomPopup.dismiss();
                    String path = file.getPath();
                    if (paths.size() < 3) {
                        paths.add(path);
                    } else {
                        paths.set(imgPosition, path);
                    }
                    compress(paths);
                    switch (imgPosition) {
                        case 1:
                            Glide.with(this).load(path).placeholder(R.drawable.default_image).into(ivOne);
                            break;
                        case 2:
                            Glide.with(this).load(path).placeholder(R.drawable.default_image).into(ivTwo);
                            break;
                        case 3:
                            Glide.with(this).load(path).placeholder(R.drawable.default_image).into(ivThree);
                            break;
                    }
                    break;
                //选择照片回调
                case REQUEST_CODE_CHOOSE:
                    bottomPopup.dismiss();
                    List<String> pathList = Matisse.obtainPathResult(data);
                    if (pathList.size() == 1) {
                        String p = pathList.get(0);
                        if (paths.size() < 3) {
                            paths.add(p);
                        } else {
                            paths.set(imgPosition, p);
                        }
                        compress(paths);
                        switch (imgPosition) {
                            case 1:
                                Glide.with(this).load(p).placeholder(R.drawable.default_image).into(ivOne);
                                break;
                            case 2:
                                Glide.with(this).load(p).placeholder(R.drawable.default_image).into(ivTwo);
                                break;
                            case 3:
                                Glide.with(this).load(p).placeholder(R.drawable.default_image).into(ivThree);
                                break;
                        }
                    }
                    break;
                default:
            }
        }
    }

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
                            sendHandlerMessage(msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                }).launch();
    }

    public static class MyListPopupWindowAdapter extends BaseAdapter {

        private List<String> stringList;
        private Context context;
        private OnItemChildCheckListener onItemChildCheckListener;

        private MyListPopupWindowAdapter(Context context, List<String> stringList, OnItemChildCheckListener onItemChildCheckListener) {
            this.context = context;
            this.stringList = stringList;
            this.onItemChildCheckListener = onItemChildCheckListener;
        }

        @Override
        public int getCount() {
            return stringList == null ? 0 : stringList.size();
        }

        @Override
        public Object getItem(int position) {
            return stringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewholder = new ViewHolder();
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_listpopupwindow_hospital, parent, false);
                viewholder.tv = convertView.findViewById(R.id.tv);
                viewholder.cb = convertView.findViewById(R.id.cb);
                viewholder.tv.setText(stringList.get(position));


                viewholder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        onItemChildCheckListener.OnItemChildChecked(buttonView, stringList.get(position), position);
                    }
                });
            }
            return convertView;
        }

        public interface OnItemChildCheckListener {
            void OnItemChildChecked(View another, String str, int position);
        }

        class ViewHolder {
            TextView tv;
            CheckBox cb;
        }

    }

    private class SuccessPopup extends BasePopupWindow {
        public SuccessPopup(Context context) {
            super(context);
            setPopupGravity(Gravity.CENTER);
        }

        @Override
        public View onCreateContentView() {
            return createPopupById(R.layout.success_popup);
        }
    }

}
