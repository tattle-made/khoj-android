package `in`.co.tattle.khoj

import `in`.co.tattle.khoj.utils.Constants
import android.graphics.Typeface.BOLD
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_static_content.*


class StaticContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_static_content)

        setSupportActionBar(toolbarStatic)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        tvStatic.text = intent.getStringExtra(Constants.CONTENT_TYPE)
        val pageType = intent.getStringExtra(Constants.CONTENT_TYPE)
        if(pageType.equals(Constants.ABOUT_US)){
            val text: Spannable = SpannableStringBuilder()
                .append("About us \n", StyleSpan(BOLD), Spannable.SPAN_INTERMEDIATE)
                .append("Khoj is an app built by Tattle Civic Technologies \n\n")
                .append("Tattle is a group of technologists, researchers, journalists and artists. We build tools and datasets to better understand and respond to (mis)information trends on chat apps and regional language social media in India. \n")
                .append("Find more about us on ")
                .append("tattle.co.in")
            tvStatic.setText(text)

        }else if(pageType.equals(Constants.PRIVACY_POLICY)){
            val text: Spannable = SpannableStringBuilder()
                .append("About Khoj:\n", StyleSpan(BOLD), Spannable.SPAN_INTERMEDIATE)
                .append("Khoj is an app you can use to verify messages you receive on your family whatsapp groups\n\n")
                .append(
                    "What Khoj Collects About You?\n",
                    StyleSpan(BOLD),
                    Spannable.SPAN_INTERMEDIATE
                )
                .append("Besides the content you share with Tattle, the app does not store any information about you or your behaviour on the app.\n\n")
                .append(
                    "Why does Khoj Needs this Information?\n",
                    StyleSpan(BOLD),
                    Spannable.SPAN_INTERMEDIATE
                )
                .append("We need any supplementary information associated with the messages you recieve to effectively verify them. This helps us run search queries to find other media like the one you submitted. It also helps us answer the following questions.\n")
                .append("1. How popular certain post is on messaging apps?\n")
                .append("2. Are certain themes of misinformation more popular than others?\n")
                .append("3. What kind of misinformation needs to be debunked on family whatsapp groups\n")
                .append("This crowdsourced data will be open-access and can be used by researchers studying spread of information and misinformation on encrypted platforms such as WhatsApp. The data will also be valuable for online information verification groups. \n\n")
                .append("Control over Information:\n", StyleSpan(BOLD), Spannable.SPAN_INTERMEDIATE)
                .append("Khoj does not collect any personal information on the user. Any user can request for access to the data being collected by the app through the contact email id.\n\n")
                .append("Reach us:\n", StyleSpan(BOLD), Spannable.SPAN_INTERMEDIATE)
                .append("You can reach out with further questions at admin@tattle.co.in\n")

            tvStatic.setText(text)
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }
}