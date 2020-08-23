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
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_homepage.*


class HomepageActivity : BaseActivity(), View.OnClickListener,
    BottomNavigationView.OnNavigationItemSelectedListener, AppBarLayout.OnOffsetChangedListener {

    private var isShow = true
    private var scrollRange = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        setSupportActionBar(toolbarHomepage)

        loadFragment(HomeNewsFragment.newInstance())

        navHome.setOnNavigationItemSelectedListener(this)

        btnNewMessage.setOnClickListener(this)

        appBar.addOnOffsetChangedListener(this)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (scrollRange == -1) {
            scrollRange = appBarLayout?.totalScrollRange!!
        }
        if (scrollRange + verticalOffset == 0) {
            toolbarHomepage.title = getString(R.string.app_name)
            isShow = true
        } else if (isShow) {
            toolbarHomepage.title = " "
            isShow = false
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

    override fun onBackPressed() {
        val selectedItemId = navHome.selectedItemId
        if (R.id.menu_home != selectedItemId) {
            navHome.selectedItemId = R.id.menu_home
        } else {
            super.onBackPressed()
        }
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.btnNewMessage) {
            startActivity(Intent(this, NewMessageActivity::class.java))
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_home -> {
                loadFragment(HomeNewsFragment.newInstance())
                showToolbarWelcome()
                toolbarHomepage.title = " "
                return true
            }
            R.id.menu_timeline -> {
                loadFragment(HistoryFragment.newInstance())
                hideToolbarWelcome()
                toolbarHomepage.title = getString(R.string.app_name)
                return true
            }
            R.id.menu_more -> {
                loadFragment(MoreFragment.newInstance())
                hideToolbarWelcome()
                toolbarHomepage.title = getString(R.string.app_name)
                return true
            }
        }
        return false
    }

    fun changeLanguage() {
        tvHello.text = getString(R.string.toolbar_greeting)
        tvFactCheck.text = getString(R.string.fact_check_rumors)
        btnNewMessage.text = getString(R.string.try_it)
    }

}