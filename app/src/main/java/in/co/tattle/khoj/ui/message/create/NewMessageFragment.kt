package `in`.co.tattle.khoj.ui.message.create

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.ui.message.response.MessageResponseActivity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_new_message.*

class NewMessageFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() =
            NewMessageFragment()
    }

    private lateinit var viewModel: NewMessageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_new_message, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewMessageViewModel::class.java)

        btnCopy.setOnClickListener(this)
        btnScreenshot.setOnClickListener(this)
        btnMedia.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)

        setupClipboardObserver()
    }

    private fun setupClipboardObserver() {
        viewModel.getClipBoard().observe(viewLifecycleOwner, Observer { clipboardText ->
            if (TextUtils.isEmpty(clipboardText)) {
                tvOr.visibility = GONE
                btnCopy.visibility = GONE
                tvClipboard.visibility = GONE
            } else {
                tvOr.visibility = VISIBLE
                btnCopy.visibility = VISIBLE
                tvClipboard.visibility = VISIBLE
                tvClipboard.text = clipboardText
            }
        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnCopy -> {
                etMessage.setText(tvClipboard.text)
            }
            R.id.btnScreenshot -> {

            }
            R.id.btnMedia -> {

            }
            R.id.btnSubmit -> {
                startActivity(Intent(context, MessageResponseActivity::class.java))
            }
        }
    }

}