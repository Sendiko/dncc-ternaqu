package com.sendiko.ternaqu.repository.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sendiko.ternaqu.network.response.TopicsItem

class SharedViewModel: ViewModel() {

    private val _topic = MutableLiveData<TopicsItem>()
    val topic: LiveData<TopicsItem> = _topic

    fun safeTopic(topic: TopicsItem){
        _topic.value = topic
    }

}