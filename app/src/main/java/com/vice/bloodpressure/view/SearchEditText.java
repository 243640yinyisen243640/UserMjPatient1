package com.vice.bloodpressure.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.StringUtils;

@SuppressLint("AppCompatCustomView")
public class SearchEditText extends EditText {
    private static final long LIMIT = 1000;
    private OnTextChangedListener mListener;
    //记录开始输入前的文本内容
    private String mStartSearchText = "";
    private Runnable mAction = new Runnable() {
        @Override
        public void run() {
            if (mListener != null) {
                String searchKeyWord = SPStaticUtils.getString("searchKeyWord");
                //判断最终和开始前是否一致
                if (!StringUtils.equals(searchKeyWord, getText().toString())) {
                    //更新mStartSearchText
                    mStartSearchText = getText().toString();
                    mListener.onTextChanged(mStartSearchText);
                }
            }
        }
    };

    public SearchEditText(Context context) {
        this(context, null);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 在 LIMIT 时间内连续输入不触发文本变化
     */
    public void setOnTextChangedListener(OnTextChangedListener listener) {
        mListener = listener;
    }

    @Override
    protected void onTextChanged(final CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        //移除上一次的回调
        removeCallbacks(mAction);
        postDelayed(mAction, LIMIT);
    }


    public void reset() {
        mStartSearchText = "";
        setText("");
        KeyboardUtils.hideSoftInput(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        removeCallbacks(mAction);
        super.onDetachedFromWindow();
    }

    public interface OnTextChangedListener {
        void onTextChanged(String text);
    }
}
