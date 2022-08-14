package com.qxy.dousheng.ui.follow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FollowViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private val personalLiveData = MutableLiveData<String>()

    fun getLiveData(): MutableLiveData<String> {
        return personalLiveData
    }

}