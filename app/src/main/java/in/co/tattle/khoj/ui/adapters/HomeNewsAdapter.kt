package `in`.co.tattle.khoj.ui.adapters

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.model.HomepageItem
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class HomeNewsAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var homepageItem: HomepageItem = HomepageItem()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ViewType.TRENDING.ordinal -> TrendingViewHolder(
                inflater.inflate(
                    R.layout.home_adapter_trending,
                    parent,
                    false
                )
            )
            ViewType.SHARE.ordinal -> ShareViewHolder(
                inflater.inflate(
                    R.layout.home_adapter_share,
                    parent,
                    false
                )
            )
            else -> TrendingViewHolder(
                inflater.inflate(
                    R.layout.home_adapter_trending,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return when (homepageItem.article_share.id) {
            -1 -> homepageItem.article_trending.size
            else -> homepageItem.article_trending.size + 1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position < homepageItem.article_trending.size -> ViewType.TRENDING.ordinal
            else -> ViewType.SHARE.ordinal
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ViewType.TRENDING.ordinal -> {
                Glide.with(context)
                    .load(homepageItem.article_trending[position].thumbnail)
                    .into((holder as TrendingViewHolder).imgTrending)
                holder.tvTrendingHead.text =
                    homepageItem.article_trending[position].heading
                holder.tvTrendingDesc.text =
                    homepageItem.article_trending[position].byline
            }
            ViewType.SHARE.ordinal -> {
                Glide.with(context)
                    .load(homepageItem.article_share.image_thumbnail)
                    .into((holder as ShareViewHolder).imgShare)
                holder.tvShareHead.text =
                    homepageItem.article_share.heading
                holder.tvShareDescription.text =
                    homepageItem.article_share.byline
            }
        }
    }

    fun updateData(homepageItem: HomepageItem) {
        this.homepageItem = homepageItem
        notifyDataSetChanged()
    }

    enum class ViewType {
        TRENDING,
        SHARE
    }

    class TrendingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgTrending: ImageView = itemView.findViewById(R.id.imgTrending)
        var tvTrendingHead: TextView = itemView.findViewById(R.id.tvTrendingHead)
        var tvTrendingDesc: TextView = itemView.findViewById(R.id.tvTrendingDescription)
    }

    class ShareViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvShare: TextView = itemView.findViewById(R.id.tvShare)
        var imgShare: ImageView = itemView.findViewById(R.id.imgShare)
        var tvShareHead: TextView = itemView.findViewById(R.id.tvShareHead)
        var tvShareDescription: TextView = itemView.findViewById(R.id.tvShareDescription)
    }
}