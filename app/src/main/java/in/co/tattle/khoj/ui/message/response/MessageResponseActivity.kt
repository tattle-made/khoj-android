package `in`.co.tattle.khoj.ui.message.response

import `in`.co.tattle.khoj.BaseActivity
import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.utils.Constants
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_new_message.*

class MessageResponseActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_response)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_message_response, MessageResponseFragment.newInstance(
                        intent.getStringExtra(Constants.MESSAGE_ID)!!
                    )
                )
                .commitNow()
        }
        setSupportActionBar(toolbarMessage)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }
}