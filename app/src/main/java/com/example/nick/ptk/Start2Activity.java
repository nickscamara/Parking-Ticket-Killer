package com.example.nick.ptk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.UUID;

public class Start2Activity extends AppCompatActivity {


    private TextView namee;


    private ImageView the_next, nexttt;
    private ProgressDialog myProgress;

    private AppCompatEditText edit1;
    private DatabaseReference myDatabase;
    private DatabaseReference myDatabase2;

    private com.google.firebase.auth.FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private String rdm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start2);

        myProgress = new ProgressDialog( this );



        mAuth = FirebaseAuth.getInstance();


        namee = findViewById(R.id.nameee);
        //next = findViewById(R.id.next12);
        edit1 = findViewById(R.id.edit);

        Typeface young = Typeface.createFromAsset(getAssets(), "fonts/Young.ttf");

        namee.setTypeface(young);

        the_next =  findViewById(R.id.next12);


        the_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // final String display_name = mDisplayName.getText().toString();
                final String name = edit1.getText().toString();

                if(!TextUtils.isEmpty(name)) {

                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();

                    myDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("name");

                    myDatabase.setValue(name);
                    //userMap.put("username", username );
                    //userMap.put("status", "I Love FastChat");
                    //myDatabase.push().setValue(userMap);





                                myProgress.dismiss();
                                //mCreatebtn.setVisibility(View.GONE);
                                //LottieAnimationView lottieAnimationView = (LottieAnimationView) findViewById(R.id.animation_view6);
                                //lottieAnimationView.setImageAssetsFolder("images/");

                                //lottieAnimationView.setAnimation("animslot.json");
                                //lottieAnimationView.loop(false);
                                //lottieAnimationView.playAnimation();


                                Intent mainIntent = new Intent(Start2Activity.this, PlateActivity.class);
                                //mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();




                }
            }
        });
    }
    @Override
    public void onBackPressed() {

    }
}




