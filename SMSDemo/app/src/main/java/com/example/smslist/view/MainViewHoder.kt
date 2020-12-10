package com.example.smslist.view

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.example.smslist.R
import com.example.smslist.model.SMSCategory
import com.example.smslist.model.SMSText
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder


class ChildViewHolder(itemView: View) : ChildViewHolder(itemView) {
    private val smsTextView: TextView = itemView.findViewById(R.id.smsTextView)

    fun bind(msg: SMSText) {
        smsTextView.text = msg.smsText

        if( msg.latestSMS)
        {
            smsTextView.setTextColor(Color.BLUE)
        }else
        {
            smsTextView.setTextColor(Color.BLACK)
        }
    }
}

class GroupViewHolder(itemView: View) : GroupViewHolder(itemView) {
    private var hoursTextView: TextView = itemView.findViewById(R.id.hoursTextView)
    val arrow = itemView.findViewById<ImageView>(R.id.arrow)

    fun bind(continent: SMSCategory) {
        hoursTextView.text = continent.hours
       if( continent.latestSMS)
       {
           hoursTextView.setTextColor(Color.BLUE)
       }else
       {
           hoursTextView.setTextColor(Color.WHITE)
       }
    }

}