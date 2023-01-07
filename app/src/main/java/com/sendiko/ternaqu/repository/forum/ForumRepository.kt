package com.sendiko.ternaqu.repository.forum

import android.app.Application
import com.sendiko.ternaqu.network.NetworkConfig
import com.sendiko.ternaqu.network.request.TopicRequest

class ForumRepository(app: Application) {

    private val client = NetworkConfig.getInstance(app)

    fun getTopics() = client.getTopics()

    fun getTopic(id : String) = client.getTopic(id)

    fun postTopic(token: String, topicRequest: TopicRequest) = client.postTopic(token, topicRequest)

}