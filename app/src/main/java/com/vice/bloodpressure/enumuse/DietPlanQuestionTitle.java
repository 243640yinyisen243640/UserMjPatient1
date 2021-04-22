package com.vice.bloodpressure.enumuse;

public enum DietPlanQuestionTitle {
    question_01(1, "性别"),
    question_02(2, "身高体重"),
    question_03(3, "活动强度"),
    question_04(4, "基本情况");

    private int number;
    private String title;

    DietPlanQuestionTitle(int number, String title) {
        this.number = number;
        this.title = title;
    }

    public static String getTitleFromNumber(int number) {
        for (DietPlanQuestionTitle enums : DietPlanQuestionTitle.values()) {
            if (number == enums.getNumber()) {
                return enums.getTitle();
            }
        }
        return "";
    }

    public int getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }
}
