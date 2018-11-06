package com.example.nick.ptk;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView myname, myplate, mystate, mycoins, myinvcode;
    private DatabaseReference myUserData;
    private FirebaseAuth myAuth;

    private ImageView configg;

    private Button finish;

    private Context context = this;
    private Activity activity = this;
    private FirebaseUser myCurrentUser;

    private AppCompatEditText invite;

    //private String current_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        myAuth = FirebaseAuth.getInstance();

        myname = findViewById(R.id.nameeee);
        mystate = findViewById(R.id.stateee);
        myplate = findViewById(R.id.plateee);
        mycoins = findViewById(R.id.coinssss);
        myinvcode = findViewById(R.id.codeeee);

        configg = findViewById(R.id.config);

        configg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                sendToStart();

            }
        });


        Typeface young = Typeface.createFromAsset(getAssets(), "fonts/Young.ttf");
        myname.setTypeface(young);
        mystate.setTypeface(young);
        //myplate.setTypeface(young);
        mycoins.setTypeface(young);
        myinvcode.setTypeface(young);
        ImageView reward = findViewById(R.id.imageView23);
        reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListAdapter listviewadapter = new listviewadapter(context,activity);
                final FrameLayout rootLayout = (FrameLayout) findViewById(android.R.id.content);
                View.inflate(context, R.layout.rewardboard, rootLayout);
                ListView rewardlist = findViewById(R.id.rewardlist);
                rewardlist.setAdapter(listviewadapter);

                ConstraintLayout rewardboard = activity.findViewById(R.id.rewardboard);
                rewardboard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rootLayout.removeView(activity.findViewById(R.id.rewardboard));
                    }
                });
            }
        });





    }

    public void onStart() {
        super.onStart();

        /**

         ConstraintLayout ct = findViewById(R.id.activity_main);

         SharedPreferences sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
         int bg = sharedPref.getInt("acitivity_main", R.drawable.bglegendary); // the second parameter will be fallback if the preference is not found
         ct.setBackgroundResource(bg);

         */

        if(!isConnected(ProfileActivity.this)) buildDialog(ProfileActivity.this).show();
        else {


            FirebaseUser currentUser = myAuth.getCurrentUser();


            if (currentUser == null) {

                sendToStart();
            } else {

                myCurrentUser = myAuth.getCurrentUser();

                String current_uid = myCurrentUser.getUid();



                myUserData = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
                myUserData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String names = dataSnapshot.child("name").getValue().toString();
                        String state = dataSnapshot.child("state").getValue().toString();
                        String plate = dataSnapshot.child("plate").getValue().toString();
                        Integer coins = dataSnapshot.child("coins").getValue(Integer.class);
                        String code = dataSnapshot.child("yourcode").getValue().toString();



                        myname.setText(names);
                        mystate.setText(state.toString().toUpperCase());
                        myplate.setText(plate.toString().toUpperCase());
                        mycoins.setText(coins.toString());
                        myinvcode.setText(code.toString());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });



            }
        }
    }

    private void sendToStart() {

        Intent startIntent = new Intent(ProfileActivity.this, StartActivity.class);
        startActivity(startIntent);



        finish();

    }

    //CONNECTIVITY
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You are not connected to the internet. Connect to continue.");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                System.exit(0);
            }
        });

        return builder;
    }
}