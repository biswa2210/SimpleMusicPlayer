package com.example.simplemusicplayer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Adding Customized Appbar
        //---------------------------------->
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.appbar);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FFA500"));
        actionBar.setBackgroundDrawable(colorDrawable);
        //---------------------------------->
        lv=(ListView)findViewById(R.id.listview);
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                        ArrayList<File> mySongs=getSongs(Environment.getExternalStorageDirectory());
                        String[] items = new String[mySongs.size()];
                        for(int i=0;i<mySongs.size();i++){
                            items[i]=mySongs.get(i).getName().replace(".mp3","").replace(".wav","");
                        }
                        CustomListView adapter = new CustomListView(MainActivity.this,R.layout.activity_custom_list_view,items);
                        lv.setAdapter(adapter);
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(MainActivity.this, PlaySongAct.class);
                                String song = lv.getItemAtPosition(i).toString();
                                intent.putExtra("songList", mySongs);
                                intent.putExtra("curr_song", song);
                                intent.putExtra("position", i);
                                startActivity(intent);
                            }
                        });

                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();

                    }
                })
                .check();
    }
    public ArrayList<File> getSongs(File file){
        ArrayList arrayList = new ArrayList();
        File[] songs = file.listFiles();
        if(songs != null){
            for(File myfile : songs){
                if(!myfile.isHidden() && myfile.isDirectory()){
                    arrayList.addAll(getSongs(myfile));
                }
                else{
                    if(myfile.getName().endsWith(".mp3") || myfile.getName().endsWith(".wav") && !myfile.getName().startsWith(".")){
                            arrayList.add(myfile);
                    }
                }
            }
        }
        return arrayList;

    }
}