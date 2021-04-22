package com.vice.bloodpressure.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.vice.bloodpressure.adapter.MySugarFilesAdapter;

public class MySugarLevel0Bean extends AbstractExpandableItem<MySugarLevel1Bean> implements MultiItemEntity {
    //组名
    private String groupName;

    public MySugarLevel0Bean(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return MySugarFilesAdapter.TYPE_LEVEL_0;
    }
}
