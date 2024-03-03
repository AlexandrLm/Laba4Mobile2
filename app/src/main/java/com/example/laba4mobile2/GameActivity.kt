package com.example.laba4mobile2

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import kotlin.math.log

class GameActivity : AppCompatActivity() {
    private lateinit var listView : ListView
    private lateinit var gameLogs : ListView
    private lateinit var whoPlay : TextView
    private var logsArray: MutableList<String> = mutableListOf()
    private var players: MutableList<Player> = mutableListOf()
    val playersArray : MutableList<String> = mutableListOf()

    private lateinit var rollButton: Button
    private lateinit var skipButton: Button

    val revolver = BooleanArray(6) // массив из 6 элементов типа Boolean
    var curBullet = 0
    var curPlayer = 0


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        revolver.fill(false) // заполняет массив значениями false
        val randomIndex = revolver.indices.random() // генерирует новый случайный индекс
        revolver[randomIndex] = true // устанавливает значение true для нового случайного элемента

        listView = findViewById(R.id.listOfPlayersText)
        gameLogs = findViewById(R.id.gameLogsListView)
        whoPlay = findViewById(R.id.whoPlayText)
        rollButton = findViewById(R.id.rollButton)
        skipButton = findViewById(R.id.skipButton)


        val settings = intent.getParcelableExtra<Settings>("settings")
        if (settings != null) {
            for(m in 1..settings.numberOfPlayers){
                players.add(Player(m, settings.numberOfSkips, settings.numberOfSpins))
                playersArray.add("Игрок №${players[m-1].number}")
            }
        }
        whoPlay.text = getString(R.string.who_play_button_text, players[0].number.toString())

        var adapter = ArrayAdapter(this, R.layout.list_item, playersArray)
        listView.choiceMode = ListView.CHOICE_MODE_SINGLE
        listView.adapter = adapter

        logsArray.add("Игра началась с ${players.size} игроками")

        var adapter1 = ArrayAdapter(this, R.layout.list_item, logsArray)
        gameLogs.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        gameLogs.adapter = adapter1

        rollButton.text = getString(R.string.roll_button_text, players[0].spins.toString())
        skipButton.text = getString(R.string.skip_button_text, players[0].skips.toString())
    }

    fun click (v : View){ // переход к текущему играющему игроку
        var i = 0
        listView.setSelection(i)
        i += 1
        if (i > 2)
            i = 0
        listView.smoothScrollToPosition(listView.selectedItemPosition)

    }

    fun updateAllLists(){
        var adapter = ArrayAdapter(this, R.layout.list_item, playersArray)
        listView.adapter = adapter

        var adapter1 = ArrayAdapter(this, R.layout.list_item, logsArray)
        gameLogs.adapter = adapter1
    }

    fun updateAllTexts(player: Player){

    }

    fun shootButtonPress(v : View){
        if(revolver[curBullet]){
            players.removeAt(curPlayer)
            playersArray.removeAt(curPlayer)


            logsArray.add("Игрок №${curPlayer+1} умер")
            revolver.fill(false) // заполняет массив значениями false
            revolver[revolver.indices.random()] = true // устанавливает значение true для нового случайного элемента
            Toast.makeText(this, "Началась новая игра", Toast.LENGTH_SHORT).show()
        }
        else{
            logsArray.add("Игроку №${curPlayer+1} повезло")
            curBullet++
            curPlayer++
        }
        updateAllLists()
    }


}