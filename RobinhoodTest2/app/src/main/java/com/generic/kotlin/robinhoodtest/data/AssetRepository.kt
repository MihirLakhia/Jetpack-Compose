package com.generic.kotlin.robinhoodtest.data

import androidx.lifecycle.MutableLiveData
import com.generic.kotlin.robinhoodtest.retrofit.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AssetRepository() {
    val assetList = MutableLiveData<List<Asset>>()

    init {
        refreshDataFromWeb()
    }
    fun refreshDataFromWeb() {
        CoroutineScope(Dispatchers.IO).launch {
            getAssetData()
        }
    }
    suspend  fun getAssetData(){
       val _assetData =  RetrofitInstance.api.getAssetData()
         assetList.postValue(_assetData)

    }

}