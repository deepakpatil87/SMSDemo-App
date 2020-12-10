package com.example.smslist.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "sms_table")
data class SMSTableModel (

    @ColumnInfo(name = "sender_name")
    var senderName: Int,

    @ColumnInfo(name = "sms_text")
    var smsText: String,

    @ColumnInfo(name = "sms_time")
    var smsTime: Long,

    @ColumnInfo(name = "latest_sms")
    var latestSMS: Boolean

): Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null

}