package com.wm.fetchsongsfromdevice.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.wm.fetchsongsfromdevice.R;
import com.wm.fetchsongsfromdevice.adapter.MusicAdapter;
import com.wm.fetchsongsfromdevice.interfaces.OnItemClickListener;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView textViewTotalSong;
    Cursor musicCursor;
    int music_column_index;
    int count;
    static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        textViewTotalSong = findViewById(R.id.textViewTotalSong);
        getDeviceSongs();
    }

    private void getDeviceSongs() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

// Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

// Show an explanation to the user *asynchronously* -- don't block
// this thread waiting for the user's response! After the user
// sees the explanation, try again to request the permission.

            } else {

// No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

// MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
// app-defined int constant. The callback method gets the
// result of the request.
            }
        }


    }

    private void AccessSongsAndInit() {
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.AudioColumns.TITLE,// 1
                MediaStore.Audio.AudioColumns.TRACK,// 2
                MediaStore.Audio.AudioColumns.YEAR,// 3
                MediaStore.Audio.AudioColumns.DURATION,// 4
                MediaStore.Audio.AudioColumns.DATA,// 5
                MediaStore.Audio.AudioColumns.DATE_MODIFIED,// 6
                MediaStore.Audio.AudioColumns.ALBUM_ID,// 7
                MediaStore.Audio.AudioColumns.ALBUM,// 8
                MediaStore.Audio.AudioColumns.ARTIST_ID,// 9
                MediaStore.Audio.AudioColumns.ARTIST,// 10
                MediaStore.Audio.AudioColumns.DISPLAY_NAME,// 11
        };
        // permission was granted, yay! Do the
        // contacts-related task you need to do.
        //callMethod();
        mMediaPlayer = new MediaPlayer();
        musicCursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null);
        assert musicCursor != null;
        count = musicCursor.getCount();
        //set Total Song count
        String totalSongCount = "Total Songs : " + "\n" + count;
        textViewTotalSong.setText(totalSongCount);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MusicAdapter(this, musicCursor, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                music_column_index = musicCursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                musicCursor.moveToPosition(position);
                String filename = musicCursor.getString(music_column_index);
                try {
                    if (mMediaPlayer.isPlaying()) mMediaPlayer.reset();
                    mMediaPlayer.setDataSource(filename);
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NotNull String[] permissions, @NotNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                AccessSongsAndInit();
            }

// other 'case' lines to check for other
// permissions this app might request
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mMediaPlayer != null)
            mMediaPlayer.stop();
    }
}
