package com.example.notificationsappbonus

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val channelId = "myapp.notifications"
    private val description = "Notification App Example"
    private lateinit var notificationManager: NotificationManager
    private lateinit var builder: Notification.Builder
    private val notificationId =123
    private lateinit var notifyButton: Button
    private lateinit var mainLayout: ConstraintLayout

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notifyButton= findViewById(R.id.button)
        mainLayout= findViewById(R.id.mainLay)

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(this, Food::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channelId,description,
                NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
            Notification.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_fastfood_24)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.ic_fastfood_24))
                .setContentIntent(pendingIntent)
                .setContentTitle("Food Notification")
                .setContentText("Food is Ready")
        } else {
            Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_fastfood_24)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.ic_fastfood_24))
                .setContentIntent(pendingIntent)
                .setContentTitle("Food Notification")
                .setContentText("Food is Ready")
        }

        val time = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Snackbar.make(mainLayout,"Time remaining: ${millisUntilFinished / 1000}",Snackbar.LENGTH_SHORT).show()
            }
            override fun onFinish() {
                notificationManager.notify(notificationId, builder.build())
            }
        }

        notifyButton.setOnClickListener{
            time.start()
        }

    }
}