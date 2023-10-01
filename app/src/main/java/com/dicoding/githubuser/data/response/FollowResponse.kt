package com.dicoding.githubuser.data.response

import com.google.gson.annotations.SerializedName


data class FollowResponseItem(


	@field:SerializedName("following_url")
	val followingUrl: String,

	@field:SerializedName("starred_url")
	val starredUrl: String,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("followers_url")
	val followersUrl: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,


	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("gravatar_id")
	val gravatarId: String,

	@field:SerializedName("node_id")
	val nodeId: String,

)
