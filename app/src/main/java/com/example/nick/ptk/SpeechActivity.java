package com.example.nick.ptk;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpeechActivity extends AppCompatActivity {

    Button btn;
    AppCompatEditText textEd;

    TextToSpeech ttt;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);



        ttt = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {

                if( status == TextToSpeech.SUCCESS)
                {

                    int result = ttt.setLanguage(Locale.US);
                    if( result == ttt.LANG_MISSING_DATA || result == ttt.LANG_NOT_SUPPORTED)
                    {

                        Toast.makeText(SpeechActivity.this, " Language not supported", Toast.LENGTH_SHORT).show();




                    }else{

                        btn.setEnabled(true);
                        ttt.setPitch(0.6f);
                        ttt.setSpeechRate(1.0f);
                        //speak();
                    }

                }




            }


        });

        btn = findViewById(R.id.spe2);
        textEd = findViewById(R.id.spe);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();

            }
        });



    }

    private void speak() {


        String text = textEd.getText().toString();

        if(containsIllegals(text))
        {
            String textv22 = "This is not a valid name! Come on! Choose wisely";
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                ttt.speak(textv22,TextToSpeech.QUEUE_FLUSH,null,null);

            }
            else
            {

                ttt.speak(textv22,TextToSpeech.QUEUE_FLUSH,null);


            }

        }
        else {


            Random rand = new Random();
            int a = rand.nextInt((100 - 0) + 1) + 0;
            ;
            if (a < 20) {
                String textv2 = "Hello there!, nice to meet you " + text;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ttt.speak(textv2, TextToSpeech.QUEUE_FLUSH, null, null);

                } else {

                    ttt.speak(textv2, TextToSpeech.QUEUE_FLUSH, null);


                }


            } else if (a < 40) {

                String textv2 = "Welcome!, my name is Odessa, nice to meet you  " + text;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ttt.speak(textv2, TextToSpeech.QUEUE_FLUSH, null, null);

                } else {

                    ttt.speak(textv2, TextToSpeech.QUEUE_FLUSH, null);


                }


            } else if (a < 60) {

                String textv2 = "Hello " + text;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ttt.speak(textv2, TextToSpeech.QUEUE_FLUSH, null, null);

                } else {

                    ttt.speak(textv2, TextToSpeech.QUEUE_FLUSH, null);


                }


            } else if (a < 80) {

                String textv2 = "What's up " + text + ". I am your assistant";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ttt.speak(textv2, TextToSpeech.QUEUE_FLUSH, null, null);

                } else {

                    ttt.speak(textv2, TextToSpeech.QUEUE_FLUSH, null);


                }


            } else if (a < 101) {

                String textv2 = "What's up " + text + ". I am your assistant. I love to speak with random people. Click next to continue the setup";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ttt.speak(textv2, TextToSpeech.QUEUE_FLUSH, null, null);


                } else {

                    ttt.speak(textv2, TextToSpeech.QUEUE_FLUSH, null);


                }


            }
        }


    }
    @Override
    protected void onDestroy()
    {

        if( ttt != null )
        {
            ttt.stop();
            ttt.shutdown();


        }
        super.onDestroy();

    }


    public boolean containsIllegals(String toExamine) {
        Pattern pattern = Pattern.compile("~!@#$%^&*()`;.,/[]]");
        Matcher matcher = pattern.matcher(toExamine);
        return matcher.find();
    }
}

