package com.vice.bloodpressure.ui.activity.circle;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.RecommendAdapter;
import com.vice.bloodpressure.base.activity.BaseActivity;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.vice.bloodpressure.utils.TimeFormatUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WriteRecommendActivity extends BaseActivity implements View.OnClickListener {

    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int OUTPUT_X = 300;
    private static final int OUTPUT_Y = 300;
    private static final int UP_PHOTO = 101;// 上传图片
    Uri newUri;
    private View layout;
    private RecyclerView rcy_write_recommend_image;
    private RecommendAdapter recommendAdapter;
    private List<Bitmap> ml;
    private Bitmap bitmap;//默认图片id：2131756091
    private PopupWindow pw;
    private RelativeLayout rlCamera, rlPhoto, rlHeadCancel;
    private WriteRecommendActivity activity;
    private String photograph = "";
    private EditText et_recommend_share;
    private List<String> urlimg;//上传图片路径
    private List<String> phpList;
    private Uri imageUri;//拍照所得到的图像的保存路径
    private String fileName;//当前用户拍照或者从相册选择的照片的文件名
    private File outputImage;
    private Uri cropImageUri;
    private File fileCropUri;//裁剪的照片
    private LoginBean user;
    private Map<String, Object> map;
    private ProgressDialog dialog;

    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //showLanguage();
        setTitle("写圈子");
        activity = this;
        user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        ml = new ArrayList<>();
        phpList = new ArrayList<>();
        urlimg = new ArrayList<>();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.add_img);
        ml.add(bitmap);


        et_recommend_share = findViewById(R.id.et_recommend_share);
        rcy_write_recommend_image = findViewById(R.id.rcy_write_recommend_image);
        recommendAdapter = new RecommendAdapter(WriteRecommendActivity.this, ml);
        //纵向线性布局
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        rcy_write_recommend_image.setLayoutManager(layoutManager);
        rcy_write_recommend_image.setAdapter(recommendAdapter);

        recommendAdapter.setOnItemClickListener(new RecommendAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == ml.size() - 1) {
                    if (urlimg.size() >= 9) {
                        ToastUtils.showShort("最多发9张图片亲");
                    } else {
                        createHeadPopupWindow();
                    }
                }
            }
        });
    }

    //    /**
    //     * 发布朋友圈
    //     */
    //    private void release() {
    //        HDUrl.okUploadRecommend(HDUrl.RELEASE, urlimg, user.getUserid(), et_recommend_share.getText().toString().trim(), new OkHttpCallBack<String>() {
    //            @Override
    //            public void onSuccess(String value) {
    //                if (value.equals("获取成功")) {
    //                    Intent intent = new Intent();
    //                    setResult(2, intent);
    //                    showDialog(false);
    //                    finish();
    //                    ToastUtils.showShort("发布成功");
    //                }
    //            }
    //
    //            @Override
    //            public void onError(int error, String errorMsg) {
    //
    //            }
    //        });
    //
    //
    //    }

    @Override
    protected View addContentLayout() {
        layout = getLayoutInflater().inflate(R.layout.activity_write_recommend_send, contentLayout, false);
        return layout;
    }

    /**
     * 拍照/相册/取消
     */
    private void createHeadPopupWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_head, null);
        pw = new PopupWindow(contentView,
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setOutsideTouchable(true);
        pw.setContentView(contentView);

        rlHeadCancel = (RelativeLayout) contentView.findViewById(R.id.rl_head_cancel);
        rlCamera = (RelativeLayout) contentView.findViewById(R.id.rl_head_camera);
        rlPhoto = (RelativeLayout) contentView.findViewById(R.id.rl_head_photo);
        rlHeadCancel.setOnClickListener(activity);
        rlCamera.setOnClickListener(activity);
        rlPhoto.setOnClickListener(activity);

        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_make_hospital, null);
        pw.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_head_camera:
                takePhoto();
                pw.dismiss();
                break;
            case R.id.rl_head_photo:
                autoObtainStoragePermission();
                pw.dismiss();
                break;
            case R.id.rl_head_cancel:
                if (pw.isShowing()) {
                    pw.dismiss();
                }
        }
    }

    /**
     * 自动获取sdk权限
     */
    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            //PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
        }
    }

    /**
     * 拍照
     *
     * @param
     */
    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ToastUtils.showShort("您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                fileName = "health" + TimeFormatUtils.getTime();
                //                //创建一个File对象，用于存放拍照所得到的照片
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                outputImage = new File(path, this.fileName + ".jpg");
                imageUri = Uri.fromFile(outputImage);
                //通过FileProvider创建一个content类型的Uri
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    imageUri = FileProvider.getUriForFile(WriteRecommendActivity.this, "com.vice.bloodpressure.FileProvider", outputImage);
                }
                //PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                ToastUtils.showShort("设备没有SD卡！");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            //调用系统相机申请拍照权限回调
            case CAMERA_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        fileName = "health" + TimeFormatUtils.getTime();
                        //                //创建一个File对象，用于存放拍照所得到的照片
                        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                        outputImage = new File(path, this.fileName + ".jpg");
                        imageUri = Uri.fromFile(outputImage);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            imageUri = FileProvider.getUriForFile(WriteRecommendActivity.this, "com.vice.bloodpressure.FileProvider", outputImage);//通过FileProvider创建一个content类型的Uri
                        //PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        ToastUtils.showShort("设备没有SD卡！");
                    }
                } else {

                    ToastUtils.showShort("请允许打开相机！！");
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            fileCropUri = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
            String paths;
            Bitmap bitmap = null;
            BitmapFactory.Options op;
            switch (requestCode) {
                //拍照完成回调
                case CODE_CAMERA_REQUEST:
                    cropImageUri = Uri.fromFile(fileCropUri);
                    //                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    String a = outputImage.getAbsolutePath();
                    urlimg.add(a);
                    op = new BitmapFactory.Options();
                    op.inSampleSize = 5;
                    op.inJustDecodeBounds = false;
                    bitmap = BitmapFactory.decodeFile(a, op);
                    if (bitmap != null) {
                        showImages(bitmap);
                    }
                    break;
                //访问相册完成回调
                case CODE_GALLERY_REQUEST:
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        //newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            newUri = FileProvider.getUriForFile(this, "com.vice.bloodpressure.FileProvider", new File(newUri.getPath()));
                        }
                        //                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                        //                        String a = newUri.getPath().substring(0, newUri.getPath().lastIndexOf(".")) + "/"+System.currentTimeMillis()+".jpg";
                        //                        File file = new File(a);
                        //                        cropImageUri = Uri.fromFile(file);

                        //paths = PhotoUtils.getPath(this, data.getData());
                        paths = "";
                        urlimg.add(paths);
                        //                        Bitmap bitmap = PhotoUtils.getBitmapFromUri(newUri, this);
                        op = new BitmapFactory.Options();
                        op.inSampleSize = 5;
                        op.inJustDecodeBounds = false;
                        bitmap = BitmapFactory.decodeFile(paths, op);
                        if (bitmap != null) {
                            showImages(bitmap);
                        }

                    } else {
                        ToastUtils.showShort("设备没有SD卡！");
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    //                    String a = cropImageUri.getPath();
                    //                    urlimg.add(a);
                    //                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    //                    if (bitmap != null) {
                    //                        showImages(bitmap);
                    //                    }
                    break;
                default:
            }
        }
    }

    private void showImages(Bitmap bitmap) {
        ml.add(0, bitmap);
        recommendAdapter.notifyDataSetChanged();
        rcy_write_recommend_image.getLayoutManager().scrollToPosition(0);
    }
}
