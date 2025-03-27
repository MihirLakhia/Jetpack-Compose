package com.generic.circleofcare.daggerhiltexample.data.repository

import android.app.Application
import com.generic.circleofcare.daggerhiltexample.R
import com.generic.circleofcare.daggerhiltexample.data.remote.MyApi
import com.generic.circleofcare.daggerhiltexample.domain.repository.MyRepository

class MyRepositoryImpl(
    private val api: MyApi,
    private val appContext: Application
):MyRepository {
    init {
        val appName = appContext.getString(R.string.app_name)
        print("hello from the repository, the application name is $appName ")
    }
    override suspend fun doNetworkCall() {
        TODO("Not yet implemented")
    }
}