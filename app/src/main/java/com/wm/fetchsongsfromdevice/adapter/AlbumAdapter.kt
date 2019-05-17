package com.wm.fetchsongsfromdevice.adapter

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.wm.fetchsongsfromdevice.R


internal class AlbumAdapter(private val context: Context, private val listOfAlbums: ArrayList<AlbumModel>) : RecyclerView.Adapter<AlbumAdapter.ViewHolderAlbum>() {
    override fun getItemCount(): Int {
        return listOfAlbums.size;
    }

    override fun onCreateViewHolder(parentGroup: ViewGroup, position: Int): ViewHolderAlbum {
        return ViewHolderAlbum(LayoutInflater.from(context).inflate(R.layout.album_row_item, parentGroup, false))
    }

    override fun onBindViewHolder(holder: ViewHolderAlbum, position: Int) {
        holder.albumTitle.text = listOfAlbums[position].albumName
        holder.albumArtist.text = listOfAlbums[position].albumArtist
        getAlbumArt(listOfAlbums[position].albumId,holder)
    }



    class ViewHolderAlbum(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var albumTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        var albumArtist: TextView = itemView.findViewById(R.id.textViewArtist)
        var imageViewArt: ImageView = itemView.findViewById(R.id.imageViewArt)
    }
    private fun getAlbumArt(album_id: Long?, holder: ViewHolderAlbum){
        val bm: Bitmap


        try {
            val sArtworkUri = Uri
                    .parse("content://media/external/audio/albumart")
            val uri = ContentUris.withAppendedId(sArtworkUri, album_id!!)
            val pfd = context.contentResolver
                    .openFileDescriptor(uri, "r")
            if (pfd != null) {
                val fd = pfd.fileDescriptor
                bm = BitmapFactory.decodeFileDescriptor(fd)
                holder.imageViewArt.setImageBitmap(bm)
            }
        } catch (e: Exception) {
        }
    }

}
