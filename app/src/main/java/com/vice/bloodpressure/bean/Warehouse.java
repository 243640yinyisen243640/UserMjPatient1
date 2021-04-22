package com.vice.bloodpressure.bean;

/**
 * @ProjectName: NewBloodPressure
 * @Package: com.vice.bloodpressure.bean
 * @ClassName: Warehouse
 * @Description: java类作用描述
 * @Author: zwk
 * @CreateDate: 2019/10/17 9:31
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/10/17 9:31
 * @UpdateRemark: 更新内容
 * @Version: 1.0
 */

public class Warehouse {

    private int id;
    private String name;
    private int pic;

    public Warehouse(int id, String name, int pic) {
        this.id = id;
        this.name = name;
        this.pic = pic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }
}
