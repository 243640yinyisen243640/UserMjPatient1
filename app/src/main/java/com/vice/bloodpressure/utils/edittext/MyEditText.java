package com.vice.bloodpressure.utils.edittext;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;


/**
 * 描述: 重写EditText 限制输入
 * 作者: LYD
 * 创建日期: 2020/4/29 16:16
 */
public class MyEditText extends androidx.appcompat.widget.AppCompatEditText {
    private static final String TAG = "MyEditText";

    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 输入法
     *
     * @param outAttrs
     * @return
     */
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new MyInputConnectionWrapper(super.onCreateInputConnection(outAttrs), false);
    }

    class MyInputConnectionWrapper extends InputConnectionWrapper implements InputConnection {


        public MyInputConnectionWrapper(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        @Override
        public boolean commitText(CharSequence text, int newCursorPosition) {
            //只能输入字母或者数字
            return super.commitText(text, newCursorPosition);
        }
    }
}
