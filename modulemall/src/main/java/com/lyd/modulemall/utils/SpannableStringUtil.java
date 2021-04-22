package com.lyd.modulemall.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.blankj.utilcode.util.PhoneUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述: 电话高亮以及点击跳转到拨号
 * 作者: LYD
 * 创建日期: 2021/3/2 16:51
 */
public class SpannableStringUtil {

    public static SpannableString convertTelUrl(final String strTel) {
        SpannableString ss = new SpannableString(strTel);
        final List<String> list = getNumbers(strTel);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                int start = strTel.indexOf(list.get(i));
                final int finalI = i;
                ss.setSpan(new ClickableSpan() {
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(Color.RED);
                        ds.setUnderlineText(true);
                    }

                    @Override
                    public void onClick(View widget) {
                        PhoneUtils.dial(list.get(finalI));
                    }
                }, start, start + (list.get(i).length()), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return ss;
    }

    /**
     * 从字符串中查找电话号码字符串
     */
    private static List<String> getNumbers(String content) {
        List<String> telPhoneList = new ArrayList<>();
        Pattern pattern = Pattern.compile("(\\d{11})");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            telPhoneList.add(matcher.group());
        }
        return telPhoneList;
    }
}
