package com.example.smslist.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smslist.R
import com.example.smslist.SMSApplication
import com.example.smslist.model.SMSTableModel
import com.example.smslist.model.SMSText
import com.example.smslist.model.SMSCategory
import com.example.smslist.viewmodel.SMSViewModel
import com.example.smslist.viewmodel.SMSViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private lateinit var calendar: Calendar
    private val wordViewModel: SMSViewModel by viewModels {
        SMSViewModelFactory((application as SMSApplication).repository)
    }

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            calendar = Calendar.getInstance()
            wordViewModel.updateLatestSMS(isSetFalse = false, isTrue = true)

            val jsonObject = JSONObject(intent?.getStringExtra("message").toString())
            val sender = jsonObject.getInt("Sender")
            val smsText = jsonObject.getString("Text")

            println(calendar.timeInMillis)
            println(System.currentTimeMillis())

            val smsTableModel = SMSTableModel(sender, smsText, calendar.timeInMillis, true)

            wordViewModel.insert(smsTableModel)

        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        wordViewModel.allSMS.observe(this) { words ->
            // Update the cached copy of the words in the adapter.
            words.let {

                val listChildHashMap = HashMap<String, MutableList<SMSText>?>()
                val latestSMSHashMap = HashMap<String, Boolean>()
                val smsTextList0 = mutableListOf<SMSText>()
                val smsTextList1 = mutableListOf<SMSText>()
                val smsTextList2 = mutableListOf<SMSText>()
                val smsTextList3 = mutableListOf<SMSText>()
                val smsTextList6 = mutableListOf<SMSText>()
                val smsTextList12 = mutableListOf<SMSText>()
                val smsTextList24 = mutableListOf<SMSText>()
                for (item in words) {
                    val millis = calendar.timeInMillis - item.smsTime

                    val hms = java.lang.String.format(
                        "%02d.%02d", TimeUnit.MILLISECONDS.toHours(millis),
                        abs(
                            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millis)
                            )
                        ),
                        abs(
                            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millis)
                            )
                        )
                    )
                    lateinit var header: String
                    when (hms.toDouble()) {
                        in 0.0..1.0 -> {
                            header = "0 hours"
                            smsTextList0.add(SMSText(item.smsText, item.latestSMS))
                            listChildHashMap[header] = smsTextList0
                            latestSMSHashMap[header] = item.latestSMS


                        }
                        in 1.0..2.0 -> {
                            header = "1 hours"
                            smsTextList1.add(SMSText(item.smsText, item.latestSMS))
                            listChildHashMap[header] = smsTextList1
                            latestSMSHashMap[header] = item.latestSMS

                        }
                        in 2.0..3.0 -> {
                            header = "2 hours"
                            smsTextList2.add(SMSText(item.smsText, item.latestSMS))
                            listChildHashMap[header] = smsTextList2
                            latestSMSHashMap[header] = item.latestSMS

                        }
                        in 3.0..4.0 -> {
                            header = "3 hours"
                            smsTextList3.add(SMSText(item.smsText, item.latestSMS))
                            listChildHashMap[header] = smsTextList3
                            latestSMSHashMap[header] = item.latestSMS

                        }
                        in 4.0..6.0 -> {
                            header = "6 hours"
                            smsTextList6.add(SMSText(item.smsText, item.latestSMS))
                            listChildHashMap[header] = smsTextList6
                            latestSMSHashMap[header] = item.latestSMS

                        }
                        in 6.0..12.0 -> {
                            header = "12 hours"
                            smsTextList12.add(SMSText(item.smsText, item.latestSMS))
                            listChildHashMap[header] = smsTextList12
                            latestSMSHashMap[header] = item.latestSMS

                        }
                        in 12.0..24.0 -> {
                            header = "24 hours"
                            smsTextList24.add(SMSText(item.smsText, item.latestSMS))
                            listChildHashMap[header] = smsTextList24
                            latestSMSHashMap[header] = item.latestSMS

                        }
                    }
                }
                val smsTextWithHours = ArrayList<SMSCategory>()
                val sorted = listChildHashMap.toSortedMap().keys

                for (key in sorted) {
                    val smsCategory =
                        SMSCategory(key, listChildHashMap[key]!!, latestSMSHashMap[key]!!)
                    smsTextWithHours.add(smsCategory)
                }

                //  listChild
                if (smsTextWithHours.size != 0) {
                    rvConinent.visibility = View.VISIBLE
                    alertTextView.visibility = View.GONE

                    val adapter = SMSAdapter(smsTextWithHours)
                    rvConinent.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        rvConinent.adapter = adapter
                    }
                } else {
                    rvConinent.visibility = View.GONE
                    alertTextView.visibility = View.VISIBLE
                }
            }
        }

    }
}
