package com.ibrahimss.model

import com.google.gson.annotations.SerializedName

data class BuySkinBody(
    @field:SerializedName("user_id")
    val userId: String,

    @field:SerializedName("skin_id")
    val skinId: Int,
)
