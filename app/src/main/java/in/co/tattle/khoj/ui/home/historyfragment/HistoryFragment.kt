package `in`.co.tattle.khoj.ui.home.historyfragment

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.ui.adapters.TimelineAdapter
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

    private lateinit var timelineAdapter: TimelineAdapter
    private lateinit var viewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
        timelineAdapter = TimelineAdapter()
        recyclerTimeline.adapter = timelineAdapter
    }

}