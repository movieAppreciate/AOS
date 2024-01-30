package com.proj.movieappreciate.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proj.movieappreciate.data.dataSource.model.Report
import com.proj.movieappreciate.data.repository.ReportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchFragmentViewModel @Inject constructor(private val reporitory : ReportRepository) : ViewModel() {

    private val _reportList = MutableLiveData<Result<List<Report>>>()

    val reportList : LiveData<Result<List<Report>>> get() = _reportList

    fun getAllReports() {

        viewModelScope.launch {
            try {
                val result = reporitory.getAllReport()
                _reportList.value = result

            } catch (e : Exception){
                _reportList.value = Result.failure(e)
            }

        }
    }
}