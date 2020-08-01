package `in`.co.tattle.khoj.utils

import `in`.co.tattle.khoj.R
import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ViewPagerUtils {
    companion object {
        @JvmStatic
        fun markActiveIndicator(position: Int, linearLayout: LinearLayout) {
            for (i in 0 until linearLayout.childCount) {
                val childView = linearLayout.getChildAt(i) as ImageView
                if (i == position) {
                    childView.setImageResource(R.drawable.active_indicator)
                } else {
                    childView.setImageResource(R.drawable.inactive_indicator)
                }
            }
        }

        @JvmStatic
        fun createPageIndicator(
            itemCount: Int,
            context: Context,
            linearLayout: LinearLayout
        ) {
            val indicators = arrayOfNulls<ImageView>(itemCount)
            val layoutParams: LinearLayout.LayoutParams =
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            layoutParams.setMargins(8, 0, 0, 0)
            for (i in indicators.indices) {
                indicators[i] = ImageView(context)
                indicators[i].apply {
                    this?.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.inactive_indicator
                        )
                    )
                    this?.layoutParams = layoutParams
                }
                linearLayout.addView(indicators[i])
            }
        }
    }
}