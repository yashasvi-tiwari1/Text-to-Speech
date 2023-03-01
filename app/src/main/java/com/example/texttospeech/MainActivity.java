package com.example.texttospeech;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT = 33;
    //declare variables
        TextToSpeech tts;
        EditText editText;
        Button BtnSpeak;
        SeekBar pitchx,speedx;
        TextView mic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initializing varaibles
        editText = findViewById(R.id.edittext1);
      findViewById(R.id.txt_mic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speechToText();
            }
        });
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
    private void speechToText(){
        Intent intent
                = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e) {
            Toast
                    .makeText(MainActivity.this, " " + e.getMessage(),
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                editText.setText(
                        Objects.requireNonNull(result).get(0));
            }
        }
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