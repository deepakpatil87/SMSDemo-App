package com.example.smslist

import android.app.Application
import com.example.smslist.repository.SMSRepository
import com.example.smslist.room.SMSRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class SMSApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    var applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { SMSRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { SMSRepository(database.smsDao()) }
}
