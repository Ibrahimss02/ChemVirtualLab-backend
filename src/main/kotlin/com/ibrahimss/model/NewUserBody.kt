package com.ibrahimss.model

import com.google.gson.annotations.SerializedName

data class NewUserBody(
    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("name")
    val name: String,
)