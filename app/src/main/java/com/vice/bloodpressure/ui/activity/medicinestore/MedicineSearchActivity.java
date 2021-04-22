package com.vice.bloodpressure.ui.activity.medicinestore;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;
import com.vice.bloodpressure.utils.DialogUtils;
import com.vice.bloodpressure.utils.edittext.EditTextUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 药品搜索
 * 作者: LYD
 * 创建日期: 2019/4/3 13:50
 */
public class MedicineSearchActivity extends BaseActivity {

    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;

    @BindView(R.id.tfl_history)
    TagFlowLayout flowLayoutHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEditText();
        hideTitleBar();
        initHistory();
    }

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_medicine_search, contentLayout, false);
        return layout;
    }

    private void setEditText() {
        //必须设置属性单行
        EditTextUtils.setOnEditorActionListener(etInput, new EditTextUtils.OnActionSearchListener() {
            @Override
            public void onActionSearch() {
                toSearch();
            }
        });
    }

    private void toSearch() {
        String keyWord = etInput.getText().toString().trim();
        if (TextUtils.isEmpty(keyWord)) {
            ToastUtils.showShort("请输入药品关键字");
            return;
        }
        writeHistory(keyWord);
        initHistory();
        toSearchResultActivity(keyWord);
    }

    private void toSearchResultActivity(String keyWord) {
        etInput.setText("");
        Intent intent = new Intent(getPageContext(), MedicineSearchResultListActivity.class);
        intent.putExtra("keyWord", keyWord);
        startActivity(intent);
    }

    private void writeHistory(String write) {
        if (TextUtils.isEmpty(write)) {
            return;
        }
        String writeHistory = "";
        //获取历史记录
        List<String> readHistoryList = readHistory();
        //如果不重复，则添加为第一个历史记录；
        //如果重复，则删除已有，再添加为第一个历史记录；
        for (int i = 0; i < readHistoryList.size(); i++) {
            boolean hasWrite = readHistoryList.get(i).equals(write);
            if (hasWrite) {
                readHistoryList.remove(i);
                break;
            }
        }
        readHistoryList.add(0, write);
        //历史记录最多为10个
        if (readHistoryList.size() > 10) {
            readHistoryList = readHistoryList.subList(0, 10);
        }
        //将ArrayList转为String
        for (int i = 0; i < readHistoryList.size(); i++) {
            writeHistory += readHistoryList.get(i) + "wy";
        }
        SPStaticUtils.put("search_history", writeHistory);
    }

    private List<String> readHistory() {
        List<String> readHistoryList = new ArrayList<>();
        String search_history = SPStaticUtils.getString("search_history");
        //将String转为List
        if (!TextUtils.isEmpty(search_history)) {
            String[] strs = search_history.split("wy");
            for (int i = 0; i < strs.length; i++) {
                readHistoryList.add(i, strs[i]);
            }
        }
        return readHistoryList;
    }

    private void initHistory() {
        final List<String> readHistory = readHistory();
        if (readHistory != null && readHistory.size() > 0) {
            llHistory.setVisibility(View.VISIBLE);
        } else {
            llHistory.setVisibility(View.GONE);
        }
        //为FlowLayout填充数据
        TagAdapter tagAdapter = new TagAdapter(readHistory) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {

                TextView view = (TextView) View.inflate(getPageContext(), R.layout.flowlayout_textview, null);
                view.setText(readHistory.get(position));
                return view;
            }
        };
        flowLayoutHistory.setAdapter(tagAdapter);
        //为FlowLayout的标签设置监听事件
        flowLayoutHistory.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                toSearchResultActivity(readHistory.get(position));
                return true;
            }
        });
    }

    @OnClick({R.id.iv_top_back, R.id.tv_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.tv_clear:
                clearDialog();
                break;
        }
    }

    private void clearDialog() {
        DialogUtils.getInstance().showDialog(getPageContext(), "", "确认要删除全部历史记录?", true, new DialogUtils.DialogCallBack() {
            @Override
            public void execEvent() {
                etInput.setText("");
                SPStaticUtils.put("search_history", "");
                initHistory();
            }
        });
    }


}
