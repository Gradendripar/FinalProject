package com.example.finalproject.ui.card

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finalproject.ui.card.model.CardMatchingGame
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Exception

class CardViewModel(application: Application) : AndroidViewModel(application) {
    private var _game: MutableLiveData<CardMatchingGame> = MutableLiveData()
    private var rgame = loadData()
    fun get(): LiveData<CardMatchingGame> {
        if (rgame != null) {
            _game.postValue(rgame)
        } else {
            _game.postValue(CardMatchingGame(24))
        }
        return _game
    }

    val game: LiveData<CardMatchingGame> = get()

    //保存数据
    fun saveData() {
        try {
            val output = getApplication<Application>().openFileOutput(
                gameFile,
                AppCompatActivity.MODE_PRIVATE
            )
            ObjectOutputStream(output).use {
                it.writeObject(CardFragment.cardGame)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //加载数据
    fun loadData(): CardMatchingGame? {
        try {
            val input = getApplication<Application>()?.openFileInput(gameFile)
            val objectInputStream = ObjectInputStream(input)
            val game = objectInputStream.readObject() as CardMatchingGame
            objectInputStream.close()
            input.run {
                objectInputStream.close()
                close()
            }
            return game
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}