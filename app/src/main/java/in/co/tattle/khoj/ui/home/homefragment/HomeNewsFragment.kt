package `in`.co.tattle.khoj.ui.home.homefragment

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.model.homenews.Homepage
import `in`.co.tattle.khoj.ui.adapters.HomeNewsAdapter
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home_news.*

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
        return inflater.inflate(R.layout.fragment_home_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeNewsViewModel::class.java)

        imgRefresh.setOnClickListener(this)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        recyclerHome.layoutManager = LinearLayoutManager(context)
        homeNewsAdapter = HomeNewsAdapter(requireContext())
        recyclerHome.adapter = homeNewsAdapter
    }

    private fun setupObservers() {
        /*viewModel.getHomepage().observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> {
                    showLoading()
                }
                Status.SUCCESS -> {
                    result.data?.let { homepageData ->
                        showHome()
                        homeNewsAdapter.updateData(homepageData[0])
                    }
                }
                Status.ERROR -> {
                    showError()
                }
                Status.NO_NETWORK -> {
                    showOffline()
                }
            }
        })*/
        showLoading()
        val handler: Handler = Handler()
        handler.postDelayed(Runnable {
            showHome()
            val homepage = Gson().fromJson(temp, Homepage::class.java)
            homeNewsAdapter.updateData(homepage[0])
        }, 1500)
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


    val temp: String = "[\n" +
            "    {\n" +
            "        \"id\": 1,\n" +
            "        \"publication_date\": \"2020-07-06\",\n" +
            "        \"Greeting\": \"Namaste\",\n" +
            "        \"cover_image\": \"https://cdn.pixabay.com/photo/2016/05/06/09/04/leaf-1375470_1280.jpg\",\n" +
            "        \"created_at\": \"2020-07-05T04:12:57.247Z\",\n" +
            "        \"updated_at\": \"2020-07-05T04:12:57.247Z\",\n" +
            "        \"article_trending\": [\n" +
            "            {\n" +
            "                \"id\": 1,\n" +
            "                \"url\": \"https://www.boomlive.in/fake-news/was-modis-visit-to-leh-hospital-staged-army-rubbishes-claims-8749\",\n" +
            "                \"heading\": \"Was Modi's Visit To Leh Hospital Staged? Army Rubbishes Claims\",\n" +
            "                \"byline\": \"The Indian Army in a statement said that PM Modi visited patients being treated in an training hall converted to a ward\\n\\nhttps://www.boomlive.in/fake-news/was-modis-visit-to-leh-hospital-staged-army-rubbishes-claims-8749\",\n" +
            "                \"thumbnail\": \"https://cdn.pixabay.com/photo/2016/05/06/09/04/leaf-1375470_1280.jpg\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 2,\n" +
            "                \"url\": \"https://www.altnews.in/no-bjp-leader-vikas-dubey-is-not-the-same-vikas-dubey-accused-of-killing-8-up-cops/\",\n" +
            "                \"heading\": \"No, BJP leader Vikas Dubey is not the same ‘Vikas Dubey’ accused of killing 8 UP cops\",\n" +
            "                \"byline\": \"A photograph of a man with Uttar Pardesh Chief Minister Yogi Adityanath was shared by Jan Adhikar Party leader Rajesh Ranjan, popularly known as Pappu Yadav. He claimed that the man is gangster Vikas Dubey whose attempted arrest in Kanpur led to the death of eight policemen.\",\n" +
            "                \"thumbnail\": \"https://cdn.pixabay.com/photo/2014/02/09/05/19/woman-262498_1280.jpg\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 3,\n" +
            "                \"url\": \"https://www.vishvasnews.com/viral/fact-check-message-attributed-to-delhi-police-commissioner-is-not-true/\",\n" +
            "                \"heading\": \"Fact Check: दिल्ली पुलिस आयुक्त ने नहीं जारी किया यह संदेश\",\n" +
            "                \"byline\": \"सोशल मीडिया पर आज कल एक संदेश वायरल हो रही है, जिसे दिल्ली पुलिस आयुक्त के नाम से जारी किया गया बताया जा रहा है। संदेश में लोगों से अपनी तस्वीर को वॉट्सऐप पर अपनी डीपी के रूप में अपलोड न करने का अनुरोध किया है। \",\n" +
            "                \"thumbnail\": \"https://cdn.pixabay.com/photo/2017/05/15/10/08/theyyam-2314340_1280.jpg\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"article_share\": {\n" +
            "            \"id\": 1,\n" +
            "            \"heading\": \"Kejriwal says less people in Delhi need hospitalisation now, more beds are free\",\n" +
            "            \"byline\": \"Delhi Chief Minister Arvind Kejriwal on Sunday said more coronavirus patients are getting better at home and claimed that fewer need hospitalisation.\\n\\nThe number of coronavirus infections in India rose to 6,48,315 on Saturday with 22,771 new cases. This is the highest single-day rise in infections so far. The toll rose to 18,655 with 442 new fatalities.\",\n" +
            "            \"image_thumbnail\": \"https://cdn.pixabay.com/photo/2020/05/20/17/46/flower-5197539_1280.jpg\"\n" +
            "        }\n" +
            "    }\n" +
            "]"
}