package com.generic.kotlin.robinhoodtest

import android.util.Log
import androidx.compose.runtime.internal.liveLiteral
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.generic.kotlin.robinhoodtest.data.Asset
import com.generic.kotlin.robinhoodtest.data.AssetRepository

class AssetViewModel : ViewModel() {
    private val dataRepo = AssetRepository()

    private val _assestData = dataRepo.assetList
    val assestData: LiveData<List<Asset>>
        get() = _assestData

    fun getFilterData(category: String) {
        val filterData = assestData.value

        if (filterData != null) {
            _assestData.value = assestData.value!!.filter {
                    (it.instrument_type.contains(category))
                }
            Log.d("Asset data", _assestData.value.toString())

        }
    }


}