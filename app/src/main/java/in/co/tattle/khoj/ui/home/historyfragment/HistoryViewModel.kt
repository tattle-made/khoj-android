package `in`.co.tattle.khoj.ui.home.historyfragment

import `in`.co.tattle.khoj.model.timeline.UserTimeline
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HistoryViewModel : ViewModel() {

    val userTimeline: MutableLiveData<ArrayList<UserTimeline>> by lazy {
        MutableLiveData<ArrayList<UserTimeline>>()
    }

    init {
        val temp: ArrayList<UserTimeline> = arrayListOf()
        var tempUserTimeline: UserTimeline = UserTimeline(
            "18 July 2020",
            "Fact Check: 'Story' Of Teacher Bullied During Online Class Shared As News"
        )
        temp.add(tempUserTimeline)

        tempUserTimeline = UserTimeline(
            "18 July 2020",
            "Viral Post Makes Numerous False Claims About Effects Of Face Masks"
        )
        temp.add(tempUserTimeline)
        tempUserTimeline = UserTimeline(
            "14 July 2020",
            "Prathap NM, hailed as 'drone scientist', passes off German and Japanese drones as his own"
        )
        temp.add(tempUserTimeline)
        tempUserTimeline = UserTimeline(
            "14 July 2020",
            "Prathap NM, hailed as 'drone scientist', passes off German and Japanese drones as his own"
        )
        temp.add(tempUserTimeline)
        tempUserTimeline = UserTimeline(
            "15 July 2020",
            "Prathap NM, hailed as 'drone scientist', passes off German and Japanese drones as his own"
        )
        temp.add(tempUserTimeline)
        tempUserTimeline = UserTimeline(
            "16 July 2020",
            "Prathap NM, hailed as 'drone scientist', passes off German and Japanese drones as his own"
        )
        temp.add(tempUserTimeline)
        tempUserTimeline = UserTimeline(
            "16 July 2020",
            "Fact Check: Murder caught on cam goes viral with communal spin"
        )
        temp.add(tempUserTimeline)
        tempUserTimeline = UserTimeline(
            "16 June 2020",
            "Amendments made to the Code of Criminal Procedure Act in 2008 & 2010 are intact"
        )
        temp.add(tempUserTimeline)

        userTimeline.value = temp
    }
}