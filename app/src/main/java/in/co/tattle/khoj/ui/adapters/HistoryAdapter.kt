package `in`.co.tattle.khoj.ui.adapters

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.model.queryhistory.QueryHistoryItem
import `in`.co.tattle.khoj.utils.Utils
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView

class HistoryAdapter(val onTimelineClick: (queryId: String) -> Unit) :
    RecyclerView.Adapter<HistoryAdapter.TimelineViewHolder>() {

    private var queryHistory: ArrayList<QueryHistoryItem> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TimelineViewHolder {
        return TimelineViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.adapter_message_timeline,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return queryHistory.size
    }

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        if (position != 0 && TextUtils.equals(
                Utils.getDateFromDateTime(queryHistory[position].createdAt),
                Utils.getDateFromDateTime(queryHistory[position - 1].createdAt)
            )
        ) {
            holder.date.visibility = GONE
            holder.circleView.visibility = GONE
        } else {
            holder.date.visibility = VISIBLE
            holder.circleView.visibility = VISIBLE
            holder.date.text = Utils.getDateFromDateTime(queryHistory[position].createdAt)
        }
        if (TextUtils.isEmpty(queryHistory[position].question)) {
            holder.timelineText.visibility = GONE
        } else {
            holder.timelineText.visibility = VISIBLE
            holder.timelineText.text = queryHistory[position].question
        }
        if (queryHistory[position].media.size > 0) {
            holder.timelineImage.visibility = VISIBLE
            val circularProgressDrawable = CircularProgressDrawable(holder.timelineImage.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            if (TextUtils.isEmpty(queryHistory[position].question)) {
                Glide.with(holder.timelineImage.context)
                    .load(queryHistory[position].media[0].formats.small.url)
                    .placeholder(circularProgressDrawable)
                    .into(holder.timelineImage)
            } else {
                Glide.with(holder.timelineImage.context)
                    .load(queryHistory[position].media[0].formats.thumbnail.url)
                    .placeholder(circularProgressDrawable)
                    .into(holder.timelineImage)
            }
        } else {
            holder.timelineImage.visibility = GONE
        }

        holder.cardMessage.setOnClickListener {
            onTimelineClick(queryHistory[position]._id)
        }
    }

    fun updateData(timeline: ArrayList<QueryHistoryItem>) {
        this.queryHistory = timeline
        notifyDataSetChanged()
    }

    class TimelineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.tvDate)
        val timelineText: TextView = itemView.findViewById(R.id.tvTimelineText)
        val timelineImage: ImageView = itemView.findViewById(R.id.ivTimeline)
        val timelineResponse: TextView = itemView.findViewById(R.id.tvTimelineResponse)
        val circleView: View = itemView.findViewById(R.id.view_circle)
        val cardMessage: MaterialCardView = itemView.findViewById(R.id.cardMessage)
    }
}