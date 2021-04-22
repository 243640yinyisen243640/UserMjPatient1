package com.vice.bloodpressure.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * 描述:TextWatcher工具类
 * 作者: LYD
 * 创建日期: 2019/9/21 17:25
 */
public class TextWatcherUtils {
    private TextWatcherUtils() {
    }

    /**
     * Java5变长参数:
     * vararg parameter must be the last in the list
     *
     * @param listener
     * @param ets
     */
    public static void addTextChangedListener(OnTextChangedListener listener, EditText... ets) {
        for (EditText editText : ets) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (listener != null) {
                        listener.onTextChanged(s.toString());
                    }
                }
            });
        }

    }

    public interface OnTextChangedListener {
        void onTextChanged(String s);
    }

}
