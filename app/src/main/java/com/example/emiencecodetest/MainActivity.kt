package com.example.emiencecodetest

import android.os.Bundle
import android.text.format.DateUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.emiencecodetest.base.NetworkResult
import com.example.emiencecodetest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var viewModel: MatchViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        this?.run {
            viewModel = ViewModelProvider(this)[MatchViewModel::class.java]
        } ?: throw Throwable("invalid activity")

        getMatchDetails()
        viewModel.vehicleCallApi()
    }

    private fun getMatchDetails() {


        viewModel.response.observe(
            this,
            androidx.lifecycle.Observer { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        response.data?.let {

                            var cricketList = arrayListOf<Data>()
                            var tennisList = arrayListOf<Data>()
                            var soccerList = arrayListOf<Data>()

                            for (item in response.data.data) {
                                when (item.sportId) {
                                    "4" -> cricketList.add(item)
                                    "2" -> tennisList.add((item))
                                    "1" -> soccerList.add(item)
                                }
                            }

                            getCricketList(cricketList)
                            getTennisList(tennisList)
                            getSoccerList(soccerList)

                            Toast.makeText(
                                this,
                                response.message,
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                    }

                    is NetworkResult.Error -> {
                        Toast.makeText(
                            this,
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {}
                }
            })
    }

    fun isTomorrow(d: Date): Boolean {
        return DateUtils.isToday(d.time - DateUtils.DAY_IN_MILLIS)
    }

    fun getCricketList(cricketList:ArrayList<Data>){
        var onplaycricketlist = arrayListOf<Data>()
        var todayPlaylist = arrayListOf<Data>()
        var tommorrowplaylist = arrayListOf<Data>()

        for (item in cricketList) {

            if (isTomorrow(convertStringIntoDate(item.openDate))) {
                tommorrowplaylist.add(item)
            } else if (convertStringIntoDate(item.openDate).compareTo(getCurrentDate()) > 0) {
                todayPlaylist.add(item)
            }else {
                onplaycricketlist.add(item)
            }
        }
    }

    fun getTennisList(tennisList:ArrayList<Data>){
        var onplaycricketlist = arrayListOf<Data>()
        var todayplaylist = arrayListOf<Data>()
        var tommorrowplaylist = arrayListOf<Data>()


        for (item in tennisList) {


            if (isTomorrow(convertStringIntoDate(item.openDate))) {
                tommorrowplaylist.add(item)
            } else if (convertStringIntoDate(item.openDate).compareTo(getCurrentDate()) > 0) {
                todayplaylist.add(item)
            }else {
                onplaycricketlist.add(item)
            }
        }
    }

    fun getSoccerList(soccerlist:ArrayList<Data>){
        var onplaycricketlist = arrayListOf<Data>()
        var todayplaylist = arrayListOf<Data>()
        var tommorrowplaylist = arrayListOf<Data>()


        for (item in soccerlist) {

                if (isTomorrow(convertStringIntoDate(item.openDate))) {
                    tommorrowplaylist.add(item)
                } else if (convertStringIntoDate(item.openDate).compareTo(getCurrentDate()) > 0) {
                    todayplaylist.add(item)
                }else {
                    onplaycricketlist.add(item)
                }

        }
    }

    fun getCurrentDate():Date{
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss aaa")
        val current = formatter.format(time)
        val currentDatetime = formatter.parse(current)
        return currentDatetime
    }

    fun convertStringIntoDate(datevalue:String):Date {
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss aaa")

            val date: Date = format.parse(datevalue)
        return date
    }
}

