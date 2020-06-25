package com.tattle.khoj.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tattle.khoj.R
import com.tattle.khoj.model.IntroObject

class IntroAdapter(private val introList: List<IntroObject>) :
    RecyclerView.Adapter<IntroAdapter.IntroViewHolder>() {

    inner class IntroViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val introImage = view.findViewById<ImageView>(R.id.ivIntro)
        val introText = view.findViewById<TextView>(R.id.tvIntro)
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
        holder.introText.text = introList[position].description
        holder.introImage.post(Runnable {
            val animation: Animation = AnimationUtils.loadAnimation(
                holder.introImage.context,
                introList[position].animation
            )
            holder.introImage.startAnimation(animation)
        })

    }

}