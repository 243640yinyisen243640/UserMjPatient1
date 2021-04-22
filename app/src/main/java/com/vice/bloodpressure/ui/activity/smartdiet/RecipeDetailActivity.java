package com.vice.bloodpressure.ui.activity.smartdiet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.allen.library.RxHttpUtils;
import com.allen.library.bean.BaseData;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.RecipeDetailPercentBarAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.RecipeDetailBean;
import com.vice.bloodpressure.net.Service;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.vice.bloodpressure.utils.TurnsUtils;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 描述:  菜谱详情
 * 作者: LYD
 * 创建日期: 2020/3/16 16:58
 */
public class RecipeDetailActivity extends BaseHandlerActivity {
    @BindView(R.id.img_dish)
    ImageView imgDish;
    @BindView(R.id.tv_dish_name)
    TextView tvDishName;
    @BindView(R.id.tv_one)
    TextView tvOne;
    @BindView(R.id.tv_two)
    TextView tvTwo;
    @BindView(R.id.tv_three)
    TextView tvThree;
    @BindView(R.id.pc_energy_percent)
    PieChart pcEnergyPercent;
    @BindView(R.id.gv_energy_one_gram)
    GridView gvEnergyOneGram;
    @BindView(R.id.lv_material_percent)
    ListView lvMaterialPercent;
    @BindView(R.id.gv_energy_percent)
    GridView gvEnergyPercent;

    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("智能食谱");
        setMore();
        setMaterials();
        int id = getIntent().getIntExtra("greensid", 0);
        getRecipeDetail(id);
    }

    private void setMore() {
        getTvSave().setText("换我想吃");
        getTvSave().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getPageContext(), DietPlanFoodsSelectAndSearchActivity.class);
                Bundle extras = getIntent().getExtras();
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    private void setMaterials() {
        String str = getIntent().getStringExtra("str");
        SpanUtils.with(tvOne)
                .appendImage(R.drawable.recipe_detail_one, SpanUtils.ALIGN_CENTER)
                .appendSpace(30, ColorUtils.getColor(R.color.white_text))
                .append("材料：").setForegroundColor(ColorUtils.getColor(R.color.black_text))
                .append(str).setForegroundColor(ColorUtils.getColor(R.color.gray_text))
                .create();
    }

    private void getRecipeDetail(int id) {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        String token = user.getToken();
        RxHttpUtils.createApi(Service.class)
                .getRecipeDetail(token, id)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BaseData<RecipeDetailBean>>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(BaseData<RecipeDetailBean> recipeDetailBeanBaseData) {
                        RecipeDetailBean recipeDetailBean = recipeDetailBeanBaseData.getData();
                        String dish_name = recipeDetailBean.getDish_name();
                        tvDishName.setText(dish_name);
                        title = dish_name;
                        String pic = recipeDetailBean.getPic();
                        Glide.with(Utils.getApp())
                                .load(pic)
                                .error(R.drawable.recipe_no_image)
                                .placeholder(R.drawable.recipe_no_image)
                                .into(imgDish);
                        //调料
                        String seasoning = recipeDetailBean.getSeasoning();
                        SpanUtils.with(tvTwo)
                                .appendImage(R.drawable.recipe_detail_two, SpanUtils.ALIGN_CENTER)
                                .appendSpace(30, ColorUtils.getColor(R.color.white_text))
                                .append("调料：").setForegroundColor(ColorUtils.getColor(R.color.black_text))
                                .append(seasoning).setForegroundColor(ColorUtils.getColor(R.color.gray_text))
                                .create();
                        //做法
                        String practice = recipeDetailBean.getPractice();
                        String replacePractice = practice.replace("\\n", "\n");
                        SpanUtils.with(tvThree)
                                .appendImage(R.drawable.recipe_detail_three, SpanUtils.ALIGN_CENTER)
                                .appendSpace(30, ColorUtils.getColor(R.color.white_text))
                                .append("做法：").setForegroundColor(ColorUtils.getColor(R.color.black_text))
                                .append(replacePractice).setForegroundColor(ColorUtils.getColor(R.color.gray_text))
                                .create();
                        RecipeDetailPercentBarAdapter adapter = new RecipeDetailPercentBarAdapter(getPageContext(), recipeDetailBean.getName(), recipeDetailBean.getGramsrate());
                        lvMaterialPercent.setAdapter(adapter);
                        EnergyPercentAdapter percentAdapter = new EnergyPercentAdapter(getPageContext(), recipeDetailBean.getName(), recipeDetailBean.getHqrate());
                        gvEnergyPercent.setAdapter(percentAdapter);
                        showPieChart(pcEnergyPercent, getPieChartData(recipeDetailBean.getHqrate(), recipeDetailBean.getName()));
                        List<Gramsper> gramsperList = new ArrayList<>();
                        GramsperAdapter gramsperAdapter = new GramsperAdapter(RecipeDetailActivity.this, gramsperList);
                        gvEnergyOneGram.setAdapter(gramsperAdapter);
                    }
                });
    }


    private List<PieEntry> getPieChartData(List<String> list1, List<String> list2) {
        List<PieEntry> mPie = new ArrayList<>();
        if (list1 != null && list2 != null) {
            for (int i = 0; i < list1.size(); i++) {
                // 参数1为 value，参数2为 data。
                // 如 PieEntry(0.15F, "90分以上");  表示90分以上的人占比15%。
                PieEntry pieEntry = new PieEntry(TurnsUtils.getFloat(list1.get(i), 0), list2.get(i));
                mPie.add(pieEntry);
            }
        }
        return mPie;
    }

    private void showPieChart(PieChart pieChart, List<PieEntry> pieList) {
        PieDataSet dataSet = new PieDataSet(pieList, "");
        int[] intArray = Utils.getApp().getResources().getIntArray(R.array.diet_plan_colors);
        dataSet.setColors(intArray);
        PieData pieData = new PieData(dataSet);

        pieChart.setHoleRadius(90f);
        pieChart.setUsePercentValues(true);
        //设置使用百分比
        //设置描述
        pieChart.getDescription().setEnabled(false);
        //设置半透明圆环的半径, 0为透明
        pieChart.setTransparentCircleRadius(0f);

        pieChart.setHighlightPerTapEnabled(false);//点击是否放大

        pieChart.setCenterText(title);//设置环中的文字
        pieChart.setCenterTextSize(18f);//设置环中文字的大小
        pieChart.setDrawCenterText(true);//设置绘制环中文字
        //设置初始旋转角度
        pieChart.setRotationAngle(-15);

        //数据连接线距图形片内部边界的距离，为百分数
        dataSet.setValueLinePart1OffsetPercentage(80f);

        // 设置饼块之间的间隔
        dataSet.setSliceSpace(0f);
        dataSet.setHighlightEnabled(true);
        // 显示图例
        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);

        // 和四周相隔一段距离,显示数据
        pieChart.setExtraOffsets(26, 5, 26, 5);

        // 设置pieChart图表是否可以手动旋转
        pieChart.setRotationEnabled(false);
        // 设置piecahrt图表点击Item高亮是否可用
        pieChart.setHighlightPerTapEnabled(false);
        // 设置pieChart图表展示动画效果，动画运行1.4秒结束
        pieChart.animateY(1400, Easing.EaseInOutQuad);
        //设置pieChart是否只显示饼图上百分比不显示文字
        pieChart.setDrawEntryLabels(false);
        // 绘制内容value，设置字体颜色大小
        pieData.setDrawValues(false);
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.DKGRAY);

        pieChart.setData(pieData);
        // 更新 piechart 视图
        pieChart.postInvalidate();
    }

    private String percentFormat(float num) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        return nf.format(num * 100) + "%";
    }


    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_recipe_detail, contentLayout, false);
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    private class EnergyPercentAdapter extends BaseAdapter {

        private Context context;
        private int[] colors = Utils.getApp().getResources().getIntArray(R.array.diet_plan_colors);
        private List<String> names;
        private List<String> values;

        private EnergyPercentAdapter(Context context, List<String> names, List<String> values) {
            this.context = context;
            this.names = names;
            this.values = values;
        }

        @Override
        public int getCount() {
            return names == null ? 0 : names.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(context, R.layout.item_energy_percent, null);
                viewHolder.tvSquare = convertView.findViewById(R.id.tv_square);
                viewHolder.tvValue = convertView.findViewById(R.id.tv_percent);
                viewHolder.tvSquare.setBackgroundColor(colors[position]);
                viewHolder.tvValue.setText(String.format("%s：%s", names.get(position), percentFormat(Float.parseFloat(values.get(position)))));
                convertView.setTag(viewHolder);
            }
            return convertView;
        }

        private class ViewHolder {
            TextView tvSquare;
            TextView tvValue;
        }
    }

    private class GramsperAdapter extends BaseAdapter {

        private Context context;
        private List<Gramsper> gramspers;

        private GramsperAdapter(Context context, List<Gramsper> gramspers) {
            this.context = context;
            this.gramspers = gramspers;
        }

        @Override
        public int getCount() {
            return gramspers.size();
        }

        @Override
        public Gramsper getItem(int position) {
            return gramspers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(context, R.layout.item_energy_one_gram, null);
                viewHolder.tvMaterialName = convertView.findViewById(R.id.tv_material_name);
                viewHolder.tvMaterialWeight = convertView.findViewById(R.id.tv_material_weight);

                viewHolder.tvMaterialName.setText(gramspers.get(position).getName());
                viewHolder.tvMaterialWeight.setText(String.format("%skcal", gramspers.get(position).getValue()));
                convertView.setTag(viewHolder);
            }
            return convertView;
        }

        private class ViewHolder {
            TextView tvMaterialName;
            TextView tvMaterialWeight;
        }
    }

    private class Gramsper {
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
