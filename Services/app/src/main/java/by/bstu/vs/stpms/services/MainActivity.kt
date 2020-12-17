package by.bstu.vs.stpms.services

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import by.bstu.vs.stpms.services.services.MyDownloadService
import by.bstu.vs.stpms.services.services.MyLocationService
import kotlinx.android.synthetic.main.activity_main.*

private const val REQUEST_CODE: Int = 101

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestLocationWithPermissionCheck()
        download_button.setOnClickListener {
            val downloadIntent = Intent(this, MyDownloadService::class.java)
            downloadIntent.putExtra("image_uri", download_edit_text.text.toString())
            startService(downloadIntent)
        }


    }

    private fun requestLocationWithPermissionCheck() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(
                                this,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED)) {

            requestPermissions(
                    arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.INTERNET,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    REQUEST_CODE
            )
        } else {
            requestLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        button_start.setOnClickListener {
            val service = Intent(this, MyLocationService::class.java)
            ContextCompat.startForegroundService(this, service)
        }
        button_stop.setOnClickListener {
            val service = Intent(this, MyLocationService::class.java)
            stopService(service)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when (requestCode) {
            REQUEST_CODE ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLocation()
                }
        }
    }
}