package by.bstu.vs.stpms.services

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

private const val REQUEST_CODE: Int = 101

class MainActivity : AppCompatActivity() {

    private lateinit var mLocationManager: LocationManager
    private lateinit var previousLocation: Location
    private var count = 0
    private var averageSpeed = 0.0

    private var mLocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            val speed = if (location.hasSpeed()) {
                location.speed
            } else {
                previousLocation.let { lastLocation ->
                    val elapsedTimeInSeconds = (location.time - lastLocation.time) / 1_000
                    val distanceInMeters = lastLocation.distanceTo(location)
                    distanceInMeters * 3.6f / elapsedTimeInSeconds
                }
            }
            previousLocation = location
            averageSpeed = (averageSpeed * count + speed) / ++count
            text_location.text = averageSpeed.toString()
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            Log.d("LOCATION", " Status Changed ")
        }

//        override fun onProviderEnabled(provider: String?) {
//            Log.d("LOCATION", " Provider Enabled")
//        }
//
//        override fun onProviderDisabled(provider: String?) {
//            var intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//            startActivity(intent)
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

            requestPermissions(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET),
                    REQUEST_CODE)
        } else {
            requestLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        button_location.setOnClickListener {

            val criteria = Criteria()
            criteria.accuracy = Criteria.ACCURACY_COARSE
            criteria.isAltitudeRequired = false
            criteria.isBearingRequired = false
            criteria.isCostAllowed = true
            criteria.powerRequirement = Criteria.POWER_LOW

            val provider = mLocationManager.getBestProvider(criteria, true)

            Log.d("LOCATION", "best Provider $provider")

            provider?.let { mLocationManager.requestLocationUpdates(provider, 1000, 0f, mLocationListener) }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when (requestCode) {
            REQUEST_CODE ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLocation()
                    return
                }
        }
    }
}