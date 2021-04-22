package com.vice.bloodpressure.ui.fragment.healthydiet;

import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.DietPlanSelectFoodAdapter;
import com.vice.bloodpressure.base.fragment.BaseEventBusFragment;
import com.vice.bloodpressure.bean.DietPlanFoodChildBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.imp.OnItemClickListener;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class DietPlanSelectFoodsFragment extends BaseEventBusFragment {
    private static final String TAG = "DietPlanSelectFoodsFragment";
    @BindView(R.id.rv_child)
    RecyclerView rvChild;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    private DietPlanSelectFoodAdapter adapter;
    private List<DietPlanFoodChildBean> list;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_diet_plan_select_foods;
    }

    @Override
    protected void init(View rootView) {
        int fragmentPosition = getArguments().getInt("position", 0);
        list = (List<DietPlanFoodChildBean>) getArguments().getSerializable("list");
        if (list != null && list.size() > 0) {
            rvChild.setVisibility(View.VISIBLE);
            llEmpty.setVisibility(View.GONE);
            setAdapter(fragmentPosition);
        } else {
            rvChild.setVisibility(View.GONE);
            llEmpty.setVisibility(View.VISIBLE);
        }
        //KeyboardUtils.hideSoftInput(rvChild);
    }


    private void setAdapter(int fragmentPosition) {
        adapter = new DietPlanSelectFoodAdapter(list);
        rvChild.setLayoutManager(new LinearLayoutManager(getPageContext()));
        rvChild.setAdapter(adapter);
        //默认选中第一个
        //adapter.setSelection(0);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.setSelection(position);
                DietPlanFoodChildBean dietPlanFoodChildBean = list.get(position);
                HashMap<Integer, DietPlanFoodChildBean> selectMap = new HashMap<>();
                selectMap.put(fragmentPosition, dietPlanFoodChildBean);
                EventBusUtils.post(new EventMessage<>(ConstantParam.DIET_PLAN_SELECT_FRAGMENT_TO_ACTIVITY, selectMap));
            }
        });
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.SEARCH_TO_SELECT:
                int fragmentPosition = getArguments().getInt("position", 0);
                DietPlanFoodChildBean data = (DietPlanFoodChildBean) event.getData();
                int cid = data.getCid();
                int id = data.getId();
                for (int i = 0; i < list.size(); i++) {
                    int cid1 = list.get(i).getCid();
                    if (cid == cid1) {
                        int id1 = list.get(i).getId();
                        if (id == id1) {
                            adapter.setSelection(i);
                            DietPlanFoodChildBean dietPlanFoodChildBean = list.get(i);
                            HashMap<Integer, DietPlanFoodChildBean> selectMap = new HashMap<>();
                            selectMap.put(fragmentPosition, dietPlanFoodChildBean);
                            EventBusUtils.post(new EventMessage<>(ConstantParam.DIET_PLAN_SELECT_FRAGMENT_TO_ACTIVITY, selectMap));
                        }
                    }
                }
                break;
        }
    }
}
