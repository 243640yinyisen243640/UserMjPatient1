package com.vice.bloodpressure.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.vice.bloodpressure.R;


/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
public class NewMarkerView extends MarkerView {


    public static final int ARROW_SIZE = 20;
    private static final float CIRCLE_OFFSET = 5;
    private static final float STOKE_WIDTH = 3;
    private int[] lineColor;
    private int chartNum;
    private TextView tvContent;
    private CallBack mCallBack;
    private Boolean needFormat = true;
    private int index;

    public NewMarkerView(Context context, int layoutResource, int[] lineColor, int chartNum) {
        super(context, layoutResource);
        this.lineColor = lineColor;
        this.chartNum = chartNum;
        tvContent = (TextView) findViewById(R.id.tv_content);
    }


    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        index = highlight.getDataSetIndex();//这个方法用于获得折线是哪根
        String values;
        if (e instanceof CandleEntry) {
            CandleEntry ce = (CandleEntry) e;
            values = "" + Utils.formatNumber(ce.getHigh(), 0, true);
        } else {
            if (!needFormat) {
                values = "" + e.getY();
            } else {
                values = "" + Utils.formatNumber(e.getY(), 0, true);
            }
        }

        if (mCallBack != null) {
            mCallBack.onCallBack(e.getX(), values);
        }
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
        MPPointF offset = getOffset();
        Chart chart = getChartView();
        float width = getWidth();
        float height = getHeight();
        // posY \posX 指的是markerView左上角点在图表上面的位置
        //处理Y方向
        if (posY <= height + ARROW_SIZE) {// 如果点y坐标小于markerView的高度，如果不处理会超出上边界，处理了之后这时候箭头是向上的，我们需要把图标下移一个箭头的大小
            offset.y = ARROW_SIZE;
        } else {//否则属于正常情况，因为我们默认是箭头朝下，然后正常偏移就是，需要向上偏移markerView高度和arrow size，再加一个stroke的宽度，因为你需要看到对话框的上面的边框
            offset.y = -height - ARROW_SIZE - STOKE_WIDTH; // 40 arrow height   5 stroke width
        }
        //处理X方向，分为3种情况，1、在图表左边 2、在图表中间 3、在图表右边
        //
        if (posX > chart.getWidth() - width) {//如果超过右边界，则向左偏移markerView的宽度
            if (posX + width / 2 > chart.getWidth()) {
                offset.x = chart.getWidth() - width - posX;
            } else if (posX - width / 2 < 0) {
                offset.x = -posX;
            } else {
                offset.x = -width / 2;
            }

        } else {//默认情况，不偏移（因为是点是在左上角）
            if (posX + width / 2 > chart.getWidth()) {
                offset.x = chart.getWidth() - width - posX;
            } else if (posX - width / 2 < 0) {
                offset.x = -posX;
            } else {
                offset.x = -width / 2;
            }
        }
        return offset;
    }

    @Override
    public void draw(Canvas canvas, float posX, float posY) {
        Paint paint = new Paint();//绘制边框的画笔
        paint.setStrokeWidth(STOKE_WIDTH);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        switch (chartNum) {
            case 2:
                if (index == 0) {
                    paint.setColor(lineColor[0]);
                } else {
                    paint.setColor(lineColor[1]);
                }
                break;
            case 1:
                paint.setColor(lineColor[0]);
                break;
        }

        Paint whitePaint = new Paint();//绘制底色白色的画笔
        whitePaint.setStyle(Paint.Style.FILL);
        whitePaint.setColor(Color.WHITE);

        Chart chart = getChartView();
        float width = getWidth();
        float height = getHeight();

        MPPointF offset = getOffsetForDrawingAtPoint(posX, posY);
        int saveId = canvas.save();

        Path path;
        if (posY < height + ARROW_SIZE) {//处理超过上边界
            path = new Path();
            path.moveTo(0, 0);
            if (posX > chart.getWidth() - width) {//超过右边界
                if (posX > posX + offset.x + width / 5 && posX < posX + offset.x + 4 * width / 5) {//在图表中间
                    path.lineTo(width / 2 - ARROW_SIZE / 2, 0);
                    path.lineTo(-offset.x, -ARROW_SIZE + CIRCLE_OFFSET);
                    path.lineTo(width / 2 + ARROW_SIZE / 2, 0);
                    LogUtils.e(1);
                } else {
                    path.lineTo(width - ARROW_SIZE, 0);
                    path.lineTo(-offset.x, -ARROW_SIZE + CIRCLE_OFFSET);
                    path.lineTo(width, 0);
                    LogUtils.e(2);
                }
            } else {
                if (posX > width / 4) {//在图表中间
                    path.lineTo(width / 2 - ARROW_SIZE / 2, 0);
                    path.lineTo(-offset.x, -ARROW_SIZE + CIRCLE_OFFSET);
                    path.lineTo(width / 2 + ARROW_SIZE / 2, 0);
                    LogUtils.e(3);
                } else {//超过左边界
                    path.lineTo(-offset.x, -ARROW_SIZE + CIRCLE_OFFSET);
                    path.lineTo(0 + ARROW_SIZE, 0);
                    LogUtils.e(4);
                }
            }
            path.lineTo(0 + width, 0);
            path.lineTo(0 + width, 0 + height);
            path.lineTo(0, 0 + height);
            path.lineTo(0, 0);
            path.offset(posX + offset.x, posY + offset.y);
        } else {//没有超过上边界
            path = new Path();
            path.moveTo(0, 0);
            path.lineTo(0 + width, 0);
            path.lineTo(0 + width, 0 + height);
            if (posX > chart.getWidth() - width) {
                if (posX > posX + offset.x + width / 5 && posX < posX + offset.x + 4 * width / 5) {
                    path.lineTo(width / 2 + ARROW_SIZE / 2, 0 + height);
                    path.lineTo(-offset.x, height + ARROW_SIZE - CIRCLE_OFFSET);
                    path.lineTo(width / 2 - ARROW_SIZE / 2, 0 + height);
                    path.lineTo(0, 0 + height);
                    LogUtils.e(5);
                } else {
                    path.lineTo(-offset.x, height + ARROW_SIZE - CIRCLE_OFFSET);
                    path.lineTo(width - ARROW_SIZE, 0 + height);
                    path.lineTo(0, 0 + height);
                    LogUtils.e(6);
                }
            } else {
                if (posX > width / 4) {
                    path.lineTo(width / 2 + ARROW_SIZE / 2, 0 + height);
                    path.lineTo(-offset.x, height + ARROW_SIZE - CIRCLE_OFFSET);
                    path.lineTo(width / 2 - ARROW_SIZE / 2, 0 + height);
                    path.lineTo(0, 0 + height);
                    LogUtils.e(7);
                } else {
                    path.lineTo(0 + ARROW_SIZE, 0 + height);
                    path.lineTo(-offset.x, height + ARROW_SIZE - CIRCLE_OFFSET);
                    path.lineTo(0, 0 + height);
                    LogUtils.e(8);
                }
            }
            path.lineTo(0, 0);
            path.offset(posX + offset.x, posY + offset.y);
        }

        // translate to the correct position and draw
        canvas.drawPath(path, whitePaint);
        canvas.drawPath(path, paint);
        canvas.translate(posX + offset.x, posY + offset.y);
        draw(canvas);
        canvas.restoreToCount(saveId);
    }


    public void setCallBack(CallBack callBack) {
        this.mCallBack = callBack;
    }

    public TextView getTvContent() {
        return tvContent;
    }

    public interface CallBack {
        void onCallBack(float x, String value);
    }
}
