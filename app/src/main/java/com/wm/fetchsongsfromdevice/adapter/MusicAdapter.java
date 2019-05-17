package com.wm.fetchsongsfromdevice.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wm.fetchsongsfromdevice.R;
import com.wm.fetchsongsfromdevice.interfaces.OnItemClickListener;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.VIewHolderMusic> {
    private Context context;
    private Cursor cursor;
    private OnItemClickListener onItemClickListener;

    public MusicAdapter(Context context, Cursor musiccursor, OnItemClickListener onItemClickListener) {
        this.context = context;
        cursor = musiccursor;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public VIewHolderMusic onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.song_row_item, viewGroup, false);
        return new VIewHolderMusic(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VIewHolderMusic vIewHolderMusic, final int position) {
        int index = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME);
        cursor.moveToPosition(position);
        int index1 = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION);
        cursor.moveToPosition(position);

       // long minutes = TimeUnit.MILLISECONDS.toMinutes(cursor.getLong(index1));
        //long seconds = TimeUnit.MILLISECONDS.toSeconds(cursor.getLong(index1));
//        Log.d("readable time", millisecondsToTime(cursor.getLong(index1)));
        //code for album image//perfectly work
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToPosition(position);
        String pathId = cursor.getString(column_index);
        Log.d("Path", "path id=" + pathId);
        MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
        metaRetriever.setDataSource(pathId);
        Bitmap songImage = null;
        try {
            byte[] art = metaRetriever.getEmbeddedPicture();
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inSampleSize = 2;
            songImage = BitmapFactory.decodeByteArray(art, 0, art.length, opt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        vIewHolderMusic.song_title.setText(cursor.getString(index));
        vIewHolderMusic.song_size.setText(millisecondsToTime(cursor.getLong(index1)));

        if (songImage != null)
            vIewHolderMusic.imageIcon.setImageBitmap(songImage);
        else
            vIewHolderMusic.imageIcon.setImageResource(R.drawable.ic_audiotrack);
        vIewHolderMusic.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    class VIewHolderMusic extends RecyclerView.ViewHolder {
        TextView song_title, song_size;
        ImageView imageIcon;

        VIewHolderMusic(@NonNull View itemView) {
            super(itemView);
            song_size = itemView.findViewById(R.id.song_size);
            song_title = itemView.findViewById(R.id.song_title);
            imageIcon = itemView.findViewById(R.id.imageIcon);
        }
    }

    private String millisecondsToTime(long milliseconds) {
        long minutes = (milliseconds / 1000) / 60;
        long seconds = (milliseconds / 1000) % 60;
        String secondsStr = Long.toString(seconds);
        String secs;
        if (secondsStr.length() >= 2) {
            secs = secondsStr.substring(0, 2);
        } else {
            secs = "0" + secondsStr;
        }

        return minutes + ":" + secs;
    }
}
