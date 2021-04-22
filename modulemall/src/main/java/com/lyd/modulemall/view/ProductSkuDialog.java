package com.lyd.modulemall.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.ProductBean;
import com.lyd.modulemall.databinding.DialogProductSkuBinding;
import com.lyd.modulemall.utils.NumberUtils;
import com.wuhenzhizao.sku.bean.Sku;
import com.wuhenzhizao.sku.bean.SkuAttribute;
import com.wuhenzhizao.sku.view.OnSkuListener;
import com.wuhenzhizao.titlebar.utils.AppUtils;

import java.util.List;

/**
 * Created by liufei on 2017/11/30.
 */
public class ProductSkuDialog extends Dialog {
    private DialogProductSkuBinding binding;
    private Context context;
    private ProductBean product;
    private List<Sku> skuList;
    private Callback callback;

    private Sku selectedSku;
    private String priceFormat;
    private String stockQuantityFormat;

    public ProductSkuDialog(@NonNull Context context) {
        this(context, R.style.CommonBottomDialogStyle);
    }

    public ProductSkuDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
        initView();
    }

    private void initView() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_product_sku, null, false);
        setContentView(binding.getRoot());
        binding.ibSkuClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        binding.btnSkuQuantityMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = binding.etSkuQuantityInput.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt > 1) {
                    String newQuantity = String.valueOf(quantityInt - 1);
                    binding.etSkuQuantityInput.setText(newQuantity);
                    binding.etSkuQuantityInput.setSelection(newQuantity.length());
                    updateQuantityOperator(quantityInt - 1);
                }
            }
        });
        binding.btnSkuQuantityPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = binding.etSkuQuantityInput.getText().toString();
                if (TextUtils.isEmpty(quantity) || selectedSku == null) {
                    return;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt < selectedSku.getStockQuantity()) {
                    String newQuantity = String.valueOf(quantityInt + 1);
                    binding.etSkuQuantityInput.setText(newQuantity);
                    binding.etSkuQuantityInput.setSelection(newQuantity.length());
                    updateQuantityOperator(quantityInt + 1);
                }
            }
        });
        binding.etSkuQuantityInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId != EditorInfo.IME_ACTION_DONE || selectedSku == null) {
                    return false;
                }
                String quantity = binding.etSkuQuantityInput.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return false;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt <= 1) {
                    binding.etSkuQuantityInput.setText("1");
                    binding.etSkuQuantityInput.setSelection(1);
                    updateQuantityOperator(1);
                } else if (quantityInt >= selectedSku.getStockQuantity()) {
                    String newQuantity = String.valueOf(selectedSku.getStockQuantity());
                    binding.etSkuQuantityInput.setText(newQuantity);
                    binding.etSkuQuantityInput.setSelection(newQuantity.length());
                    updateQuantityOperator(selectedSku.getStockQuantity());
                } else {
                    binding.etSkuQuantityInput.setSelection(quantity.length());
                    updateQuantityOperator(quantityInt);
                }
                return false;
            }
        });
        binding.scrollSkuList.setListener(new OnSkuListener() {
            @Override
            public void onUnselected(SkuAttribute unselectedAttribute) {
                selectedSku = null;
                //GImageLoader.displayUrl(context, binding.ivSkuLogo, product.getMainImage());
                Glide.with(Utils.getApp()).load(product.getMainImage()).into(binding.ivSkuLogo);
                binding.tvSkuQuantity.setText(String.format(stockQuantityFormat, product.getStockQuantity()));
                String firstUnselectedAttributeName = binding.scrollSkuList.getFirstUnelectedAttributeName();
                binding.tvSkuInfo.setText("请选择：" + firstUnselectedAttributeName);
                //binding.btnSubmit.setEnabled(false);
                String quantity = binding.etSkuQuantityInput.getText().toString();
                if (!TextUtils.isEmpty(quantity)) {
                    updateQuantityOperator(Integer.valueOf(quantity));
                } else {
                    updateQuantityOperator(0);
                }
            }

            @Override
            public void onSelect(SkuAttribute selectAttribute) {
                String firstUnselectedAttributeName = binding.scrollSkuList.getFirstUnelectedAttributeName();
                binding.tvSkuInfo.setText("请选择：" + firstUnselectedAttributeName);
            }

            @Override
            public void onSkuSelected(Sku sku) {
                selectedSku = sku;
                Glide.with(Utils.getApp()).load(product.getMainImage()).into(binding.ivSkuLogo);
                List<SkuAttribute> attributeList = selectedSku.getAttributes();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < attributeList.size(); i++) {
                    if (i != 0) {
                        builder.append("　");
                    }
                    SkuAttribute attribute = attributeList.get(i);
                    builder.append("\"" + attribute.getValue() + "\"");
                }
                binding.tvSkuInfo.setText("已选：" + builder.toString());
                binding.tvSkuQuantity.setText(String.format(stockQuantityFormat, selectedSku.getStockQuantity()));
                //binding.btnSubmit.setEnabled(true);
                String quantity = binding.etSkuQuantityInput.getText().toString();
                if (!TextUtils.isEmpty(quantity)) {
                    updateQuantityOperator(Integer.valueOf(quantity));
                } else {
                    updateQuantityOperator(0);
                }
            }
        });
        //添加购物车
        binding.tvGoodsAddShopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedSku == null) {
                    ToastUtils.showShort("请选择商品属性");
                    return;
                }
                String quantity = binding.etSkuQuantityInput.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt > 0 && quantityInt <= selectedSku.getStockQuantity()) {
                    callback.onAdded(selectedSku, quantityInt, 1);
                    dismiss();
                } else {
                    ToastUtils.showShort("商品数量超出库存，请修改数量");
                }
            }
        });
        //立即购买
        binding.tvGoodsBuyAtOnce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedSku == null) {
                    ToastUtils.showShort("请选择商品属性");
                    return;
                }
                String quantity = binding.etSkuQuantityInput.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt > 0 && quantityInt <= selectedSku.getStockQuantity()) {
                    callback.onAdded(selectedSku, quantityInt, 2);
                    dismiss();
                } else {
                    ToastUtils.showShort("商品数量超出库存，请修改数量");
                    //Toast.makeText(getContext(), "商品数量超出库存，请修改数量", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setData(final ProductBean product, Callback callback) {
        this.product = product;
        this.skuList = product.getSkus();
        this.callback = callback;
        priceFormat = context.getString(R.string.comm_price_format);
        stockQuantityFormat = context.getString(R.string.product_detail_sku_stock);
        updateSkuData();
        updateQuantityOperator(1);
    }

    private void updateSkuData() {
        binding.scrollSkuList.setSkuList(product.getSkus());
        Sku firstSku = product.getSkus().get(0);
        if (firstSku.getStockQuantity() > 0) {
            selectedSku = firstSku;
            //选中第一个sku
            binding.scrollSkuList.setSelectedSku(selectedSku);
            Glide.with(Utils.getApp()).load(product.getMainImage()).into(binding.ivSkuLogo);
            binding.tvSkuSellingPrice.setText(String.format(priceFormat, NumberUtils.formatNumber(selectedSku.getSellingPrice())));
            binding.tvSkuSellingPriceUnit.setText("/" + product.getMeasurementUnit());
            binding.tvSkuQuantity.setText(String.format(stockQuantityFormat, selectedSku.getStockQuantity()));
            //binding.btnSubmit.setEnabled(selectedSku.getStockQuantity() > 0);
            List<SkuAttribute> attributeList = selectedSku.getAttributes();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < attributeList.size(); i++) {
                if (i != 0) {
                    builder.append("　");
                }
                SkuAttribute attribute = attributeList.get(i);
                builder.append("\"" + attribute.getValue() + "\"");
            }
            binding.tvSkuInfo.setText("已选：" + builder.toString());
        } else {
            Glide.with(Utils.getApp()).load(product.getMainImage()).into(binding.ivSkuLogo);
            binding.tvSkuSellingPrice.setText(String.format(priceFormat, NumberUtils.formatNumber(product.getSellingPrice() / 100)));
            binding.tvSkuSellingPriceUnit.setText("/" + product.getMeasurementUnit());
            binding.tvSkuQuantity.setText(String.format(stockQuantityFormat, product.getStockQuantity()));
            //binding.btnSubmit.setEnabled(false);
            binding.tvSkuInfo.setText("请选择：" + skuList.get(0).getAttributes().get(0).getKey());
        }
    }

    private void updateQuantityOperator(int newQuantity) {
        if (selectedSku == null) {
            binding.btnSkuQuantityMinus.setEnabled(false);
            binding.btnSkuQuantityPlus.setEnabled(false);
            binding.etSkuQuantityInput.setEnabled(false);
        } else {
            if (newQuantity <= 1) {
                binding.btnSkuQuantityMinus.setEnabled(false);
                binding.btnSkuQuantityPlus.setEnabled(true);
            } else if (newQuantity >= selectedSku.getStockQuantity()) {
                binding.btnSkuQuantityMinus.setEnabled(true);
                binding.btnSkuQuantityPlus.setEnabled(false);
            } else {
                binding.btnSkuQuantityMinus.setEnabled(true);
                binding.btnSkuQuantityPlus.setEnabled(true);
            }
            binding.etSkuQuantityInput.setEnabled(true);
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        //解决键盘遮挡输入框问题
        Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.getDecorView().setPadding(0, 0, 0, 0);
        AppUtils.transparencyBar(getWindow());
    }


    public interface Callback {
        void onAdded(Sku sku, int quantity, int type);
    }
}
