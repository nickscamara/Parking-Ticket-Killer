package com.example.nick.ptk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private TextView login, email, passs;

    private Toolbar myToolbar;

    private TextView pass;

    private AppCompatEditText myEmail2;
    private AppCompatEditText myPass;

    private ImageView login_btn;

    private ProgressDialog myProgressLogin;

    private FirebaseAuth myAuth;


    private ImageView mylog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.awt);
        email = findViewById(R.id.email_name22);
        passs = findViewById(R.id.email_name33);
        pass = findViewById(R.id.pass);



        Typeface young = Typeface.createFromAsset(getAssets(), "fonts/Young.ttf");
        login.setTypeface(young);
        email.setTypeface(young);
        passs.setTypeface(young);


        myAuth = FirebaseAuth.getInstance();

        //myToolbar = (Toolbar) findViewById(R.id.login_toolbar);
        //setSupportActionBar(myToolbar);


        //getSupportActionBar().setTitle("Login");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myProgressLogin = new ProgressDialog(this);

        myEmail2 =  findViewById(R.id.edit11);
        myPass =  findViewById(R.id.edit22);
        mylog = (ImageView)findViewById(R.id.login);

        //pass = findViewById(R.id.forgot_pass);


        mylog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String email = myEmail2.getText().toString();
                String password = myPass.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

                    myProgressLogin.setTitle("Logging In");
                    myProgressLogin.setMessage("Please Wait");
                    myProgressLogin.setCanceledOnTouchOutside(false);
                    myProgressLogin.show();


                    loginUser(email, password);

                }

            }
        });
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent n = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(n);


            }
        });
    }

    private void loginUser(String email, String password){

        myAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    myProgressLogin.dismiss();
                    //login_btn.setVisibility(View.GONE);

                    //LottieAnimationView lottieAnimationView = (LottieAnimationView) findViewById(R.id.animation_view5);
                    //lottieAnimationView.setImageAssetsFolder("images/");

                    //lottieAnimationView.setAnimation("animslot.json");
                    //lottieAnimationView.loop(false);
                    //lottieAnimationView.playAnimation();

                    Handler h = new Handler();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent main = new Intent( LoginActivity.this, MapsActivity.class);
                            main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(main);
                            finish();
                        }
                    },100);



                }else{

                    myProgressLogin.hide();
                    Toast.makeText(LoginActivity.this, "Email or Password is wrong", Toast.LENGTH_LONG).show();

                }
            }
        });



    }
}
