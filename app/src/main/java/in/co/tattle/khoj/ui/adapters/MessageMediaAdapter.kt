package `in`.co.tattle.khoj.ui.adapters

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.ui.adapters.MessageMediaAdapter.MediaViewHolder
import `in`.co.tattle.khoj.utils.Constants
import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class MessageMediaAdapter(val onAdapterClick: (uri: Uri) -> Unit, val source: String) :
    RecyclerView.Adapter<MediaViewHolder>() {

    var mediaList: ArrayList<Uri> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        return MediaViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_media, parent, false),
            source
        )
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.media.setImageURI(mediaList[position])
        if (TextUtils.equals(source, Constants.SELECTED_MEDIA)) {
            holder.delete.setOnClickListener {
                onAdapterClick(mediaList[position])
            }
        } else if (TextUtils.equals(source, Constants.SCREENSHOTS)) {
            holder.media.setOnClickListener {
                onAdapterClick(mediaList[position])
            }
        }
    }

    class MediaViewHolder(itemView: View, source: String) : RecyclerView.ViewHolder(itemView) {
        val media: ImageView = itemView.findViewById(R.id.imgMedia)
        val delete: ImageView = itemView.findViewById(R.id.imgDelete)

        init {
            if (TextUtils.equals(source, Constants.SCREENSHOTS)) {
                delete.visibility = GONE
            }
        }

    }

    fun updateMedia(mediaList: ArrayList<Uri>) {
        this.mediaList = mediaList
        notifyDataSetChanged()
    }
}