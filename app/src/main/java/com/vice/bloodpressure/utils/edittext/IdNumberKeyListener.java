package com.vice.bloodpressure.utils.edittext;

import android.text.method.NumberKeyListener;


/**
 * 描述: 身份证号监听(限制输入并默认打开数字键盘)
 * 作者: LYD
 * 创建日期: 2019/10/25 17:49
 */
public class IdNumberKeyListener extends NumberKeyListener {
    /**
     * @return ：返回哪些希望可以被输入的字符,默认不允许输入
     */
    @Override
    protected char[] getAcceptedChars() {
        char[] chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'X', 'x'};
        return chars;
    }

    /**
     * 0：无键盘,键盘弹不出来
     * 1：英文键盘
     * 2：模拟键盘
     * 3：数字键盘
     *
     * @return
     */
    @Override
    public int getInputType() {
        return 3;
    }
}
