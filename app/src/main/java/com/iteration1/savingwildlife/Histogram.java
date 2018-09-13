package com.iteration1.savingwildlife;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;


import java.util.Random;


public class Histogram extends View implements View.OnClickListener {
    int width, height;

    public float[] getValues() {
        return values;
    }

    public void setValues(float[] values) {
        this.values = values;
        Log.d("value setted", Float.toString(values[1]));
        initValuesAndMaxY();
        this.invalidate();
    }

    Paint paintBar, paintText;
    float[] values = new float[10];
    float[] valuesTemp = new float[10];
    int colorBackground = android.R.color.darker_gray;
    int[] colorBar = new int[]{R.color.colorPrimaryDark, R.color.colorPrimary, R.color.colorAccent};// column colors
    float maxY;
    int barMarginLeft = 7;
    int tagHeight = 45;
    boolean anim;
    boolean showLineXNums = false, showLineYNums = true;//show x,y label
    int lineYNums = 5;
    ValueAnimator valueAnimator;

    public Histogram(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Histogram(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paintText = paintBar = new Paint();
        paintBar.setAntiAlias(true);
        paintText.setAntiAlias(true);
        paintText.setTextSize(12);
        paintText.setColor(getResources().getColor(colorBar[0]));
        initValuesAndMaxY();
        setOnClickListener(this);
    }

    private void initValuesAndMaxY() {
        Random random = new Random();
        values[0] = 527114;
        values[1] = 135411;
        values[2] = 314236;
        values[3] = 146157;
        values[4] = 298658;
        values[5] = 1176145;
        values[6] = 142355;
        values[7] = 357492;
        values[8] = 263964;
        values[9] = 119964;
        for (float i : values) {
            maxY = maxY < i ? i : maxY;
        }
        while(maxY%1000 != 0){
            maxY = maxY + 1;
        }
        Log.d("logged", Float.toString(maxY));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        int barWidth = (width - tagHeight) / values.length;
        int j = 0;
        if (!anim)
            valuesTemp = values.clone();
        for (int i = 0; i < valuesTemp.length; i++) {
            RectF rect = new RectF();
            rect.left = tagHeight + i * barWidth + barMarginLeft;
            rect.top = (height - tagHeight) * (1 - 1.0f * valuesTemp[i] / maxY);
            rect.right = rect.left + barWidth - barMarginLeft;
            rect.bottom = height - tagHeight;
            //draw the barBackground
            paintBar.setColor(getResources().getColor(colorBackground));
            canvas.drawRect(rect.left, 0, rect.right, rect.bottom, paintBar);
            //paint the bar
            j = j > colorBar.length - 1 ? 0 : j;
            paintBar.setColor(getResources().getColor(colorBar[j++]));
            canvas.drawRect(rect, paintBar);
            if (showLineXNums) {
                //draw x-coordinate num
                float textWidth = paintText.measureText(String.valueOf(i));
                float textLeft = rect.left + rect.width() / 2 - textWidth / 2;
                canvas.drawText(String.valueOf(i), textLeft, height, paintText);
            }
        }
        //draw y-coordinate num
        if (showLineYNums) {
            int avgHeight = (height - tagHeight) / lineYNums;
            for (int i = 0; i < lineYNums; i++) {
                float x = 0;
                float y = (height - tagHeight) - avgHeight * (i + 1);
                int valueY = (int) (maxY * (i + 1) / lineYNums);
                // canvas写字是从x、y轴往右上写的
                canvas.drawText(String.valueOf(valueY), x, y + 30, paintText);
            }
        }
    }

    @Override
    public void onClick(View v) {
        anim = true;
        valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valuesTemp = values.clone();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                for (int i = 0; i < valuesTemp.length; i++) {
                    //update valuesTemps
                    float animatedValue = (float) animation.getAnimatedValue();
                    valuesTemp[i] = maxY * animatedValue < values[i] ? maxY * animatedValue : values[i];
                }
                invalidate();
            }
        });
//        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.setDuration(2000l);
        valueAnimator.start();
    }
}