package com.generic.kotlin.robinhoodtest.retrofit

import androidx.lifecycle.MutableLiveData
import com.generic.kotlin.robinhoodtest.data.Asset
import retrofit2.http.GET

interface AssetApi {

    @GET("dns-mcdaid/b248c852b743ad960616bac50409f0f0/raw/6921812bfb76c1bea7868385adf62b7f447048ce/instruments.json")
    suspend fun getAssetData() :List<Asset>
}