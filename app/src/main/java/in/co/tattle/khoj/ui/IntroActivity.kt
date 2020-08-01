package `in`.co.tattle.khoj.ui

import `in`.co.tattle.khoj.BaseActivity
import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.model.homenews.IntroObject
import `in`.co.tattle.khoj.ui.adapters.IntroAdapter
import `in`.co.tattle.khoj.ui.home.HomepageActivity
import `in`.co.tattle.khoj.utils.ViewPagerUtils
import android.content.Intent
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : BaseActivity() {

    private lateinit var introAdapter: IntroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        setupIntroAdapter()

        introViewPager.adapter = introAdapter

        ViewPagerUtils.createPageIndicator(
            introAdapter.itemCount,
            this,
            llIndicatorContainer
        )
        ViewPagerUtils.markActiveIndicator(0, llIndicatorContainer)

        introViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setupNavigationButtons(position)
                ViewPagerUtils.markActiveIndicator(position, llIndicatorContainer)
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
        val intent = Intent(this@IntroActivity, HomepageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
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

}