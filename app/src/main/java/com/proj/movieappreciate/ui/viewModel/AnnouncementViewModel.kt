package com.proj.movieappreciate.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proj.movieappreciate.data.dataSource.model.AnnouncementResponse
import com.proj.movieappreciate.data.repository.AnnounceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.apache.commons.lang3.mutable.Mutable
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class AnnouncementViewModel @Inject constructor(
    private val announceRepository: AnnounceRepository
): ViewModel() {

    private val _announcementResponse = MutableLiveData<Result<AnnouncementResponse>>()

    val announcementResponse : LiveData<Result<AnnouncementResponse>> get() = _announcementResponse



    fun registAnnouncement(title: String, content : String, type : String ) {
        viewModelScope.launch {
            try{
                val result = announceRepository.registAnnouncement(title,content,type)
                _announcementResponse.value = result

                Log.d("공지 뷰 모델 ", _announcementResponse.value.toString())
            }
            catch (e : Exception) {
               _announcementResponse.value = Result.failure(e)
            }
        }
    }



}