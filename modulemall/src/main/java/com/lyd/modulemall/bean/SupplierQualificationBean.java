package com.lyd.modulemall.bean;

import java.util.ArrayList;

public class SupplierQualificationBean {
    /**
     * organization_id : 1
     * company_name : 我是供货商
     * business_license_img : http://d.xiyuns.cn/publics/shopimg/goods1.png
     * qualification_img : ["http://d.xiyuns.cn/publics/shopimg/goods1.png"]
     */

    private int organization_id;
    private String company_name;
    private String business_license_img;
    private ArrayList<String> qualification_img;

    public int getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(int organization_id) {
        this.organization_id = organization_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getBusiness_license_img() {
        return business_license_img;
    }

    public void setBusiness_license_img(String business_license_img) {
        this.business_license_img = business_license_img;
    }

    public ArrayList<String> getQualification_img() {
        return qualification_img;
    }

    public void setQualification_img(ArrayList<String> qualification_img) {
        this.qualification_img = qualification_img;
    }
}
