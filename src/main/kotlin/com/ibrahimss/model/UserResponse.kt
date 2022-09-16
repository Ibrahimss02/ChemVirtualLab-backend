package com.ibrahimss.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("uid")
    val uid: String,
    
    @field:SerializedName("email")
    val email: String,
    
    @field:SerializedName("name")
    val name: String,
    
    @field:SerializedName("level")
    val level: Int,
    
    @field:SerializedName("coins")
    val coins: Int,
    
    @field:SerializedName("badge")
    val badge: Int,

    @field:SerializedName("skins")
    val skins: List<SkinResponse>,
)