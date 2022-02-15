package com.example.simplemusicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PlaySongAct extends AppCompatActivity {
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.stop();
        mp.release();
        timer.cancel();

    }


    TextView tv;
    ImageView musicIcon,play,prev,next;
    Integer pos;
    ArrayList<File> songs;
    MediaPlayer mp;
    String songName;
    SeekBar seekBar;
    Timer timer=new Timer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        tv=(TextView) findViewById(R.id.sonagName);
        play=(ImageView) findViewById(R.id.playpauseButton);
        prev=(ImageView) findViewById(R.id.prevButton);
        next=(ImageView) findViewById(R.id.nextButton);
        seekBar=(SeekBar)findViewById(R.id.MusicPath);
        musicIcon=(ImageView)findViewById(R.id.musicIcon);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        songs=(ArrayList) bundle.getParcelableArrayList("songList");
        songName=intent.getStringExtra("curr_song");
        tv.setText(songName);
        tv.setSelected(true);
        pos=intent.getIntExtra("position",0);
        Uri uri = Uri.parse(songs.get(pos).toString());
        mp=MediaPlayer.create(this,uri);
        seekBar.setMax(mp.getDuration());
        mp.start();
        musicIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicIcon.setRotation(musicIcon.getRotation() + 90);
            }
        });
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(mp!=null) {
                    seekBar.setProgress(mp.getCurrentPosition());
                }
            }
        },0,800);

     new Timer().scheduleAtFixedRate(new TimerTask() {
         @Override
         public void run() {
             if(mp!=null){
                 mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                     @Override
                     public void onCompletion(MediaPlayer mediaPlayer) {
                         mp.stop();
                         mp.release();
                         if(pos!=songs.size()-1){
                             pos=pos+1;
                         }
                         else{
                             pos=0;
                         }
                         Uri uri = Uri.parse(songs.get(pos).toString());
                         mp=MediaPlayer.create(getApplicationContext(),uri);
                         mp.start();
                         play.setImageResource(R.drawable.pause);
                         seekBar.setProgress(0);
                         seekBar.setMax(mp.getDuration());
                         tv.setText(songs.get(pos).getName().toString());

                     }
                 });
             }
         }
     },0,500);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                    mp.seekTo(seekBar.getProgress());
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp.isPlaying()){
                    play.setImageResource(R.drawable.play);
                    mp.pause();
                }
                else{
                    play.setImageResource(R.drawable.pause);
                    mp.start();
                }
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(mp!=null){
                    mp.stop();
                    mp.release();
                }


                if(pos!=0){
                    pos=pos-1;
                }
                else{
                    pos=songs.size()-1;
                }
                Uri uri = Uri.parse(songs.get(pos).toString());
                mp=MediaPlayer.create(getApplicationContext(),uri);
                mp.start();
                play.setImageResource(R.drawable.pause);
                seekBar.setProgress(0);
                seekBar.setMax(mp.getDuration());
                tv.setText(songs.get(pos).getName().toString());

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp!=null){
                    mp.stop();
                    mp.release();
                }
             
                if(pos!=songs.size()-1){
                    pos=pos+1;
                }
                else{
                    pos=0;
                }
                Uri uri = Uri.parse(songs.get(pos).toString());
                mp=MediaPlayer.create(getApplicationContext(),uri);
                mp.start();
                play.setImageResource(R.drawable.pause);
                seekBar.setProgress(0);
                seekBar.setMax(mp.getDuration());
                tv.setText(songs.get(pos).getName().toString());

            }
        });







    }

}