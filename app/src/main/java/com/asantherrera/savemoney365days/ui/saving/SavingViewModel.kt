package com.asantherrera.savemoney365days.ui.saving

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.asantherrera.savemoney365days.R
import com.asantherrera.savemoney365days.application.AppConstants.YEAR_DAYS
import com.asantherrera.savemoney365days.application.AppConstants.ZERO
import com.asantherrera.savemoney365days.database.SaveMoneyDatabase
import com.asantherrera.savemoney365days.database.Saving
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random

class SavingViewModel : ViewModel() {

    private var actualResult: Int = ZERO
    private val db = SaveMoneyDatabase()
    private var _result = MutableLiveData<String>()
    val result: LiveData<String> = _result
    private var _remaining = MutableLiveData<String>()

    val remaining: LiveData<String> = _remaining

    var allSavings: List<Saving> = listOf()
    var localRemaining: Int = 0

    fun observeAllSavings(context: Context, owner: LifecycleOwner) {
        db.getRoom(context).savingDao().getAll().observe(owner, Observer {
            if (it != null) {
                allSavings = it
                localRemaining = YEAR_DAYS - it.size
                if (localRemaining > ZERO) {
                    _remaining.value = "$localRemaining"
                } else {
                    resetValues(context)
                }
            }
        })
    }

    fun onLuckyBtnClicked() {
        viewModelScope.launch(Dispatchers.Main) {
            var randomInt = ZERO
            var isValidNum = false
            while (!isValidNum) {
                randomInt = Random.nextInt(YEAR_DAYS) + 1
                val saving = allSavings.find { it.id == randomInt }
                if ((saving == null && actualResult != randomInt) || (saving == null && localRemaining == 1)) {
                    isValidNum = true
                }
            }
            actualResult = randomInt
            _result.value = actualResult.toString()
        }
    }

    fun onSaveBtnClicked(context: Context) {
        viewModelScope.launch(Dispatchers.Main) {
            if (actualResult > ZERO) {
                val calendar = GregorianCalendar.getInstance()
                calendar.timeZone = TimeZone.getDefault()
                val date = calendar.time
                val currency = Currency.getInstance("MXN")
                val newSaving = Saving(actualResult, date, currency)

                db.getRoom(context).savingDao().insert(newSaving)

                _result.value = context.resources.getString(R.string.congrats)

            }


        }
    }

    fun resetValues(context: Context) {
        viewModelScope.launch(Dispatchers.Main) {
            db.getRoom(context).savingDao().deleteAll()
            _remaining.value = context.resources.getString(R.string.num_365)
            _result.value = context.resources.getString(R.string.completed_challenge)
        }
    }


}