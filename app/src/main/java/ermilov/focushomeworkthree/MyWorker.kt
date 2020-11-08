package ermilov.focushomeworkthree

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters


class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    private var currentTime = 0
    companion object{
        val KEY_TASK_OUTPUT = "key_task_output"
    }

    override fun doWork(): Result {
        val count = inputData.getInt(KEY_TASK_OUTPUT, currentTime)
        currentTime = count+1
        if(currentTime==30){
            displayNotification(currentTime.toString(), KEY_TASK_OUTPUT)
        }

        val outputData = Data.Builder().putInt(KEY_TASK_OUTPUT, currentTime).build()


        return Result.success(outputData)
    }


    private fun displayNotification(task: String, desc: String) {
        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("Hi", "Hi", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(applicationContext, "Hi")
                .setContentTitle(task)
                .setContentText(desc)
                .setSmallIcon(R.drawable.ic_delete)
        manager.notify(1, builder.build())
    }
}