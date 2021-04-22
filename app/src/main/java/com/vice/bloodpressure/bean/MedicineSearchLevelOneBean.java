package com.vice.bloodpressure.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.vice.bloodpressure.adapter.MedicineSearchResultLevelAdapter;

public class MedicineSearchLevelOneBean implements MultiItemEntity {


    /**
     * id : 1
     * medicine : 测试药物2
     */

    private int id;
    private String medicine;
    private String contenturl;


    /**
     *
     */
    public MedicineSearchLevelOneBean() {

    }

    public MedicineSearchLevelOneBean(int id, String medicine, String contenturl) {
        this.id = id;
        this.medicine = medicine;
        this.contenturl = contenturl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getContenturl() {
        return contenturl;
    }

    public void setContenturl(String contenturl) {
        this.contenturl = contenturl;
    }

    @Override
    public int getItemType() {
        return MedicineSearchResultLevelAdapter.TYPE_LEVEL_1;
    }
}
