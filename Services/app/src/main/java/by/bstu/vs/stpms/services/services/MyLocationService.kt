package by.bstu.vs.stpms.services.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import by.bstu.vs.stpms.services.App.Companion.CHANNEL_ID
import by.bstu.vs.stpms.services.MainActivity
import by.bstu.vs.stpms.services.R
import java.util.*


class MyLocationService: Service() {
    private val TAG = "TEST_LOCATION"
    private val LOCATION_INTERVAL = 1000
    private val LOCATION_DISTANCE = 10f

    private lateinit var mLocationManager: LocationManager
    private lateinit var previousLocation: Location
    private lateinit var notificationIntent: Intent
    private var firstStart = false
    private var count = 0
    private var averageSpeed = 0.0


    private var mLocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            val speed = if (firstStart) {
                firstStart = false
                0f
            } else {
                previousLocation.let { lastLocation ->
                    val elapsedTimeInSeconds = (location.time - lastLocation.time) / 1_000
                    val distanceInMeters = lastLocation.distanceTo(location)
                    distanceInMeters * 3.6f / elapsedTimeInSeconds
                }
            }
            previousLocation = location
            averageSpeed = (averageSpeed * count + speed) / ++count

            fun Double.format(digits: Int) = "%.${digits}f".format(this)
            updateNotification("Average speed: ${averageSpeed.format(2)} km/h")

            Log.d(TAG, "avg speed: $averageSpeed")
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            Log.d(TAG, "Status Changed")
        }
    }


    override fun onBind(arg0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        Log.d(TAG, "onCreate")
        initializeLocationManager()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        notificationIntent = Intent(this, MainActivity::class.java)
        startForeground(1, getNotification("text"))

        Timer().apply {
            schedule(object : TimerTask() {
                override fun run() {
                    stopSelf()
                }
            }, 5000)
        }
        
        return START_STICKY
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
        mLocationManager.removeUpdates(mLocationListener)
    }

    @SuppressLint("MissingPermission")
    private fun initializeLocationManager() {
        Log.d(TAG, "initializeLocationManager")
        firstStart = true
        mLocationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_COARSE
        criteria.isAltitudeRequired = false
        criteria.isBearingRequired = false
        criteria.isCostAllowed = true
        criteria.powerRequirement = Criteria.POWER_LOW

        val provider = mLocationManager.getBestProvider(criteria, true)

        Log.d(TAG, "best Provider $provider")

        provider?.let { mLocationManager.requestLocationUpdates(
            provider,
            1000,
            0f,
            mLocationListener
        ) }

    }

    private fun getNotification(text: String): Notification {
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        return Notification.Builder(this, CHANNEL_ID)
            .setContentTitle(text)
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setContentIntent(pendingIntent)
            .build()
    }

    fun updateNotification(text: String) {
        val notification: Notification = getNotification(text)

        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(1, notification)
    }

}