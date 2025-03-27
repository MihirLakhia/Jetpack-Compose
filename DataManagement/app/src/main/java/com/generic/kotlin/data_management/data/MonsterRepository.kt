package com.generic.kotlin.data_management.data

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.androiddata.data.Monster
import com.generic.kotlin.data_management.R
import com.generic.kotlin.data_management.utilities.FileHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types


class MonsterRepository(val app: Application) {

    val monsterData = MutableLiveData<List<Monster>>()
    val monstername = MutableLiveData<String>()
    private val listType = Types.newParameterizedType(
        List::class.java, Monster::class.java
    )
    init {
        getMonsterData()
      //  Log.i(LOG_TAG, "Network available: ${networkAvailable()}")
    }
    fun getMonsterData() {
        monstername.value = "ABC"
        val text = FileHelper.getTextFromResources(app, R.raw.monster_data)
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<List<Monster>> =
            moshi.adapter(listType)
        Log.d("Data","repo fun getMonsterData $text")
        monsterData.value = (adapter.fromJson(text) ?: emptyList())
    }
}