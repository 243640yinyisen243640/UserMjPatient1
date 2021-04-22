package com.lyd.baselib.utils.edittext;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 描述: EditText常用操作
 * 作者: LYD
 * 创建日期: 2019/12/20 16:26
 */
public class EditTextUtils {

    private EditTextUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 搜索回调
     *
     * @param et
     * @param listener
     */
    public static void setOnActionSearch(EditText et, OnActionSearchListener listener) {
        et.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    listener.onActionSearch();
                    return true;
                }
                return false;
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////
    // interface
    ///////////////////////////////////////////////////////////////////////////
    public interface OnActionSearchListener {
        void onActionSearch();
    }
}
