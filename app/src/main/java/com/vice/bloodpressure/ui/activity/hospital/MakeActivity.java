package com.vice.bloodpressure.ui.activity.hospital;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.GifSizeFilter;
import com.vice.bloodpressure.utils.PickerUtils;
import com.lyd.baselib.utils.SharedPreferencesUtils;
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

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by yicheng on 2018/6/13.
 * <p>
 * 预约住院
 */

public class MakeActivity extends BaseHandlerActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private static final String TAG = "MakeActivity";
    private static final int COMPRESS_OVER = 10010;
    private static final int UP_PHOTO = 10011;
    private static final int REQUEST_CODE_CHOOSE = 10012;
    private static final int SUBMIT_SUCCESS = 10013;
    private static final String[] name = {"请选择", "初次住院", "再次住院"};
    private Spinner spMakeHosType;
    private String cardNumber;//住院类型
    private EditText etNickname;//患者名称
    private EditText etAge;//患者年龄
    private String sex = "";//性别
    private EditText etPhone;//联系电话
    private EditText etDiagnosis;//疾病诊断
    private EditText etStay;//住院目的
    private TextView tvTime;//预约住院时间
    private ImageView ivOne, ivTwo, ivThree;//图一、图二、图三

    private LoginBean user;
    private String photograph = "";
    private List<String> urlimg;//上传图片路径
    private List<String> phpList;
    private int mPosition;

    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case UP_PHOTO:
                List<String> photoList = (List<String>) msg.obj;
                for (int i = 0; i < photoList.size(); i++) {
                    uploadPhoto(photoList.get(i));
                }
                break;
            case COMPRESS_OVER:
                urlimg = (List<String>) msg.obj;
                break;
            case SUBMIT_SUCCESS:
                //清除压缩文件
                FileUtils.deleteAllInDir("/storage/emulated/0/Android/data/com.vice.bloodpressure/cache/luban_disk_cache/");
                ToastUtils.showShort("提交成功！请耐心等待医生安排");
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.make_hospital));
        user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        urlimg = new ArrayList<>();
        phpList = new ArrayList<>();
        initViews();
    }

    private void initViews() {
        spMakeHosType = findViewById(R.id.sp_make_hos_type);
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, name);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMakeHosType.setAdapter(adapter);
        spMakeHosType.setOnItemSelectedListener(this);

        //提交
        Button btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);
        etNickname = findViewById(R.id.et_make_hos_nickname);
        etAge = findViewById(R.id.et_make_hos_age);
        //获取性别
        RadioGroup radioGroup = findViewById(R.id.rg_make_hos_sex);
        radioGroup.setOnCheckedChangeListener(this);
        etPhone = findViewById(R.id.et_make_hos_phone);
        etDiagnosis = findViewById(R.id.et_make_hos_diagnosis);
        etStay = findViewById(R.id.et_make_hos_stay);
        tvTime = findViewById(R.id.tv_make_hos_time);
        tvTime.setOnClickListener(this);
        ivOne = findViewById(R.id.iv_make_hos_one);
        ivTwo = findViewById(R.id.iv_make_hos_two);
        ivThree = findViewById(R.id.iv_make_hos_three);
        ivOne.setOnClickListener(this);
        ivTwo.setOnClickListener(this);
        ivThree.setOnClickListener(this);
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_make_hospital, contentLayout, false);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        cardNumber = spMakeHosType.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                if (cardNumber.equals("请选择")) {
                    ToastUtils.showShort("请选择住院类型");
                    return;
                }
                if (etNickname.getText().toString().trim().isEmpty()) {
                    ToastUtils.showShort("请填写您的名字");
                    return;
                }
                if (etAge.getText().toString().trim().isEmpty()) {
                    ToastUtils.showShort("请填写您的年龄");
                    return;
                }
                if (sex.trim().isEmpty()) {
                    ToastUtils.showShort("请填选择您的性别");
                    return;
                }
                if (etPhone.getText().toString().trim().isEmpty()) {
                    ToastUtils.showShort("请填写您的电话");
                    return;
                }
                if (!RegexUtils.isMobileSimple(etPhone.getText().toString().trim())) {
                    ToastUtils.showShort("请输入合法的手机号");
                    return;
                }
                if (etDiagnosis.getText().toString().trim().isEmpty()) {
                    ToastUtils.showShort("请填写疾病诊断");
                    return;
                }
                if (etStay.getText().toString().trim().isEmpty()) {
                    ToastUtils.showShort("请填写住院目的");
                    return;
                }
                Drawable.ConstantState state = ivOne.getDrawable().getCurrent().getConstantState();
                if (state.equals(ContextCompat.getDrawable(this, R.drawable.add_img).getConstantState())) {
                    ToastUtils.showShort("请添加病例资料");
                    return;
                }
                if (tvTime.getText().toString().trim().isEmpty()) {
                    ToastUtils.showShort("请填预约住院时间");
                    return;
                }
                Message msg = Message.obtain();
                msg.obj = urlimg;
                msg.what = UP_PHOTO;
                sendHandlerMessage(msg);
                break;
            case R.id.tv_make_hos_time:
                PickerUtils.showTime(this, new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvTime.setText(content);
                    }
                });
                break;
            case R.id.iv_make_hos_one:
                mPosition = 0;
                newAddPic();
                break;
            case R.id.iv_make_hos_two:
                mPosition = 1;
                newAddPic();
                break;
            case R.id.iv_make_hos_three:
                mPosition = 2;
                newAddPic();
                break;
        }
    }

    /**
     * 拍照
     *
     * @param
     */
    private void newAddPic() {
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
     * 知乎选择照片
     */
    private void selectPhoto() {
        Matisse.from(MakeActivity.this)
                .choose(MimeType.ofImage(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, "com.vice.bloodpressure.FileProvider"))
                //最多选1张
                .maxSelectable(1)
                //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        sex = radioButton.getText().toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //拍照完成回调
                case REQUEST_CODE_CHOOSE:
                    List<String> pathList = Matisse.obtainPathResult(data);
                    if (pathList != null && 1 == pathList.size()) {
                        if (urlimg.size() < 3) {
                            urlimg.add(pathList.get(0));
                        } else {
                            urlimg.set(mPosition, pathList.get(0));
                        }
                        compress(urlimg);
                        showImages(urlimg);
                    }
                    break;
                default:
                    break;
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

    private void showImages(List<String> pathList) {
        if (pathList.size() > 0) {
            Glide.with(this).load(pathList.get(0)).into(ivOne);
        } else {
            ivOne.setImageResource(R.drawable.add_img);
        }
        if (pathList.size() > 1) {
            Glide.with(this).load(pathList.get(1)).into(ivTwo);
        } else {
            ivTwo.setImageResource(R.drawable.add_img);
        }
        if (pathList.size() > 2) {
            Glide.with(this).load(pathList.get(2)).into(ivThree);
        } else {
            ivThree.setImageResource(R.drawable.add_img);
        }
    }


    /**
     * 提交数据
     */
    private void submitContent(String pic) {
        //姓名
        String nickname = etNickname.getText().toString().trim();
        //体重
        String age = etAge.getText().toString().trim();
        //电话
        String telephone = etPhone.getText().toString().trim();
        if (cardNumber.equals("初次住院")) {
            cardNumber = "1";
        } else if (cardNumber.equals("再次住院")) {
            cardNumber = "2";
        }
        if (sex.equals("男")) {
            sex = "1";
        } else {
            sex = "2";
        }
        //疾病诊断描述
        String describe = etDiagnosis.getText().toString().trim();
        //住院目的
        String result = etStay.getText().toString().trim();
        //时间
        String intime = tvTime.getText().toString().trim();
        Map<String, Object> map = new HashMap<>();
        map.put("docid", getIntent().getStringExtra("doctorId"));
        map.put("type", cardNumber);
        map.put("name", nickname);
        map.put("age", age);
        map.put("sex", sex);
        map.put("telephone", telephone);
        map.put("describe", describe);
        map.put("result", result);
        map.put("pic", pic);
        map.put("intime", intime);
        map.put("token", user.getToken());
        XyUrl.okPostSave(XyUrl.SUBMIT_HOSPITAL, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                sendHandlerMessage(SUBMIT_SUCCESS);
            }

            @Override
            public void onError(int error, String errorMsg) {

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
                    org.json.JSONObject jsonObject = new org.json.JSONObject(value);
                    String url = jsonObject.getString("url");
                    phpList.add(url);
                    if (urlimg.size() == phpList.size()) {
                        for (int i = 0; i < phpList.size(); i++) {
                            photograph += phpList.get(i) + ",";
                        }
                        photograph = photograph.substring(0, photograph.length() - 1);
                        submitContent(photograph);
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
}