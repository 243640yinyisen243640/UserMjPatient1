package com.vice.bloodpressure.bean;

import java.io.Serializable;

/**
 * 描述: 选中
 * 作者: LYD
 * 创建日期: 2019/10/25 15:42
 */
public class SelectedBean implements Serializable {
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
