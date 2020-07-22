package `in`.co.tattle.khoj.ui.home

import `in`.co.tattle.khoj.BaseActivity
import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.ui.home.historyfragment.HistoryFragment
import `in`.co.tattle.khoj.ui.home.homefragment.HomeNewsFragment
import `in`.co.tattle.khoj.ui.home.morefragment.MoreFragment
import `in`.co.tattle.khoj.ui.message.create.NewMessageActivity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_homepage.*

class HomepageActivity : BaseActivity(), View.OnClickListener,
    BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        setSupportActionBar(toolbarHomepage)

        loadFragment(HomeNewsFragment.newInstance())

        navHome.setOnNavigationItemSelectedListener(this)

        btnNewMessage.setOnClickListener(this)
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

    override fun onClick(view: View?) {
        if (view?.id == R.id.btnNewMessage) {
            startActivity(Intent(this, NewMessageActivity::class.java))
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_home -> {
                showToolbarWelcome()
                loadFragment(HomeNewsFragment.newInstance())
                return true
            }
            R.id.menu_timeline -> {
                hideToolbarWelcome()
                loadFragment(HistoryFragment.newInstance())
                return true
            }
            R.id.menu_more -> {
                hideToolbarWelcome()
                loadFragment(MoreFragment.newInstance())
                return true
            }
        }
        return false
    }
}