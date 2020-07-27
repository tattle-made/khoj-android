package `in`.co.tattle.khoj.ui.setup

import `in`.co.tattle.khoj.BaseActivity
import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.ui.IntroActivity
import `in`.co.tattle.khoj.utils.Constants
import `in`.co.tattle.khoj.utils.Status
import `in`.co.tattle.khoj.utils.ui.LoadingDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_setup.*

class SetupActivity : BaseActivity(), View.OnClickListener {

    lateinit var viewModel: SetupViewModel
    lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

        loadingDialog = LoadingDialog(this)
        viewModel = ViewModelProvider(this).get(SetupViewModel::class.java)

        btnHindi.setOnClickListener(this)
        btnEnglish.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnHindi -> {
                viewModel.setupLanguage(Constants.HINDI)
                registerUser(Constants.HINDI)
            }
            R.id.btnEnglish -> {
                viewModel.setupLanguage(Constants.ENGLISH)
                registerUser(Constants.ENGLISH)
            }
        }
    }

    private fun registerUser(language: String) {
        viewModel.registerUser().observe(this, Observer { result ->
            when (result.status) {
                Status.LOADING -> {
                    loadingDialog.show()
                    if (TextUtils.equals(language, Constants.ENGLISH)) {
                        loadingDialog.setMessage(getString(R.string.setting_up_eng))
                    } else if (TextUtils.equals(language, Constants.HINDI)) {
                        loadingDialog.setMessage(getString(R.string.setting_up_hin))
                    }
                }
                Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    startIntroActivity()
                }
                Status.ERROR -> {
                    loadingDialog.dismiss()
                    Toast.makeText(
                        this@SetupActivity,
                        getString(R.string.error_try_later),
                        LENGTH_SHORT
                    ).show()
                }
                Status.NO_NETWORK -> {
                    loadingDialog.dismiss()
                    Toast.makeText(this@SetupActivity, getString(R.string.offline), LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun startIntroActivity() {
        val intent = Intent(this, IntroActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}