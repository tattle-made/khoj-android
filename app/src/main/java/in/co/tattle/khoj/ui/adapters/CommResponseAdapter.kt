package `in`.co.tattle.khoj.ui.adapters

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.model.queryresponse.Response
import `in`.co.tattle.khoj.utils.Utils
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CommResponseAdapter(
    private val context: Context,
    private val responses: ArrayList<Response>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.TEXT.ordinal -> TextViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.adapter_response_text,
                    parent,
                    false
                )
            )
            ViewType.IMAGE.ordinal -> ImageViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.adapter_response_image,
                    parent,
                    false
                )
            )
            ViewType.URL.ordinal -> URLViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.adapter_response_url,
                    parent,
                    false
                )
            )
            else -> TextViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.adapter_response_text,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return responses.size
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            TextUtils.equals(responses[position].type, "text") -> ViewType.TEXT.ordinal
            TextUtils.equals(responses[position].type, "image") -> ViewType.IMAGE.ordinal
            TextUtils.equals(responses[position].type, "url") -> ViewType.URL.ordinal
            else -> ViewType.TEXT.ordinal
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ViewType.TEXT.ordinal -> {
                (holder as TextViewHolder).headline.text =
                    responses[position].text.heading
                holder.byline.text =
                    responses[position].text.byline
            }
            ViewType.IMAGE.ordinal -> {
                Glide.with(context)
                    .load(responses[position].image.image.formats.small.url)
                    .into((holder as ImageViewHolder).responseImage)
                holder.caption.text =
                    responses[position].image.caption
            }
            ViewType.URL.ordinal -> {
                Glide.with(context)
                    .load(responses[position].url.thumbnail.formats.small.url)
                    .into((holder as URLViewHolder).responseImage)
                holder.headline.text =
                    responses[position].url.headline
                holder.byline.text =
                    responses[position].url.byline
                holder.responseURL.setOnClickListener {
                    Utils.startURLActivity(context, responses[position].url.url)
                }
            }
        }
    }

    enum class ViewType {
        TEXT,
        IMAGE,
        URL
    }

    class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var headline: TextView = itemView.findViewById(R.id.tvResponseHeadline)
        var byline: TextView = itemView.findViewById(R.id.tvResponseByline)
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var caption: TextView = itemView.findViewById(R.id.tvResponseCaption)
        var responseImage: ImageView = itemView.findViewById(R.id.ivResponseImage)
    }

    class URLViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var headline: TextView = itemView.findViewById(R.id.tvResponseHeadline)
        var byline: TextView = itemView.findViewById(R.id.tvResponseByline)
        var responseImage: ImageView = itemView.findViewById(R.id.ivResponseURL)
        var responseURL: TextView = itemView.findViewById(R.id.tvResponseURL)
    }

}