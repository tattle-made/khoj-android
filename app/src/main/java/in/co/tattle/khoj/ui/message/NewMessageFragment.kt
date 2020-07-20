package `in`.co.tattle.khoj.ui.message

import `in`.co.tattle.khoj.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class NewMessageFragment : Fragment() {

    companion object {
        fun newInstance() = NewMessageFragment()
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
    }

}