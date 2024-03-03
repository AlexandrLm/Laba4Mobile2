package com.example.laba4mobile2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var settings: Settings = Settings(2,1,1)
    private lateinit var buttons : Array<Button>
    private lateinit var skipsCount : EditText
    private lateinit var rollsCount : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttons = arrayOf(findViewById(R.id.button2), findViewById(R.id.button3), findViewById(R.id.button4),
                          findViewById(R.id.button5), findViewById(R.id.button6), findViewById(R.id.button7))
        buttons[0].isActivated = true
        skipsCount = findViewById(R.id.skipsCountText)
        rollsCount = findViewById(R.id.rollsCountText)
    }

    @SuppressLint("ResourceAsColor")
    fun playersCountButtonPress(v : View){
        when (v.id) {
            R.id.button2 -> settings.numberOfPlayers = 2
            R.id.button3 -> settings.numberOfPlayers = 3
            R.id.button4 -> settings.numberOfPlayers = 4
            R.id.button5 -> settings.numberOfPlayers = 5
            R.id.button6 -> settings.numberOfPlayers = 6
            R.id.button7 -> settings.numberOfPlayers = 7
        }
        for (n in buttons){
            if (n.id == v.id)
                n.isActivated = true
            else
                n.isActivated = false
        }
    }
    fun startButtonPress(v : View){
        if( skipsCount.text.toString() == "" || rollsCount.text.toString() == "" ) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }
        else {
            settings.numberOfSkips = skipsCount.text.toString().toInt()
            settings.numberOfSpins = rollsCount.text.toString().toInt()

            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("settings", settings)
            startActivity(intent)
        }
    }
}



















