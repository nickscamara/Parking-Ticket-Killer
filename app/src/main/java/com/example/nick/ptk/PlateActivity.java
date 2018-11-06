package com.example.nick.ptk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PlateActivity extends AppCompatActivity {

    private TextView namee, namee2;


    private ImageView the_next, nexttt;
    private ProgressDialog myProgress;

    private AppCompatEditText statee, edit11;

    private AppCompatEditText edit1;
    private DatabaseReference myDatabase;
    private DatabaseReference myDatabase2;

    private com.google.firebase.auth.FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private String rdm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate);

        myProgress = new ProgressDialog( this );

        final String[] statesArray = {"AK",
                "AL",
                "AR",
                "AZ",
                "CA",
                "CO",
                "CT",
                "DE",
                "FL",
                "GA",
                "HI",
                "IA",
                "ID",
                "IL",
                "IN",
                "KS",
                "KY",
                "LA",
                "MA",
                "MD",
                "ME",
                "MI",
                "MN",
                "MO",
                "MS",
                "MT",
                "NC",
                "ND",
                "NE",
                "NH",
                "NJ",
                "NM",
                "NV",
                "NY",
                "OH",
                "OK",
                "OR",
                "PA",
                "RI",
                "SC",
                "SD",
                "TN",
                "TX",
                "UT",
                "VA",
                "VT",
                "WA",
                "WI",
                "WV",
                "WY"};



        mAuth = FirebaseAuth.getInstance();


        statee = findViewById(R.id.state);
        //next = findViewById(R.id.next12);
        edit11 = findViewById(R.id.plateee);

        namee = findViewById(R.id.stststs);
        namee2 = findViewById(R.id.stststs);

        Typeface young = Typeface.createFromAsset(getAssets(), "fonts/Young.ttf");

        namee.setTypeface(young);
        namee2.setTypeface(young);

        the_next =  findViewById(R.id.next12);


        the_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // final String display_name = mDisplayName.getText().toString();
                final String state = statee.getText().toString();
                final String plate = edit11.getText().toString();

                if(!TextUtils.isEmpty(state) && !TextUtils.isEmpty(plate)) {

                    if(stringStates(state.toUpperCase(),statesArray)) {

                        if(plate.length() <= 7) {

                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();

                            myDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("state");

                            myDatabase.setValue(state);

                            myDatabase2 = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("plate");

                            myDatabase2.setValue(plate);
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


                            Intent mainIntent = new Intent(PlateActivity.this, PreProfileActivity.class);
                            //mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish();
                        }
                        else{
                            Toast.makeText(PlateActivity.this,"Invalid License Plate", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {

                        Toast.makeText(PlateActivity.this,"Invalid State", Toast.LENGTH_SHORT).show();


                    }




                }
            }
        });
    }
    public static boolean stringStates(String inputStr, String[] item)
    {
        for(int i =0; i < item.length; i++)
        {
            if(inputStr.contains(item[i]))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {

    }

}
