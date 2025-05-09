package com.kotlin.worker.smartstudy.presentation.subject

import androidx.lifecycle.ViewModel
import com.kotlin.worker.smartstudy.domain.repository.SubjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository
):ViewModel(){
}