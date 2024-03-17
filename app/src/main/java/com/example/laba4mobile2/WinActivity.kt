package com.example.laba4mobile2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WinActivity : AppCompatActivity() {
    private lateinit var whoWinText : TextView
    @SuppressLint("StringFormatMatches")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win)

        val arguments = intent.extras
        whoWinText = findViewById(R.id.textView6)
        if (arguments != null) {
            whoWinText.text = getString(R.string.who_win_text, arguments.getInt("winPlayer"))
        }
    }
    fun exit(v : View){
        finishAffinity()
    }
    fun again(v: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}