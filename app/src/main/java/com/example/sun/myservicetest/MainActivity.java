package com.example.sun.myservicetest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sun.myservicetest.Service.MusicService;
import com.example.sun.myservicetest.Service.TimeService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /*
     * Action
     */
    private static final String ACTION_RECV_MSG = "com.example.sun.myservicetest.intent.action.RECEIVE_MESSAGE";
    private static final String ACTION_OTHER_MSG = "com.example.sun.myservicetest.intent.action.OTHER_MESSAGE";

    /*
     * Message
     */
    private static final String MESSAGE_IN="message_input";
    private static final String MESSAGE_OUT="message_output";

    /**
     * View
     */
    private Button button_start_time;
    private Button button_play_music;
    private TextView textView_show_time;

    private MessageReceiver messageReceiver;

    private boolean isStartTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //动态注册receiver
        IntentFilter filter = new IntentFilter(ACTION_RECV_MSG);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        messageReceiver = new MessageReceiver();
        registerReceiver(messageReceiver, filter);
        isStartTime = true;
    }

    private void initView(){
        button_start_time = (Button) findViewById(R.id.button_start_time);
        button_play_music = (Button) findViewById(R.id.button_play_music);
        textView_show_time = (TextView) findViewById(R.id.textView_show);
        button_start_time.setOnClickListener(this);
        button_play_music.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.button_start_time://开始倒计时
                if(isStartTime) {
                    isStartTime=false;
                    onStartTime();
                    Toast.makeText(getApplicationContext(), "开始倒计时！", Toast.LENGTH_SHORT).show();
                }
                else
                Toast.makeText(getApplicationContext(),"正在倒计时，请稍后再操作！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_play_music://开始或停止播放背景音乐
                //MediaPlayer.create(this, R.raw.lucky).start();
                Toast.makeText(getApplicationContext(), "开始播放背景音乐！", Toast.LENGTH_SHORT).show();
                Intent intentSV = new Intent(MainActivity.this, MusicService.class);
                startService(intentSV);
                break;
        }
    }

    /**
     * 倒计时函数，调用TimeService服务
     */
    private void onStartTime(){
        Intent msgIntent = new Intent(MainActivity.this,
                TimeService.class);
        msgIntent.setAction(ACTION_RECV_MSG);
        msgIntent.putExtra(MESSAGE_IN, textView_show_time.getText().toString());
        startService(msgIntent);
    }
    //广播用来接收信息
    private class MessageReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra(MESSAGE_OUT);
            isStartTime = intent.getBooleanExtra("isStartTime",false);
            textView_show_time.setText(msg);
        }
    }
}
