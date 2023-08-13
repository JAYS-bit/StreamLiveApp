package com.example.livestream;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button goLiveButton;
    TextInputEditText liveIdInput,nameInput;

    String liveID,name,userId;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goLiveButton=findViewById(R.id.go_live_button);
        liveIdInput=findViewById(R.id.live_id_input);
        nameInput=findViewById(R.id.name_input);
        sharedPreferences= getSharedPreferences("name_preference",MODE_PRIVATE);

        nameInput.setText(sharedPreferences.getString("name",""));

        liveIdInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                liveID=liveIdInput.getText().toString();
                if (liveID.length()==0){
                    goLiveButton.setText("start new live");
                }else{
                    goLiveButton.setText("Join a live");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        goLiveButton.setOnClickListener((v)->{
            name =nameInput.getText().toString();

            if(name.isEmpty()){
                nameInput.setError("Name is required");
                nameInput.requestFocus();
                return;
            }

            liveID=liveIdInput.getText().toString();

            if(liveID.length()>0 && liveID.length()!=5){

                liveIdInput.setError("Invalid live id");

                liveIdInput.requestFocus();
                return;
            }

            startMeeting();


        });

    }

    private void startMeeting() {

        sharedPreferences.edit().putString("name",name).apply();
        boolean isHost=true;
        if(liveID.length()==5)
             isHost=false;
        else
            liveID=generateLiveId();

        userId= UUID.randomUUID().toString();

        Intent intent = new Intent(MainActivity.this, LiveActivity.class);
        intent.putExtra("user_id",userId);
        intent.putExtra("name",name);
        intent.putExtra("live_id",liveID);
        intent.putExtra("isHost",isHost);
        startActivity(intent);


    }

    String generateLiveId(){
        StringBuilder id = new StringBuilder();

        while(id.length()!=5){
          int random = new Random().nextInt(10);
          id.append(random);
        }

        return id.toString();
    }
}