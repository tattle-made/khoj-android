package `in`.co.tattle.khoj.ui

import `in`.co.tattle.khoj.BaseActivity
import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.model.IntroObject
import `in`.co.tattle.khoj.ui.adapters.IntroAdapter
import `in`.co.tattle.khoj.ui.home.HomepageActivity
import android.content.Intent
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : BaseActivity() {

    private lateinit var introAdapter: IntroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        setupIntroAdapter()

        introViewPager.adapter = introAdapter

        createPageIndicator()
        markActiveIndicator(0)

        introViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setupNavigationButtons(position)
                markActiveIndicator(position)
            }
        })

        ibIntroNext.setOnClickListener {
            nextButtonClick()
        }

        ibIntroBack.setOnClickListener {
            backButtonClick()
        }
    }

    private fun backButtonClick() {
        introViewPager.currentItem--
    }

    private fun nextButtonClick() {
        if (introViewPager.currentItem == (introAdapter.itemCount - 1)) {
            startNextActivity()
        } else {
            introViewPager.currentItem++
        }
    }

    private fun startNextActivity() {
        startActivity(Intent(this@IntroActivity, HomepageActivity::class.java))
    }

    private fun setupNavigationButtons(position: Int) {
        when (position) {
            0 -> {
                ibIntroBack.visibility = INVISIBLE
            }
            (introAdapter.itemCount - 1) -> {
                ibIntroNext.setImageResource(R.drawable.ic_done)
            }
            else -> {
                ibIntroBack.visibility = VISIBLE
                ibIntroNext.setImageResource(R.drawable.ic_arrow_forward)
            }
        }
    }

    private fun setupIntroAdapter() {
        introAdapter = IntroAdapter(
            listOf(
                IntroObject(
                    getString(R.string.welcome_to_khoj_intro),
                    R.drawable.ic_namaste,
                    R.anim.alpha_anim
                ),
                IntroObject(
                    getString(R.string.get_fake_news_list),
                    R.drawable.ic_fake_news,
                    R.anim.float_down
                ),
                IntroObject(
                    getString(R.string.use_khoj_to_fact_check),
                    R.drawable.ic_detect,
                    R.anim.zoom
                )
            )
        )

    }

    private fun markActiveIndicator(position: Int) {
        for (i in 0 until llIndicatorContainer.childCount) {
            val childView = llIndicatorContainer.getChildAt(i) as ImageView
            if (i == position) {
                childView.setImageResource(R.drawable.active_indicator)
            } else {
                childView.setImageResource(R.drawable.inactive_indicator)
            }
        }
    }

    private fun createPageIndicator() {
        val indicators = arrayOfNulls<ImageView>(introAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 0, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(this)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@IntroActivity,
                        R.drawable.inactive_indicator
                    )
                )
                this?.layoutParams = layoutParams
            }
            llIndicatorContainer.addView(indicators[i])
        }
    }
}