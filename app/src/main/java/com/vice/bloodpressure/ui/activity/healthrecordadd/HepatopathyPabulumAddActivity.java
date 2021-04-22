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

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.lyd.baselib.widget.view.decoration.GridSpacingItemDecoration;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.AddImageAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.Service;
import com.lyd.baselib.utils.GifSizeFilter;
import com.vice.bloodpressure.view.popu.SlideFromBottomPopup;
import com.wei.android.lib.colorview.view.ColorEditText;
import com.wei.android.lib.colorview.view.ColorTextView;
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

/**
 * 描述:  肝病营养添加
 * 作者: LYD
 * 创建日期: 2019/9/5 17:44
 */
public class HepatopathyPabulumAddActivity extends BaseHandlerActivity {
    private static final String TAG = "HepatopathyPabulumAddActivity";
    private static final int REQUEST_CAMERA = 10010;
    private static final int REQUEST_CODE_CHOOSE = 10086;
    private static final int COMPRESS_OVER = 1008611;
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
    //图片处理
    @BindView(R.id.rv_pic_add)
    RecyclerView rvPicAdd;
    //新增四个开始
    @BindView(R.id.et_blood_ammonia)
    EditText etBloodAmmonia;
    @BindView(R.id.et_forward)
    EditText etForward;
    @BindView(R.id.et_blood_red)
    EditText etBloodRed;
    @BindView(R.id.et_blood_clotting)
    EditText etBloodClotting;
    //新增四个结束
    private File file;
    //图片处理
    private SlideFromBottomPopup popup;
    private AddImageAdapter adapter;
    private List<String> list;
    private QMUITipDialog tipDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTag();
        setPageTitle();
        initPopu();
        setPic();
    }

    /**
     * 初始化Tag值
     */
    private void setTag() {
        tvLeft.setTag(0);
        tvCenter.setTag(0);
        tvRight.setTag(0);
        tvLeftSecond.setTag(0);
        tvCenterSecond.setTag(0);
        tvRightSecond.setTag(0);
    }

    /**
     * 设置标题
     */
    private void setPageTitle() {
        setTitle("记录肝病营养指标");
        showTvSave();
        getTvSave().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSavePic();
            }
        });
    }

    /**
     * 初始化底部弹出拍照和选择相册
     */
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
                                int selectMax = 9 - (list.size());
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


    /**
     * 设置图片
     */
    private void setPic() {
        list = new ArrayList<>();
        adapter = new AddImageAdapter(this, new AddImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                popup.showPopupWindow();
            }
        });
        rvPicAdd.setAdapter(adapter);
        rvPicAdd.setLayoutManager(new GridLayoutManager(this, 4));
        rvPicAdd.addItemDecoration(new GridSpacingItemDecoration(4, 5, false));

        //设置删除
        adapter.setOnItemClickListener(new AddImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                list.remove(position);
                adapter.setList(list);
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 上传图片
     */
    private void toSavePic() {
        String alt = etAlt.getText().toString().trim();
        String ast = etAst.getText().toString().trim();
        String alp = etAlp.getText().toString().trim();

        String ggt = etGgt.getText().toString().trim();
        String tpTotal = etTpTotal.getText().toString().trim();
        String tpWhite = etTpWhite.getText().toString().trim();

        String tpEgg = etTpEgg.getText().toString().trim();
        String ag = etAg.getText().toString().trim();
        String bilTotal = etBilTotal.getText().toString().trim();

        String bilIndirect = etBilIndirect.getText().toString().trim();
        String bilDirect = etBilDirect.getText().toString().trim();
        String afp = etAfp.getText().toString().trim();

        String bloodAmmonia = etBloodAmmonia.getText().toString().trim();
        String forward = etForward.getText().toString().trim();
        String bloodRed = etBloodRed.getText().toString().trim();
        String bloodClotting = etBloodClotting.getText().toString().trim();

        String edema = "0";
        int leftTag = (int) tvLeft.getTag();
        if (1 == leftTag) {
            edema = "1";
        }
        int centerTag = (int) tvCenter.getTag();
        if (1 == centerTag) {
            edema = "2";
        }
        int rightTag = (int) tvRight.getTag();
        if (1 == rightTag) {
            edema = "3";
        }
        String nutrition = "0";
        int leftSecondTag = (int) tvLeftSecond.getTag();
        if (1 == leftSecondTag) {
            nutrition = "1";
        }
        int centerSecondTag = (int) tvCenterSecond.getTag();
        if (1 == centerSecondTag) {
            nutrition = "2";
        }
        int rightSecondTag = (int) tvRightSecond.getTag();
        if (1 == rightSecondTag) {
            nutrition = "3";
        }
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        HashMap<String, Object> map = new HashMap<>();
        map.put("access_token", user.getToken());
        map.put("transaminase", alt);
        map.put("aspertate", ast);
        map.put("phosphatase", alp);
        map.put("transpeptidase", ggt);
        map.put("totalprotein", tpTotal);
        map.put("albumin", tpWhite);
        map.put("globulin", tpEgg);
        map.put("albuminvs", ag);
        map.put("bravery", bilTotal);
        map.put("bilered", bilIndirect);
        map.put("bilirubin", bilDirect);
        map.put("afp", afp);
        map.put("edema", edema);
        map.put("nutrition", nutrition);
        map.put("blood_ammonia", bloodAmmonia);
        map.put("prealbumin", forward);
        map.put("haemoglobin", bloodRed);
        map.put("prothrombin", bloodClotting);
        //判断最少填写一个
        if (TextUtils.isEmpty(alt) && TextUtils.isEmpty(ast) && TextUtils.isEmpty(alp)
                && TextUtils.isEmpty(ggt) && TextUtils.isEmpty(tpTotal) && TextUtils.isEmpty(tpWhite) &&
                TextUtils.isEmpty(tpEgg) && TextUtils.isEmpty(ag) && TextUtils.isEmpty(bilTotal)
                && TextUtils.isEmpty(bilIndirect) && TextUtils.isEmpty(bilDirect) && TextUtils.isEmpty(afp) &&
                TextUtils.isEmpty(bloodAmmonia) && TextUtils.isEmpty(bloodClotting) && TextUtils.isEmpty(bloodRed) &&
                TextUtils.isEmpty(forward)) {
            ToastUtils.showShort("请至少填写一个!");
            return;
        }
        tipDialog = new QMUITipDialog.Builder(getPageContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
        tipDialog.show();
        RxHttpUtils.createApi(Service.class)
                .uploadFiles(getMultipartPart("liverimg[]", map, list))
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


    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_hepatopathy_pabulum_add, contentLayout, false);
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case COMPRESS_OVER:
                list = (List<String>) msg.obj;
                break;
        }
    }

    @OnClick({R.id.tv_left, R.id.tv_center, R.id.tv_right, R.id.tv_left_second, R.id.tv_center_second, R.id.tv_right_second})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                setTvChecked(tvLeft);
                setTvUnChecked(tvCenter);
                setTvUnChecked(tvRight);
                break;
            case R.id.tv_center:
                setTvUnChecked(tvLeft);
                setTvChecked(tvCenter);
                setTvUnChecked(tvRight);
                break;
            case R.id.tv_right:
                setTvUnChecked(tvLeft);
                setTvUnChecked(tvCenter);
                setTvChecked(tvRight);
                break;
            case R.id.tv_left_second:
                setTvChecked(tvLeftSecond);
                setTvUnChecked(tvCenterSecond);
                setTvUnChecked(tvRightSecond);
                break;
            case R.id.tv_center_second:
                setTvUnChecked(tvLeftSecond);
                setTvChecked(tvCenterSecond);
                setTvUnChecked(tvRightSecond);
                break;
            case R.id.tv_right_second:
                setTvUnChecked(tvLeftSecond);
                setTvUnChecked(tvCenterSecond);
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
        tv.setTag(1);
    }

    /**
     * 设置未选中
     *
     * @param tv
     */
    private void setTvUnChecked(ColorTextView tv) {
        tv.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.gray_text));
        tv.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.white));
        tv.getColorHelper().setBorderColorNormal(ColorUtils.getColor(R.color.gray_text));
        tv.setTag(0);
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
     * 知乎选择照片
     */
    private void selectPhoto(int selectMax) {
        Matisse.from(HepatopathyPabulumAddActivity.this)
                .choose(MimeType.ofImage(), false)
                .countable(true)
                .maxSelectable(selectMax)//最多选一张
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
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
                        adapter.setList(list);
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case REQUEST_CODE_CHOOSE:
                    popup.dismiss();
                    List<String> pathList = Matisse.obtainPathResult(data);
                    list.addAll(pathList);
                    compress(list);
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
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
