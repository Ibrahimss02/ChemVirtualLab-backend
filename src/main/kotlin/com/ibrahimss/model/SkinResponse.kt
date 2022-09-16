package com.ibrahimss.model

import com.google.gson.annotations.SerializedName

data class SkinResponse(
    @field:SerializedName("skin_id")
    val skinId: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("price")
    val price: Int,

    @field:SerializedName("description")
    val description: String,
)
