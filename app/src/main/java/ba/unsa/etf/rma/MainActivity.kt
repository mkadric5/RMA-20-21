package ba.unsa.etf.rma

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnApply = findViewById<Button>(R.id.apply)

//        btnApply.setOnClickListener {
//            showMessage()
//        }
    }

//    private fun showMessage() {
//        val message = findViewById<EditText>(R.id.unos).text.toString()
//        findViewById<TextView>(R.id.ispis).text=message
//    }
}

