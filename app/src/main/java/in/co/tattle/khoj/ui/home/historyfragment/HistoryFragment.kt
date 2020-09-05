package `in`.co.tattle.khoj.ui.home.historyfragment

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.ui.adapters.HistoryAdapter
import `in`.co.tattle.khoj.ui.message.create.NewMessageActivity
import `in`.co.tattle.khoj.ui.message.response.MessageResponseActivity
import `in`.co.tattle.khoj.utils.Constants
import `in`.co.tattle.khoj.utils.Status
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment(), OnClickListener {

    companion object {
        fun newInstance() = HistoryFragment()
    }

    private lateinit var timelineAdapter: HistoryAdapter
    private lateinit var viewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        imgRefresh.setOnClickListener(this)
        btnNewMessage.setOnClickListener(this)
        setupRecycler()
    }

    override fun onResume() {
        super.onResume()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.getQueryHistory().observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> {
                    showLoading()
                }
                Status.SUCCESS -> {
                    if (result.data!!.size == 0) {
                        showError(true)
                    } else {
                        showTimeline()
                        timelineAdapter.updateData(result.data)
                    }
                }
                Status.ERROR -> {
                    showError(false)
                }
                Status.NO_NETWORK -> {
                    showOffline()
                }
            }
        })
    }

    private fun setupRecycler() {
        recyclerTimeline.layoutManager = LinearLayoutManager(context)
        timelineAdapter = HistoryAdapter(onTimelineClick)
        recyclerTimeline.adapter = timelineAdapter
    }

    private
    val onTimelineClick: (queryId: String) -> Unit = { queryId ->
        val intent = Intent(context, MessageResponseActivity::class.java)
        intent.putExtra(Constants.MESSAGE_ID, queryId)
        startActivity(intent)
    }

    private fun showTimeline() {
        layoutError.visibility = GONE
        layoutLoading.visibility = GONE
        layoutTimeline.visibility = VISIBLE
    }

    private fun showLoading() {
        layoutTimeline.visibility = GONE
        layoutError.visibility = GONE
        layoutLoading.visibility = VISIBLE
    }

    private fun showError(noData: Boolean) {
        layoutTimeline.visibility = GONE
        layoutLoading.visibility = GONE
        layoutError.visibility = VISIBLE
        if (noData) {
            imgError.visibility = GONE
            btnNewMessage.visibility = VISIBLE
            imgRefresh.visibility = GONE
            tvError.setText(R.string.no_queries_found)
        } else {
            imgError.setImageResource(R.drawable.ic_api_error)
            imgError.visibility = VISIBLE
            btnNewMessage.visibility = GONE
            imgRefresh.visibility = VISIBLE
            tvError.setText(R.string.error_occurred)
        }
    }

    private fun showOffline() {
        layoutTimeline.visibility = GONE
        layoutLoading.visibility = GONE
        layoutError.visibility = VISIBLE
        imgError.setImageResource(R.drawable.ic_no_network)
        tvError.setText(R.string.offline)
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.imgRefresh) {
            setupObserver()
        } else if (view?.id == R.id.btnNewMessage) {
            startActivity(Intent(requireContext(), NewMessageActivity::class.java))
        }
    }

}