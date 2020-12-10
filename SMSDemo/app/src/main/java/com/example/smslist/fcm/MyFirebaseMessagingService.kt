package com.example.smslist.fcm;

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat

import com.example.smslist.R
import com.example.smslist.model.SMSTableModel
import com.example.smslist.room.SMSRoomDatabase
import com.example.smslist.view.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "MyFirebaseMsgService"

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
           // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${remoteMessage?.from}")
        try {
            // val wordViewModel = ViewModelProvider(applicationContext).get(WordViewModel::class.java)
            val mDb = SMSRoomDatabase.getDatabase(this, CoroutineScope(SupervisorJob()))
                    CoroutineScope(Dispatchers.IO).launch {
            mDb.smsDao().updateLatestSMS(false, true)
        }
            val jsonObject = JSONObject(remoteMessage!!.data["message"].toString())
            val sender = jsonObject.getInt("Sender")
            val smsText = jsonObject.getString("Text");
            val calendar = Calendar.getInstance()

            val smsTableModel = SMSTableModel(sender, smsText, calendar.timeInMillis,true);

            // thread {
            CoroutineScope(Dispatchers.IO).launch {
                mDb.smsDao().insert(smsTableModel)
            }

            Log.d(  TAG,"Message data payload: " + remoteMessage.data + "\n" + "$sender, $smsText")

            // Check if message contains a data payload.
            remoteMessage.data?.isNotEmpty()?.let {
                // Compose and show notification
                if (!remoteMessage.data.isNullOrEmpty()) {
                    val msg: String = remoteMessage.data.get("message").toString()

                    sendNotification(jsonObject)
                }

            }

            // Check if message contains a notification payload.
            remoteMessage.notification?.let {
                Log.d("Notification No", it.body);
                //sendNotification(remoteMessage.notification?.body)
                sendNotification(jsonObject)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    private fun sendNotification(messageBody: JSONObject) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("JSONObject", messageBody.toString())
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(messageBody.getString("Sender"))//getString(R.string.fcm_message))
            .setContentText(messageBody.getString("Text"))
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        // https://developer.android.com/training/notify-user/build-notification#Priority
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(token)
    }

}
