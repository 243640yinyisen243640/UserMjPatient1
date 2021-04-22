package com.vice.bloodpressure.ui.activity.user;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.DialogUtils;
import com.vice.bloodpressure.utils.TextWatcherUtils;
import com.vice.bloodpressure.utils.edittext.IdNumberKeyListener;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 绑定身份证号
 * 作者: LYD
 * 创建日期: 2019/9/11 16:13
 */
public class BindIdNumberActivity extends BaseHandlerActivity {
    private static final int BIND_ID_CARD_SUCCESS = 10086;
    private static final int HAVE_BIND_ID_CARD = 10010;
    @BindView(R.id.et_id_number)
    EditText etIdNumber;
    @BindView(R.id.tv_sure)
    ColorTextView tvSure;
    private String idCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("绑定身份证");
        setTextChangeListener();
        etIdNumber.setKeyListener(new IdNumberKeyListener());
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_bind_id_number, contentLayout, false);
        return view;
    }

    /**
     * 设置监听
     */
    private void setTextChangeListener() {
        TextWatcherUtils.addTextChangedListener(new TextWatcherUtils.OnTextChangedListener() {
            @Override
            public void onTextChanged(String etString) {
                if (etString.length() > 0) {
                    tvSure.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
                    tvSure.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
                } else {
                    tvSure.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.gray_text));
                    tvSure.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.color_eb));
                }
            }
        }, etIdNumber);
    }

    /**
     * 已经绑定手机号
     */
    private void haveBindIdCard() {
        String message = "该身份证号已完成登记注册，若继续强制绑定则会将原身份证账号注销且数据清除不可恢复";
        DialogUtils.getInstance().showCommonDialog(getPageContext(), "", message,
                "继续绑定", "考虑考虑", new DialogUtils.DialogCallBack() {
                    @Override
                    public void execEvent() {
                        toDoBindIdCard("1");
                    }
                }, new DialogUtils.DialogCallBack() {
                    @Override
                    public void execEvent() {

                    }
                });
    }

    /**
     * 基本校验
     */
    private void toCheck() {
        idCard = etIdNumber.getText().toString().trim();
        if (TextUtils.isEmpty(idCard)) {
            ToastUtils.showShort("请输入身份证号码");
            return;
        }
        if (!RegexUtils.isIDCard18(idCard)) {
            ToastUtils.showShort("请输入合法的身份证号码");
            return;
        }
        showWarnCheckIdCard();

    }

    private void showWarnCheckIdCard() {
        String message = "身份证号一旦提交不可修改，请确认是否输入正确";
        DialogUtils.getInstance().showCommonDialog(getPageContext(), "", message,
                "再确认下", "确认", new DialogUtils.DialogCallBack() {
                    @Override
                    public void execEvent() {
                    }
                }, new DialogUtils.DialogCallBack() {
                    @Override
                    public void execEvent() {
                        toDoBindIdCard("");
                    }
                });
    }

    /**
     * 绑定身份证号
     *
     * @param isYes 是否强制绑定
     */
    private void toDoBindIdCard(String isYes) {
        HashMap map = new HashMap<>();
        map.put("idcard", idCard);
        map.put("isyes", isYes);
        XyUrl.okPostSave(XyUrl.BIND_ID_NUMBER, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                Message message = getHandlerMessage();
                message.what = BIND_ID_CARD_SUCCESS;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
                if (30009 == error) {
                    Message message = getHandlerMessage();
                    message.what = HAVE_BIND_ID_CARD;
                    sendHandlerMessage(message);
                } else {
                    ToastUtils.showShort(errorMsg);
                }
            }
        });

    }

    @OnClick(R.id.tv_sure)
    public void onViewClicked() {
        toCheck();
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case HAVE_BIND_ID_CARD:
                haveBindIdCard();
                break;
            case BIND_ID_CARD_SUCCESS:
                //成功
                ToastUtils.showShort("获取成功");
                //身份证号绑定修改
                LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
                user.setIdcard(idCard);
                SharedPreferencesUtils.putBean(this, SharedPreferencesUtils.USER_INFO, user);
                //身份证号绑定修改
                EventBusUtils.post(new EventMessage<>(ConstantParam.BIND_ID_NUMBER, idCard));
                finish();
                break;
        }
    }
}
