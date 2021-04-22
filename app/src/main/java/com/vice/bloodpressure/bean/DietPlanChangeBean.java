package com.vice.bloodpressure.bean;

import java.util.List;

public class DietPlanChangeBean {
    private List<DietPlanFoodChildBean> breakfast;
    private List<DietPlanFoodChildBean> lunch;
    private List<DietPlanFoodChildBean> dinner;

    public List<DietPlanFoodChildBean> getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(List<DietPlanFoodChildBean> breakfast) {
        this.breakfast = breakfast;
    }

    public List<DietPlanFoodChildBean> getLunch() {
        return lunch;
    }

    public void setLunch(List<DietPlanFoodChildBean> lunch) {
        this.lunch = lunch;
    }

    public List<DietPlanFoodChildBean> getDinner() {
        return dinner;
    }

    public void setDinner(List<DietPlanFoodChildBean> dinner) {
        this.dinner = dinner;
    }

}
