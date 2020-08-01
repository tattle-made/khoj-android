package `in`.co.tattle.khoj.ui.home.historyfragment

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.ui.adapters.HistoryAdapter
import `in`.co.tattle.khoj.ui.message.response.MessageResponseActivity
import `in`.co.tattle.khoj.utils.Constants
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment() {

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

        setupRecycler()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.userTimeline.observe(viewLifecycleOwner, Observer { userTimeLine ->
            timelineAdapter.updateData(userTimeLine)
        })
    }

    private fun setupRecycler() {
        recyclerTimeline.layoutManager = LinearLayoutManager(context)
        timelineAdapter = HistoryAdapter(onTimelineClick)
        recyclerTimeline.adapter = timelineAdapter
    }

    private val onTimelineClick: () -> Unit = {
        val intent = Intent(context, MessageResponseActivity::class.java)
        intent.putExtra(Constants.MESSAGE_ID, "5f2014b62a50cc43b3e60ae2")
        startActivity(intent)
    }

}