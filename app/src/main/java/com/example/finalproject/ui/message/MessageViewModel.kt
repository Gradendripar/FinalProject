package com.example.finalproject.ui.message

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.ObjectInputStream

class MessageViewModel (application: Application): AndroidViewModel(application) {

    private  val _msgList:MutableLiveData<MutableList<MessageFragment.Msg>> = MutableLiveData()
    val msgs = loadData()
    fun getmsgList() : LiveData<MutableList<MessageFragment.Msg>>{
        if (msgs == null)
        {
            _msgList.postValue(mutableListOf())
        }else {
            _msgList.postValue(msgs)
        }
        return _msgList
    }
    val msgList: LiveData<MutableList<MessageFragment.Msg>> = getmsgList()
    fun loadData():MutableList<MessageFragment.Msg>? {
        try {
            val input = getApplication<Application>().openFileInput("data")
            val objectInputStream = ObjectInputStream(input)

            val msgs = objectInputStream.readObject() as MutableList<MessageFragment.Msg>

            objectInputStream.close()
            input?.close()
            return msgs

        }catch (e: Exception){
            return null
        }
    }
}