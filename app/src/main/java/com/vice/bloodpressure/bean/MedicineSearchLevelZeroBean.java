package com.vice.bloodpressure.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.vice.bloodpressure.adapter.MedicineSearchResultLevelAdapter;

public class MedicineSearchLevelZeroBean extends AbstractExpandableItem<MedicineSearchLevelOneBean> implements MultiItemEntity {
    private int id;
    //private int pid;
    private String classname;

    /**
     * id : 1
     * pid : 0
     * classname : 测试药物
     * picurl : http://xydoc.xiyuns.cn/Public/upload/20190226/201902261935481551180948.png
     * addtime : 1550742168
     */
    public MedicineSearchLevelZeroBean() {

    }
    //private String picurl;
    //private int addtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //    public int getPid() {
    //        return pid;
    //    }
    //
    //    public void setPid(int pid) {
    //        this.pid = pid;
    //    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return MedicineSearchResultLevelAdapter.TYPE_LEVEL_0;
    }

    //    public String getPicurl() {
    //        return picurl;
    //    }
    //
    //    public void setPicurl(String picurl) {
    //        this.picurl = picurl;
    //    }

    //    public int getAddtime() {
    //        return addtime;
    //    }
    //
    //    public void setAddtime(int addtime) {
    //        this.addtime = addtime;
    //    }
}
