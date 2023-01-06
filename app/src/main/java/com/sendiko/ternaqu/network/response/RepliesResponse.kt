package com.sendiko.ternaqu.network.response

import com.google.gson.annotations.SerializedName

data class RepliesResponse(

	@field:SerializedName("replies")
	val replies: List<RepliesItem?>? = null,

	@field:SerializedName("topic")
	val topic: Topic? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class Topic(

	@field:SerializedName("profileUrl")
	val profileUrl: String? = null,

	@field:SerializedName("question")
	val question: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("replyTo")
	val replyTo: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null
)

data class RepliesItem(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("question")
	val question: String? = null,

	@field:SerializedName("profileUrl")
	val profileUrl: String? = null,

	@field:SerializedName("replyTo")
	val replyTo: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,
)
