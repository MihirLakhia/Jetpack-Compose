package com.generic.kotlin.data_management.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androiddata.data.Monster
import com.generic.kotlin.data_management.data.MonsterRepository

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val dataRepo = MonsterRepository(app)
    private val _monsterData = dataRepo.monsterData//dataRepo.monsterData
    val monsterData : LiveData<List<Monster>> = _monsterData//dataRepo.monsterData
    private val _monsterName = MutableLiveData<String>()//dataRepo.monsterData
    val monsterName :LiveData<String> = _monsterName


}