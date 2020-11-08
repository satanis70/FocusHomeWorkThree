package ermilov.focushomeworkthree

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    var currentTime = 0
    var currentTimeMinutes = 0
    companion object{
        val KEY_TASK_OUTPUT = "key_task_output"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainHandler = Handler(Looper.getMainLooper())
            button.setOnClickListener {
                mainHandler.post(object : Runnable {
                    override fun run() {
                        setWorkRequest()
                        mainHandler.postDelayed(this, 1000)
                    }
                })
        }

            stopButton.setOnClickListener {
                mainHandler.removeMessages(0)
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
                        if (currentTime/60==1){
                            currentTimeMinutes+=1
                            currentTime = 0
                        }
                        textView2.text = currentTimeMinutes.toString()
                        textView.text = currentTime.toString()
                    })
        }
}