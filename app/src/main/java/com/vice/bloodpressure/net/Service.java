package com.vice.bloodpressure.net;

import com.allen.library.bean.BaseData;
import com.vice.bloodpressure.bean.CheckAdviceBean;
import com.vice.bloodpressure.bean.DepartmentEntity;
import com.vice.bloodpressure.bean.DietPlanChangeBean;
import com.vice.bloodpressure.bean.DietPlanFoodChildBean;
import com.vice.bloodpressure.bean.DietPlanFoodListBean;
import com.vice.bloodpressure.bean.DietPlanResultBean;
import com.vice.bloodpressure.bean.DoctorEntity;
import com.vice.bloodpressure.bean.FamilyMemberBean;
import com.vice.bloodpressure.bean.HospitalEntity;
import com.vice.bloodpressure.bean.InHospitalDetailEntity;
import com.vice.bloodpressure.bean.InHospitalEntity;
import com.vice.bloodpressure.bean.PrivacyBean;
import com.vice.bloodpressure.bean.RecipeDetailBean;
import com.vice.bloodpressure.bean.SevenAndThirtyBloodSugarListBean;
import com.vice.bloodpressure.bean.SignProtocolBean;
import com.vice.bloodpressure.bean.SugarListBean;
import com.vice.bloodpressure.bean.SugarSearchBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Service {

    /**
     * 处理状态以及处理完后的 建议查看
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("port/userdata/checkAdvice")
    Observable<BaseData<CheckAdviceBean>> checkAdvice(@Field("access_token") String access_token, @Field("id") String id);


    /**
     * 时间模糊搜索血糖记录
     *
     * @param userid
     * @param starttime
     * @param starttime
     * @return
     */
    @FormUrlEncoded
    @POST("port/userdata/bloodglucoseSearch")
    Observable<BaseData<SugarSearchBean>> getRxSugarSearchList(
            @Field("access_token") String access_token,
            @Field("userid") String userid,
            @Field("starttime") String starttime,
            @Field("endtime") String endtime,@Field("version") String version);

    /**
     * 7天和30天血糖记录
     *
     * @param userid
     * @return
     */
    @FormUrlEncoded
    @POST("port/userdata/bloodglucose")
    Observable<BaseData<SevenAndThirtyBloodSugarListBean>> getSevenAndThirtyBloodSugar(
            @Field("access_token") String access_token,
            @Field("userid") String userid,@Field("version") String version );

    /**
     * 每个时间点血糖测量详情
     *
     * @param userid
     * @param time
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("port/userdata/bloodglucosedetail")
    Observable<BaseData<List<SugarListBean>>> getSugarList(
            @Field("access_token") String access_token,
            @Field("userid") String userid,
            @Field("time") String time,
            @Field("type") String type);


    /**
     * 删除用药记录
     *
     * @param access_token
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("port/Personal/delmedicine")
    Observable<BaseData> delMedicine(
            @Field("access_token") String access_token,
            @Field("id") String id);


    /**
     * 肝病上传
     *
     * @param
     * @param files
     * @return
     */
    @Multipart
    @POST("/port/Record/addLivers")
    Observable<String> uploadFiles(@Part List<MultipartBody.Part> files);


    /**
     * 肝病上传
     *
     * @param
     * @param files
     * @return
     */
    @Multipart
    @POST("/addFeedback")
    Observable<String> addFeedBack(@Part List<MultipartBody.Part> files);




    /**
     * 智能饮食开始
     */

    /**
     * 饮食方案详情
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("/port/Food/dietPlanDetail")
    Observable<BaseData<DietPlanResultBean>> getDietPlantDetail(
            @Field("access_token") String access_token,
            @Field("id") int id);


    /**
     * 饮食方案换一换
     *
     * @param access_token
     * @param id
     * @param day
     * @return
     */
    @FormUrlEncoded
    @POST("/port/Food/dietPlanChange")
    Observable<BaseData<DietPlanChangeBean>> dietPlanChange(
            @Field("access_token") String access_token,
            @Field("id") int id,
            @Field("day") String day);


    /**
     * 生成七天饮食
     *
     * @param access_token
     * @param id
     * @param day
     * @return
     */
    @FormUrlEncoded
    @POST("/port/Food/dietPlandayAdd")
    Observable<BaseData<DietPlanChangeBean>> dietPlanAdd(
            @Field("access_token") String access_token,
            @Field("id") int id,
            @Field("day") String day);


    /**
     * 查看详情
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("/port/Food/dietDetail")
    Observable<BaseData<DietPlanChangeBean>> getDietDetail(
            @Field("access_token") String access_token,
            @Field("id") int id,
            @Field("day") String day);

    /**
     * 早中晚换一换
     *
     * @param access_token
     * @param id
     * @param day
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("/port/Food/getEatChange")
    Observable<BaseData<List<DietPlanFoodChildBean>>> getEatChange(
            @Field("access_token") String access_token,
            @Field("id") int id,
            @Field("day") String day,
            @Field("type") String type);

    /**
     * 菜谱详情
     *
     * @param access_token
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("/port/Food/greensDetail")
    Observable<BaseData<RecipeDetailBean>> getRecipeDetail(
            @Field("access_token") String access_token,
            @Field("id") int id);


    /**
     * 菜谱分类列表
     *
     * @param access_token
     * @return
     */
    @FormUrlEncoded
    @POST("/port/Food/greensChangeOneList")
    Observable<BaseData<List<DietPlanFoodChildBean>>> getGreenChangeOneList(
            @Field("access_token") String access_token,
            @Field("id") int id);


    /**
     * 菜谱分类列表
     *
     * @param access_token
     * @return
     */
    @FormUrlEncoded
    @POST("/port/Food/contrastGreens")
    Observable<BaseData<DietPlanFoodListBean>> getMyDietPlanDetailList(
            @Field("access_token") String access_token,
            @Field("type") String type);


    /**
     * 菜谱搜索功能
     *
     * @param access_token
     * @param keyword
     * @return
     */
    @FormUrlEncoded
    @POST("/port/Food/greensSearch")
    Observable<BaseData<List<DietPlanFoodChildBean>>> getGreensSearch(
            @Field("access_token") String access_token,
            @Field("keyword") String keyword,
            @Field("id") String id,
            @Field("type") String type);

    /**
     * 智能饮食结束
     */







    @FormUrlEncoded
    @POST("/port/Familysign/getHospitals")
    Observable<BaseData<List<HospitalEntity>>> getHospitals(
            @Field("access_token") String access_token,
            @Field("hospitalname") String hospitalname
    );


    @FormUrlEncoded
    @POST("/port/Familysign/getDepartments")
    Observable<BaseData<List<DepartmentEntity>>> getDepartments(
            @Field("access_token") String access_token,
            @Field("hosid") int hosid
    );

    @FormUrlEncoded
    @POST("/port/Familysign/getDoctors")
    Observable<BaseData<List<DoctorEntity>>> getDoctors(
            @Field("access_token") String access_token,
            @Field("hosid") int hosid,
            @Field("depid") int depid
    );


    @FormUrlEncoded
    @POST("/index/Familysign/familyInhospitalList")
    Observable<BaseData<List<InHospitalEntity>>> familyInhospitalList(
            @Field("access_token") String access_token,
            @Field("page") int page,
            @Field("docid") int docid
    );

    @FormUrlEncoded
    @POST("/index/Familysign/familyInhospitalAdd")
    Observable<BaseData> familyInhospitalAdd(
            @Field("access_token") String access_token,
            @Field("type") String type,
            @Field("name") String name,
            @Field("age") String age,
            @Field("sex") String sex,
            @Field("telephone") String telephone,
            @Field("describe") String describe,
            @Field("pic") String pic,
            @Field("intime") String intime,
            @Field("docid") int docid
    );


    /*
     * 描述:     签约预约住院详情
     */

    @FormUrlEncoded
    @POST("/port/Familysign/familyinhospitaldetail")
    Observable<BaseData<InHospitalDetailEntity>> familyinhospitaldetail(
            @Field("access_token") String access_token,
            @Field("id") int id
    );

    /*
     * 描述:     签约协议
     */
    @FormUrlEncoded
    @POST("/index/Familysign/agreement")
    Observable<BaseData<SignProtocolBean>> agreement(
            @Field("access_token") String access_token,
            @Field("userid") int userid
    );


    /*
     * 描述:     解签
     */
    @FormUrlEncoded
    @POST("/index/Familysign/rescission")
    Observable<BaseData> rescission(
            @Field("access_token") String access_token
    );


    /*
     * 描述:     家庭列表
     */
    @FormUrlEncoded
    @POST("/index/Familysign/familyIndex")
    Observable<BaseData<List<FamilyMemberBean>>> familyIndex(
            @Field("access_token") String access_token
    );

    /*
     * 描述:     隐私设置
     */
    @FormUrlEncoded
    @POST("/index/Familysign/privacySettings")
    Observable<BaseData> privacySettings(
            @Field("access_token") String access_token,
            @Field("type") int type,
            @Field("userid") int userid
    );

    /*
     * 描述:     获取隐私
     */
    @FormUrlEncoded
    @POST("/index/Familysign/privacySettings")
    Observable<BaseData<PrivacyBean>> getPrivacy(
            @Field("access_token") String access_token,
            @Field("userid") int userid
    );


    /**
     * 家签医生添加
     *
     * @param files
     * @return
     */
    @Multipart
    @POST("/index/Familysign/familyDoctorAdd")
    Observable<String> familyDoctorAdd(@Part List<MultipartBody.Part> files);


    /**
     * 签约结果处理
     *
     * @param files
     * @return
     */
    @Multipart
    @POST("/port/Familysign/apply_deal")
    Observable<String> familyDoctorApplyDeal(@Part List<MultipartBody.Part> files);
}
