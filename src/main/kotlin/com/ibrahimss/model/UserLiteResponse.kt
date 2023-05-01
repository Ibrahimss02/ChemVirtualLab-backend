package com.ibrahimss.model

import com.google.gson.annotations.SerializedName

data class UserLiteResponse(
    @field:SerializedName("name")
    val name: String,
    
    @field:SerializedName("coin")
    val coins: Int,
)
