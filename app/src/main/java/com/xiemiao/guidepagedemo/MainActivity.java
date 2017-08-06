package com.xiemiao.guidepagedemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.xiemiao.guidepagelib.view.GuidePage;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private GuidePage guidePage;
    private Integer[] images = {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e, R
            .mipmap.f};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        guidePage = (GuidePage) findViewById(R.id.guidePage);
        //设置图片集合,圆点颜色大小
        guidePage.setLocalImageResList(Arrays.asList(images)).setOvalIndicator(Color.parseColor
                ("#00FF00"), Color.parseColor("#FFFFFF"), 10);

        //设置进入按钮（文字，颜色，大小，背景）点击事件
        guidePage.setOnEntryClickListener("立即体验", Color.parseColor("#000000"), 10, R.drawable
                .shape_radius_yellow_bg, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "进入主界面", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
