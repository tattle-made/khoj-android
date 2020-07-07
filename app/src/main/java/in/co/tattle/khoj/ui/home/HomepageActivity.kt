package `in`.co.tattle.khoj.ui.home

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.ui.home.historyfragment.HistoryFragment
import `in`.co.tattle.khoj.ui.home.homefragment.HomeNewsFragment
import `in`.co.tattle.khoj.ui.home.morefragment.MoreFragment
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_homepage.*

class HomepageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        setSupportActionBar(toolbarHomepage)

        loadFragment(HomeNewsFragment.newInstance())

        navHome.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    showToolbarWelcome()
                    loadFragment(HomeNewsFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_timeline -> {
                    hideToolbarWelcome()
                    loadFragment(HistoryFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_more -> {
                    hideToolbarWelcome()
                    loadFragment(MoreFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    private fun showToolbarWelcome() {
        llToolbarWelcome.visibility = VISIBLE
    }

    private fun hideToolbarWelcome() {
        llToolbarWelcome.visibility = GONE
    }

    private fun loadFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_home, fragment)
        fragmentTransaction.commit()
    }
}