package com.asantherrera.savemoney365days.ui.total

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.asantherrera.savemoney365days.database.SaveMoneyDatabase
import com.asantherrera.savemoney365days.database.Saving
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class TotalViewModel : ViewModel() {
    private val db = SaveMoneyDatabase()
    var total: MutableLiveData<Int> = MutableLiveData()
    var totalList: MutableLiveData<List<Saving>> = MutableLiveData()
    var totalListByAmount: MutableLiveData<List<Saving>> = MutableLiveData()
    var totalListByDate: MutableLiveData<List<Saving>> = MutableLiveData()
    private var descAmount: Boolean = false
    private var descDate: Boolean = false
    private var localList: List<Saving> = listOf()

    fun showAll(owner: LifecycleOwner, context: Context) {

        viewModelScope.launch {
            db.getRoom(context).savingDao().getAll().observe(owner, Observer {
                totalList.value = it
                localList = it
            })
        }
    }


    fun getListByAmount() {
        if (!descAmount) {
            totalListByAmount.value =
                localList.sortedByDescending { it.id }
            descAmount = true
        } else {
            totalListByAmount.value =
                localList.sortedBy { it.id }
            descAmount = false
        }

    }

    fun getListByDate() {

        if (!descDate) {
            totalListByDate.value =
                localList.sortedByDescending { it.date }
            descDate = true
        } else {
            totalListByDate.value =
                localList.sortedBy { it.date }
            descDate = false
        }

    }

}