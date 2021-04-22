package com.vice.bloodpressure.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.vice.bloodpressure.adapter.MySugarFilesAdapter;

public class MySugarLevel1Bean implements MultiItemEntity {
    private String name;//左边名称
    private String content;//右边内容
    private String utit;//单位

    public MySugarLevel1Bean() {
    }

    public MySugarLevel1Bean(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUtit() {
        return utit;
    }

    public void setUtit(String utit) {
        this.utit = utit;
    }

    @Override
    public int getItemType() {
        return MySugarFilesAdapter.TYPE_LEVEL_1;
    }

}
