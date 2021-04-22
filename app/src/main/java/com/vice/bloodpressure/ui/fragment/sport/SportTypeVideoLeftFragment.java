package com.vice.bloodpressure.ui.fragment.sport;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.ui.activity.sport.HomeSportCountDownActivity;
import com.wei.android.lib.colorview.view.ColorButton;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 描述:  运动类型之视频
 * 作者: LYD
 * 创建日期: 2020/9/30 10:04
 */
public class SportTypeVideoLeftFragment extends BaseFragment {
    @BindView(R.id.img_bg)
    ImageView imgBg;
    @BindView(R.id.tv_sport_type)
    TextView tvSportType;
    @BindView(R.id.tv_sport_time)
    TextView tvSportTime;
    @BindView(R.id.tv_sport_kcal)
    TextView tvSportKcal;
    @BindView(R.id.tv_sport_desc)
    TextView tvSportDesc;
    @BindView(R.id.tv_sport_warning)
    TextView tvSportWarning;
    @BindView(R.id.bt_begin_exercise)
    ColorButton btBeginExercise;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sport_type_video_left;
    }

    @Override
    protected void init(View rootView) {
        int sportType = getArguments().getInt("sportType");
        if (5 == sportType) {
            imgBg.setImageResource(R.drawable.sport_bg_skipping);
            tvSportDesc.setText("跳绳是一种有效的有氧运动，其简单，有趣，不受气候的影响，男女老少皆宜。跳绳不但可以帮你消耗身体内多余的脂肪，使肌肉变得富有弹性，还可以让全身肌肉匀称有力，同时会让你的呼吸系统、心脏、心血管系统得到充分的锻炼。");
            tvSportWarning.setText("注意事项：" + "\n" + "1.平稳，有节奏的呼吸。" + "\n" + "2.身体上部保持平衡，不要左右摆动。" + "\n" + "3.人体要放松，动作要协调。" + "\n" + "4.开始双脚同时跳，然后过渡到双脚交替跳。" + "\n" + "5.跳绳不要跳得太高，绳子能过去就可以了。" + "\n" + "6.建议配合监测心率心速情况");
        } else {
            imgBg.setImageResource(R.drawable.sport_bg_taiji);
            tvSportDesc.setText("太极拳运动的特点，是静中之动，虽动犹静。动所以活气血，静所以养脑力。动静结合，身心皆练，内外一体，刚柔相济，所以同时能充分发挥医疗体育的作用。现代医学研究表明，太极拳和一般的健身体操不同，除去全身各个肌肉群、关节需要活动外，还要配合均匀的深呼吸与横膈运动，而更重要的是需要精神的专注，心静、用意，这样就对中枢神经系统起了良好的影响，从而给其他系统与器官的活动和改善打下了良好的基础。");
            tvSportWarning.setText("注意事项：" + "\n" + "1.选择适宜的锻炼场地，如平整、松软的草地或泥土地，尽量避开坚硬的水泥地或石板地。" + "\n" + "2.练拳以前，要有针对性地做准备活动，比如原地慢跑几分钟、做几节按摩操等。" + "\n" + "3.遵照循序渐进的原则，先进行分段、分式的练习，再练全套。" + "\n" + "4.运动量不宜过大或过于集中，没有一定功底的中老年人群尤其要注意。");
        }
        String sportTypeStr = getArguments().getString("sportTypeStr");
        String sportTime = getArguments().getString("sportTime");
        int kcal = getArguments().getInt("kcal");
        tvSportType.setText(sportTypeStr);
        tvSportTime.setText("时长:" + sportTime);
        tvSportKcal.setText("消耗:" + kcal + "千卡");
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick(R.id.bt_begin_exercise)
    public void onViewClicked() {
        Intent intent = new Intent(getPageContext(), HomeSportCountDownActivity.class);
        intent.putExtras(getArguments());
        startActivity(intent);
    }


}
