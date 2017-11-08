package com.wind.simonarcviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wind.arcview.ArcImageView;
import com.wind.arcview.HomeBanner;
import com.wind.arcview.SimonArcView;

public class MainActivity extends AppCompatActivity {
    private SimonArcView arcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HomeBanner homeBanner= (HomeBanner) findViewById(R.id.hb_banner);
        homeBanner.setImagesRes(new int[]{R.drawable.banner5,R.drawable.banner5,R.drawable.banner5,R.drawable.banner5});
        ArcImageView arcImageView=new ArcImageView(this);
    }
}
