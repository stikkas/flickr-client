package com.example.photogallery

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.flow.first

private const val TAG = "PollWorker"

class PollWorker(
    private val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        val repository = PreferencesRepository.get()
        val photoRep = PhotoRepository()
        val query = repository.storedQuery.first()
        val lastId = repository.lastResultId.first()
        if (query.isEmpty()) {
            Log.i(TAG, "No save query, finishing early.")
            return Result.success()
        }
        return try {
            val items = photoRep.searchPhotos(query)
            if (items.isNotEmpty()) {
                val newResultId = items.first().id
                if (newResultId == lastId) {
                    Log.i(TAG, "Still have the same result: $newResultId")
                } else {
                    Log.i(TAG, "Got a new result: $newResultId")
                    repository.setLastResultId(newResultId)
                    notifyUser()
                }
            }
            Result.success()
        } catch (ex: Exception) {
            Log.e(TAG, "Background update failed", ex)
            Result.failure()
        }
    }

    private fun notifyUser() {
        val intent = MainActivity.newIntent(context)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val resource = context.resources
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setTicker(resource.getString(R.string.new_pictures_title))
            .setSmallIcon(android.R.drawable.ic_menu_report_image)
            .setContentTitle(resource.getString(R.string.new_pictures_title))
            .setContentText(resource.getString(R.string.new_pictures_text))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        NotificationManagerCompat.from(context).notify(0, notification)
    }
}