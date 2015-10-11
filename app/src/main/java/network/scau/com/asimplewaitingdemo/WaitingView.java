package network.scau.com.asimplewaitingdemo;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by zhy on 2015/10/10 0010.
 */
public class WaitingView extends RelativeLayout {

    private Context context;

    private float circleRadius = 20f;
    private float rotatoRadius;
    private long TIME = 1400;

    private int color = Color.GRAY;

    //重置动画flag
    private static boolean reset = false;

    //消失动画flag
    private boolean dismiss = false;

    //结束动画flag
    private boolean stop = false;

    //主角，三个小球
    private CircleView c1, c2, c3;


    //左球第一个动画，左移，旋转，缩小
    private AnimationSet left_translate_rotate_scale;

    //右球第一个动画，右移，旋转，缩小
    private AnimationSet right_translate_rotate_scale;

    //左球第二个动画集，右移，放大
    private AnimationSet left_translate_scale;

    //右球第二个动画集，右移，放大
    private AnimationSet right_translate_scale;

    //中球第一个动画集，缩小
    private AnimationSet mid_scale_small;

    //中球第二个动画集，放大
    private AnimationSet mid_scale_big;

    //结束动画集，放大，变淡
//    private AnimationSet finish_scale_alpha;


    private Animation left_ratote, right_rotate;
    private Animation right_translate_to_right, left_translate_to_left, left_translate_to_right, right_translate_to_left, translate_left_to_center, translate_right_to_center;
    private Animation scale_small, scale_big, scale_bigger;
    private Animation alpha_low;


    //-------------------------------------------------

    private static WaitingView wv;

    private OnFinish listener = new OnFinish() {

        @Override
        public void onFinish() {
            popupWindow.dismiss();
            wv.stop();
            wv.resetAnima();
        }
    };


    private static PopupWindow popupWindow;


    public static void showWaitingView(Context context, View view, int color, float radius) {

        wv = new WaitingView(context);
        wv.setCircleRadius(radius);
        wv.setColor(color);

        View layoutView = LayoutInflater.from(context).inflate(R.layout.pupop, null);
        wv = (WaitingView) layoutView.findViewById(R.id.id_wv);

        wv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv.dismiss();

            }
        });


        popupWindow = new PopupWindow(layoutView, 300, 300,
                false);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        wv.start();

    }

    public static void showWaitingView(Context context, View view) {

        wv = new WaitingView(context);

        View layoutView = LayoutInflater.from(context).inflate(R.layout.pupop, null);
        wv = (WaitingView) layoutView.findViewById(R.id.id_wv);

        wv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv.dismiss();

            }
        });


        popupWindow = new PopupWindow(layoutView, 300, 300,
                false);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        wv.start();

    }

    public static boolean isExist() {
        return wv != null;
    }

    public static void close() {
        wv.dismiss();
    }

    //---------------------------------------------------

    public WaitingView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public WaitingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        init();
    }

    public WaitingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }


    public void setCircleRadius(float circleRadius) {
        this.circleRadius = circleRadius;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private void init() {
        View view = RelativeLayout.inflate(context, R.layout.waiting_animation, this);


        c1 = (CircleView) view.findViewById(R.id.id_cv1);
        c2 = (CircleView) view.findViewById(R.id.id_cv2);
        c3 = (CircleView) view.findViewById(R.id.id_cv3);

        c1.setCircleRadius(circleRadius);
        c1.setCircleColor(color);

        c2.setCircleRadius(circleRadius);
        c2.setCircleColor(color);

        c3.setCircleRadius(circleRadius);
        c3.setCircleColor(color);

        rotatoRadius = 4 * circleRadius;

        left_translate_rotate_scale = new AnimationSet(false);
        right_translate_rotate_scale = new AnimationSet(false);
        left_translate_scale = new AnimationSet(false);
        right_translate_scale = new AnimationSet(false);
        mid_scale_small = new AnimationSet(false);
        mid_scale_big = new AnimationSet(false);

        //缩小
        scale_small = new ScaleAnimation(1f, 0.7f, 1f, 0.7f, circleRadius, circleRadius);
        //放大
        scale_big = new ScaleAnimation(0.7f, 1f, 0.7f, 1f, circleRadius, circleRadius);

        //结束时的放大
        scale_bigger = new ScaleAnimation(1f, 2f, 1f, 2f, circleRadius, circleRadius);
        //结束时变浅至消失
        alpha_low = new AlphaAnimation(1f, 0f);
        alpha_low.setFillAfter(true);

        //左球旋转
        left_ratote = new RotateAnimation(359f, 0f, 3 * circleRadius, circleRadius);
        //右球旋转
        right_rotate = new RotateAnimation(359f, 0f, -circleRadius, circleRadius);


        //左球左移
        left_translate_to_left = new TranslateAnimation(0, -rotatoRadius, 0, 0);
        //右球右移
        right_translate_to_right = new TranslateAnimation(0, rotatoRadius, 0, 0);

        //左球右移
        left_translate_to_right = new TranslateAnimation(-rotatoRadius, 0, 0, 0);
        //右球左移
        right_translate_to_left = new TranslateAnimation(rotatoRadius, 0, 0, 0);

        resetAnima();
        left_translate_rotate_scale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.e("wv", "left_translate_rotate_scale finish");

                if (!stop) {
                    if (dismiss) {
                        //添加 消失动画
                        addDismissAnima(mid_scale_big, left_translate_scale, right_translate_scale);


                    }

                    clear();

                    //开始第二个动画集
                    c1.startAnimation(mid_scale_big);
                    c2.startAnimation(left_translate_scale);
                    c3.startAnimation(right_translate_scale);

                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //---------------------------------------------
        left_translate_scale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.e("wv", "left_translate_scale finish");
                if (!stop) {
                    if (dismiss) {
                        //添加 消失动画
                        addDismissAnima(mid_scale_small, left_translate_rotate_scale, right_translate_rotate_scale);

                    }

                    clear();

                    c1.startAnimation(mid_scale_small);
                    c2.startAnimation(left_translate_rotate_scale);
                    c3.startAnimation(right_translate_rotate_scale);

                }
            }


            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //--------------------------------------------

        scale_bigger.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                c1.setAlpha(0f);
                c2.setAlpha(0f);
                c3.setAlpha(0f);
                clear();
                stop = true;
                listener.onFinish();
                Toast.makeText(context, "dismiss", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        start();

    }

    private void clear() {
        c1.clearAnimation();
        c2.clearAnimation();
        c3.clearAnimation();
    }

    public void start() {
        stop = false;
        dismiss = false;

        c1.startAnimation(mid_scale_small);
        c2.startAnimation(left_translate_rotate_scale);
        c3.startAnimation(right_translate_rotate_scale);
    }

    public void stop() {

        dismiss();
        stop = true;
        reset = false;
        c1.clearAnimation();
        c2.clearAnimation();
        c3.clearAnimation();
    }

    public void dismiss() {
        dismiss = true;
        stop = false;

    }


    private void addDismissAnima(AnimationSet set1, AnimationSet set2, AnimationSet set3) {
        set1.addAnimation(scale_bigger);
        set1.addAnimation(alpha_low);

        set2.addAnimation(scale_bigger);
        set2.addAnimation(alpha_low);

        set3.addAnimation(scale_bigger);
        set3.addAnimation(alpha_low);
    }

    private void resetAnima() {
        c1.setAlpha(1f);
        c2.setAlpha(1f);
        c2.setAlpha(1f);

        clear();

        //---------------------

        left_translate_rotate_scale.addAnimation(scale_small);
        left_translate_rotate_scale.addAnimation(left_ratote);
        left_translate_rotate_scale.addAnimation(left_translate_to_left);
        left_translate_rotate_scale.setFillAfter(true);
        left_translate_rotate_scale.setDuration(TIME / 2);

        right_translate_rotate_scale.addAnimation(scale_small);
        right_translate_rotate_scale.addAnimation(right_rotate);
        right_translate_rotate_scale.addAnimation(right_translate_to_right);
        right_translate_rotate_scale.setFillAfter(true);
        right_translate_rotate_scale.setDuration(TIME / 2);

        mid_scale_small.addAnimation(scale_small);
        mid_scale_small.setFillAfter(true);
        mid_scale_small.setDuration(TIME / 2);


        //---------------------------------
        left_translate_scale.addAnimation(scale_big);
        left_translate_scale.addAnimation(left_translate_to_right);
        left_translate_scale.setFillAfter(true);
        left_translate_scale.setDuration(TIME / 2);

        right_translate_scale.addAnimation(scale_big);
        right_translate_scale.addAnimation(right_translate_to_left);
        right_translate_scale.setFillAfter(true);
        right_translate_scale.setDuration(TIME / 2);


        mid_scale_big.addAnimation(scale_big);
        mid_scale_big.setFillAfter(true);
        mid_scale_big.setDuration(TIME / 2);

        //----------------------------------


    }
}
