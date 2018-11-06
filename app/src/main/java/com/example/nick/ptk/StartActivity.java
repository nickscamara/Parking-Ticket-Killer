package com.example.nick.ptk;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class StartActivity extends AppCompatActivity {

    private ImageView img1, img2, img3, img4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        img1 = findViewById(R.id.start);
        img2 = findViewById(R.id.start2);
        img3 = findViewById(R.id.start3);
        img4 = findViewById(R.id.start4);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img1.setVisibility(View.GONE);
                img3.setVisibility(View.VISIBLE);
                Intent n = new Intent(StartActivity.this, RegisterActivity.class);
                startActivity(n);
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        img3.setVisibility(View.INVISIBLE);
                        img1.setVisibility(View.VISIBLE);

                    }
                },100);

            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img2.setVisibility(View.GONE);
                img4.setVisibility(View.VISIBLE);
                Intent n = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(n);
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        img4.setVisibility(View.INVISIBLE);
                        img2.setVisibility(View.VISIBLE);

                    }
                },100);

            }
        });

    }
}
