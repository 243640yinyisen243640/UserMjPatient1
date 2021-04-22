package com.vice.bloodpressure.ui.activity.setting;

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

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.lyd.baselib.widget.view.decoration.GridSpacingItemDecoration;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.AddImageOnlyOneAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.Service;
import com.lyd.baselib.utils.GifSizeFilter;
import com.vice.bloodpressure.view.popu.SlideFromBottomPopup;
import com.wei.android.lib.colorview.view.ColorButton;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class FeedBackActivity extends BaseHandlerActivity {
    private static final int REQUEST_CODE_CHOOSE = 10010;
    private static final int REQUEST_CAMERA = 10011;
    private static final int COMPRESS_OVER = 10012;
    @BindView(R.id.et_question)
    EditText etQuestion;
    @BindView(R.id.rv_pic)
    RecyclerView rvPic;
    @BindView(R.id.bt_submit)
    ColorButton btSubmit;

    private QMUITipDialog tipDialog;

    private AddImageOnlyOneAdapter addImageAdapter;
    private SlideFromBottomPopup popup;
    //上传图片路径
    private File file;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("问题反馈");
        initPopup();
        initRv();
    }

    private void initPopup() {
        popup = new SlideFromBottomPopup(getPageContext());
        popup.findViewById(R.id.tv_album).setOnClickListener(new View.OnClickListener() {
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


    private void initRv() {
        list = new ArrayList<>();
        addImageAdapter = new AddImageOnlyOneAdapter(this, new AddImageOnlyOneAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                KeyboardUtils.hideSoftInput(FeedBackActivity.this);
                popup.showPopupWindow();
            }
        });
        rvPic.setAdapter(addImageAdapter);
        rvPic.setLayoutManager(new GridLayoutManager(this, 3));
        rvPic.addItemDecoration(new GridSpacingItemDecoration(3, 25, false));
        //设置删除
        addImageAdapter.setOnItemClickListener(new AddImageOnlyOneAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                list.remove(position);
                addImageAdapter.setList(list);
                addImageAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_feed_back, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick(R.id.bt_submit)
    public void onViewClicked() {
        checkSubmit();
    }

    private void checkSubmit() {
        String question = etQuestion.getText().toString().trim();
        if (TextUtils.isEmpty(question)) {
            ToastUtils.showShort("问题描述不能为空");
            return;
        }
        toDoSubmit(question);


    }


    private void toDoSubmit(String question) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("content", question);
        tipDialog = new QMUITipDialog.Builder(getPageContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
        tipDialog.show();
        RxHttpUtils.createApi(Service.class)
                .addFeedBack(getMultipartPart("pic", map, list))
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<String>() {
                    @Override
                    protected void onError(String errorMsg) {
                    }

                    @Override
                    protected void onSuccess(String string) {
                        tipDialog.dismiss();
                        //清除压缩文件
                        FileUtils.deleteAllInDir("/storage/emulated/0/Android/data/com.vice.bloodpressure/cache/luban_disk_cache/");
                        EventBusUtils.post(new EventMessage<>(ConstantParam.HEPATOPATHY_PABULUM_ADD));
                        ToastUtils.showShort("获取成功");
                        finish();
                    }
                });
    }

    /**
     * 选择照片
     */
    private void selectPhoto() {
        Matisse.from(FeedBackActivity.this)
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


    /**
     * 拍照和选择相册的回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA:
                    popup.dismiss();
                    if (file != null) {
                        list.add(file.getPath());
                        compress(list);
                        addImageAdapter.setList(list);
                        addImageAdapter.notifyDataSetChanged();
                    }
                    break;
                case REQUEST_CODE_CHOOSE:
                    popup.dismiss();
                    List<String> pathList = Matisse.obtainPathResult(data);
                    list.addAll(pathList);
                    compress(list);
                    addImageAdapter.setList(list);
                    addImageAdapter.notifyDataSetChanged();
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


    /**
     * 可以根据需求自行修改(这里只是简单demo示例)
     *
     * @param fileName  后台协定的接受图片的name（没特殊要求就可以随便写）
     * @param paramsMap 普通参数 图文混合参数
     * @param filePaths 图片路径
     * @return List<MultipartBody.Part>
     */
    private List<MultipartBody.Part> getMultipartPart(String fileName, Map<String, Object> paramsMap,
                                                      List<String> filePaths) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (null != paramsMap) {
            for (String key : paramsMap.keySet()) {
                builder.addFormDataPart(key, (String) paramsMap.get(key));
            }
        }
        for (int i = 0; i < filePaths.size(); i++) {
            File file = new File(filePaths.get(i));
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart(fileName, file.getName(), imageBody);
        }
        return builder.build().parts();
    }
}