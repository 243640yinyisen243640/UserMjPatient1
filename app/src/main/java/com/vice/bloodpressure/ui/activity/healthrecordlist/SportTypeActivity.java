package com.vice.bloodpressure.ui.activity.healthrecordlist;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SportTypeActivity extends BaseActivity {

    private ListView listView;

    private TypedArray sportImages = Utils.getApp().getResources().obtainTypedArray(R.array.sport_img);
    private String[] sportTypes = Utils.getApp().getResources().getStringArray(R.array.sport_type);
    private String[] sportCommands = Utils.getApp().getResources().getStringArray(R.array.sport_command);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("运动方式");
        initViews();
        setListView();
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_sport_type, contentLayout, false);
    }

    private void initViews() {
        listView = findViewById(R.id.listView);
    }


    private void setListView() {
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < sportImages.length(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("img", sportImages.getResourceId(i, R.drawable.default_image));
            map.put("type", sportTypes[i]);
            map.put("command", sportCommands[i]);
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.item_sport_type_listview
                , new String[]{"img", "type", "command"}, new int[]{R.id.img_sportType_lisViewItem,
                R.id.tv_sport_type_item, R.id.tvCommand_sportType_lisViewItem});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = view.findViewById(R.id.tv_sport_type_item);
                String type = textView.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("position", position);
                intent.putExtra("type", type);
                setResult(0, intent);
                finish();
            }
        });
    }
}
