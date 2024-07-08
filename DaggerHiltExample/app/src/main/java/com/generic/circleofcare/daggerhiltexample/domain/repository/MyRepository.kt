package com.generic.circleofcare.daggerhiltexample.domain.repository

interface MyRepository {
    suspend fun doNetworkCall()
}