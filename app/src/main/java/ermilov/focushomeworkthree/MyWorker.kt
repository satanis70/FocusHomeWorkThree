package ermilov.focushomeworkthree

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    companion object{
        val KEY_TASK_OUTPUT = "key_task_output"
    }

    override fun doWork(): Result {
        var count = inputData.getInt(KEY_TASK_OUTPUT, 0)
        count+=1

        val outputData = Data.Builder().putInt(KEY_TASK_OUTPUT, count).build()
        /*for (i in 0..5) {
            Thread.sleep(500)
            count = i
            output = Data.Builder().putInt(KEY_TASK_OUTPUT, count).build()
        }*/
        return Result.success(outputData)
    }
}