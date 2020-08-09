package `in`.co.tattle.khoj.ui.adapters

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.model.IntroObject
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class IntroAdapter(private val introList: List<IntroObject>) :
    RecyclerView.Adapter<IntroAdapter.IntroViewHolder>() {

    inner class IntroViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val introImage: ImageView = view.findViewById(R.id.ivIntro)
//        val introText: TextView = view.findViewById(R.id.tvIntro)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroViewHolder {
        return IntroViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.intro_slider_layout,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return introList.size
    }

    override fun onBindViewHolder(holder: IntroViewHolder, position: Int) {
        holder.introImage.setImageResource(introList[position].imageId)
//        holder.introText.text = introList[position].description
//        holder.introImage.post(Runnable {
//            val animation: Animation = AnimationUtils.loadAnimation(
//                holder.introImage.context,
//                introList[position].animation
//            )
//            holder.introImage.startAnimation(animation)
//        })

    }

}