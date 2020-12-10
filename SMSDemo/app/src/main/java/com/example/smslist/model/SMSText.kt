package com.example.smslist.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SMSText(val smsText: String,val latestSMS: Boolean) : Parcelable