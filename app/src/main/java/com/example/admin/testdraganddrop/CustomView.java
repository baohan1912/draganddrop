package com.example.admin.testdraganddrop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class CustomView extends View {
    private RectF hcn;
    private ArrayList<Paint> paints;
    private Timer timer;


    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //Need to control timer
        addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                timer.cancel();
            }
        });
        initPaint();
    }


    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int wid, hei;

        int w = MeasureSpec.getSize(widthMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
//
//        if (wMode == MeasureSpec.EXACTLY) {
//            wid = w;
//        } else if (wMode == MeasureSpec.AT_MOST) {
//            wid = Math.min(300, w);
//        } else {
//            wid = 300;
//        }
//
//        if (hMode == MeasureSpec.EXACTLY) {
//            hei = h;
//        } else if (hMode == MeasureSpec.AT_MOST) {
//            hei = Math.min(150, h);
//        } else {
//            hei = 150;
//        }
        setMeasuredDimension(w, h);
    }

    private void initPaint() {
        hcn = new RectF();
        Paint hcnPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint hcnPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint hcnPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);

        paints = new ArrayList<>();
        paints.addAll(Arrays.asList(hcnPaint1, hcnPaint2, hcnPaint3));

        paints.get(2).setColor(getResources().getColor(R.color.light_gray, null));
        paints.get(1).setColor(getResources().getColor(R.color.light_gray, null));
        paints.get(0).setColor(getResources().getColor(R.color.gray, null));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float w = getWidth();
        float h = getHeight();

        float x = w / 2f;
        float y = h / 2;
        float vertical = h / 3f;

        hcn.set(50, 0, w - 50, vertical);
        canvas.drawRect(hcn, paints.get(2));

        hcn.set(50, vertical, w - 50, 2f * vertical);
        canvas.drawRect(hcn, paints.get(1));

        hcn.set(50, vertical * 2f, w - 50, 3 * vertical);
        canvas.drawRect(hcn, paints.get(0));
    }


    public void autoFlickering() {
        final int grayColor = getResources().getColor(R.color.gray, null);
        final android.os.Handler handler = new android.os.Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < paints.size(); ++i) {
                    //Return 0 if cur in last;
                    if (paints.get(i).getColor() == grayColor) {
                        if (i == paints.size() - 1) {
                            setGray(0);
                        } else {
                            setGray(++i);
                        }
                        break;
                    }
                }
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
                Log.d("HMM", "kec");
            }
        }, 1000, 1000);
    }


    private void setGray(int positon) {
        for (int i = 0; i < paints.size(); ++i) {
            paints.get(i).setColor(getResources().getColor(R.color.light_gray, null));
        }
        paints.get(positon).setColor(getResources().getColor(R.color.gray, null));
        invalidate();
    }

    public void stopFlickering() {
        timer.cancel();
    }
}
