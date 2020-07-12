package `in`.co.tattle.khoj.ui.setup

import `in`.co.tattle.khoj.BaseActivity
import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.ui.IntroActivity
import `in`.co.tattle.khoj.utils.Constants
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_setup.*

class SetupActivity : BaseActivity(), View.OnClickListener {

    lateinit var viewModel: SetupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

        viewModel = ViewModelProvider(this).get(SetupViewModel::class.java)

        btnHindi.setOnClickListener(this)
        btnEnglish.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnHindi -> {
                viewModel.setupLanguage(Constants.HINDI)
                startIntroActivity()
            }
            R.id.btnEnglish -> {
                viewModel.setupLanguage(Constants.ENGLISH)
                startIntroActivity()
            }
        }
    }

    private fun startIntroActivity() {
        val intent = Intent(this, IntroActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}