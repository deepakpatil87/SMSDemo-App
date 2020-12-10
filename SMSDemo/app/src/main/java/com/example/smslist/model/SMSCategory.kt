package com.example.smslist.model


import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup


data class SMSCategory(
    val hours: String, val smsText: MutableList<SMSText>,val latestSMS:Boolean
):  ExpandableGroup<SMSText>(hours, smsText) {

}