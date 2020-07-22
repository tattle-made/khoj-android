package `in`.co.tattle.khoj.ui.message.response

import `in`.co.tattle.khoj.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_message_response.*

class MessageResponseFragment : Fragment() {

    companion object {
        fun newInstance() =
            MessageResponseFragment()
    }

    private lateinit var viewModel: MessageResponseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_message_response, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MessageResponseViewModel::class.java)
        Glide.with(this)
            .load("https://cdn.pixabay.com/photo/2014/02/09/05/19/woman-262498_1280.jpg")
            .into(ivMessage)

        tvMessage.text =
            "A photograph of a man with Uttar Pardesh Chief Minister Yogi Adityanath was shared by Jan Adhikar Party leader Rajesh Ranjan, popularly known as Pappu Yadav. He claimed that the man is gangster Vikas Dubey whose attempted arrest in Kanpur led to the death of eight policemen."
    }

}