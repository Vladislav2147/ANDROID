package by.bstu.vs.stpms.services.services

import android.app.DownloadManager
import android.app.IntentService
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import android.widget.Toast
import by.bstu.vs.stpms.services.R
import java.io.File
import java.text.DateFormat
import java.util.*


class MyDownloadService : IntentService("MyDownloadService") {


    override fun onHandleIntent(intent: Intent?) {

        val imageUri = intent?.getCharSequenceExtra("image_uri").toString()
        val imageStorageDir = File(Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES), "myfolder")


        if (!imageStorageDir.exists()) {
            imageStorageDir.mkdirs()
        }


        val file = getFileName(imageUri)


        val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val downloadUri: Uri = Uri.parse(imageUri)
        val request = DownloadManager.Request(downloadUri)

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setDestinationInExternalPublicDir(DIRECTORY_PICTURES + File.separator, file)
                .setTitle(file).setDescription("Save image")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        dm.enqueue(request)

        Toast.makeText(applicationContext, "Downloaded", Toast.LENGTH_LONG).show()

    }

    private fun getFileName(imageUri: String): String {
        var imgExtension = ".jpg"

        when {
            imageUri.contains(".gif") -> imgExtension = ".gif"
            imageUri.contains(".png") -> imgExtension = ".png"
            imageUri.contains(".3gp") -> imgExtension = ".3gp"
        }

        val date: String = DateFormat.getDateTimeInstance().format(Date())
        return getString(R.string.app_name) + "-image-" +
                date
                        .replace(" ", "")
                        .replace(":", "")
                        .replace(".", "") +
                imgExtension
    }
}