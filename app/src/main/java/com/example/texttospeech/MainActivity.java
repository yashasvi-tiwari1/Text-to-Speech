package com.example.texttospeech;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
        //declare variables
        TextToSpeech tts;
        EditText editText;
        Button BtnSpeak;
        SeekBar pitchx,speedx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initializing varaibles
        editText = findViewById(R.id.edittext1);
        pitchx = findViewById(R.id.pitchx);
        speedx = findViewById(R.id.speedx);
        BtnSpeak = findViewById(R.id.btnspeak);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
              //we will check the speak status
                if (status == TextToSpeech.SUCCESS){
                    int output = tts.setLanguage(Locale.ENGLISH);
                    if(output == TextToSpeech.LANG_MISSING_DATA|| output == TextToSpeech.LANG_NOT_SUPPORTED){
                        Toast.makeText(getApplicationContext(), "Welcome to Text-to-Speech", Toast.LENGTH_SHORT).show();
                    }else {

                        Toast.makeText(getApplicationContext(), "Welcome to Text-to-Speech", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        BtnSpeak.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //create speak method
                Speak();
            }
        });
    }
    private void Speak() {
        //set speak items
        String speakText = editText.getText().toString();


        if (speakText.isEmpty()){

            speakText="Enter a text to speak";
        }

        //convert our typed text to speech
        //set seekbar by default to 50

        float pitchn = (float) pitchx.getProgress()/50;
        if (pitchn<0.1)
            pitchn = 0.1f;
        float speedrate = (float) speedx.getProgress()/50;
        if (speedrate<0.1)
            speedrate = 0.1f;
        // we are now going to set our seekbar to tts
        tts.setPitch(pitchn); //this method sets the speech pitch for TTS engine
        tts.setSpeechRate(speedrate); //this method sets the speech rate
        tts.speak(speakText,TextToSpeech.QUEUE_ADD,null);
    }
    @Override
    protected void onDestroy() {
        //check
        super.onDestroy();
        if (tts != null) {
            tts.stop(); //this method stops speaking
        }
    }
}