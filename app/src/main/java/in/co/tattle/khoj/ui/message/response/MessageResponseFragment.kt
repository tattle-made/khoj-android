package `in`.co.tattle.khoj.ui.message.response

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.utils.Constants
import `in`.co.tattle.khoj.utils.Status
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_message_response.*

class MessageResponseFragment : Fragment() {

    companion object {
        fun newInstance(messageId: String): MessageResponseFragment {
            val messageResponseFragment = MessageResponseFragment()
            val bundle = Bundle()
            bundle.putString(Constants.MESSAGE_ID, messageId)
            messageResponseFragment.arguments = bundle
            return messageResponseFragment
        }
    }

    private lateinit var messageId: String
    private lateinit var viewModel: MessageResponseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_message_response, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messageId = arguments?.getString(Constants.MESSAGE_ID)!!

        viewModel = ViewModelProvider(this).get(MessageResponseViewModel::class.java)
        /*Glide.with(this)
            .load("https://cdn.pixabay.com/photo/2014/02/09/05/19/woman-262498_1280.jpg")
            .into(ivMessage)

        tvMessage.text =
            "A photograph of a man with Uttar Pardesh Chief Minister Yogi Adityanath was shared by Jan Adhikar Party leader Rajesh Ranjan, popularly known as Pappu Yadav. He claimed that the man is gangster Vikas Dubey whose attempted arrest in Kanpur led to the death of eight policemen."*/

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getMessageResponse(messageId)
            .observe(viewLifecycleOwner, Observer { messageResponse ->
                when (messageResponse.status) {
                    Status.LOADING -> {
                        showLoading()
                    }
                    Status.SUCCESS -> {
                        showResponse()
                    }
                    Status.ERROR -> {
                        showError()
                    }
                    Status.NO_NETWORK -> {
                        showOffline()
                    }
                }
            })
    }

    private fun showResponse() {
        layoutError.visibility = View.GONE
        layoutLoading.visibility = View.GONE
        layoutResponse.visibility = View.VISIBLE
    }

    private fun showLoading() {
        layoutResponse.visibility = View.GONE
        layoutError.visibility = View.GONE
        layoutLoading.visibility = View.VISIBLE
    }

    private fun showError() {
        layoutResponse.visibility = View.GONE
        layoutLoading.visibility = View.GONE
        layoutError.visibility = View.VISIBLE
        imgError.setImageResource(R.drawable.ic_api_error)
        tvError.setText(R.string.error_occurred)
    }

    private fun showOffline() {
        layoutResponse.visibility = View.GONE
        layoutLoading.visibility = View.GONE
        layoutError.visibility = View.VISIBLE
        imgError.setImageResource(R.drawable.ic_no_network)
        tvError.setText(R.string.offline)
    }
}