package com.vice.bloodpressure.ui.activity.user;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.ArrayRes;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.MyLiverFilesLvAdapter;
import com.vice.bloodpressure.adapter.MyLiverFilesLvNewAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.LiverFilesBean;
import com.vice.bloodpressure.bean.LiverFilesRefreshBean;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.MySugarLevel1Bean;
import com.vice.bloodpressure.imp.AdapterClickLiverFilesImp;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.CityPickerUtils;
import com.vice.bloodpressure.utils.DialogUtils;
import com.vice.bloodpressure.utils.PickerUtils;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.vice.bloodpressure.utils.TimeFormatUtils;
import com.vice.bloodpressure.utils.TurnsUtils;
import com.vice.bloodpressure.view.MyListView;
import com.vice.bloodpressure.view.popu.LiverFilesInputShowUtils;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  肝病档案
 * 作者: LYD
 * 创建日期: 2020/4/9 11:46
 */
public class MyLiverFilesActivity extends BaseHandlerActivity implements AdapterClickLiverFilesImp {
    private static final String TAG = "MyLiverFilesNewActivity";
    private static final int GET_LIVER_INFO = 10011;
    private static final int SAVE_DATA = 10012;
    private static final int SAVE_PCD = 10013;
    //基本信息
    @BindView(R.id.img_base_info)
    ImageView imgBaseInfo;
    @BindView(R.id.ll_base_info)
    LinearLayout llBaseInfo;
    @BindView(R.id.lv_base_info)
    MyListView lvBaseInfo;
    @BindView(R.id.el_base_info)
    ExpandableLayout elBaseInfo;
    //体格检查
    @BindView(R.id.img_body_check)
    ImageView imgBodyCheck;
    @BindView(R.id.ll_body_check)
    LinearLayout llBodyCheck;
    @BindView(R.id.lv_body_check)
    MyListView lvBodyCheck;
    @BindView(R.id.el_body_check)
    ExpandableLayout elBodyCheck;
    //实验室检查
    @BindView(R.id.img_lab_check)
    ImageView imgLabCheck;
    @BindView(R.id.ll_lab_check)
    LinearLayout llLabCheck;
    @BindView(R.id.lv_lab_check)
    MyListView lvLabCheck;
    @BindView(R.id.el_lab_check)
    ExpandableLayout elLabCheck;
    //肝病记录
    @BindView(R.id.img_liver_record)
    ImageView imgLiverRecord;
    @BindView(R.id.ll_liver_record)
    LinearLayout llLiverRecord;
    @BindView(R.id.lv_liver_record)
    MyListView lvLiverRecord;
    @BindView(R.id.el_liver_record)
    ExpandableLayout elLiverRecord;
    //轻诊断
    @BindView(R.id.img_soft_check)
    ImageView imgSoftCheck;
    @BindView(R.id.ll_soft_check)
    LinearLayout llSoftCheck;
    @BindView(R.id.lv_soft_check)
    MyListView lvSoftCheck;
    @BindView(R.id.el_soft_check)
    ExpandableLayout elSoftCheck;
    //用药史
    @BindView(R.id.img_medicine_history)
    ImageView imgMedicineHistory;
    @BindView(R.id.ll_medicine_history)
    LinearLayout llMedicineHistory;
    @BindView(R.id.lv_medicine_history)
    MyListView lvMedicineHistory;
    @BindView(R.id.el_medicine_history)
    ExpandableLayout elMedicineHistory;
    //基本信息
    private MyLiverFilesLvAdapter baseInfoAdapter;
    private List<MySugarLevel1Bean> listBaseInfo;
    //体格检查
    private MyLiverFilesLvNewAdapter bodyCheckAdapter;
    private List<MySugarLevel1Bean> listBodyCheck;
    //实验室检查
    private MyLiverFilesLvNewAdapter labCheckAdapter;
    private List<MySugarLevel1Bean> listLabCheck;
    //肝病记录
    private String liverRecordOne;
    private String liverRecordTwo;
    private String liverRecordThree;
    //轻诊断
    private String softcheck;
    //用药史
    private String medicineHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("个人档案");
        getLiverInfo();
    }

    /**
     * 获取肝病信息
     */
    private void getLiverInfo() {
        LoginBean userLogin = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", userLogin.getToken());
        XyUrl.okPost(XyUrl.GET_LIVER_FILES, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                //获取基本
                LiverFilesBean liverFilesBean = JSONObject.parseObject(value, LiverFilesBean.class);
                Message message = getHandlerMessage();
                message.what = GET_LIVER_INFO;
                message.obj = liverFilesBean;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_my_liver_files, contentLayout, false);
        return view;
    }


    /**
     * 设置
     *
     * @param data
     */
    private void setData(LiverFilesBean data) {
        //设置基本信息
        setBaseInfo(data);
        //设置体格检查
        setBodyCheck(data);
        //设置实验室检查
        setLabCheck(data);
        //设置肝病记录
        setLiverRecord(data);
        //设置轻诊断
        setSoftCheck(data);
        //设置用药史
        setMedicineHistory(data);
    }


    /**
     * 设置基本信息
     *
     * @param data
     */
    private void setBaseInfo(LiverFilesBean data) {
        List<String> listStr = new ArrayList<>();
        String nickname = data.getNickname();
        listStr.add(nickname);
        String minzu = data.getMinzu();
        listStr.add(minzu);
        String address = data.getNativeplace();
        listStr.add(address);
        String sex = data.getSex();
        listStr.add(sex);
        String culture = data.getCulture();
        listStr.add(culture);
        String drink = data.getDrink();
        listStr.add(drink);
        String birthtime = data.getBirthtime();
        listStr.add(birthtime);
        String profession = data.getProfession();
        listStr.add(profession);

        listBaseInfo = new ArrayList<>();
        String[] stringFirstLevel = Utils.getApp().getResources().getStringArray(R.array.liver_files_base_info);
        for (int i = 0; i < stringFirstLevel.length; i++) {
            MySugarLevel1Bean bean = new MySugarLevel1Bean();
            bean.setName(stringFirstLevel[i]);
            bean.setContent(listStr.get(i));
            listBaseInfo.add(bean);
        }

        baseInfoAdapter = new MyLiverFilesLvAdapter(getPageContext(), R.layout.item_health_archive, listBaseInfo, this, 0);
        lvBaseInfo.setAdapter(baseInfoAdapter);
    }

    /**
     * 设置体格检查
     *
     * @param data
     */
    private void setBodyCheck(LiverFilesBean data) {
        List<String> listStr = new ArrayList<>();
        String height = data.getHeight();
        listStr.add(height);
        String weight = data.getWeight();
        listStr.add(weight);
        //计算BMI
        if (!TextUtils.isEmpty(height) && !TextUtils.isEmpty(weight)) {
            double doubleHeight = TurnsUtils.getDouble(height, 0);
            double doubleWeight = TurnsUtils.getDouble(weight, 0);
            double doubleHeightM = doubleHeight / 100;
            double doubleBmi = doubleWeight / (doubleHeightM * doubleHeightM);
            DecimalFormat df = new DecimalFormat("0.00");
            String bmi = df.format(doubleBmi);
            listStr.add(bmi);
        } else {
            listStr.add("");
        }
        String waistline = data.getWaistline();
        listStr.add(waistline);
        String hipline = data.getHipline();
        listStr.add(hipline);
        //计算腰臀比
        if (!TextUtils.isEmpty(waistline) && !TextUtils.isEmpty(hipline)) {
            double doubleW = TurnsUtils.getDouble(waistline, 0);
            double doubleH = TurnsUtils.getDouble(hipline, 0);
            double doubleWh = doubleW / doubleH;
            DecimalFormat df = new DecimalFormat("0.00");
            String wh = df.format(doubleWh);
            listStr.add(wh);
        } else {
            listStr.add("");
        }
        String tizhi = data.getTizhi();
        listStr.add(tizhi);
        String stun = data.getStun();
        listStr.add(stun);
        String syao = data.getSyao();
        listStr.add(syao);
        String woli = data.getWoli();
        listStr.add(woli);


        listBodyCheck = new ArrayList<>();
        String[] stringFirstLevel = Utils.getApp().getResources().getStringArray(R.array.liver_files_body_check);
        String[] stringFirstLevelUnit = Utils.getApp().getResources().getStringArray(R.array.liver_files_body_check_unit);
        for (int i = 0; i < stringFirstLevel.length; i++) {
            MySugarLevel1Bean bean = new MySugarLevel1Bean();
            bean.setName(stringFirstLevel[i]);
            bean.setContent(listStr.get(i));
            //设置单位
            bean.setUtit(stringFirstLevelUnit[i]);
            listBodyCheck.add(bean);
        }

        bodyCheckAdapter = new MyLiverFilesLvNewAdapter(getPageContext(), R.layout.item_liver_files, listBodyCheck, this, 1);
        lvBodyCheck.setAdapter(bodyCheckAdapter);
    }

    /**
     * 设置实验室检查
     */
    private void setLabCheck(LiverFilesBean data) {
        List<String> listStr = new ArrayList<>();
        String alts = data.getAlts();
        listStr.add(alts);
        String bai = data.getBai();
        listStr.add(bai);
        String xuet = data.getXuet();
        listStr.add(xuet);
        String xueh = data.getXueh();
        listStr.add(xueh);
        String zong = data.getZong();
        listStr.add(zong);
        String qian = data.getQian();
        listStr.add(qian);
        String ning = data.getNing();
        listStr.add(ning);
        String xuea = data.getXuea();
        listStr.add(xuea);


        listLabCheck = new ArrayList<>();
        String[] stringFirstLevel = Utils.getApp().getResources().getStringArray(R.array.liver_files_lab_check);
        String[] stringFirstLevelUnit = Utils.getApp().getResources().getStringArray(R.array.liver_files_lab_check_unit);
        for (int i = 0; i < stringFirstLevel.length; i++) {
            MySugarLevel1Bean bean = new MySugarLevel1Bean();
            bean.setName(stringFirstLevel[i]);
            bean.setContent(listStr.get(i));
            bean.setUtit(stringFirstLevelUnit[i]);
            listLabCheck.add(bean);
        }
        labCheckAdapter = new MyLiverFilesLvNewAdapter(getPageContext(), R.layout.item_liver_files, listLabCheck, this, 2);
        lvLabCheck.setAdapter(labCheckAdapter);
    }

    /**
     * 设置肝病记录
     *
     * @param data
     */
    private void setLiverRecord(LiverFilesBean data) {
        List<String> listStr = new ArrayList<>();
        liverRecordOne = data.getTextarea0();
        liverRecordTwo = data.getTextarea1();
        liverRecordThree = data.getTextarea2();
        listStr.add("查看详情");
        listStr.add("查看详情");
        listStr.add("查看详情");

        List<MySugarLevel1Bean> listLiverRecord = new ArrayList<>();
        String[] stringFirstLevel = Utils.getApp().getResources().getStringArray(R.array.liver_files_liver_record);
        for (int i = 0; i < stringFirstLevel.length; i++) {
            MySugarLevel1Bean bean = new MySugarLevel1Bean();
            bean.setName(stringFirstLevel[i]);
            bean.setContent(listStr.get(i));
            listLiverRecord.add(bean);
        }
        //肝病记录
        MyLiverFilesLvAdapter liverRecordAdapter = new MyLiverFilesLvAdapter(getPageContext(), R.layout.item_health_archive, listLiverRecord, this, 3);
        lvLiverRecord.setAdapter(liverRecordAdapter);

    }

    /**
     * 设置轻诊断
     *
     * @param data
     */
    private void setSoftCheck(LiverFilesBean data) {
        softcheck = data.getDiagnosis();
        List<MySugarLevel1Bean> listLiverRecord = new ArrayList<>();
        MySugarLevel1Bean bean = new MySugarLevel1Bean();
        bean.setName("轻诊断");
        bean.setContent("查看详情或编辑");
        listLiverRecord.add(bean);
        //肝病记录
        MyLiverFilesLvAdapter liverRecordAdapter = new MyLiverFilesLvAdapter(getPageContext(), R.layout.item_health_archive, listLiverRecord, this, 4);
        lvSoftCheck.setAdapter(liverRecordAdapter);
    }

    /**
     * 设置用药史
     *
     * @param data
     */
    private void setMedicineHistory(LiverFilesBean data) {
        medicineHistory = data.getMedchinehis();
        List<MySugarLevel1Bean> listLiverRecord = new ArrayList<>();
        MySugarLevel1Bean bean = new MySugarLevel1Bean();
        bean.setName("用药史");
        bean.setContent("查看详情或编辑");
        listLiverRecord.add(bean);
        //肝病记录
        MyLiverFilesLvAdapter liverRecordAdapter = new MyLiverFilesLvAdapter(getPageContext(), R.layout.item_health_archive, listLiverRecord, this, 5);
        lvMedicineHistory.setAdapter(liverRecordAdapter);
    }

    @OnClick({R.id.ll_base_info, R.id.ll_body_check, R.id.ll_lab_check, R.id.ll_liver_record, R.id.ll_soft_check, R.id.ll_medicine_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_base_info:
                toToggle(elBaseInfo, imgBaseInfo);
                break;
            case R.id.ll_body_check:
                toToggle(elBodyCheck, imgBodyCheck);
                break;
            case R.id.ll_lab_check:
                toToggle(elLabCheck, imgLabCheck);
                break;
            case R.id.ll_liver_record:
                toToggle(elLiverRecord, imgLiverRecord);
                break;
            case R.id.ll_soft_check:
                toToggle(elSoftCheck, imgSoftCheck);
                break;
            case R.id.ll_medicine_history:
                toToggle(elMedicineHistory, imgMedicineHistory);
                break;
        }
    }


    /**
     * 切换
     *
     * @param el
     * @param img
     */
    private void toToggle(ExpandableLayout el, ImageView img) {
        el.toggle();
        Bitmap bitmap = ImageUtils.getBitmap(R.drawable.right_arrow);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (el.isExpanded()) {
            Bitmap resizedBitmap = ImageUtils.rotate(bitmap, 90, width, height);
            img.setImageBitmap(resizedBitmap);
        } else {
            Bitmap resizedBitmap = ImageUtils.rotate(bitmap, 0, width, height);
            img.setImageBitmap(resizedBitmap);
        }
    }

    @Override
    public void onAdapterClick(View view, int position, int type) {
        switch (type) {
            case 0:
                resetBaseInfo(position, type);
                break;
            case 1:
                resetBodyCheck(position, type);
                break;
            case 2:
                resetLabCheck(position, type);
                break;
            case 3:
                resetLiverRecord(position, type);
                break;
            case 4:
                resetSoftCheck(position, type);
                break;
            case 5:
                resetMedicineHistory(position, type);
                break;
        }
    }


    /**
     * 重新设置基本信息
     *
     * @param position
     */
    private void resetBaseInfo(int position, int type) {
        switch (position) {
            case 0:
                showEditDialog(position, type, "姓名", "请输入姓名", "nickname");
                break;
            case 1:
                showEditDialog(position, type, "民族", "请输入民族", "minzu");
                break;
            case 2:
                showCityPickerView(position, type);
                break;
            case 3:
                showBottomDialog(position, type, "sex", R.array.liver_files_base_info_sex);
                break;
            case 4:
                showBottomDialog(position, type, "culture", R.array.liver_files_base_info_culture);
                break;
            case 5:
                showBottomDialog(position, type, "drink", R.array.liver_files_base_info_is_or_not);
                break;
            case 6:
                showBottomTimeDialog(position, type, "birthtime");
                break;
            case 7:
                showBottomDialog(position, type, "profession", R.array.liver_files_base_info_profession);
                break;
        }
    }


    /**
     * 重新设置体格检查信息
     *
     * @param position
     */
    private void resetBodyCheck(int position, int type) {
        switch (position) {
            case 0:
                showEditDialog(position, type, "身高", "请输入身高", "height");
                break;
            case 1:
                showEditDialog(position, type, "体重", "请输入体重", "weight");
                break;
            //BMI
            case 2:
                break;
            case 3:
                showEditDialog(position, type, "腰围", "请输入腰围", "waistline");
                break;
            case 4:
                showEditDialog(position, type, "臀围", "请输入臀围", "hipline");
                break;
            //腰臀比
            case 5:
                break;
            //体脂百分比
            case 6:
                showEditDialog(position, type, "体脂百分比", "请输入体脂百分比", "tizhi");
                break;
            case 7:
                showEditDialog(position, type, "上臀围", "请输入上臀围", "stun");
                break;
            case 8:
                showEditDialog(position, type, "上腰肌围", "请输入上腰肌围", "syao");
                break;
            case 9:
                showEditDialog(position, type, "握力", "请输入握力", "woli");
                break;
        }
    }


    /**
     * 重新设置实验室检查
     *
     * @param position
     * @param type
     */
    private void resetLabCheck(int position, int type) {
        switch (position) {
            case 0:
                showEditDialog(position, type, "ALT", "请输入ALT", "alts");
                break;
            case 1:
                showEditDialog(position, type, "白蛋白", "请输入白蛋白", "bai");
                break;
            case 2:
                showEditDialog(position, type, "血糖", "请输入血糖", "xuet");
                break;
            case 3:
                showEditDialog(position, type, "血红蛋白", "请输入血红蛋白", "xueh");
                break;
            case 4:
                showEditDialog(position, type, "总胆红素", "请输入总胆红素", "zong");
                break;
            case 5:
                showEditDialog(position, type, "前白蛋白", "请输入前白蛋白", "qian");
                break;
            case 6:
                showEditDialog(position, type, "凝血酶原活力度", "请输入凝血酶原活力度", "ning");
                break;
            case 7:
                showEditDialog(position, type, "血氨", "请输入血氨", "xuea");
                break;
        }
    }

    /**
     * 重新设置肝病记录
     *
     * @param position
     * @param type
     */
    private void resetLiverRecord(int position, int type) {
        switch (position) {
            case 0:
                showMultiLineEditDialog(position, type, "现在疾病情况", liverRecordOne, "textarea0");
                break;
            case 1:
                showMultiLineEditDialog(position, type, "既往疾病情况", liverRecordTwo, "textarea1");
                break;
            case 2:
                showMultiLineEditDialog(position, type, "膳食史", liverRecordThree, "textarea2");
                break;
        }
    }


    /**
     * 重置轻诊断
     *
     * @param position
     * @param type
     */
    private void resetSoftCheck(int position, int type) {
        showMultiLineEditDialog(position, type, "轻诊断", softcheck, "diagnosis");
    }

    /**
     * 重新设置用药史
     *
     * @param position
     * @param type
     */
    private void resetMedicineHistory(int position, int type) {
        showMultiLineEditDialog(position, type, "用药史", medicineHistory, "medchinehis");
    }

    /**
     * 展示多行
     *
     * @param position
     * @param type
     * @param title
     * @param content
     * @param postKey
     */
    private void showMultiLineEditDialog(int position, int type, String title, String content, String postKey) {
        LiverFilesInputShowUtils.showPopup(getPageContext(), title, content, new LiverFilesInputShowUtils.DialogInputCallBack() {
            @Override
            public void execEvent(String content) {
                toDoSave(position, type, postKey, content);
            }
        });
    }


    /**
     * 底部时间选择
     *
     * @param position
     * @param type
     * @param birthtime
     */
    private void showBottomTimeDialog(int position, int type, String birthtime) {
        PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
            @Override
            public void execEvent(String content) {
                long timeMs = TimeUtils.string2Millis(content, TimeFormatUtils.getDefaultFormat());
                long timeS = timeMs / 1000;
                toDoSaveBottom(position, type, birthtime, timeS + "", content);
            }
        });
    }

    /**
     * 显示底部选择
     *
     * @param position
     * @param type
     */
    private void showBottomDialog(int position, int type, String postKey, @ArrayRes int resId) {
        String[] sexStr = getResources().getStringArray(resId);
        List<String> listStr = Arrays.asList(sexStr);
        PickerUtils.showOptionPosition(getPageContext(), new PickerUtils.PositionCallBack() {
            @Override
            public void execEvent(String content, int selectPosition) {
                toDoSaveBottom(position, type, postKey, selectPosition + 1 + "", content);
            }
        }, listStr);
    }

    /**
     * 展示省市县
     */
    private void showCityPickerView(int position, int type) {
        CityPickerUtils.show(getPageContext(), new CityPickerUtils.CityPickerCallBack() {
            @Override
            public void execEvent(String pName, String pId, String cName, String cId, String dName, String did) {
                StringBuilder sb = new StringBuilder();
                sb.append(pName);
                sb.append(cName);
                sb.append(dName);
                toDoSavePCD("jbprov", pId);
                toDoSavePCD("jbcity", cId);
                toDoSavePCD("jbdist", did);
                toDoSave(position, type, "nativeplace", sb.toString());
            }
        });
    }

    /**
     * 展示编辑弹窗
     *
     * @param title
     * @param hint
     * @param postKey
     */
    private void showEditDialog(int position, int type, String title, String hint, String postKey) {
        DialogUtils.getInstance().showEditTextDialog(getPageContext(), title, hint, text1 -> {
            toDoSave(position, type, postKey, text1);
        });
    }


    /**
     * 保存修改
     *
     * @param postKey
     * @param postValue
     */
    private void toDoSave(int position, int type, String postKey, String postValue) {
        Map<String, Object> map = new HashMap<>();
        map.put("fieldname", postKey);
        map.put("fieldvalue", postValue);
        XyUrl.okPostSave(XyUrl.EDIT_LIVER_FILE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String msg) {
                LiverFilesRefreshBean bean = new LiverFilesRefreshBean(type, position, postValue);
                Message handlerMessage = getHandlerMessage();
                handlerMessage.what = SAVE_DATA;
                handlerMessage.obj = bean;
                sendHandlerMessage(handlerMessage);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    private void toDoSaveBottom(int position, int type, String postKey, String postValue, String content) {
        Map<String, Object> map = new HashMap<>();
        map.put("fieldname", postKey);
        map.put("fieldvalue", postValue);
        XyUrl.okPostSave(XyUrl.EDIT_LIVER_FILE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String msg) {
                LiverFilesRefreshBean bean = new LiverFilesRefreshBean(type, position, content);
                Message handlerMessage = getHandlerMessage();
                handlerMessage.what = SAVE_DATA;
                handlerMessage.obj = bean;
                sendHandlerMessage(handlerMessage);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    /**
     * 保存省市县编码
     *
     * @param postKey
     * @param postValue
     */
    private void toDoSavePCD(String postKey, String postValue) {
        Map<String, Object> map = new HashMap<>();
        map.put("fieldname", postKey);
        map.put("fieldvalue", postValue);
        XyUrl.okPostSave(XyUrl.EDIT_LIVER_FILE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String msg) {
                Message handlerMessage = getHandlerMessage();
                handlerMessage.what = SAVE_PCD;
                sendHandlerMessage(handlerMessage);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_LIVER_INFO:
                LiverFilesBean data = (LiverFilesBean) msg.obj;
                setData(data);
                break;
            case SAVE_DATA:
                ToastUtils.showShort("保存成功");
                LiverFilesRefreshBean refreshBean = (LiverFilesRefreshBean) msg.obj;
                toRefreshData(refreshBean);
                break;
        }
    }


    /**
     * 刷新页面
     *
     * @param refreshBean
     */
    private void toRefreshData(LiverFilesRefreshBean refreshBean) {
        int type = refreshBean.getType();
        int position = refreshBean.getPosition();
        String value = refreshBean.getValue();
        switch (type) {
            case 0:
                listBaseInfo.get(position).setContent(value);
                baseInfoAdapter.notifyDataSetChanged();
                break;
            case 1:
                listBodyCheck.get(position).setContent(value);
                bodyCheckAdapter.notifyDataSetChanged();
                break;
            case 2:
                listLabCheck.get(position).setContent(value);
                labCheckAdapter.notifyDataSetChanged();
                break;
            case 3:
                switch (position) {
                    case 0:
                        liverRecordOne = value;
                        break;
                    case 1:
                        liverRecordTwo = value;
                        break;
                    case 2:
                        liverRecordThree = value;
                        break;
                }
                break;
            case 4:
                softcheck = value;
                break;
            case 5:
                medicineHistory = value;
                break;
        }
    }
}
