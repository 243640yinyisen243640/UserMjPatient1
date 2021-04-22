package com.vice.bloodpressure.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * @ProjectName: NewBloodPressure
 * @Package: com.vice.bloodpressure.view
 * @ClassName: JustifyTextView
 * @Description: 根据传入的TextView自动适应宽度的TextView
 * @Author: zwk
 * @CreateDate: 2019/10/21 10:48
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/10/21 10:48
 * @UpdateRemark: 更新内容
 * @Version: 1.0
 */

public class JustifyTextView extends AppCompatTextView {

    private int mLineY;
    private float mViewWidth = 0;

    public JustifyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        TextPaint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        paint.drawableState = getDrawableState();
        String text = getText().toString();
        mLineY = 0;
        mLineY += getTextSize();
        float width = StaticLayout.getDesiredWidth(text, 0, text.length(), getPaint());
        drawScaleText(canvas, text, width);
        Paint.FontMetrics fm = paint.getFontMetrics();
        int textHeight = (int) fm.top;
        mLineY += textHeight;
    }

    public void drawScaleText(Canvas canvas, String line, float lineWidth) {
        float x = 0;
        float d = (mViewWidth - lineWidth) / (line.length() - 1);

        for (int i = 0; i < line.length(); i++) {
            String c = String.valueOf(line.charAt(i));
            float cw = StaticLayout.getDesiredWidth(c, getPaint());
            canvas.drawText(c, x, mLineY, getPaint());
            x += cw + d;
        }
    }

    public void setTitleWidth(TextView tv) {
        String text = tv.getText().toString();
        mViewWidth = StaticLayout.getDesiredWidth(text, 0, text.length(), getPaint());
        setWidth((int) mViewWidth);
        invalidate();
    }

}
