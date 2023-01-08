package com.sendiko.ternaqu.network.response

import com.google.gson.annotations.SerializedName

data class LogoutResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("token_type")
	val tokenType: String? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("token")
	val token: String? = null
)
