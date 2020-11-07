package ermilov.focushomeworkthree

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    companion object{
        val KEY_TASK_OUTPUT = "key_task_output"
        var currentTime = 0
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            setWorkRequest()
        }

        for (i in currentTime..5){
            setWorkRequest()
        }
    }

    fun setWorkRequest(){
        val data: Data = Data.Builder().putInt(KEY_TASK_OUTPUT, currentTime).build()
        val workManager = WorkManager.getInstance(applicationContext)
        val uploadRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
                    .setInputData(data)
                    .build()
            workManager.enqueue(uploadRequest)
            workManager.getWorkInfoByIdLiveData(uploadRequest.id)
                    .observe(this, Observer {
                            val dataWork = it.outputData
                            val time = dataWork.getInt(MyWorker.KEY_TASK_OUTPUT, currentTime)
                            currentTime = time
                            textView.text = currentTime.toString()
                    })
    }
}