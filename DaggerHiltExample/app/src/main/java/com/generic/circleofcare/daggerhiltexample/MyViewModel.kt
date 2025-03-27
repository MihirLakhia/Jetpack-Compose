package com.generic.circleofcare.daggerhiltexample

import androidx.lifecycle.ViewModel
import com.generic.circleofcare.daggerhiltexample.domain.repository.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: MyRepository
):ViewModel() {
}