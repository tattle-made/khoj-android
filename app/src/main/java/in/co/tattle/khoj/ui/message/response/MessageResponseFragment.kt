package `in`.co.tattle.khoj.ui.message.response

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.model.Feedback
import `in`.co.tattle.khoj.model.queryresponse.QueryResponse
import `in`.co.tattle.khoj.model.queryresponse.Response
import `in`.co.tattle.khoj.model.queryresponse.Summary
import `in`.co.tattle.khoj.ui.adapters.CommResponseAdapter
import `in`.co.tattle.khoj.ui.adapters.MediaPagerAdapter
import `in`.co.tattle.khoj.utils.Constants
import `in`.co.tattle.khoj.utils.Status
import `in`.co.tattle.khoj.utils.ViewPagerUtils
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_message_response.*

class MessageResponseFragment : Fragment(), RadioGroup.OnCheckedChangeListener {

    companion object {
        fun newInstance(messageId: String): MessageResponseFragment {
            val messageResponseFragment = MessageResponseFragment()
            val bundle = Bundle()
            bundle.putString(Constants.MESSAGE_ID, messageId)
            messageResponseFragment.arguments = bundle
            return messageResponseFragment
        }
    }

    private lateinit var queryID: String
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
        setupObservers()
    }

    //setup Observer for the response for the query
    private fun setupObservers() {
        viewModel.getMessageResponse(messageId)
            .observe(viewLifecycleOwner, Observer { queryResponse ->
                when (queryResponse.status) {
                    Status.LOADING -> {
                        showLoading()
                    }
                    Status.SUCCESS -> {
                        queryID = queryResponse.data!!._id
                        showResponse(queryResponse.data)
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

    //show response when API is successful
    private fun showResponse(data: QueryResponse?) {
        layoutError.visibility = GONE
        layoutLoading.visibility = GONE
        layoutResponse.visibility = VISIBLE
        setupResponse(data)
    }

    private fun setupResponse(queryResponse: QueryResponse?) {
        //setup media viewpager
        if (queryResponse!!.media.size > 0) {
            mediaViewPager.visibility = VISIBLE
            mediaViewPager.adapter = MediaPagerAdapter(queryResponse.media)
            if (queryResponse.media.size > 1) {
                ViewPagerUtils.createPageIndicator(
                    mediaViewPager.adapter!!.itemCount,
                    requireContext(),
                    llIndicatorContainer
                )
                ViewPagerUtils.markActiveIndicator(0, llIndicatorContainer)

                mediaViewPager.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        ViewPagerUtils.markActiveIndicator(position, llIndicatorContainer)
                    }
                })
            }
        } else {
            mediaViewPager.visibility = GONE
        }

        //setup question
        if (!TextUtils.isEmpty(queryResponse.question)) {
            tvQuestion.visibility = VISIBLE
            tvQuestion.text = queryResponse.question
        } else {
            tvQuestion.visibility = GONE
        }

        //setup community response
        if (queryResponse.responses.isNotEmpty()) {
            tvCommResponse.visibility = VISIBLE
            recyclerCommunityResponse.visibility = VISIBLE
            setupCommResponseRecycler(queryResponse.responses)
            ivThankYou.visibility = GONE
            tvThankYou.visibility = GONE
        } else {
            tvCommResponse.visibility = GONE
            recyclerCommunityResponse.visibility = GONE
            ivThankYou.visibility = VISIBLE
            tvThankYou.visibility = VISIBLE
        }

        //setup user feedback
        if (queryResponse.responses.any { Response ->
                TextUtils.equals(Response.type, "summary")
            }) {
            cardFeedback.visibility = VISIBLE
            if (TextUtils.equals(Constants.POSITIVE, queryResponse.user_feedback)) {
                rgFeedback.check(rbYes.id)
            } else if (TextUtils.equals(Constants.NEGATIVE, queryResponse.user_feedback)) {
                rgFeedback.check(rbNo.id)
            }
            rgFeedback.setOnCheckedChangeListener(this)
        } else {
            cardFeedback.visibility = GONE
        }
    }

    private fun setupCommResponseRecycler(responses: ArrayList<Response>) {
        recyclerCommunityResponse.layoutManager = LinearLayoutManager(requireContext())
        recyclerCommunityResponse.adapter =
            CommResponseAdapter(requireContext(), responses, shareSummary)
    }

    private fun showLoading() {
        layoutResponse.visibility = GONE
        layoutError.visibility = GONE
        layoutLoading.visibility = VISIBLE
    }

    private fun showError() {
        layoutResponse.visibility = GONE
        layoutLoading.visibility = GONE
        layoutError.visibility = VISIBLE
        imgError.setImageResource(R.drawable.ic_api_error)
        tvError.setText(R.string.error_occurred)
    }

    private fun showOffline() {
        layoutResponse.visibility = GONE
        layoutLoading.visibility = GONE
        layoutError.visibility = VISIBLE
        imgError.setImageResource(R.drawable.ic_no_network)
        tvError.setText(R.string.offline)
    }

    override fun onCheckedChanged(rg: RadioGroup?, rb: Int) {
        if (rg?.id == R.id.rgFeedback) {
            if (rb == R.id.rbYes) {
                submitFeedback(Feedback(Constants.POSITIVE))
            } else if (rb == R.id.rbNo) {
                submitFeedback(Feedback(Constants.NEGATIVE))
            }
        }
    }

    private fun submitFeedback(feedback: Feedback) {
        viewModel.submitFeedback(queryID, feedback).observe(
            viewLifecycleOwner, Observer { feedbackResponse ->
                when (feedbackResponse.status) {
                    Status.SUCCESS -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.feedback_registered),
                            LENGTH_SHORT
                        ).show()
                    }
                    Status.ERROR -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.error_try_later),
                            LENGTH_SHORT
                        ).show()
                    }
                    Status.NO_NETWORK -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.offline),
                            LENGTH_SHORT
                        ).show()
                    }
                }
            }
        )
    }

    private val shareSummary: (summary: Summary) -> Unit = { summary ->
        val shareIntent = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, summary.heading + "\n" + summary.url)
            putExtra(Intent.EXTRA_TITLE, summary.heading)
            type = "text/plain"
        }, null)
        startActivity(shareIntent)

    }
}