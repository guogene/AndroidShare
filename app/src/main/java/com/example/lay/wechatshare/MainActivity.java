package com.example.lay.wechatshare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;
import android.provider.Settings;
import android.os.Build;
import android.net.Uri;
import android.app.Activity;
import com.example.lay.wechatshare.floatwindowlib.DraggableFloatView;
import com.example.lay.wechatshare.floatwindowlib.DraggableFloatWindow;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public Button get_btn;
    public TextView data_view;
    private static final int OVERLAY_PERMISSION_REQ_CODE = 0x001;
    private DraggableFloatWindow mFloatWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intEvent();
        initListener();

    }

    private void initListener() {
        get_btn.setOnClickListener(this);
    }

    private void intEvent(){
        get_btn = findViewById(R.id.get);
        data_view = findViewById(R.id.data_stage);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // 测试点击按钮
            case R.id.get:
                btn_event();
                break;
        }
    }

    private void btn_event() {
        String text = "你好世界";
        Toast.makeText(MainActivity.this,text,Toast.LENGTH_LONG).show();
        mFloatWindow = DraggableFloatWindow.getDraggableFloatWindow(MainActivity.this, null);
        mFloatWindow.show();
        mFloatWindow.setOnTouchButtonListener(new DraggableFloatView.OnTouchButtonClickListener() {
            @Override
            public void onClick(View view) {
                mFloatWindow.dismiss();
            }
        });

    }

    /**
     * 引入别人的
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case OVERLAY_PERMISSION_REQ_CODE:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (!Settings.canDrawOverlays(this)) {
                            Toast.makeText(MainActivity.this, "权限授予失败，无法开启悬浮窗", Toast.LENGTH_SHORT).show();
                        } else {
                            // TODO: 18/1/7 已经授权
                        }
                    }
                    break;
            }
        }
    }

}
