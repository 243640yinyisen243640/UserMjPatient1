package com.vice.bloodpressure.ui.activity.smartsearch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.SmartSearchHomeLeftAdapter;
import com.vice.bloodpressure.adapter.SmartSearchHomeRightAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.LabelBean;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  智能搜索 首页
 * 作者: LYD
 * 创建日期: 2020/4/22 10:03
 */
public class SmartSearchHomeActivity extends BaseHandlerActivity implements AdapterView.OnItemClickListener {
    private static final int GET_FIRST_LEVEL = 10010;
    private static final int GET_SECOND_LEVEL = 10011;
    @BindView(R.id.lv_left)
    ListView lvLeft;
    @BindView(R.id.tv_indicator_text)
    TextView tvIndicatorText;
    @BindView(R.id.tv_refresh)
    TextView tvRefresh;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    //一级
    private List<LabelBean> firstLevelList;
    private SmartSearchHomeLeftAdapter leftAdapter;
    private int pageIndex = 1;
    private int firstLevelId;
    //二级

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("智能搜索");
        getFirstLevel();
    }


    /**
     * 获取一级分类
     */
    private void getFirstLevel() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        Map<String, Object> map = new HashMap<>();
        map.put("docid", user.getDocid());
        XyUrl.okPost(XyUrl.GET_VISIT, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<LabelBean> labelList = JSONObject.parseArray(value, LabelBean.class);
                Message message = Message.obtain();
                message.what = GET_FIRST_LEVEL;
                message.obj = labelList;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    private void getSecondLevel(int pageIndex, int id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("page", pageIndex);
        XyUrl.okPost(XyUrl.GET_VISITER, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                try {
                    List<LabelBean> labelList = JSONObject.parseArray(value, LabelBean.class);
                    Message message = getHandlerMessage();
                    message.what = GET_SECOND_LEVEL;
                    message.obj = labelList;
                    sendHandlerMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });

    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_smart_search_home, contentLayout, false);
    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_FIRST_LEVEL:
                firstLevelList = (List<LabelBean>) msg.obj;
                leftAdapter = new SmartSearchHomeLeftAdapter(getPageContext(), firstLevelList);
                lvLeft.setAdapter(leftAdapter);
                lvLeft.setOnItemClickListener(this);
                //获取二级
                if (firstLevelList != null && firstLevelList.size() > 0) {
                    firstLevelId = firstLevelList.get(0).getId();
                    getSecondLevel(pageIndex, firstLevelId);
                    //设置二级指示器
                    String remarkname = firstLevelList.get(0).getRemarkname();
                    tvIndicatorText.setText(remarkname);
                }
                break;
            case GET_SECOND_LEVEL:
                List<LabelBean> secondLevelList = (List<LabelBean>) msg.obj;
                rvList.setLayoutManager(new GridLayoutManager(getPageContext(), 3));
                SmartSearchHomeRightAdapter rightAdapter = new SmartSearchHomeRightAdapter(secondLevelList);
                rvList.setAdapter(rightAdapter);
                rightAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                        Intent intent = new Intent(getPageContext(), SmartSearchListActivity.class);
                        intent.putExtra("id", secondLevelList.get(position).getId());
                        startActivity(intent);
                    }
                });
                break;
        }
    }


    @OnClick(R.id.tv_refresh)
    public void onViewClicked() {
        pageIndex++;
        getSecondLevel(pageIndex, firstLevelId);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //重新设置页码
        pageIndex = 1;
        //设置二级指示器
        String remarkname = firstLevelList.get(position).getRemarkname();
        tvIndicatorText.setText(remarkname);
        //设置选中
        firstLevelId = firstLevelList.get(position).getId();
        leftAdapter.setCheck(position);
        leftAdapter.notifyDataSetChanged();
        //获取二级
        getSecondLevel(pageIndex, firstLevelId);
    }
}
