package com.vice.bloodpressure.ui.activity.healthrecordlist;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;

public class BmiDetailActivity extends BaseActivity {

    private String string1, string2, string3, string4, string5, string6;

    private TextView tvBmiOne, tvBmiTwo, tvBmiThird, tvBmiFour, tvBmiFive, tvBmiSix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("什么是BMI");
        initString();
        initViews();
        setTextViewText();
    }

    private void initString() {
        string1 = getString(R.string.BMI_index);
        string2 = getString(R.string.BMI_formula);
        string3 = getString(R.string.low_weight);
        string4 = getString(R.string.normal_weight);
        string5 = getString(R.string.over_height);
        string6 = getString(R.string.fat);
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_what_is_bmi, contentLayout, false);
    }

    private void initViews() {
        tvBmiOne = findViewById(R.id.tv_bmi_one);
        tvBmiTwo = findViewById(R.id.tv_bmi_two);
        tvBmiThird = findViewById(R.id.tv_bmi_third);
        tvBmiFour = findViewById(R.id.tv_bmi_four);
        tvBmiFive = findViewById(R.id.tv_bmi_five);
        tvBmiSix = findViewById(R.id.tv_bmi_six);
    }


    private void setTextViewText() {
        StyleSpan style_BOLD = new StyleSpan(Typeface.BOLD);

        SpannableString string_1 = new SpannableString(string1);
        string_1.setSpan(style_BOLD, 0, 6, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvBmiOne.setText(string_1);

        SpannableString string_2 = new SpannableString(string2);
        string_2.setSpan(style_BOLD, 0, 3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvBmiTwo.setText(string_2);

        SpannableString string_3 = new SpannableString(string3);
        string_3.setSpan(style_BOLD, 0, 3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvBmiThird.setText(string_3);

        SpannableString string_4 = new SpannableString(string4);
        string_4.setSpan(style_BOLD, 0, 4, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvBmiFour.setText(string_4);

        SpannableString string_5 = new SpannableString(string5);
        string_5.setSpan(style_BOLD, 0, 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvBmiFive.setText(string_5);

        SpannableString string_6 = new SpannableString(string6);
        string_6.setSpan(style_BOLD, 0, 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvBmiSix.setText(string_6);

    }


}
