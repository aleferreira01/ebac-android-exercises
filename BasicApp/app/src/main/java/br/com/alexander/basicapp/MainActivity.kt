package br.com.alexander.basicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var myActivity: LinearLayout? = null
    var editName: EditText? = null
    var textResult: TextView? = null
    var numberClicks = 0
    var randomNumber = 0
    var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myActivity = findViewById(R.id.view_activity)
        editName = findViewById(R.id.edit_name)
        textResult = findViewById(R.id.text_result)
        val buttonAction = findViewById<Button>(R.id.button_action)

        buttonAction.setOnClickListener {
            numberClicks++
            changeText()
            changeBackground()
        }
    }

    private fun changeBackground() {
        randomNumber = Random.nextInt(0,3)
        when (randomNumber) {
            0 -> myActivity?.background = getDrawable(R.drawable.background_image_1)
            1 -> myActivity?.background = getDrawable(R.drawable.background_image_2)
            else -> myActivity?.background = getDrawable(R.drawable.background_image_3)
        }
    }

    private fun changeText() {
        name = editName?.text.toString()
        textResult?.text = getString(R.string.string_result, name, numberClicks.toString())
    }
}