package com.example.nick.ptk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.util.ArrayList;
import java.util.List;

public class btnmain extends AppCompatActivity {

    List<Integer> imgs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btnmain);
        initData();
        final HorizontalInfiniteCycleViewPager hz = findViewById(R.id.horizontal);
        BgdAdapter adapter1 = new BgdAdapter(imgs,this,this );
        hz.setAdapter(adapter1);
    }

    private void initData() {

        imgs.add(R.drawable.icon1);
        imgs.add(R.drawable.icon2);
        imgs.add(R.drawable.icon3);






    }
}
