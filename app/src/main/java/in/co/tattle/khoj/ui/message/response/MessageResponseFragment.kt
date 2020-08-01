package `in`.co.tattle.khoj.ui.message.response

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.model.queryresponse.QueryResponse
import `in`.co.tattle.khoj.model.queryresponse.Response
import `in`.co.tattle.khoj.ui.adapters.CommResponseAdapter
import `in`.co.tattle.khoj.ui.adapters.MediaPagerAdapter
import `in`.co.tattle.khoj.utils.Constants
import `in`.co.tattle.khoj.utils.Status
import `in`.co.tattle.khoj.utils.ViewPagerUtils
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
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
            .observe(viewLifecycleOwner, Observer { queryResponse ->
                when (queryResponse.status) {
                    Status.LOADING -> {
                        showLoading()
                    }
                    Status.SUCCESS -> {
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
        if (queryResponse.media.isNotEmpty()) {
            tvCommResponse.visibility = VISIBLE
            recyclerCommunityResponse.visibility = VISIBLE
            setupCommResponseRecycler(queryResponse.responses)
        } else {
            tvCommResponse.visibility = GONE
            recyclerCommunityResponse.visibility = GONE
        }

        //setup user feedback
    }

    private fun setupCommResponseRecycler(media: ArrayList<Response>) {
        recyclerCommunityResponse.layoutManager = LinearLayoutManager(requireContext())
        recyclerCommunityResponse.adapter = CommResponseAdapter(requireContext(), media)
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
}