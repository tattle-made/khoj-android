package `in`.co.tattle.khoj.ui.home.morefragment

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.utils.Constants
import `in`.co.tattle.khoj.utils.LocalizationUtil
import `in`.co.tattle.khoj.utils.PreferenceUtils
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_more.*

class MoreFragment : Fragment(), RadioGroup.OnCheckedChangeListener {

    companion object {
        fun newInstance() = MoreFragment()
    }

    private lateinit var viewModel: MoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MoreViewModel::class.java)

        setupLanguage()
        rgLanguage.setOnCheckedChangeListener(this)

    }

    private fun setupLanguage() {
        val language =
            PreferenceUtils.getPrefString(requireContext(), PreferenceUtils.SELECTED_LANGUAGE)
        if (TextUtils.equals(language, Constants.HINDI)) {
            rgLanguage.check(rbHindi.id)
        } else {
            rgLanguage.check(rbEnglish.id)
        }
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        if (p1 == rbEnglish.id) {
            PreferenceUtils.setPrefString(
                requireContext(),
                PreferenceUtils.SELECTED_LANGUAGE,
                Constants.ENGLISH
            )
        } else {
            PreferenceUtils.setPrefString(
                requireContext(),
                PreferenceUtils.SELECTED_LANGUAGE,
                Constants.HINDI
            )
        }
        LocalizationUtil.applyLanguage(requireContext())
    }
}