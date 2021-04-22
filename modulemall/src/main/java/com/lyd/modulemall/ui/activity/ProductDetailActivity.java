package com.lyd.modulemall.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.utils.DisableDisplayDpiChangeUtils;
import com.lyd.baselib.utils.TurnsUtils;
import com.lyd.baselib.utils.engine.GlideImageEngine;
import com.lyd.modulemall.R;
import com.lyd.modulemall.adapter.MallProductDetailBannerAdapter;
import com.lyd.modulemall.bean.ProductBean;
import com.lyd.modulemall.bean.ProductCollectBean;
import com.lyd.modulemall.bean.ProductDetailBean;
import com.lyd.modulemall.bean.ProductSkuBean;
import com.lyd.modulemall.constant.MallConstantParam;
import com.lyd.modulemall.databinding.ActivityProductDetailBinding;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.net.OnError;
import com.lyd.modulemall.ui.BaseWebViewActivity;
import com.lyd.modulemall.ui.activity.pay.ConfirmOrderActivity;
import com.lyd.modulemall.ui.activity.shoppingcart.ShoppingCartActivity;
import com.lyd.modulemall.ui.activity.supplier.SupplierQualificationActivity;
import com.lyd.modulemall.view.ProductSkuDialog;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.rxjava.rxlife.RxLife;
import com.wuhenzhizao.sku.bean.Sku;
import com.wuhenzhizao.sku.bean.SkuAttribute;
import com.zackratos.ultimatebarx.library.UltimateBarX;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.PageStyle;
import com.zhpan.indicator.enums.IndicatorStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;
import liys.click.AClickStr;
import liys.click.OnClickUtils;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  商品详情页
 * 作者: LYD
 * 创建日期: 2020/12/23 14:35
 */
public class ProductDetailActivity extends AppCompatActivity implements BannerViewPager.OnPageClickListener {
    private static final String TAG = "ProductDetailActivity";
    private ActivityProductDetailBinding productDetailBinding;
    private int productId;
    private int supplier_id;
    private int collect_type;
    private List<String> goods_img_url;
    private ProductSkuDialog skuDialog;
    private ProductSkuBean mySkuBean;
    private ProductDetailBean productDetailBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skuDialog = new ProductSkuDialog(this);
        productDetailBinding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(productDetailBinding.getRoot());
        //必须在setContentView之后调用
        OnClickUtils.init(this);
        initStatusBar();
        productId = getIntent().getIntExtra("goods_id", 1);
        //获取商品详情
        getProductDetail();
        //获取商品规格
        getProductSku();
        DisableDisplayDpiChangeUtils.disabledDisplayDpiChange(this);
    }


    private void initStatusBar() {
        UltimateBarX.with(this)
                .transparent()
                .applyStatusBar();
    }


    /**
     * 获取商品详情
     */
    private void getProductDetail() {
        HashMap map = new HashMap<>();
        map.put("goods_id", productId);
        RxHttp.postForm(MallUrl.GET_PRODUCT_DETAIL)
                .addAll(map)
                .asResponse(ProductDetailBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<ProductDetailBean>() {
                    @Override
                    public void accept(ProductDetailBean data) throws Exception {
                        productDetailBean = data;
                        //退款说明
                        String return_desc = data.getReturn_desc();
                        productDetailBinding.tvRefundDescText.setText(return_desc);
                        //收藏
                        collect_type = data.getCollect_type();
                        if (1 == collect_type) {
                            Drawable drawableLeft = getResources().getDrawable(R.drawable.product_detail_favo_yes);
                            productDetailBinding.tvFavo.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);
                        } else {
                            Drawable drawableLeft = getResources().getDrawable(R.drawable.product_detail_favo_no);
                            productDetailBinding.tvFavo.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);
                        }
                        //设置图片轮播
                        goods_img_url = data.getGoods_img_url();
                        setBanner();
                        //设置商品信息
                        String price = data.getPrice();
                        String market_price = data.getMarket_price();
                        String goods_name = data.getGoods_name();
                        int sales = data.getTotal_sales();
                        productDetailBinding.tvCurrentPrice.setText(price);
                        productDetailBinding.tvOriginalPrice.setText(market_price);
                        productDetailBinding.tvOriginalPrice.setPaintFlags(productDetailBinding.tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        productDetailBinding.tvProductName.setText(goods_name);
                        productDetailBinding.tvSaleCount.setText("已售" + sales + "件");
                        //设置发货信息
                        String shipments_address = data.getShipments_address();
                        String shipping_default_money = data.getShipping_default_money();
                        productDetailBinding.tvPlaceOfDispatch.setText(shipments_address);
                        productDetailBinding.tvPostage.setText("快递:" + shipping_default_money);
                        //获取供应商资质
                        supplier_id = data.getSupplier_id();

                        String description = data.getDescription();
                        productDetailBinding.webDetail.loadUrl(description);
                        //使用WebView加载网页,而不是浏览器
                        productDetailBinding.webDetail.setWebViewClient(getWebViewClient());
                        productDetailBinding.webDetail.setWebChromeClient(getWebChromeClient());
                        //商品状态 1上架，2下架
                        int state = data.getState();
                        if (2 == state) {
                            productDetailBinding.tvSoldOutDesc.setVisibility(View.VISIBLE);
                        } else {
                            productDetailBinding.tvSoldOutDesc.setVisibility(View.GONE);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });

    }

    /**
     * 设置轮播图
     *
     * @param goods_img_url
     */
    @SuppressWarnings("all")
    private void setBanner() {
        productDetailBinding.bannerView.setVisibility(View.VISIBLE);
        //初始化属性
        productDetailBinding.bannerView.
                setPageStyle(PageStyle.NORMAL)
                .setPageMargin(0)
                .setRevealWidth(0)

                .setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setIndicatorSliderColor(ColorUtils.getColor(R.color.color_dc), ColorUtils.getColor(R.color.main_green))
                .setIndicatorVisibility(View.VISIBLE)

                .setLifecycleRegistry(getLifecycle())
                .setOnPageClickListener(this)
                .setAdapter(new MallProductDetailBannerAdapter())
                .setInterval(3000)
                .create(goods_img_url);
    }

    /**
     * 获取商品规格
     */
    private void getProductSku() {
        HashMap map = new HashMap<>();
        map.put("goods_id", productId);
        RxHttp.postForm(MallUrl.GET_PRODUCT_SKU)
                .addAll(map)
                .asResponse(ProductSkuBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<ProductSkuBean>() {
                    @Override
                    public void accept(ProductSkuBean data) throws Exception {
                        //showChooseGoodsPopup = new ShowChooseGoodsPopup(ProductDetailActivity.this);
                        //showChooseGoodsPopup.setData("0", data);
                        //设置sku
                        mySkuBean = data;
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    /**
     * 点击图片 图片轮播
     *
     * @param clickedView
     * @param position
     */
    @Override
    public void onPageClick(View clickedView, int position) {
        MNImageBrowser.with(ProductDetailActivity.this)
                .setCurrentPosition(position)
                .setImageEngine(new GlideImageEngine())
                .setImageList((ArrayList<String>) goods_img_url)
                .setIndicatorHide(false)
                .setFullScreenMode(true)
                .show(clickedView);
    }


    @Override
    protected void onResume() {
        productDetailBinding.webDetail.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        productDetailBinding.webDetail.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        productDetailBinding.webDetail.onDestroy();
        super.onDestroy();
    }

    /**
     * 处理各种通知 & 请求事件
     *
     * @return
     */
    private WebViewClient getWebViewClient() {
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //Log.e(TAG, "拦截 url 跳转,在里边添加点击链接跳转或者操作");
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //Log.e(TAG, "开始加载网页回调");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }
        };
        return webViewClient;
    }

    /**
     * 辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等
     *
     * @return
     */
    private WebChromeClient getWebChromeClient() {
        WebChromeClient webChromeClient = new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            /**
             * 收到加载进度变化
             */
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        };
        return webChromeClient;
    }


    /**
     * 添加至购物车
     *
     * @param skuId
     * @param count
     */
    private void addToCart(String skuId, int count) {
        HashMap map = new HashMap<>();
        map.put("goods_id", productId);
        map.put("goods_sku_id", skuId);
        map.put("num", count);
        RxHttp.postForm(MallUrl.ADD_TO_SHOPPING_CART)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String data) throws Exception {
                        ToastUtils.showShort("添加成功");
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    @AClickStr({"img_back", "img_cart", "tv_favo", "tv_chat", "ll_supplier_qualification", "tv_add_cart", "tv_buy_quick", "ll_select_specification"})
    public void click(View view, String viewId) {
        Intent intent = null;
        switch (viewId) {
            case "img_back":
                finish();
                break;
            case "img_cart":
                intent = new Intent(this, ShoppingCartActivity.class);
                startActivity(intent);
                break;
            case "tv_favo":
                toFavo();
                break;
            case "tv_chat":
                intent = new Intent(this, BaseWebViewActivity.class);
                intent.putExtra("title", "客服");
                intent.putExtra("url", MallConstantParam.SERVICE_URL);
                startActivity(intent);
                break;
            case "ll_supplier_qualification":
                intent = new Intent(this, SupplierQualificationActivity.class);
                intent.putExtra("supplier_id", supplier_id);
                startActivity(intent);
                break;
            case "tv_add_cart":
            case "tv_buy_quick":
            case "ll_select_specification":
                Log.e(TAG, "被点击");
                showSkuDialog();
                //                //选择规格
                //                showChooseGoodsPopup.showPopupWindow();
                //                showChooseGoodsPopup.setOnChooseGoodsOKListener(new ShowChooseGoodsPopup.OnChooseGoodsOKListener() {
                //                    @Override
                //                    public void onChooseGoodsOkListener(int skuId, int count, int type) {
                //                        showChooseGoodsPopup.dismiss();
                //                        if (1 == type) {
                //                            //添加购物车
                //                            addToCart(skuId, count);
                //                        } else {
                //                            Intent intent = new Intent(ProductDetailActivity.this, ConfirmOrderActivity.class);
                //                            Bundle bundle = new Bundle();
                //                            bundle.putString("goods_id", productId + "");
                //                            bundle.putString("goods_sku_id", skuId + "");
                //                            bundle.putString("num", count + "");
                //                            bundle.putString("order_tag", "buy_now");
                //                            intent.putExtras(bundle);
                //                            startActivity(intent);
                //                        }
                //                    }
                //                });
                break;
        }
    }

    private void showSkuDialog() {
        //主设置
        ProductBean product = new ProductBean();
        product.setMainImage(productDetailBean.getGoods_img_url().get(0));
        product.setSellingPrice(TurnsUtils.getDouble(productDetailBean.getPrice(), 0));
        product.setMeasurementUnit("件");
        product.setStockQuantity(productDetailBean.getStock());
        //设置sku
        ArrayList<Sku> listSku = new ArrayList<>();
        for (int i = 0; i < mySkuBean.getList().size(); i++) {
            Sku sku = new Sku();
            sku.setId(mySkuBean.getList().get(i).getGoods_sku_id() + "");
            sku.setMainImage(mySkuBean.getList().get(i).getSku_img());
            sku.setStockQuantity(mySkuBean.getList().get(i).getStock());
            sku.setSellingPrice(mySkuBean.getList().get(i).getPrice());
            //设置attrs
            ArrayList<SkuAttribute> listSkuAttr = new ArrayList<>();
            for (int j = 0; j < mySkuBean.getSpec().size(); j++) {
                SkuAttribute skuAttribute = new SkuAttribute();
                skuAttribute.setKey(mySkuBean.getSpec().get(j).getKey());
                skuAttribute.setValue(mySkuBean.getList().get(i).getSpec_value().get(j));
                listSkuAttr.add(skuAttribute);
            }
            sku.setAttributes(listSkuAttr);
            listSku.add(sku);
        }
        product.setSkus(listSku);
        Log.e(TAG, "listSku==" + listSku);
        skuDialog.setData(product, new ProductSkuDialog.Callback() {
            @Override
            public void onAdded(Sku sku, int count, int type) {
                if (1 == type) {
                    addToCart(sku.getId(), count);
                } else {
                    Intent intent = new Intent(ProductDetailActivity.this, ConfirmOrderActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("goods_id", productId + "");
                    bundle.putString("goods_sku_id", sku.getId() + "");
                    bundle.putString("num", count + "");
                    bundle.putString("order_tag", "buy_now");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        skuDialog.show();
    }

    private void toFavo() {
        HashMap map = new HashMap<>();
        map.put("goods_id", productId);
        map.put("collect_type", collect_type);
        RxHttp.postForm(MallUrl.EDIT_GOODS_COLLECTION)
                .addAll(map)
                .asResponse(ProductCollectBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<ProductCollectBean>() {
                    @Override
                    public void accept(ProductCollectBean data) throws Exception {
                        getProductDetail();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {
                        getProductDetail();
                    }
                });
    }

//    /**
//     * 重写 getResource 方法，防止系统字体影响
//     * 禁止app字体大小跟随系统字体大小调节
//     */
//    @Override
//    public Resources getResources() {
//        Resources resources = super.getResources();
//        if (resources != null && resources.getConfiguration().fontScale != 1.0f) {
//            Configuration configuration = resources.getConfiguration();
//            configuration.setToDefaults();
//            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
//        }
//        return resources;
//    }
}