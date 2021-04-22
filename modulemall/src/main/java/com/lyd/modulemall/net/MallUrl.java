package com.lyd.modulemall.net;


import com.lyd.baselib.BuildConfig;

import rxhttp.wrapper.annotation.DefaultDomain;

/**
 * 描述: RxHttp Url开始
 * 作者: LYD
 * 创建日期: 2019/12/25 13:37
 */
public class MallUrl {
    //环境
    private final static boolean EXTERNAL_RELEASE = BuildConfig.ENVIRONMENT;
    //正式地址
    private final static String DOMAIN = "https://wt.app.wotongsoft.com";
    //测试地址
    private final static String DOMAIN_TEST = "http://d.xiyuns.cn";
    //设置为默认域名
    @DefaultDomain()
    public static String HOST_URL = EXTERNAL_RELEASE ? DOMAIN : DOMAIN_TEST;

    //首页开始
    //首页数据（轮播图，分类，活动）
    public final static String MALL_HOME_INDEX = HOST_URL + "/nsindex";
    //商品列表（首页精品，分类，全部，活动商品列表）
    public final static String GET_GOODS_LIST = HOST_URL + "/nsgoodslist";
    //商品所有标签（用于查询）
    public final static String GET_GOODS_LABEL_LIST = HOST_URL + "/nsgoodslabel";
    //首页结束

    //优惠券开始
    //优惠劵列表
    public final static String GET_COUPON_LIST = HOST_URL + "/nscouponlist";
    //我的优惠券列表
    public final static String GET_MY_COUPON_LIST = HOST_URL + "/nsowncoupon";
    //领取优惠劵
    public final static String GET_COUPON = HOST_URL + "/nsgetcoupons";
    //优惠券结束


    //商品开始
    //商品详情
    public final static String GET_PRODUCT_DETAIL = HOST_URL + "/nsgoodsdetail";
    //商品详情下的供货商资质
    public final static String GET_PRODUCT_SUPPLIER = HOST_URL + "/nsgoodsqualification";
    //商品规格
    public final static String GET_PRODUCT_SKU = HOST_URL + "/nsgoodssku";
    //商品结束

    //收藏开始
    //我的收藏
    public final static String GET_GOODS_COLLECTION_LIST = HOST_URL + "/nsowncollect";
    //编辑商品收藏
    public final static String EDIT_GOODS_COLLECTION = HOST_URL + "/nseditcollect";
    //收藏结束

    //用户收货地址开始
    //添加/编辑收货地址
    public final static String ADD_OR_EDIT_ADDRESS = HOST_URL + "/nsadduseraddress";
    //编辑收货地址页详情
    public final static String GET_ADDRESS_DETAIL = HOST_URL + "/nsedituseraddress";
    //删除收货地址
    public final static String DEL_ADDRESS = HOST_URL + "/nsdeluseraddress";
    //收货地址列表
    public final static String GET_ADDRESS_LIST = HOST_URL + "/nsuseraddress";
    //获取区域(获取省市县三级)
    public final static String GET_AREAS = HOST_URL + "/nsareas";
    //用户收货地址结束

    //支付开始
    //结算页面
    public final static String GET_CONFIRM_ORDER_DETAIL = HOST_URL + "/nsorderinfo";
    //创建订单（立即支付）
    public final static String CREATE_ORDER= HOST_URL + "/nsordercreate";
    //选择支付页面数据
    public final static String GET_ORDER_PAY_DATA= HOST_URL + "/nsselectpaytype ";
    //支付
    public final static String ORDER_PAY = HOST_URL + "/nsorderpay";
    //支付结束

    //购物车开始
    //添加购物车
    public final static String ADD_TO_SHOPPING_CART = HOST_URL + "/nsaddcart";
    //删除购物车商品
    public final static String DEL_SHOPPING_CART_PRODUCT = HOST_URL + "/nsdelcart";
    //编辑购物车商品数量
    public final static String EDIT_SHOPPING_CART_PRODUCT_COUNT = HOST_URL + "/nseditcart";
    //购物车列表
    public final static String GET_SHOPPING_CART_LIST = HOST_URL + "/nsowncart";
    //购物车结束


    //订单开始
    //订单列表
    public final static String GET_ORDER_LIST = HOST_URL + "/nsorderlist";
    //删除订单
    public final static String DEL_ORDER = HOST_URL + "/nsdelorder";
    //取消订单（待支付能取消）
    public final static String CANCEL_ORDER = HOST_URL + "/nscancelorder";
    //确认收货
    public final static String CONFIRM_ORDER = HOST_URL + "/nsorderconfirmrecieve";
    //订单详情页
    public final static String GET_ORDER_DETAIL = HOST_URL + "/nsorderdetail";
    //订单商品添加购物车
    public final static String ORDER_ADD_CART = HOST_URL + "/nsaddcartorder";
    //订单更换地址
    public final static String ORDER_CHANGE_ADDRESS = HOST_URL + "/nsupdateorderaddress";
    //获取时时物流信息
    public final static String GET_ORDER_LOGISTICS = HOST_URL + "/nsgetorderlogistics";
    //订单结束

    //退款订单
    //退款商品订单列表
    public final static String GET_REFUND_ORDER_LIST = HOST_URL + "/nsrefundorderlist";
    //退款商品订单删除
    public final static String DEL_REFUND_GOODS = HOST_URL + "/nsdelrefundgoods";
    //商品申请退款页数据
    public final static String REFUND_ORDER_GOODS = HOST_URL + "/nsrefundordergoods";
    //图片上传
    public final static String UPLOAD_IMAGE = HOST_URL + "/nsuploadimg";
    //订单商品申请退款/申请修改
    public final static String REFUND_ORDER = HOST_URL + "/nsrefundorder";
    //退货订单详情
    public final static String REFUND_GOODS_DETAIL = HOST_URL + "/nsrefundgoodsdetail";
    //根据物流单号获取物流公司信息
    public final static String LOGISTICS_COMPANY = HOST_URL + "/nslogisticscompany";
    //退货添加退货物流信息
    public final static String ADD_LOGISTICS = HOST_URL + "/nsaddlogistics";
    //退款撤销申请
    public final static String REVOCATION_REFUND = HOST_URL + "/nsrevocationrefund";
    //退款订单

}
