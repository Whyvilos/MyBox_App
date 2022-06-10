package com.example.mybox.allscreens.notice_screen

import androidx.lifecycle.ViewModel
import com.example.mybox.data.remote.responses.JsonStatus
import com.example.mybox.repository.MyBoxRepository
import com.example.mybox.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NoticeModel @Inject constructor(
    private val repository: MyBoxRepository
) : ViewModel() {

    suspend fun getCheckNotice(
        accessToken: String,
    ):Resource<JsonStatus>{
        return repository.getCheckNotices("Bearer $accessToken")
    }

}