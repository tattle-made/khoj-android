package `in`.co.tattle.khoj.utils.ui

import `in`.co.tattle.khoj.R
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import kotlinx.android.synthetic.main.loading_layout.*

class LoadingDialog(context: Context) : AlertDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.loading_layout)

        setCancelable(false)

    }

    fun setMessage(loadingText: String) {
        tvLoadingText.text = loadingText
    }

}