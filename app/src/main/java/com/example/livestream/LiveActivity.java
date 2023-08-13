package com.example.livestream;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zegocloud.uikit.prebuilt.livestreaming.ZegoUIKitPrebuiltLiveStreamingConfig;
import com.zegocloud.uikit.prebuilt.livestreaming.ZegoUIKitPrebuiltLiveStreamingFragment;

public class LiveActivity extends AppCompatActivity {


    String userID,name,liveId;
    boolean isHost;
    TextView liveIdText;
    ImageView shareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        liveIdText=findViewById(R.id.live_id_text_view);
        shareBtn=findViewById(R.id.share_btn);

        userID=getIntent().getStringExtra("user_id");
        name=getIntent().getStringExtra("name");
        liveId=getIntent().getStringExtra("live_id");
        isHost=getIntent().getBooleanExtra("isHost",false);


        liveIdText.setText(liveId);
        addFragment();


        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,"Join my live in app \n liveId: "+liveId);
                startActivity(Intent.createChooser(intent,"Share Via"));
            }
        });

    }


    void addFragment(){

        ZegoUIKitPrebuiltLiveStreamingConfig config;

        if(isHost){
            config=ZegoUIKitPrebuiltLiveStreamingConfig.host();
        }else{

            config=ZegoUIKitPrebuiltLiveStreamingConfig.audience();
        }

        ZegoUIKitPrebuiltLiveStreamingFragment fragment= ZegoUIKitPrebuiltLiveStreamingFragment.newInstance(
               AppConstants.appId,AppConstants.appSign,userID,name,liveId,config

        );

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commitNow();

    }
}