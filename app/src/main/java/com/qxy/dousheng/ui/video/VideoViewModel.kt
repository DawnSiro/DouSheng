package com.qxy.dousheng.ui.video

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VideoViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private val personalLiveData = MutableLiveData<String>()

    fun getLiveData(): MutableLiveData<String> {
        return personalLiveData
    }

}