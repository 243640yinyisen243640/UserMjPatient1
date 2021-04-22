package com.vice.bloodpressure.utils.edittext;

import android.util.Log;
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

    private static final String TAG = "EditTextUtils";

    private EditTextUtils() {

    }


    /**
     * 搜索回调
     *
     * @param et
     * @param listener
     */
    public static void setOnEditorActionListener(EditText et, OnActionSearchListener listener) {
        et.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Log.e(TAG, "actionId==" + actionId);
                    listener.onActionSearch();
                    return true;
                }
                return false;
            }
        });
    }

    public interface OnActionSearchListener {
        void onActionSearch();
    }
}
