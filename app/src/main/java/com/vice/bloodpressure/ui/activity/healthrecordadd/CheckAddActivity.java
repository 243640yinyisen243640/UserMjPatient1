package com.vice.bloodpressure.ui.activity.healthrecordadd;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.lyd.baselib.widget.view.decoration.GridSpacingItemDecoration;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.AddImageAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.GifSizeFilter;
import com.vice.bloodpressure.utils.PickerUtils;
import com.vice.bloodpressure.view.popu.SlideFromBottomPopup;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import org.json.JSONException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by yicheng on 2018/5/31.
 * 添加检查记录
 */

public class CheckAddActivity extends BaseHandlerActivity implements View.OnClickListener {
    private static final String TAG = "CheckAddActivity";
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int COMPRESS_OVER = 0xa5;
    //上传图片
    private static final int UP_PHOTO = 101;
    private static final int REQUEST_CAMERA = 10086;
    private static final int REQUEST_CODE_CHOOSE = 10087;
    private TextView tvCheck;
    private EditText etCheck;
    private AddImageAdapter addImageAdapter;
    private File file;
    private String photograph = "";
    private List<String> phpList;
    //上传图片路径
    private List<String> urlimg;

    private SlideFromBottomPopup popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("记录检查");
        phpList = new ArrayList<>();
        initData();
        initViews();
        initPopu();
        setTime();
        showTvSave();
    }

    private void initPopu() {
        popup = new SlideFromBottomPopup(getPageContext());
        popup.findViewById(R.id.tv_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtils
                        .permission(PermissionConstants.STORAGE)
                        .callback(new PermissionUtils.SimpleCallback() {
                            @Override
                            public void onGranted() {
                                int selectMax = 9 - (phpList.size());
                                selectPhoto(selectMax);
                            }

                            @Override
                            public void onDenied() {
                                ToastUtils.showShort("请允许使用存储权限");
                            }
                        }).request();
            }
        });
        popup.findViewById(R.id.tv_take_photo).setOnClickListener(new View.OnClickListener() {
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

    private void initData() {
        urlimg = new ArrayList<>();
    }

    private void initViews() {
        RecyclerView rvPic = findViewById(R.id.rv_pic);
        tvCheck = findViewById(R.id.tv_enter_time_check);
        tvCheck.setOnClickListener(this);
        etCheck = findViewById(R.id.et_check_name);
        TextView btnSave = getTvSave();
        btnSave.setText("保存");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkName = etCheck.getText().toString();
                if (TextUtils.isEmpty(checkName)) {
                    ToastUtils.showShort("请填写项目名称");
                    return;
                }
                String content = tvCheck.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtils.showShort("请选择时间");
                    return;
                }
                if (urlimg.size() < 1) {
                    ToastUtils.showShort("请选择照片");
                    return;
                }
                Message msg = getHandlerMessage();
                msg.obj = urlimg;
                msg.what = UP_PHOTO;
                sendHandlerMessage(msg);
            }
        });
        addImageAdapter = new AddImageAdapter(this, new AddImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                KeyboardUtils.hideSoftInput(CheckAddActivity.this);
                popup.showPopupWindow();
            }
        });
        rvPic.setAdapter(addImageAdapter);
        rvPic.setLayoutManager(new GridLayoutManager(this, 4));
        rvPic.addItemDecoration(new GridSpacingItemDecoration(4, 25, false));

        //设置删除
        addImageAdapter.setOnItemClickListener(new AddImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                urlimg.remove(position);
                addImageAdapter.setList(urlimg);
                addImageAdapter.notifyDataSetChanged();
                //addImageAdapter.notifyItemRemoved(position);
                //addImageAdapter.notifyItemRangeChanged(position, urlimg.size());
                //ToastUtils.showShort(position);
            }
        });
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_check_add, contentLayout, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_enter_time_check:
                PickerUtils.showTimeHm(CheckAddActivity.this, new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvCheck.setText(content);
                    }
                });
                break;
        }
    }


    private void selectPhoto(int selectMax) {
        Matisse.from(CheckAddActivity.this)
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

    /**
     * 保存数据
     */
    private void saveData(String photograph) {
        String datetime = tvCheck.getText().toString().trim();
        String checkName = etCheck.getText().toString();
        HashMap<String, Object> map = new HashMap<>();
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        map.put("picurl", photograph);
        map.put("datetime", datetime);
        map.put("hydname", checkName);
        XyUrl.okPostSave(XyUrl.ADD_CHECK, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                //清除压缩文件
                FileUtils.deleteAllInDir("/storage/emulated/0/Android/data/com.vice.bloodpressure/cache/luban_disk_cache/");
                EventBusUtils.post(new EventMessage<>(ConstantParam.CHECK_RECORD_ADD));
                ToastUtils.showShort(R.string.save_ok);
                finish();
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    /**
     * 拍照
     *
     * @param
     */
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Test/" + System.currentTimeMillis() + ".jpg");
        file.getParentFile().mkdirs();
        //改变Uri
        Uri uri = FileProvider.getUriForFile(this, "com.vice.bloodpressure.FileProvider", file);
        //添加权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //拍照完成回调
                case REQUEST_CAMERA:
                    popup.dismiss();
                    String path = file.getPath();
                    urlimg.add(path);
                    compress(urlimg);
                    addImageAdapter.setList(urlimg);
                    addImageAdapter.notifyDataSetChanged();
                    break;
                //选择照片回调
                case REQUEST_CODE_CHOOSE:
                    popup.dismiss();
                    List<String> pathList = Matisse.obtainPathResult(data);
                    urlimg.addAll(pathList);
                    compress(urlimg);
                    addImageAdapter.setList(urlimg);
                    addImageAdapter.notifyDataSetChanged();
                    break;
                case CODE_RESULT_REQUEST:
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

    private void showImagesList(List<String> pathList) {
        urlimg.addAll(pathList);
        addImageAdapter.setList(urlimg);
        addImageAdapter.notifyDataSetChanged();
    }

    private void showImages(String bitmap) {
        urlimg.add(bitmap);
        addImageAdapter.setList(urlimg);
        addImageAdapter.notifyDataSetChanged();
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
                        saveData(photograph);
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
        }
    }


    private void setTime() {
        SimpleDateFormat Allformat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String allTimeString = TimeUtils.millis2String(System.currentTimeMillis(), Allformat);
        tvCheck.setText(allTimeString);
    }

}
