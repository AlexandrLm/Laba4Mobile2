package com.example.laba4mobile2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    private lateinit var listView : ListView
    private lateinit var gameLogs : ListView

    private lateinit var whoPlay : TextView

    private var logsArray: MutableList<String> = mutableListOf() // Список для логов игры
    private var playersViewArray : MutableList<String> = mutableListOf() // Список для нижнего ListView
    private var players: MutableList<Player> = mutableListOf() // Список игроков класса Player

    private lateinit var rollButton: Button
    private lateinit var skipButton: Button

    private var revolver = BooleanArray(6) // массив из 6 элементов типа Boolean
    private var curBullet = 0 // текущий номер пули
    private var curPlayer = 0 // текущий игрок

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
        if (settings != null) { //извлекаем данные из MainActivity и заполняем список игроков
            for(m in 1..settings.numberOfPlayers){
                players.add(Player(m, settings.numberOfSkips, settings.numberOfSpins)) // Добавляем нужное кол-во одинаковых игроков
                playersViewArray.add("Игрок №${players[m-1].number}") // Добавляем элементы в список для вывода текущих игроков
            }
        }

        val adapter = ArrayAdapter(this, R.layout.list_item, playersViewArray) // Создаем адаптер
        //listView.choiceMode = ListView.CHOICE_MODE_SINGLE // Нужно для того что бы можно было кликать на элементы списка
        listView.adapter = adapter

        logsArray.add("В револьвере 6 патронов") // Первый лог игры
        logsArray.add("Игра началась с ${players.size} игроками")

        val adapter1 = ArrayAdapter(this, R.layout.list_item, logsArray) // Создаем адаптер
        //gameLogs.choiceMode = ListView.CHOICE_MODE_MULTIPLE // Нужно для того что бы можно было кликать на элементы списка
        gameLogs.adapter = adapter1

        updateAllTexts(players[curPlayer]) // Кто сейчас выбирает (Сначала нулевой игрок)
    }

//    fun click (v : View){ // переход к текущему играющему игроку (пока что не используется)
//        var i = 0
//        listView.setSelection(i) // это может быть полезно
//        i += 1
//        if (i > 2)
//            i = 0
//        listView.smoothScrollToPosition(listView.selectedItemPosition) // это может быть полезно
//
//    }

    private fun updateAllLists(){ // Обновляет все списки (логи, текущие игроки)
        val adapter = ArrayAdapter(this, R.layout.list_item, playersViewArray)
        listView.adapter = adapter

        val adapter1 = ArrayAdapter(this, R.layout.list_item, logsArray)
        gameLogs.adapter = adapter1
    }

    private fun updateAllTexts(player: Player){ // Обновляет все надписи на кнопках и заголовок
        whoPlay.text = getString(R.string.who_play_text, player.number.toString())
        rollButton.text = getString(R.string.roll_button_text, player.spins.toString())
        skipButton.text = getString(R.string.skip_button_text, player.skips.toString())
    }

    fun shootButtonPress(v : View){
        if(revolver[curBullet]){
            playersViewArray.removeAt(curPlayer)
            logsArray.add(0, "Игрок №${players[curPlayer].number} умер")
            players.removeAt(curPlayer)

            if(players.size == 1){ //Если остался один игрок
                win()
                return
            }

            logsArray.add(0, "Револьвер перезарядился")
            revolver.fill(false) // заполняет массив значениями false
            revolver[revolver.indices.random()] = true // устанавливает значение true для нового случайного элемента

            Toast.makeText(this, "Началась новая игра", Toast.LENGTH_SHORT).show()
            curBullet = 0
        }
        else{
            logsArray.add(0,"Игроку №${players[curPlayer].number} повезло")
            curBullet = (curBullet + 1) % revolver.size
            curPlayer = (curPlayer + 1) % players.size
        }

        if(curPlayer == players.size)
            curPlayer = (curPlayer + 1) % players.size
        println(curPlayer)
        updateAllLists()
        updateAllTexts(players[curPlayer])

    }
    fun skipButtonPress(v : View){
        if(players[curPlayer].skips > 0){
            players[curPlayer].skips--
            logsArray.add(0, "Игрок №${players[curPlayer].number} пропускает ход")
            curPlayer = (curPlayer + 1) % players.size
            updateAllLists()
            updateAllTexts(players[curPlayer])
        }
        else
            Toast.makeText(this, "Нет пропусков", Toast.LENGTH_SHORT).show()
    }
    fun rollButtonPress(v : View){
        if (players[curPlayer].spins > 0) {
            logsArray.add(0, "Револьвер перезарядился")
            revolver.fill(false) // заполняет массив значениями false
            revolver[revolver.indices.random()] =
                true // устанавливает значение true для нового случайного элемента
            players[curPlayer].spins--
            updateAllLists()
            updateAllTexts(players[curPlayer])
        }
        else
            Toast.makeText(this, "Нет прокруток", Toast.LENGTH_SHORT).show()
    }
    private fun win(){
        val intent = Intent(this, WinActivity::class.java)
        intent.putExtra("winPlayer", players[0].number)
        startActivity(intent)
    }
}