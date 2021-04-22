package com.vice.bloodpressure.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: NewBloodPressure
 * @Package: com.vice.bloodpressure.utils
 * @ClassName: ListCastUtils
 * @Description: List强制类型转换
 * @Author: zwk
 * @CreateDate: 2019/11/27 14:25
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/27 14:25
 * @UpdateRemark: 更新内容
 * @Version: 1.0
 */

public class ListCastUtils {
    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }

}
