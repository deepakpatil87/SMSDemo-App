package com.example.smslist.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.smslist.model.SMSTableModel
import kotlinx.coroutines.CoroutineScope

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */
@Database(entities = [SMSTableModel::class], version = 2)
abstract class SMSRoomDatabase : RoomDatabase() {

    abstract fun smsDao(): SMSDao

    companion object {
        @Volatile
        private var INSTANCE: SMSRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): SMSRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SMSRoomDatabase::class.java,
                    "sms_database.db"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()

                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }


//        /**
//         * Populate the database in a new coroutine.
//         * If you want to start with more words, just add them.
//         */
//        suspend fun populateDatabase(wordDao: WordDao) {
//            // Start the app with a clean database every time.
//            // Not needed if you only populate on creation.
//            wordDao.deleteAll()
//
//            var word = Word("Hello")
//            wordDao.insert(word)
//            word = Word("World!")
//            wordDao.insert(word)
//        }
//    }
//    /*companion object {
//        @Volatile
//        private var INSTANCE: SMSRoomDatabase? = null
//
//        fun getDatabase(
//            context: Context
//        ): SMSRoomDatabase {if (INSTANCE != null) return INSTANCE!!
//
//            synchronized(this) {
//
//                INSTANCE = Room
//                    .databaseBuilder(context, SMSRoomDatabase::class.java, "LOGIN_DATABASE")
//                    .fallbackToDestructiveMigration()
//                    .build()
//
//                return INSTANCE!!
//
//            }
//        }
//
//
//    }*/
    }
}
