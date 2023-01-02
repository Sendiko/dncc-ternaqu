package com.sendiko.ternaqu.network.response

import com.google.gson.annotations.SerializedName

data class RecipeResponse(

	@field:SerializedName("recipe")
	val recipe: List<RecipeItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class RecipeItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("steps")
	val steps: String? = null,

	@field:SerializedName("benefit")
	val benefit: String? = null,

	@field:SerializedName("tools_and_materials")
	val toolsAndMaterials: String? = null
)
