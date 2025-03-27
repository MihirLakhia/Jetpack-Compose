package com.generic.circleofcare.daggerhiltexample.data.remote

import retrofit2.http.GET

interface MyApi {
    @GET("test")
    suspend fun doNetworkCall()
}