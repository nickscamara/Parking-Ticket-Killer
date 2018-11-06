package com.example.nick.ptk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    private AppCompatEditText mDisplayName;
    private AppCompatEditText mEmail;
    private AppCompatEditText mPassword, mPasswordd;
    private AppCompatEditText mUsername;
    private ImageView mCreatebtn;

    private Toolbar myToolbar;
    private ProgressDialog myProgress;

    private DatabaseReference myDatabase;
    private DatabaseReference myDatabase2;

    private com.google.firebase.auth.FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private TextView txt, txt1, txt2, txt3;

    private String rdm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myProgress = new ProgressDialog( this );



        mAuth = FirebaseAuth.getInstance();

        //mDisplayName = (AppCompatEditText) findViewById(R.id.reg_name);
        mEmail = (AppCompatEditText) findViewById(R.id.edit1);
        mPassword = (AppCompatEditText) findViewById(R.id.edit2);
        mPasswordd = (AppCompatEditText) findViewById(R.id.edit3);
        //mUsername = (AppCompatEditText)findViewById(R.id.username_edit);

        txt = findViewById(R.id.email_name);
        txt1 = findViewById(R.id.email_name2);
        txt2 = findViewById(R.id.email_name3);
        txt3 = findViewById(R.id.email_name4);

        Typeface young = Typeface.createFromAsset(getAssets(), "fonts/Young.ttf");
        txt.setTypeface(young);
        txt1.setTypeface(young);
        txt2.setTypeface(young);
        txt3.setTypeface(young);


        mCreatebtn =  findViewById(R.id.next_my);


        mCreatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // final String display_name = mDisplayName.getText().toString();
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                final String password2 = mPasswordd.getText().toString();
                //final String username = "@" + mUsername.getText().toString();

                /**

                 Query usernameQ = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("username").equalTo(username);

                 usernameQ.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() > 0)
                {
                Toast.makeText(RegisterActivity.this, "Choose a different username", Toast.LENGTH_SHORT).show();
                }else{

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
                });


                }
                 typez                */
                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) ) {

                    if(password.equals(password2)) {


                        myProgress.setTitle("Registering User");
                        myProgress.setMessage("Please, wait while we connect to our servers");
                        myProgress.setCanceledOnTouchOutside(false);
                        myProgress.show();

                        register_user(email, password);

                    }
                    else{

                        Toast.makeText(RegisterActivity.this,"Passwords don't match, try again", Toast.LENGTH_SHORT).show();
                    }





                }















            }








        });

    }

    private void register_user(final String email, String password) {

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){


                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();

                    myDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    final HashMap<String, Object> userMap = new HashMap<>();
                    userMap.put("email", email);
                    userMap.put("coins", 100);
                    //userMap.put("username", username );
                    //userMap.put("status", "I Love FastChat");
                    myDatabase.push().setValue(userMap);



                    String uuid = UUID.randomUUID().toString();

                    String a = uuid.substring(0,8);

                    userMap.put("yourcode",a);

                    //PUSH USERNAME







                    //final HashMap<String, Integer> userMap2 = new HashMap<>();








                    myDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            myDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {

                                        myProgress.dismiss();
                                        mCreatebtn.setVisibility(View.GONE);
                                        //LottieAnimationView lottieAnimationView = (LottieAnimationView) findViewById(R.id.animation_view6);
                                        //lottieAnimationView.setImageAssetsFolder("images/");

                                        //lottieAnimationView.setAnimation("animslot.json");
                                        //lottieAnimationView.loop(false);
                                        //lottieAnimationView.playAnimation();


                                        Intent mainIntent = new Intent(RegisterActivity.this, Start2Activity.class);
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainIntent);
                                        finish();





                                    }
                                }
                            });
                        }
                    });
















                }else{

                    myProgress.hide();
                    Toast.makeText(RegisterActivity.this, "You got some error and cannot sign in, try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
