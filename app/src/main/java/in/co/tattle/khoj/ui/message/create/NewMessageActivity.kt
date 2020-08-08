package `in`.co.tattle.khoj.ui.message.create

import `in`.co.tattle.khoj.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_message.*


class NewMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        setSupportActionBar(toolbarMessage)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_message,
                    NewMessageFragment.newInstance()
                )
                .commitNow()
        }
    }

    /*private fun receiveTextIntent(intent: Intent) {
        val values: String? = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (values == null) {
            tvHeading.text = getString(R.string.received_null)
            return;
        } else {
            tvReceived.visibility = View.VISIBLE
            ivReceived.visibility = View.GONE
            tvHeading.text = getString(R.string.received_text)
            tvReceived.text = values
        }
    }

    private fun receiveImageIntent(intent: Intent) {
        val uri: Uri? = intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri
        if (uri == null) {
            tvHeading.text = getString(R.string.received_null)
            return;
        } else {
            tvReceived.visibility = View.GONE
            ivReceived.visibility = View.VISIBLE
            tvHeading.text = getString(R.string.received_image)
            ivReceived.setImageURI(uri)
        }
    }*/

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