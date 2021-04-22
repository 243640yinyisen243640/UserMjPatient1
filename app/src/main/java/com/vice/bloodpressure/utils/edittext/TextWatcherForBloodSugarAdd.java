package com.vice.bloodpressure.utils.edittext;


import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.vice.bloodpressure.utils.TurnsUtils;

/**
 * 描述:  血糖添加输入限制
 * 作者: LYD
 * 创建日期: 2020/4/29 16:18
 */
public class TextWatcherForBloodSugarAdd implements TextWatcher {
    private EditText editText;
    private int maxInput;

    public TextWatcherForBloodSugarAdd(EditText editText) {
        this.editText = editText;
    }

    public TextWatcherForBloodSugarAdd setDigits(int max) {
        maxInput = max;
        return this;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        //拦截大于40.0
        String temp = editable.toString().trim();
        double inputResult = TurnsUtils.getDouble(temp, 0);
        if (inputResult > maxInput) {
            editText.setText("");
        }
        //设置小数点后一位
        //返回指定字符在此字符串中第一次出现处的索引
        int positionDot = temp.indexOf(".");
        //获取光标位置
        int index = editText.getSelectionStart();
        //如果包含小数点
        if (positionDot >= 0 && temp.length() - 2 > positionDot) {
            //删除小数点后一位
            editable.delete(index - 1, index);
        }
    }
}
