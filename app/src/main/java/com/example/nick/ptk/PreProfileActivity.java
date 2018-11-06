package com.example.nick.ptk;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class PreProfileActivity extends AppCompatActivity {

    private TextView nname, uusername, uuusername;
    private DatabaseReference myUserData;
    private FirebaseAuth myAuth;

    private Button finish;

    private FirebaseUser myCurrentUser;

    private AppCompatEditText invite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_profile);
        myAuth = FirebaseAuth.getInstance();

        nname = findViewById(R.id.anamee);
        uusername = findViewById(R.id.ausername);
        uuusername = findViewById(R.id.anamee2);
        finish = findViewById(R.id.imageView11);
        invite = findViewById(R.id.code_invite);


        Typeface young = Typeface.createFromAsset(getAssets(), "fonts/Young.ttf");
        nname.setTypeface(young);
        uusername.setTypeface(young);
        //uuusername.setTypeface(young);

        myCurrentUser = myAuth.getCurrentUser();

        String current_uid = myCurrentUser.getUid();

        myUserData = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        myUserData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String names = dataSnapshot.child("name").getValue().toString();
                String usern = dataSnapshot.child("plate").getValue().toString();
                String state = dataSnapshot.child("state").getValue().toString();



                uusername.setText(names);
                nname.setText(usern.toString().toUpperCase());
                uuusername.setText(state.toString().toUpperCase());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String inivi = invite.getText().toString().toUpperCase();
                if(!TextUtils.isEmpty(inivi))
                {
                    //Build a query to find the invite code

                    /**

                    Query usernameQ = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("yourcode").equalTo(inivi);

                    usernameQ.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getChildrenCount() > 0)
                            {
                                Toast.makeText(PreProfileActivity.this, "Invalid Code", Toast.LENGTH_SHORT).show();
                            }else{

                    */
                                FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                                String uid = current_user.getUid();

                                myUserData = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("coins");

                                Toast.makeText(PreProfileActivity.this, "You have earned 100 coins!", Toast.LENGTH_SHORT).show();


                                myUserData.runTransaction(new Transaction.Handler() {
                                    @Override
                                    public Transaction.Result doTransaction(MutableData mutableData) {
                                        long value = 0;

                                        value = mutableData.getValue(Long.class);

                                        value = value + 350;
                                        mutableData.setValue(value);

                                        Intent n = new Intent(PreProfileActivity.this, MapsActivity.class);

                                        startActivity(n);

                                        return Transaction.success(mutableData);
                                    }


                                    @Override
                                    public void onComplete(DatabaseError databaseError, boolean b,
                                                           DataSnapshot dataSnapshot) {

                                    }


                                }  );

                }else{ Intent n = new Intent(PreProfileActivity.this, MapsActivity.class);
                    startActivity(n);}
            }
        });




    }
    @Override
    public void onBackPressed() {

    }
}
