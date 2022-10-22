package com.ibrahimss.model

import com.google.gson.annotations.SerializedName

data class UserSkinResponse(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("skins")
    val skins: List<SkinResponse>,
)
