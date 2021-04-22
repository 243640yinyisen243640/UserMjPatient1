package com.vice.bloodpressure.enumuse;

public enum BloodSugarControlTarget {

    TYPE_ONE(1, "3.9-7.2", "5-10"),
    TYPE_TWO(2, "4.4-7", "4.4-10"),
    TYPE_PREGNANT(3, "3.1-5.3", "3.1-6.7"),
    TYPE_OTHER(4, "3.0-6.0", "3.0-7.8");

    private int type;
    private String before;
    private String after;

    BloodSugarControlTarget(int type, String before, String after) {
        this.type = type;
        this.before = before;
        this.after = after;
    }

    /**
     * 根据类型 获取 空腹控制范围
     *
     * @param type
     * @return
     */
    public static String getBeforeFromType(int type) {
        for (BloodSugarControlTarget enums : BloodSugarControlTarget.values()) {
            if (type == enums.getType()) {
                return enums.getBefore();
            }
        }
        return "";
    }

    /**
     * 根据类型 获取 餐后控制范围
     *
     * @param type
     * @return
     */
    public static String getAfterFromType(int type) {
        for (BloodSugarControlTarget enums : BloodSugarControlTarget.values()) {
            if (type == enums.getType()) {
                return enums.getAfter();
            }
        }
        return "";
    }

    public int getType() {
        return type;
    }

    public String getBefore() {
        return before;
    }

    public String getAfter() {
        return after;
    }
}
