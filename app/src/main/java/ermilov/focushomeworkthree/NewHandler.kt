package ermilov.focushomeworkthree

import android.os.Handler
import android.os.Looper
import android.widget.TextView

class NewHandler(textViewm: TextView): Thread() {
    val textView:TextView = textViewm
    var handler = Handler(Looper.getMainLooper())
    override fun run() {
        super.run()
        var counter = 0
        handler.post{
            textView.text = counter.toString()
        }
        counter+=1
    }
}