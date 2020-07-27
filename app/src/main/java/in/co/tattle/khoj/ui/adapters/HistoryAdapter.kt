package `in`.co.tattle.khoj.ui.adapters

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.model.timeline.UserTimeline
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class HistoryAdapter(val onTimelineClick: () -> Unit) :
    RecyclerView.Adapter<HistoryAdapter.TimelineViewHolder>() {

    var timeline: ArrayList<UserTimeline> = arrayListOf()

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
        return timeline.size
    }

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        if (position != 0 && TextUtils.equals(
                timeline[position].date,
                timeline[position - 1].date
            )
        ) {
            holder.date.visibility = GONE
            holder.circleView.visibility = GONE
        } else {
            holder.date.visibility = VISIBLE
            holder.circleView.visibility = VISIBLE
            holder.date.text = timeline[position].date
        }
        holder.timelineText.text = timeline[position].message

        holder.cardMessage.setOnClickListener {
            onTimelineClick()
        }
    }

    fun updateData(timeline: ArrayList<UserTimeline>) {
        this.timeline = timeline
        notifyDataSetChanged()
    }

    class TimelineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.tvDate)
        val timelineText: TextView = itemView.findViewById(R.id.tvTimelineText)
        val circleView: View = itemView.findViewById(R.id.view_circle)
        val cardMessage: MaterialCardView = itemView.findViewById(R.id.cardMessage)
    }
}