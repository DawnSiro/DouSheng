package com.qxy.dousheng.ui.personal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PersonalViewModel : ViewModel() {

    private val personalLiveData = MutableLiveData<String>()

    fun getLiveData(): MutableLiveData<String>{
        return personalLiveData
    }

}