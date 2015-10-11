package network.scau.com.asimplewaitingdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ZhengHY on 2015/10/7 0007.
 */
public class CircleView extends View {

    private int mViewWidth;
    private int mViewHeight;

    private int circleColor = Color.RED;

    private int textColor = Color.BLUE;


    private Paint mPaint ;

    private String mText = "";

    private float circleRadius = 10f;

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
    }

    public void setCircleRadius(float circleRadius) {
        this.circleRadius = circleRadius;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public CircleView(Context context) {
        super(context);
        init();
    }

    public int getCircleColor() {
        return circleColor;
    }

    public float getCircleRadius() {
        return circleRadius;
    }

    public String getText() {
        return mText;
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.CircleView);
        circleRadius = typedArray.getFloat(R.styleable.CircleView_circleRadius, 10f);
        circleColor = typedArray.getColor(R.styleable.CircleView_circleColor, Color.RED);
        mText = typedArray.getString(R.styleable.CircleView_circleText);
        if(mText==null){
            mText = "";
        }
        init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.CircleView);
        circleRadius = typedArray.getFloat(R.styleable.CircleView_circleRadius, 10f);
        circleColor = typedArray.getColor(R.styleable.CircleView_circleColor, Color.RED);
        mText = typedArray.getString(R.styleable.CircleView_circleText);
        if(mText==null){
            mText = "";
        }
        init();
    }

    private void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {



        mPaint.setColor(circleColor);
        mPaint.setAntiAlias(true);
        canvas.drawCircle(mViewWidth / 2, mViewHeight / 2, circleRadius, mPaint);
        if(mText!=null){
            mPaint.setTextSize(mViewHeight*7/8);
            float textWidth = mPaint.measureText(mText);
            float percent = (circleRadius * 2)/textWidth;

            mPaint.setColor(textColor);
            canvas.drawText(mText,mViewWidth /2 - textWidth/2,mViewHeight/2+mPaint.getTextSize()/3 ,mPaint);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }


    private int measureWidth(int widthMeasureSpec) {
        int width ;

        int mode = MeasureSpec.getMode(widthMeasureSpec);

        int size = MeasureSpec.getSize(widthMeasureSpec);


        if (mode == MeasureSpec.EXACTLY) {
            width = size;
        } else {
            //不是精确模式的话得自己结合paddin
            int desire = (int)circleRadius * 2 + getPaddingLeft() + getPaddingRight();

            if (mode == MeasureSpec.AT_MOST) {
                width = Math.min(desire, size);
            } else {
                width = desire;
            }
        }
        mViewWidth = width;
//        circleRadius = width/2;
        return width;
    }

    private int measureHeight(int heightMeasureSpec) {
        int height ;

        int mode = MeasureSpec.getMode(heightMeasureSpec);

        int size = MeasureSpec.getSize(heightMeasureSpec);


        if (mode == MeasureSpec.EXACTLY) {
            height = size;
        } else {
            //不是精确模式的话得自己结合paddin
            int desire = (int)circleRadius * 2 + getPaddingTop() + getPaddingBottom();
            if (mode == MeasureSpec.AT_MOST) {
                height = Math.min(desire, size);
            } else {
                height = desire;
            }
        }
        mViewHeight = height;
        return height;
    }


}
