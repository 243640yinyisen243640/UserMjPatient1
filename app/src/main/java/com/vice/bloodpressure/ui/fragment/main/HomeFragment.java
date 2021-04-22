package com.vice.bloodpressure.ui.fragment.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionOwner;
import com.gyf.immersionbar.components.SimpleImmersionProxy;
import com.imuxuan.floatingview.FloatingMagnetView;
import com.imuxuan.floatingview.FloatingView;
import com.imuxuan.floatingview.MagnetViewListener;
import com.lihang.ShadowLayout;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.lyd.libsteps.StepUtil;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.HomeBloodSugarAdapter;
import com.vice.bloodpressure.adapter.HomeEightModuleAdapter;
import com.vice.bloodpressure.adapter.HomeRecycleAdapter;
import com.vice.bloodpressure.adapter.HomeTwoGoodsAdapter;
import com.vice.bloodpressure.adapter.ImageAdapter;
import com.vice.bloodpressure.base.activity.BaseWebViewActivity;
import com.vice.bloodpressure.base.fragment.BaseEventBusFragment;
import com.vice.bloodpressure.bean.BannerBean;
import com.vice.bloodpressure.bean.DietPlanAddSuccessBean;
import com.vice.bloodpressure.bean.GoodsRecommendBean;
import com.vice.bloodpressure.bean.HomeBloodSugarListBean;
import com.vice.bloodpressure.bean.HomeSportBean;
import com.vice.bloodpressure.bean.HomeSportChangeBean;
import com.vice.bloodpressure.bean.IndexDietBean;
import com.vice.bloodpressure.bean.IndexEducationBean;
import com.vice.bloodpressure.bean.LabelBean;
import com.vice.bloodpressure.bean.NewsListBean;
import com.vice.bloodpressure.bean.ShopTitleBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.food.FoodHomeActivity;
import com.vice.bloodpressure.ui.activity.healthrecordlist.HealthActivity;
import com.vice.bloodpressure.ui.activity.home.LittleToolsListActivity;
import com.vice.bloodpressure.ui.activity.medicinestore.MedicineStoreMainActivity;
import com.vice.bloodpressure.ui.activity.mydevice.ScanActivity;
import com.vice.bloodpressure.ui.activity.smartanalyse.AnalyseListActivity;
import com.vice.bloodpressure.ui.activity.smartdiet.DietPlanDetailActivity;
import com.vice.bloodpressure.ui.activity.smartdiet.DietPlanMainActivity;
import com.vice.bloodpressure.ui.activity.smartdiet.DietPlanQuestionActivity;
import com.vice.bloodpressure.ui.activity.smartdiet.DietPlanResultActivity;
import com.vice.bloodpressure.ui.activity.smarteducation.SmartEducationClassDetailAudioActivity;
import com.vice.bloodpressure.ui.activity.smarteducation.SmartEducationClassDetailTextActivity;
import com.vice.bloodpressure.ui.activity.smarteducation.SmartEducationClassDetailVideoActivity;
import com.vice.bloodpressure.ui.activity.smarteducation.SmartEducationMainActivity;
import com.vice.bloodpressure.ui.activity.smartmakepolicy.MakePolicyActivity;
import com.vice.bloodpressure.ui.activity.smartsearch.SmartSearchHomeActivity;
import com.vice.bloodpressure.ui.activity.sport.HomeSportListActivity;
import com.vice.bloodpressure.ui.activity.sport.HomeSportQuestionActivity;
import com.vice.bloodpressure.ui.activity.sport.SportTypeManualAddActivity;
import com.vice.bloodpressure.ui.activity.sport.SportTypeVideoActivity;
import com.vice.bloodpressure.ui.activity.sysmsg.SystemMsgListActivity;
import com.vice.bloodpressure.ui.activity.user.WarningRemindActivity;
import com.vice.bloodpressure.ui.fragment.other.BmiFragment;
import com.vice.bloodpressure.ui.fragment.other.XueTangFragment;
import com.vice.bloodpressure.ui.fragment.other.XueYaFragment;
import com.vice.bloodpressure.utils.BadgeUtils;
import com.vice.bloodpressure.utils.LoadingDialogUtils;
import com.vice.bloodpressure.utils.MyFragmentStatePagerAdapter;
import com.vice.bloodpressure.utils.PickerUtils;
import com.vice.bloodpressure.utils.RvUtils;
import com.vice.bloodpressure.utils.TurnsUtils;
import com.vice.bloodpressure.view.ObservableScrollView;
import com.vice.bloodpressure.view.TextProgressBar;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 首页Fragment
 * 作者: LYD
 * 创建日期: 2020/4/16 9:31
 */
public class HomeFragment extends BaseEventBusFragment implements SimpleImmersionOwner {
    private static final String TAG = "HomeFragment";
    private static final int DOCTOR_BIND = 10010;
    private static final int DOCTOR_BIND_NOT = 10011;
    private static final int GET_SYSTEM_MESSAGE_COUNT = 10012;
    private static final int GET_SHOP_TITLE = 10013;
    private static final int GET_SMART_SEARCH = 10014;
    private static final int GET_BANNER_LIST = 10015;
    private static final int GET_TWO_GOOD = 10016;
    private static final int GET_KNOWLEDGE_LIST = 10017;
    private static final int GET_DIET_PLAN_SUCCESS = 10020;
    private static final int GET_DIET_PLAN_ERROR = 10021;
    private static final int GET_INDEX_EDUCATION = 10022;
    private static final int GET_INDEX_DIET = 10023;
    private static final int GET_HOME_BLOOD_SUGAR_LIST = 10024;
    private static final int GET_SPORT_SUCCESS = 10025;
    private static final int GET_SPORT_ERROR = 10026;
    private static final int GET_INDEX_DIET_ERROR = 10027;
    private static final int CHANGE_SPORT_SUCCESS = 10028;
    private static final int REFRESH_DIET_SUCCESS = 10029;
    private static final int GET_INDEX_EDUCATION_ERROR = 10030;
    private static final int GET_SYSTEM_MESSAGE_COUNT_EMPTY = 10031;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.srl_home)
    SmartRefreshLayout srlHome;
    @BindView(R.id.srl_header)
    MaterialHeader srlHeader;
    @BindView(R.id.tv_system_message_number)
    ColorTextView tvSystemMessageNumber;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.vp_three_fragment)
    ViewPager vpThreeFragment;
    @BindView(R.id.ll_three_fragment)
    LinearLayout llThreeFragment;
    @BindView(R.id.rv_eight_module)
    RecyclerView rvEightModule;
    @BindView(R.id.rv_two_goods)
    RecyclerView rvTwoGoods;
    @BindView(R.id.gv_knowledge)
    GridView gvKnowledge;
    @BindView(R.id.sc_home)
    ObservableScrollView scHome;
    //智能教育开始
    @BindView(R.id.ll_guide)
    LinearLayout llGuide;
    @BindView(R.id.tv_relate_to_me)
    TextView tvRelateToMe;
    @BindView(R.id.tv_home_smart_education_bank_name)
    TextView tvHomeSmartEducationBankName;
    @BindView(R.id.tv_home_smart_education_class_name)
    TextView tvHomeSmartEducationClassName;
    @BindView(R.id.tv_home_smart_education_class_detail)
    TextView tvHomeSmartEducationClassDetail;
    @BindView(R.id.img_home_smart_education_bank_pic)
    QMUIRadiusImageView imgHomeSmartEducationBankPic;
    @BindView(R.id.tv_home_smart_education_look_time)
    TextView tvHomeSmartEducationLookTime;
    @BindView(R.id.fl_home_smart_education)
    FrameLayout flHomeSmartEducation;
    //智能教育结束
    //血糖记录开始
    @BindView(R.id.rv_home_blood_sugar_list)
    RecyclerView rvHomeBloodSugarList;
    //血糖记录开始
    //智能饮食 开始
    @BindView(R.id.img_home_diet_have_refresh)
    ImageView imgHomeDietHaveRefresh;
    @BindView(R.id.tv_home_diet_empty_desc)
    TextView tvHomeDietEmptyDesc;
    @BindView(R.id.tv_home_diet_empty_open)
    ColorTextView tvHomeDietEmptyOpen;
    @BindView(R.id.fl_home_diet_empty)
    ShadowLayout llHomeDietEmpty;
    @BindView(R.id.vf_home_diet_have)
    ViewFlipper vfHomeDietHave;
    @BindView(R.id.fl_home_diet_have)
    ShadowLayout llHomeDietHave;
    @BindView(R.id.fl_system_message)
    FrameLayout flSystemMessage;
    @BindView(R.id.img_scan)
    ImageView imgScan;
    @BindView(R.id.img_warn_message)
    ImageView imgWarnMessage;
    @BindView(R.id.tv_home_diet_kj)
    TextView tvHomeDietKj;
    @BindView(R.id.tv_home_diet_carbon_water)
    TextView tvHomeDietCarbonWater;
    @BindView(R.id.tv_home_diet_protein)
    TextView tvHomeDietProtein;
    @BindView(R.id.tv_home_diet_fat)
    TextView tvHomeDietFat;
    @BindView(R.id.tv_home_diet_pie_vegetables)
    TextView tvHomeDietPieVegetables;
    @BindView(R.id.tv_home_diet_pie_cereal)
    TextView tvHomeDietPieCereal;
    @BindView(R.id.tv_home_diet_oil)
    TextView tvHomeDietOil;
    @BindView(R.id.tv_home_diet_salt)
    TextView tvHomeDietSalt;
    @BindView(R.id.tv_home_diet_pie_meat_and_egg)
    TextView tvHomeDietPieMeatAndEgg;
    @BindView(R.id.tv_home_diet_pie_milk)
    TextView tvHomeDietPieMilk;
    @BindView(R.id.tv_health_record)
    TextView tvHealthRecord;
    @BindView(R.id.tv_home_sport_look_more)
    TextView tvHomeSportLookMore;
    @BindView(R.id.tv_home_sport_desc)
    TextView tvHomeSportDesc;
    @BindView(R.id.tv_home_sport_kj)
    TextView tvHomeSportKj;
    @BindView(R.id.tv_home_sport_consume_kcal)
    TextView tvHomeSportConsumeKcal;
    @BindView(R.id.tv_home_sport_today_steps)
    TextView tvHomeSportTodaySteps;
    @BindView(R.id.tv_home_sport_today_steps_kcal)
    TextView tvHomeSportTodayStepsKcal;
    @BindView(R.id.tv_home_sport_recommend_type)
    TextView tvHomeSportRecommendType;
    @BindView(R.id.tv_home_sport_recommend_time)
    TextView tvHomeSportRecommendTime;
    @BindView(R.id.tv_home_sport_recommend_type_kcal)
    TextView tvHomeSportRecommendTypeKcal;
    @BindView(R.id.rl_home_sport_to_do)
    RelativeLayout rlHomeSportToDo;
    @BindView(R.id.ll_home_sport_have)
    LinearLayout llHomeSportHave;
    @BindView(R.id.ll_home_sport_empty)
    LinearLayout llHomeSportEmpty;
    @BindView(R.id.tv_home_sport_empty_desc)
    TextView tvHomeSportEmptyDesc;
    @BindView(R.id.tv_home_sport_empty_open)
    TextView tvHomeSportEmptyOpen;
    @BindView(R.id.pb_home_sport)
    TextProgressBar pbHomeSport;
    @BindView(R.id.fl_home_education_have)
    ShadowLayout flHomeEducationHave;
    @BindView(R.id.fl_home_education_empty)
    ShadowLayout flHomeEducationEmpty;
    //智能饮食 结束
    //运动之饼图 开始
    @BindView(R.id.ll_pie_vegetables)
    LinearLayout llPieVegetables;
    @BindView(R.id.ll_pie_cereal)
    LinearLayout llPieCereal;
    @BindView(R.id.ll_pie_oil)
    LinearLayout llPieOil;
    @BindView(R.id.ll_pie_meat_and_egg)
    LinearLayout llPieMeatAndEgg;
    @BindView(R.id.ll_pie_milk)
    LinearLayout llPieMilk;
    //运动之饼图 结束
    private SimpleImmersionProxy mSimpleImmersionProxy = new SimpleImmersionProxy(this);
    private List<BannerBean> bannerList;

    private IndexEducationBean indexEducationBean;
    private List<String> listSportType;
    private ArrayList<String> listSportStr;
    private ArrayList<String> listSportCoefficient;
    private int indexRefreshId;
    //运动
    private String sportTypeStr;
    private String sportTime;
    private int remainingKcal;
    private double weight;
    private int reduceProgress;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init(View rootView) {
        //滑动状态栏变色
        setDragView();
        setScrollView();
        //获取运动权限
        //applySportPermission();
        //设置RecycleView
        setRvForScrollView();
        //设置八个模块
        setEightModule();
        //设置刷新
        setRefresh();
        //新增的所有需要刷新的接口都需要 添加到此处
        addNeedRefresh();
    }

    private void setScrollView() {
        scHome.setOnScrollStatusListener(new ObservableScrollView.OnScrollStatusListener() {
            @Override
            public void onScrollStop() {
                setDragView();
            }

            @Override
            public void onScrolling() {
                FloatingView.get().remove();
            }
        });
    }

    private void setDragView() {
        FloatingView.get().icon(R.drawable.dragview_health_record);
        FloatingView.get().customView(R.layout.dragview_home_to_do);
        FloatingView.get().add();
        FloatingView.get().listener(new MagnetViewListener() {


            @Override
            public void onRemove(FloatingMagnetView magnetView) {

            }

            @Override
            public void onClick(FloatingMagnetView magnetView) {
                startActivity(new Intent(getPageContext(), HealthActivity.class));
                //startActivity(new Intent(getPageContext(), TestActivity.class));
            }
        });

        int allDp = 48 + 230 + 80 + 5 + 10 + 10;
        int allDpPx = ConvertUtils.dp2px(allDp);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.TOP | Gravity.END;
        //计算高度
        params.setMargins(0, allDpPx, 0, 0);
        FloatingView.get().layoutParams(params);
    }

    private void addNeedRefresh() {
        //设置BMI 血糖 血压
        setThreeFragment();
        //获取未读消息数量
        getSystemMessageCount();
        //获取标题
        getShopTitle();
        //获取轮播图
        getBannerList();
        //获取两个产品
        getTwoGood();
        //获取知识库
        getKnowledge();
        //获取首页教育推荐
        getIndexEducation();
        //获取血糖记录
        getHomeBloodSugarList();
        //获取食物
        getIndexDiet();
        //获取首页运动
        //getHomeSport();
        applySportPermission();
    }


    /**
     * 申请运动信息
     */
    @SuppressLint("WrongConstant")
    private void applySportPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            PermissionUtils
                    .permission(Manifest.permission.ACTIVITY_RECOGNITION)
                    .callback(new PermissionUtils.SimpleCallback() {
                        @Override
                        public void onGranted() {
                            //获取首页运动
                            getHomeSport();
                        }

                        @Override
                        public void onDenied() {
                            ToastUtils.showShort("请允许获取健身运动信息");
                        }
                    }).request();
        } else {
            //获取首页运动
            getHomeSport();
        }
    }


    /**
     * 获取首页运动
     */
    private void getHomeSport() {
        int todayStep = StepUtil.getTodayStep(getPageContext());
        HashMap map = new HashMap<>();
        map.put("steps", todayStep);
        map.put("version", ConstantParam.SERVER_VERSION);
        XyUrl.okPost(XyUrl.INDEX_SPORT, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                HomeSportBean indexEducationBean = JSONObject.parseObject(value, HomeSportBean.class);
                Message message = Message.obtain();
                message.what = GET_SPORT_SUCCESS;
                message.obj = indexEducationBean;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
                sendHandlerMessage(GET_SPORT_ERROR);
            }
        });
    }


    /**
     * 获取血糖记录
     */
    private void getHomeBloodSugarList() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.GET_HOME_BLOOD_SUGAR_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                HomeBloodSugarListBean homeBloodSugarListBean = JSONObject.parseObject(value, HomeBloodSugarListBean.class);
                Message message = Message.obtain();
                message.what = GET_HOME_BLOOD_SUGAR_LIST;
                message.obj = homeBloodSugarListBean;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    /**
     * 获取首页教育推荐
     */
    private void getIndexEducation() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.SMART_EDUCATION_INDEX_SERIES, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                IndexEducationBean indexEducationBean = JSONObject.parseObject(value, IndexEducationBean.class);
                Message message = Message.obtain();
                message.what = GET_INDEX_EDUCATION;
                message.obj = indexEducationBean;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
                sendHandlerMessage(GET_INDEX_EDUCATION_ERROR);
            }
        });
    }

    /**
     * 获取首页饮食
     */
    private void getIndexDiet() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.SMART_EDUCATION_INDEX_DIET, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                IndexDietBean indexEducationBean = JSONObject.parseObject(value, IndexDietBean.class);
                Message message = Message.obtain();
                message.what = GET_INDEX_DIET;
                message.obj = indexEducationBean;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
                sendHandlerMessage(GET_INDEX_DIET_ERROR);
            }
        });
    }


    private void setRefresh() {
        //开启嵌套滑动
        srlHome.setEnableNestedScroll(true);
        srlHome.setEnableOverScrollBounce(false);
        srlHeader.setColorSchemeColors(ColorUtils.getColor(R.color.main_home));
        srlHome.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                addNeedRefresh();
                srlHome.finishRefresh();
            }
        });
        srlHome.setEnableLoadMore(false);
    }

    /**
     * 解决ScrollView嵌套RV
     */
    private void setRvForScrollView() {
        RvUtils.setRvForScrollView(rvEightModule);
        RvUtils.setRvForScrollView(rvTwoGoods);
    }

    /**
     * 获取知识库
     */
    private void getKnowledge() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        HashMap map = new HashMap<>();
        map.put("docid", user.getDocid());
        XyUrl.okPost(XyUrl.GET_KNOWLEDGE_CATEGORY, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<NewsListBean> knowledgeList = JSONObject.parseArray(value, NewsListBean.class);
                Message message = Message.obtain();
                message.what = GET_KNOWLEDGE_LIST;
                message.obj = knowledgeList;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    /**
     * 获取两个产品
     */
    private void getTwoGood() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        HashMap map = new HashMap<>();
        map.put("docid", user.getDocid());
        XyUrl.okPost(XyUrl.GET_GOODS_RECOMMEND, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<GoodsRecommendBean> goodsList = JSONObject.parseArray(value, GoodsRecommendBean.class);
                Message msg = Message.obtain();
                msg.obj = goodsList;
                msg.what = GET_TWO_GOOD;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    /**
     * 获取首页轮播
     */
    private void getBannerList() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        HashMap map = new HashMap<>();
        map.put("docid", user.getDocid());
        map.put("type", 1);
        XyUrl.okPost(XyUrl.GET_ADV, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<BannerBean> bannerList = JSONObject.parseArray(value, BannerBean.class);
                Message message = Message.obtain();
                message.what = GET_BANNER_LIST;
                message.obj = bannerList;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });

    }

    /**
     * 获取标题
     */
    private void getShopTitle() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.GET_SHOP_TITLE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ShopTitleBean data = JSONObject.parseObject(value, ShopTitleBean.class);
                Message message = Message.obtain();
                message.what = GET_SHOP_TITLE;
                message.obj = data;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    /**
     * 获取未读消息数量
     */
    private void getSystemMessageCount() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.GET__PORT_MESSAGE_WARNCOUNT, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                try {
                    org.json.JSONObject jsonObject = new org.json.JSONObject(value);
                    int num = jsonObject.getInt("num");
                    Message message = Message.obtain();
                    message.what = GET_SYSTEM_MESSAGE_COUNT;
                    message.obj = num;
                    sendHandlerMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int error, String errorMsg) {
                sendHandlerMessage(GET_SYSTEM_MESSAGE_COUNT_EMPTY);
            }
        });
    }


    /**
     * 智能决策判断
     */
    private void getDoctorInfo() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.MY_DOCTOR, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                Message msg = Message.obtain();
                msg.what = DOCTOR_BIND;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {
                Message msg = Message.obtain();
                msg.what = DOCTOR_BIND_NOT;
                sendHandlerMessage(msg);
            }
        });
    }

    /**
     * 设置BMI 血糖 血压
     */
    private void setThreeFragment() {
        List<Fragment> list = new ArrayList<>();
        list.add(new XueYaFragment());
        list.add(new XueTangFragment());
        list.add(new BmiFragment());

        list.add(new XueYaFragment());
        list.add(new XueTangFragment());
        list.add(new BmiFragment());

        list.add(new XueYaFragment());
        MyFragmentStatePagerAdapter adapter = new MyFragmentStatePagerAdapter(getChildFragmentManager(), list);
        vpThreeFragment.setOffscreenPageLimit(7);
        vpThreeFragment.setAdapter(adapter);
        vpThreeFragment.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int currentPosition;

            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPosition = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                //ViewPager.SCROLL_STATE_IDLE 标识的状态是当前页面完全展现，并且没有动画正在进行中，如果不
                //是此状态下执行setCurrentItem方法回在首位替换的时候会出现跳动！
                if (i != ViewPager.SCROLL_STATE_IDLE)
                    return;
                if (currentPosition == 0) {
                    vpThreeFragment.setCurrentItem(list.size() / 2, false);
                } else if (currentPosition == 1) {
                    vpThreeFragment.setCurrentItem(list.size() - 3, false);
                } else if (currentPosition == list.size() - 2) {
                    vpThreeFragment.setCurrentItem(2, false);
                } else if (currentPosition == list.size() - 1) {
                    vpThreeFragment.setCurrentItem(list.size() / 2, false);
                }
            }
        });
        vpThreeFragment.setPageMargin(20);
        vpThreeFragment.setPageTransformer(true, new ScaleInTransformer());
        vpThreeFragment.setCurrentItem(1);
    }

    /**
     * 设置八个按钮
     */
    private void setEightModule() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            list.add(i + "");
        }
        rvEightModule.setLayoutManager(new GridLayoutManager(getPageContext(), 4));
        HomeEightModuleAdapter homeEightModuleAdapter = new HomeEightModuleAdapter(list);
        rvEightModule.setAdapter(homeEightModuleAdapter);
        homeEightModuleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                Intent intent = null;
                switch (position) {
                    //智能分析
                    case 0:
                        intent = new Intent(getPageContext(), AnalyseListActivity.class);
                        startActivity(intent);
                        break;
                    //智能决策
                    case 1:
                        getDoctorInfo();
                        break;
                    //智能搜索
                    case 2:
                        intent = new Intent(getPageContext(), SmartSearchHomeActivity.class);
                        startActivity(intent);
                        break;
                    //食材库
                    case 3:
                        startActivity(new Intent(getPageContext(), FoodHomeActivity.class));
                        break;
                    //药品库
                    case 4:
                        startActivity(new Intent(getPageContext(), MedicineStoreMainActivity.class));
                        break;
                    //智能饮食
                    case 5:
                        getIsHaveReport();
                        break;
                    //智能教育
                    case 6:
                        startActivity(new Intent(getPageContext(), SmartEducationMainActivity.class));
                        break;
                    //家庭管理
                    case 7:
                        startActivity(new Intent(getPageContext(), LittleToolsListActivity.class));
                        break;
                }
            }
        });
    }

    /**
     * 是否已经获取过智能饮食
     */
    private void getIsHaveReport() {
        HashMap map = new HashMap<>();
//        LoginBean loginBean = (LoginBean) SharedPreferencesUtils.getBean(getActivity(), SharedPreferencesUtils.USER_INFO);
//        map.put("userid", loginBean.getUserid());
        XyUrl.okPost(XyUrl.DIET_LAST_DIET_PLAN, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                DietPlanAddSuccessBean data = JSONObject.parseObject(value, DietPlanAddSuccessBean.class);
                Message message = getHandlerMessage();
                message.what = GET_DIET_PLAN_SUCCESS;
                message.obj = data;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
                sendHandlerMessage(GET_DIET_PLAN_ERROR);
            }
        });
    }


    /**
     * 设置两个商品
     *
     * @param list
     */
    private void setTwoGoods(List<GoodsRecommendBean> list) {
        rvTwoGoods.setLayoutManager(new GridLayoutManager(getPageContext(), 2));
        HomeTwoGoodsAdapter homeTwoGoodsAdapter = new HomeTwoGoodsAdapter(list);
        rvTwoGoods.setAdapter(homeTwoGoodsAdapter);
    }


    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.fl_system_message, R.id.img_scan, R.id.img_warn_message, R.id.tv_health_record, R.id.tv_relate_to_me,
            R.id.fl_home_smart_education, R.id.img_home_diet_have_refresh, R.id.tv_home_diet_empty_open, R.id.tv_home_sport_look_more, R.id.tv_home_sport_empty_open,
            R.id.ll_home_sport_recommend_type, R.id.rl_home_sport_to_do, R.id.tv_home_diet_detail, R.id.tv_home_diet_change_my_love})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //系统消息
            case R.id.fl_system_message:
                startActivity(new Intent(getPageContext(), SystemMsgListActivity.class));
                break;
            //扫码
            case R.id.img_scan:
                toScan();
                break;
            //预警消息
            case R.id.img_warn_message:
                startActivity(new Intent(getPageContext(), WarningRemindActivity.class));
                break;
            //健康记录
            case R.id.tv_health_record:
                startActivity(new Intent(getPageContext(), HealthActivity.class));
                break;
            //智能教育与我相关 进列表
            case R.id.tv_relate_to_me:
                startActivity(new Intent(getPageContext(), SmartEducationMainActivity.class));
                break;
            //智能教育系列 进详情页
            case R.id.fl_home_smart_education:
                int type = indexEducationBean.getType();
                int status = indexEducationBean.getStatus();
                int cId = indexEducationBean.getCid();
                int id = indexEducationBean.getId();
                String link = indexEducationBean.getLinks();
                String webLink = indexEducationBean.getWeblink();
                String duration = indexEducationBean.getDuration();
                int readtime = indexEducationBean.getReadtime();
                Bundle bundle = new Bundle();
                bundle.putInt("cId", cId);
                bundle.putInt("id", id);
                bundle.putString("link", link);
                bundle.putString("webLink", webLink);
                bundle.putString("duration", duration);
                bundle.putInt("type", type);
                bundle.putString("from", status + "");
                bundle.putInt("readTime", readtime);
                Intent intent = null;
                switch (type) {
                    case 1:
                        intent = new Intent(getPageContext(), SmartEducationClassDetailTextActivity.class);
                        break;
                    case 2:
                        intent = new Intent(getPageContext(), SmartEducationClassDetailAudioActivity.class);
                        break;
                    case 3:
                        intent = new Intent(getPageContext(), SmartEducationClassDetailVideoActivity.class);
                        break;
                }
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            //刷新
            case R.id.img_home_diet_have_refresh:
                toRefreshHomeDiet();
                break;
            //打开
            case R.id.tv_home_diet_empty_open:
                startActivity(new Intent(getPageContext(), DietPlanQuestionActivity.class));
                break;
            //首页运动之查看更多
            case R.id.tv_home_sport_look_more:
                startActivity(new Intent(getPageContext(), HomeSportListActivity.class));
                break;
            //首页运动之运动答题
            case R.id.tv_home_sport_empty_open:
                startActivity(new Intent(getPageContext(), HomeSportQuestionActivity.class));
                break;
            //推荐运动
            case R.id.ll_home_sport_recommend_type:
                if (listSportStr != null && listSportStr.size() > 0) {
                    PickerUtils.showOptionPosition(getPageContext(), new PickerUtils.PositionCallBack() {
                        @Override
                        public void execEvent(String content, int position) {
                            //运动修改
                            toChangeRecommendType(content, position + 1);
                        }
                    }, listSportStr);
                }
                break;
            //去完成
            case R.id.rl_home_sport_to_do:
                int sportType = 0;
                double sportCoefficient = 0;
                for (int i = 0; i < listSportStr.size(); i++) {
                    if (listSportStr.get(i).equals(sportTypeStr)) {
                        sportType = TurnsUtils.getInt(listSportType.get(i), 0);
                        sportCoefficient = TurnsUtils.getDouble(listSportCoefficient.get(i), 0);
                    }
                }
                Intent intentToDo;
                Bundle bundleToDo = new Bundle();
                if (sportType == 5 || sportType == 7) {
                    intentToDo = new Intent(getPageContext(), SportTypeVideoActivity.class);
                    bundleToDo.putString("sportTime", sportTime);
                    bundleToDo.putString("sportTypeStr", sportTypeStr);
                    bundleToDo.putInt("kcal", remainingKcal);
                } else {
                    intentToDo = new Intent(getPageContext(), SportTypeManualAddActivity.class);
                }
                bundleToDo.putInt("sportType", sportType);
                bundleToDo.putDouble("weight", weight);
                bundleToDo.putDouble("sportCoefficient", sportCoefficient);
                intentToDo.putExtras(bundleToDo);
                startActivity(intentToDo);
                break;
            case R.id.tv_home_diet_detail:
                intent = new Intent(getPageContext(), DietPlanResultActivity.class);
                intent.putExtra("id", indexRefreshId);
                startActivity(intent);
                break;
            //饮食之换我想吃
            case R.id.tv_home_diet_change_my_love:
                intent = new Intent(getPageContext(), DietPlanDetailActivity.class);
                intent.putExtra("id", indexRefreshId);
                intent.putExtra("day", "");
                startActivity(intent);
                break;
        }
    }


    /**
     * 刷新三餐
     */
    private void toRefreshHomeDiet() {
        LoadingDialogUtils.show(getActivity());
        HashMap map = new HashMap<>();
        map.put("id", indexRefreshId);
        XyUrl.okPost(XyUrl.INDEX_DIET_REFRESH, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                LoadingDialogUtils.unInit();
                IndexDietBean data = JSONObject.parseObject(value, IndexDietBean.class);
                Message message = getHandlerMessage();
                message.what = REFRESH_DIET_SUCCESS;
                message.obj = data;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
            }
        });
    }

    /**
     * 运动修改
     *
     * @param content
     */
    private void toChangeRecommendType(String content, int sportType) {
        int todayStep = StepUtil.getTodayStep(getPageContext());
        HashMap map = new HashMap<>();
        map.put("sportType", sportType);
        map.put("steps", todayStep);
        XyUrl.okPost(XyUrl.INDEX_CHANGE_SPORT, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                HomeSportChangeBean data = JSONObject.parseObject(value, HomeSportChangeBean.class);
                data.setSportType(content);
                Message message = getHandlerMessage();
                message.what = CHANGE_SPORT_SUCCESS;
                message.obj = data;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
            }
        });
    }

    /**
     * 去扫码
     */
    private void toScan() {
        PermissionUtils
                .permission(PermissionConstants.CAMERA)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        Intent intent = new Intent(getPageContext(), ScanActivity.class);
                        intent.putExtra("type", "home");
                        startActivity(intent);
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.showShort("请允许使用相机权限");
                    }
                }).request();
    }


    //Fragment状态栏实现
    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true).statusBarColor(R.color.main_home).init();
    }

    @Override
    public boolean immersionBarEnabled() {
        return true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mSimpleImmersionProxy.setUserVisibleHint(isVisibleToUser);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSimpleImmersionProxy.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSimpleImmersionProxy.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mSimpleImmersionProxy.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mSimpleImmersionProxy.onConfigurationChanged(newConfig);
    }
    //Fragment状态栏实现

    /**
     * 根据返回list 转换为字符串
     *
     * @param list
     * @return
     */
    private String getIndexDietText(List<String> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i));
                sb.append("\u3000");
            }
        }
        return sb.toString();
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.WARN_COUNT_REFRESH:
                getSystemMessageCount();
                break;
            case ConstantParam.BMI_RECORD_ADD:
            case ConstantParam.BLOOD_PRESSURE_RECORD_ADD:
                setThreeFragment();
                break;
            case ConstantParam.HOME_SPORT_QUESTION_SUBMIT:
                getHomeSport();
                break;
            case ConstantParam.HOME_DIET_QUESTION_SUBMIT:
                getIndexDiet();
                break;
            case ConstantParam.BLOOD_SUGAR_ADD:
                getHomeBloodSugarList();
                setThreeFragment();
                break;
            case ConstantParam.DEL_SUGAR_REFRESH:
                getHomeBloodSugarList();
                break;
            case ConstantParam.HOME_EDUCATION_REFRESH:
                getIndexEducation();
                break;
        }
    }

    @Override
    public void processHandlerMsg(Message msg) {
        Intent intent = null;
        switch (msg.what) {
            case GET_SYSTEM_MESSAGE_COUNT:
                int num = (int) msg.obj;
                if (num > 0) {
                    BadgeUtils.setHuaWei(num);
                    BadgeUtils.setVivo(num);
                    tvSystemMessageNumber.setVisibility(View.VISIBLE);
                    tvSystemMessageNumber.setText("" + num);
                } else {
                    tvSystemMessageNumber.setVisibility(View.GONE);
                }
                break;
            case GET_SYSTEM_MESSAGE_COUNT_EMPTY:
                tvSystemMessageNumber.setVisibility(View.GONE);
                break;
            case DOCTOR_BIND:
                intent = new Intent(getPageContext(), MakePolicyActivity.class);
                startActivity(intent);
                break;
            case DOCTOR_BIND_NOT:
                ToastUtils.showShort("请先绑定医生");
                break;
            case GET_SHOP_TITLE:
                ShopTitleBean shopTitleBean = (ShopTitleBean) msg.obj;
                String appTitle = shopTitleBean.getApptitle();
                tvTitle.setText(appTitle);
                break;
            case GET_SMART_SEARCH:
                List<LabelBean> labelList = (List<LabelBean>) msg.obj;
                if (labelList != null && labelList.size() > 0) {
                    LabelBean labelBean = labelList.get(0);
                    int id = labelBean.getId();
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
                break;
            case GET_BANNER_LIST:
                bannerList = (List<BannerBean>) msg.obj;
                ImageAdapter adapter = new ImageAdapter(bannerList);
                banner.setAdapter(adapter)
                        .addBannerLifecycleObserver(this)
                        .setIndicator(new CircleIndicator(getPageContext())).setOnBannerListener((data, position) -> {
                    if (!TextUtils.isEmpty(bannerList.get(position).getLink())) {
                        Intent intentBanner = new Intent(getPageContext(), BaseWebViewActivity.class);
                        intentBanner.putExtra("title", bannerList.get(position).getTitle());
                        intentBanner.putExtra("url", bannerList.get(position).getLink());
                        startActivity(intentBanner);
                    }
                });
                break;
            case GET_TWO_GOOD:
                List<GoodsRecommendBean> list = (List<GoodsRecommendBean>) msg.obj;
                setTwoGoods(list);
                break;
            case GET_KNOWLEDGE_LIST:
                List<NewsListBean> knowledgeList = (List<NewsListBean>) msg.obj;
                //知识库适配器
                if (knowledgeList != null && knowledgeList.size() > 0) {
                    HomeRecycleAdapter homeRecycleAdapter = new HomeRecycleAdapter(getPageContext(), R.layout.recyclerview_item, knowledgeList);
                    gvKnowledge.setNumColumns(knowledgeList.size());
                    gvKnowledge.setAdapter(homeRecycleAdapter);
                }
                break;
            //答过题,获取到最新的Id,跳转详情
            case GET_DIET_PLAN_SUCCESS:
                DietPlanAddSuccessBean data = (DietPlanAddSuccessBean) msg.obj;
                //饮食id
                int id = data.getId();
                intent = new Intent(getPageContext(), DietPlanResultActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
            //没有答题,去答题
            case GET_DIET_PLAN_ERROR:
                intent = new Intent(getPageContext(), DietPlanMainActivity.class);
                startActivity(intent);
                break;
            case GET_INDEX_EDUCATION:
                flHomeEducationHave.setVisibility(View.VISIBLE);
                flHomeEducationHave.setVisibility(View.GONE);
                indexEducationBean = (IndexEducationBean) msg.obj;
                Glide.with(Utils.getApp()).load(indexEducationBean.getSeriesimg()).into(imgHomeSmartEducationBankPic);
                String seriename = indexEducationBean.getSeriename();
                String classesname = indexEducationBean.getClassesname();
                String introduc = indexEducationBean.getIntroduc();
                String wordnum = indexEducationBean.getWordnum() + "";
                String timeminutes = indexEducationBean.getTimeminutes() == null ? "" : indexEducationBean.getTimeminutes();
                tvHomeSmartEducationBankName.setText(seriename);
                tvHomeSmartEducationClassName.setText(classesname);
                tvHomeSmartEducationClassDetail.setText(introduc);
                SpanUtils.with(tvHomeSmartEducationLookTime)
                        .append("本节共").setForegroundColor(ColorUtils.getColor(R.color.black_text))
                        .append(wordnum).setForegroundColor(ColorUtils.getColor(R.color.main_home))
                        .append("字，阅读时间约").setForegroundColor(ColorUtils.getColor(R.color.black_text))
                        .append(timeminutes).setForegroundColor(ColorUtils.getColor(R.color.main_home))
                        .append("分钟").setForegroundColor(ColorUtils.getColor(R.color.black_text))
                        .create();
                break;
            case GET_INDEX_EDUCATION_ERROR:
                flHomeEducationHave.setVisibility(View.GONE);
                flHomeEducationEmpty.setVisibility(View.VISIBLE);
                break;
            case GET_INDEX_DIET:
                IndexDietBean indexDietBean = (IndexDietBean) msg.obj;
                indexRefreshId = indexDietBean.getId();
                int nephropathylei = indexDietBean.getNephropathylei();
                //先判断肾病期数
                if (3 == nephropathylei || 4 == nephropathylei || 5 == nephropathylei) {
                    llHomeDietHave.setVisibility(View.GONE);
                    llHomeDietEmpty.setVisibility(View.VISIBLE);
                    tvHomeDietEmptyDesc.setText("经系统检测，您是糖尿病肾病Ⅲ期及以上患者，此类型饮食较为特殊，需要严格控制蛋白质的摄入，建议您咨询专业临床营养师为您设计精确的饮食方案。");
                    tvHomeDietEmptyOpen.setText("重新开启");
                } else {
                    int profession = indexDietBean.getProfession();
                    if (4 == profession) {
                        llHomeDietHave.setVisibility(View.GONE);
                        llHomeDietEmpty.setVisibility(View.VISIBLE);
                        tvHomeDietEmptyDesc.setText("经系统检测，您体力活动为卧床，目前小慧还没有将您的身体状况纳入计算范围，暂时无法出具饮食方案，给您带来不便十分抱歉。");
                        tvHomeDietEmptyOpen.setText("重新开启");
                    } else {
                        llHomeDietHave.setVisibility(View.VISIBLE);
                        llHomeDietEmpty.setVisibility(View.GONE);
                        int allKcal = indexDietBean.getKcal().getAllKcal();
                        tvHomeDietKj.setText(allKcal + "千卡");
                        String carbonWater = indexDietBean.getKcal().getTanshui();
                        String protein = indexDietBean.getKcal().getDanbaizhi();
                        String fat = indexDietBean.getKcal().getZhifang();
                        tvHomeDietCarbonWater.setText(carbonWater);
                        tvHomeDietProtein.setText(protein);
                        tvHomeDietFat.setText(fat);
                        List<String> listFood = indexDietBean.getKcal().getFood();
                        List<TextView> listTvFood = new ArrayList<>();
                        //设置饼图谷物 蔬菜 肉蛋 乳
                        listTvFood.add(tvHomeDietPieCereal);
                        listTvFood.add(tvHomeDietPieVegetables);
                        listTvFood.add(tvHomeDietPieMeatAndEgg);
                        listTvFood.add(tvHomeDietPieMilk);


                        if (listFood != null && listFood.size() == listTvFood.size()) {
                            for (int i = 0; i < listFood.size(); i++) {
                                listTvFood.get(i).setText(listFood.get(i));
                            }
                        }
                        //三餐轮播
                        List<String> breakfast = indexDietBean.getBreakfast();
                        List<String> lunch = indexDietBean.getLunch();
                        List<String> dinner = indexDietBean.getDinner();

                        View viewBreakFast = View.inflate(getPageContext(), R.layout.include_home_diet_have_breakfast, null);
                        TextView tvBreakFast = viewBreakFast.findViewById(R.id.tv_home_diet_have_breakfast);

                        View viewLunch = View.inflate(getPageContext(), R.layout.include_home_diet_have_lunch, null);
                        TextView tvLunch = viewLunch.findViewById(R.id.tv_home_diet_have_lunch);

                        View viewDinner = View.inflate(getPageContext(), R.layout.include_home_diet_have_dinner, null);
                        TextView tvDinner = viewDinner.findViewById(R.id.tv_home_diet_have_dinner);


                        vfHomeDietHave.addView(viewBreakFast);
                        vfHomeDietHave.addView(viewLunch);
                        vfHomeDietHave.addView(viewDinner);

                        tvBreakFast.setText(getIndexDietText(breakfast));
                        tvLunch.setText(getIndexDietText(lunch));
                        tvDinner.setText(getIndexDietText(dinner));
                    }
                }
                break;
            case GET_INDEX_DIET_ERROR:
                llHomeDietHave.setVisibility(View.GONE);
                llHomeDietEmpty.setVisibility(View.VISIBLE);
                break;
            //五日血糖
            case GET_HOME_BLOOD_SUGAR_LIST:
                HomeBloodSugarListBean homeBloodSugarListBean = (HomeBloodSugarListBean) msg.obj;
                List<HomeBloodSugarListBean.InfoBean> bloodSugarList = homeBloodSugarListBean.getInfo();
                if (bloodSugarList != null && bloodSugarList.size() > 0) {
                    rvHomeBloodSugarList.setLayoutManager(new LinearLayoutManager(getPageContext()));
                    rvHomeBloodSugarList.setAdapter(new HomeBloodSugarAdapter(bloodSugarList, getPageContext()));
                }
                break;
            case GET_SPORT_SUCCESS:
                listSportType = new ArrayList<>();
                listSportStr = new ArrayList<>();
                listSportCoefficient = new ArrayList<>();
                HomeSportBean homeSportBean = (HomeSportBean) msg.obj;
                int isSport = homeSportBean.getIssport();
                int profession = homeSportBean.getActivity();
                if (2 == isSport || 4 == profession) {
                    //不适合运动
                    llHomeSportHave.setVisibility(View.GONE);
                    llHomeSportEmpty.setVisibility(View.VISIBLE);
                    if (2 == isSport) {
                        tvHomeSportEmptyDesc.setText("经系统分析，您存在运动禁忌症，不符合运动条件，无法制定运动处方，待禁忌症相关指标解除后，请重新填写问卷，获得个性化的运动方案！");
                    } else {
                        tvHomeSportEmptyDesc.setText("您好，您的运动强度为“卧床”，目前小慧无法为您制定运动处方，建议您在保证安全的前提下，选择进行卧床运动，如双手十指交叉握拳运动、双足十趾伸曲运动、挺胸收腹运动等。");
                    }
                } else {
                    //适合运动
                    llHomeSportHave.setVisibility(View.VISIBLE);
                    llHomeSportEmpty.setVisibility(View.GONE);
                    //设置今日
                    String message = homeSportBean.getMessage();
                    tvHomeSportDesc.setText(message);
                    int kcals = homeSportBean.getKcals();
                    tvHomeSportKj.setText(kcals + "千卡");
                    int totalKcal = homeSportBean.getTotalKcal();
                    tvHomeSportConsumeKcal.setText(totalKcal + "");
                    //步行步数以及消耗热量
                    int todayStep = StepUtil.getTodayStep(getPageContext());
                    tvHomeSportTodaySteps.setText("今日一共走了" + todayStep + "步");
                    int stepKcal = homeSportBean.getStepKcal();
                    tvHomeSportTodayStepsKcal.setText(stepKcal + "千卡");
                    //推荐运动
                    sportTypeStr = homeSportBean.getSportType();
                    sportTime = homeSportBean.getSportTime();
                    remainingKcal = homeSportBean.getRemainingKcal();
                    //获取三大数组
                    List<List<String>> listSportArr = homeSportBean.getSportArr();
                    for (int i = 0; i < listSportArr.size(); i++) {
                        List<String> listArr = listSportArr.get(i);
                        for (int j = 0; j < listArr.size(); j++) {
                            if (0 == j) {
                                String s = listArr.get(0);
                                listSportType.add(s);
                            } else if (1 == j) {
                                String s = listArr.get(1);
                                listSportStr.add(s);
                            } else {
                                String s = listArr.get(2);
                                listSportCoefficient.add(s);
                            }
                        }
                    }
                    weight = homeSportBean.getWeight();
                    tvHomeSportRecommendType.setText(sportTypeStr);
                    tvHomeSportRecommendTime.setText(sportTime);
                    tvHomeSportRecommendTypeKcal.setText(remainingKcal + "千卡");
                    //消耗进度
                    reduceProgress = homeSportBean.getProgress();
                    //设置进度
                    pbHomeSport.setProgress(reduceProgress);
                    //步行判断显示隐藏
                    if ("步行".equals(sportTypeStr)) {
                        rlHomeSportToDo.setVisibility(View.GONE);
                    } else if (reduceProgress == 100) {
                        rlHomeSportToDo.setVisibility(View.GONE);
                    } else {
                        rlHomeSportToDo.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case GET_SPORT_ERROR:
                llHomeSportHave.setVisibility(View.GONE);
                llHomeSportEmpty.setVisibility(View.VISIBLE);
                break;
            case CHANGE_SPORT_SUCCESS:
                HomeSportChangeBean sportChangeBean = (HomeSportChangeBean) msg.obj;
                sportTypeStr = sportChangeBean.getSportType();
                sportTime = sportChangeBean.getMinutes();
                remainingKcal = sportChangeBean.getRekcal();
                tvHomeSportRecommendType.setText(sportTypeStr);
                tvHomeSportRecommendTime.setText(sportTime);
                tvHomeSportRecommendTypeKcal.setText(remainingKcal + "千卡");
                if ("步行".equals(sportTypeStr)) {
                    rlHomeSportToDo.setVisibility(View.GONE);
                } else if (reduceProgress == 100) {
                    rlHomeSportToDo.setVisibility(View.GONE);
                } else {
                    rlHomeSportToDo.setVisibility(View.VISIBLE);
                }
                break;
            case REFRESH_DIET_SUCCESS:
                vfHomeDietHave.removeAllViews();

                IndexDietBean refreshDietBean = (IndexDietBean) msg.obj;
                indexRefreshId = refreshDietBean.getId();
                List<String> breakfast = refreshDietBean.getBreakfast();
                List<String> lunch = refreshDietBean.getLunch();
                List<String> dinner = refreshDietBean.getDinner();

                View viewBreakFast = View.inflate(getPageContext(), R.layout.include_home_diet_have_breakfast, null);
                TextView tvBreakFast = viewBreakFast.findViewById(R.id.tv_home_diet_have_breakfast);

                View viewLunch = View.inflate(getPageContext(), R.layout.include_home_diet_have_lunch, null);
                TextView tvLunch = viewLunch.findViewById(R.id.tv_home_diet_have_lunch);

                View viewDinner = View.inflate(getPageContext(), R.layout.include_home_diet_have_dinner, null);
                TextView tvDinner = viewDinner.findViewById(R.id.tv_home_diet_have_dinner);


                vfHomeDietHave.addView(viewBreakFast);
                vfHomeDietHave.addView(viewLunch);
                vfHomeDietHave.addView(viewDinner);

                tvBreakFast.setText(getIndexDietText(breakfast));
                tvLunch.setText(getIndexDietText(lunch));
                tvDinner.setText(getIndexDietText(dinner));
                break;
        }
    }
}