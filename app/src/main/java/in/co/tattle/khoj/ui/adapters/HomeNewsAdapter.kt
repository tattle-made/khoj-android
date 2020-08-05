package `in`.co.tattle.khoj.ui.adapters

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.model.homenews.ArticleShare
import `in`.co.tattle.khoj.model.homenews.ArticleTrending
import `in`.co.tattle.khoj.utils.Utils
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView

class HomeNewsAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var articleTrending: ArrayList<ArticleTrending> = arrayListOf()
    private var articleShare: ArrayList<ArticleShare> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.TRENDING.ordinal -> TrendingViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.adapter_response_url,
                    parent,
                    false
                )
            )
            ViewType.SHARE.ordinal -> ShareViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.adapter_home_share,
                    parent,
                    false
                )
            )
            else -> TrendingViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.adapter_response_url,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return (articleTrending.size + articleShare.size)
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position < articleTrending.size -> ViewType.TRENDING.ordinal
            else -> ViewType.SHARE.ordinal
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        when (getItemViewType(position)) {
            ViewType.TRENDING.ordinal -> {
                Glide.with(context)
                    .load(articleTrending[position].thumbnail.formats.thumbnail.url)
                    .placeholder(circularProgressDrawable)
                    .into((holder as TrendingViewHolder).responseImage)
                holder.headline.text =
                    articleTrending[position].Headline
                holder.byline.text =
                    articleTrending[position].byline
                holder.card.setOnClickListener {
                    Utils.startURLActivity(context, articleTrending[position].source)
                }
            }
            ViewType.SHARE.ordinal -> {
                Glide.with(context)
                    .load(articleShare[position - articleTrending.size].thumbnail.formats.thumbnail.url)
                    .placeholder(circularProgressDrawable)
                    .into((holder as ShareViewHolder).imgShare)
                holder.tvShareHead.text =
                    articleShare[position - articleTrending.size].Headline
                holder.tvShareDescription.text =
                    articleShare[position - articleTrending.size].byline
            }
        }
    }

    fun updateData(
        articleTrending: ArrayList<ArticleTrending>,
        articleShare: ArrayList<ArticleShare>
    ) {
        this.articleTrending = articleTrending
        this.articleShare = articleShare
        notifyDataSetChanged()
    }

    enum class ViewType {
        TRENDING,
        SHARE
    }

    class TrendingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headline: TextView = itemView.findViewById(R.id.tvResponseHeadline)
        val byline: TextView = itemView.findViewById(R.id.tvResponseByline)
        val responseImage: ImageView = itemView.findViewById(R.id.ivResponseURL)
        val responseURL: TextView = itemView.findViewById(R.id.tvResponseURL)
        val card: MaterialCardView = itemView.findViewById(R.id.cardResponse)

        init {
            responseURL.visibility = GONE
        }
    }

    class ShareViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvShare: TextView = itemView.findViewById(R.id.tvShare)
        val imgShare: ImageView = itemView.findViewById(R.id.imgShare)
        val tvShareHead: TextView = itemView.findViewById(R.id.tvShareHead)
        val tvShareDescription: TextView = itemView.findViewById(R.id.tvShareDescription)
    }
}