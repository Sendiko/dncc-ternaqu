package com.sendiko.ternaqu.network.request

import com.google.gson.annotations.SerializedName

data class TopicRequest(

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("question")
	val question: String? = null,

	@field:SerializedName("replyTo")
	val replyTo: Int? = null,
)
