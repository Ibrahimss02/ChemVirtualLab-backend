package com.ibrahimss.model

import com.google.gson.annotations.SerializedName

data class UserBody(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("email")
    val email: String,
    
    @field:SerializedName("name")
    val name: String,
    
    @field:SerializedName("level")
    val level: Int,
    
    @field:SerializedName("coin")
    val coin: Int,
    
    @field:SerializedName("exp")
    val exp: Int,

    @field:SerializedName("badge")
    val badge: Int,
)
