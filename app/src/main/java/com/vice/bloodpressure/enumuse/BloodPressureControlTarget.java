package com.vice.bloodpressure.enumuse;

public enum BloodPressureControlTarget {
    TYPE_13(13, "<130/80"),
    TYPE_14(14, "<140/90"),
    TYPE_15(15, "<150/90");

    private int type;
    private String controlTarget;

    BloodPressureControlTarget(int type, String controlTarget) {
        this.type = type;
        this.controlTarget = controlTarget;
    }

    /**
     * 根据类型 获取 血压控制范围
     *
     * @param type
     * @return
     */
    public static String getBpFromType(int type) {
        for (BloodPressureControlTarget enums : BloodPressureControlTarget.values()) {
            if (type == enums.getType()) {
                return enums.getControlTarget();
            }
        }
        return "";
    }

    public int getType() {
        return type;
    }

    public String getControlTarget() {
        return controlTarget;
    }
}
