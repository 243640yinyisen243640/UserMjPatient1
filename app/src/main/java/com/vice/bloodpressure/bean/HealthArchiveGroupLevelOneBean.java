package com.vice.bloodpressure.bean;


import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.vice.bloodpressure.adapter.HealthArchiveGroupLevelAdapter;

import java.io.Serializable;


public class HealthArchiveGroupLevelOneBean implements MultiItemEntity, Serializable {
    private String name;
    private String content;

    private String unit;

    private String center;
    private String id;

    private String startTime;
    private String endTime;

    public HealthArchiveGroupLevelOneBean(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public HealthArchiveGroupLevelOneBean(String name, String content, String unit) {
        this.name = name;
        this.content = content;
        this.unit = unit;
    }


    public HealthArchiveGroupLevelOneBean(String name, String content, String center, String id, String startTime, String endTime) {
        this.name = name;
        this.content = content;
        this.center = center;
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public int getItemType() {
        return TextUtils.isEmpty(center) ? HealthArchiveGroupLevelAdapter.TYPE_LEVEL_1 : HealthArchiveGroupLevelAdapter.TYPE_LEVEL_2;
    }


}
