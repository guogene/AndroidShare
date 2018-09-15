package com.example.lay.wechatshare.floatwindowlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lay.wechatshare.R;
import com.example.lay.wechatshare.floatwindowlib.OnFlingListener;

/**
 * Created by chentao on 2018/1/14.
 * 浮动按钮视图
 */

@SuppressLint("ViewConstructor")
public class DraggableFloatView extends LinearLayout implements View.OnClickListener {
    private int windowImgStaut = 1; //控制悬浮窗的状态
    private static final String TAG = DraggableFloatView.class.getSimpleName();

    private Context mContext;
    private ImageView mTouchBt;
    private OnFlingListener mOnFlingListener;
    private OnTouchButtonClickListener mTouchButtonClickListener;

    @SuppressLint("ClickableViewAccessibility")
    public DraggableFloatView(Context context, OnFlingListener flingListener) {
        super(context);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.floatview_freeposition, this);
        mTouchBt = findViewById(R.id.staut);
        mOnFlingListener = flingListener;
        mTouchBt.setOnTouchListener(new OnTouchListener() {

            //刚按下是起始位置的坐标
            float startDownX, startDownY;
            float downX, downY;
            float moveX, moveY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "ACTION_DOWN");
                        startDownX = downX = motionEvent.getRawX();
                        startDownY = downY = motionEvent.getRawY();
                        return false;
                    case MotionEvent.ACTION_MOVE:
                        Log.d(TAG, "ACTION_MOVE");
                        moveX = motionEvent.getRawX();
                        moveY = motionEvent.getRawY();
                        if (mOnFlingListener != null)
                            mOnFlingListener.onMove(moveX - downX, moveY - downY);
                        downX = moveX;
                        downY = moveY;
                        return true;
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "ACTION_UP");
                        float upX = motionEvent.getRawX();
                        float upY = motionEvent.getRawY();
                        if (upX == startDownX && upY == startDownY)
                            return false;
                        else
                            return true;
                }
                return true;
            }
        });
        mTouchBt.setOnClickListener(this);
    }

    public void setTouchButtonClickListener(OnTouchButtonClickListener touchButtonClickListener) {
        mTouchButtonClickListener = touchButtonClickListener;
    }

    @Override
    public void onClick(View v) {
        if (mTouchButtonClickListener != null) {
            mTouchButtonClickListener.onClick(v);
        }
        switch (v.getId()){
            case R.id.staut:
                changeImage();
        }
    }
    //改变发图的状态图标
    private void changeImage() {
        if (windowImgStaut == 1){
            mTouchBt.setImageDrawable(getResources().getDrawable(R.drawable.pause));
            windowImgStaut = 0;
        }else {
            mTouchBt.setImageDrawable(getResources().getDrawable(R.drawable.start));
            windowImgStaut = 1;
        }
    }




    public interface OnTouchButtonClickListener {
        void onClick(View view);
    }
}
