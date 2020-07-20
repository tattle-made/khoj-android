package `in`.co.tattle.khoj.ui.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class NewMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_message_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NewMessageFragment.newInstance())
                .commitNow()
        }
    }
}