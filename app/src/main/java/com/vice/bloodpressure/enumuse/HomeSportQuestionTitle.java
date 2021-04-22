package com.vice.bloodpressure.enumuse;

public enum HomeSportQuestionTitle {
    question_01(1, "运动禁忌症"),
    question_02(2, "年龄"),
    question_03(3, "身高体重"),
    question_04(4, "既往病史及并发症"),
    question_05(5, "运动习惯");

    private int number;
    private String title;

    HomeSportQuestionTitle(int number, String title) {
        this.number = number;
        this.title = title;
    }

    public static String getTitleFromNumber(int number) {
        for (HomeSportQuestionTitle enums : HomeSportQuestionTitle.values()) {
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
