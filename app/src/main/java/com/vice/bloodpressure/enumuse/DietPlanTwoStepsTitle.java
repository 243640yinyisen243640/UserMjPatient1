package com.vice.bloodpressure.enumuse;

public enum DietPlanTwoStepsTitle {
    BREAK_FAST(1, "早餐"),
    LUNCH(2, "午餐"),
    DINNER(3, "晚餐");

    private int position;
    private String name;

    DietPlanTwoStepsTitle(int position, String name) {
        this.position = position;
        this.name = name;
    }

    public static String getNameFromPosition(int position) {
        for (DietPlanTwoStepsTitle enums : DietPlanTwoStepsTitle.values()) {
            if (position == enums.getPosition()) {
                return enums.getName();
            }
        }
        return "";
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }
}
