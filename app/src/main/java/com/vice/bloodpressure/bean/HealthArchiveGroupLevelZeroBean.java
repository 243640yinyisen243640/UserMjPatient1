package com.vice.bloodpressure.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.vice.bloodpressure.adapter.HealthArchiveGroupLevelAdapter;


/**
 * 描述: 健康档案一级标题
 * 作者: LYD
 * 创建日期: 2019/3/5 10:17
 */
public class HealthArchiveGroupLevelZeroBean extends AbstractExpandableItem<HealthArchiveGroupLevelOneBean> implements MultiItemEntity {
    //组名
    private String groupName;

    public HealthArchiveGroupLevelZeroBean(String groupName) {
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
        return HealthArchiveGroupLevelAdapter.TYPE_LEVEL_0;
    }

}
