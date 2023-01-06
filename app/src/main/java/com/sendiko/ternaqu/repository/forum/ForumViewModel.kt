package com.sendiko.ternaqu.repository.forum

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sendiko.ternaqu.network.response.TopicsResponse
import com.sendiko.ternaqu.repository.model.FailedMessage
import com.sendiko.ternaqu.repository.model.Topic
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "ForumViewModel"
class ForumViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = ForumRepository(app)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty : LiveData<Boolean> = _isEmpty

    private val _isFailed = MutableLiveData<FailedMessage>()
    val isFailed: LiveData<FailedMessage> = _isFailed

    fun getTopics(): LiveData<ArrayList<Topic>> {
        _isLoading.value = true
        val resultTopic = MutableLiveData<ArrayList<Topic>>()
        val topicList = ArrayList<Topic>()
        val request = repo.getTopics()
        request.enqueue(
            object : Callback<TopicsResponse> {
                override fun onResponse(
                    call: Call<TopicsResponse>,
                    response: Response<TopicsResponse>
                ) {
                    _isLoading.value = false
                    when (response.code()) {
                        200 -> {
                            for (i in response.body()!!.topics!!) {
                                when (i) {
                                    null -> _isEmpty.value = true
                                    else -> {
                                        val topic = Topic(
                                            i.id?:0,
                                            i.name?:"",
                                            i.title?:"",
                                            i.question?:"",
                                            i.profileUrl?:""
                                        )
                                        topicList.add(topic)
                                        resultTopic.value = topicList
                                    }
                                }
                            }
                        }
                        404 -> {
                            _isFailed.value = FailedMessage(true, "there is no topic")
                        }
                    }
                }

                override fun onFailure(call: Call<TopicsResponse>, t: Throwable) {
                    _isFailed.value = FailedMessage(true, "${t.message}")
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message}")
                }

            }
        )
        return resultTopic
    }

}