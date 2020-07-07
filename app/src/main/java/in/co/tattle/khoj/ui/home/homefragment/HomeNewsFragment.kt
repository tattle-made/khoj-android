package `in`.co.tattle.khoj.ui.home.homefragment

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.ui.adapters.HomeNewsAdapter
import `in`.co.tattle.khoj.utils.Status
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.home_news_fragment.*

class HomeNewsFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = HomeNewsFragment()
    }

    private lateinit var viewModel: HomeNewsViewModel
    private lateinit var homeNewsAdapter: HomeNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_news_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeNewsViewModel::class.java)

        imgRefresh.setOnClickListener(this)

        setupObservers()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerHome.layoutManager = LinearLayoutManager(context)
        recyclerHome.adapter = context?.let { HomeNewsAdapter(it) }
    }

    private fun setupObservers() {
        viewModel.getHomepage().observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> {
                    showLoading()
                }
                Status.SUCCESS -> {
                    result.data?.let { homepageData ->
                        showHome()
                        (recyclerHome.adapter as HomeNewsAdapter).updateData(homepageData[0])
                    }
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

    private fun showHome() {
        layoutError.visibility = GONE
        layoutLoading.visibility = GONE
        layoutHome.visibility = VISIBLE
    }

    private fun showLoading() {
        layoutHome.visibility = GONE
        layoutError.visibility = GONE
        layoutLoading.visibility = VISIBLE
    }

    private fun showError() {
        layoutHome.visibility = GONE
        layoutLoading.visibility = GONE
        layoutError.visibility = VISIBLE
        imgError.setImageResource(R.drawable.ic_api_error)
        tvError.setText(R.string.error_occurred)
    }

    private fun showOffline() {
        layoutHome.visibility = GONE
        layoutLoading.visibility = GONE
        layoutError.visibility = VISIBLE
        imgError.setImageResource(R.drawable.ic_no_network)
        tvError.setText(R.string.offline)
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.imgRefresh) {
            setupObservers()
        }
    }
}