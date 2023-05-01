package com.ibrahimss.model

import com.google.gson.annotations.SerializedName

data class LeaderboardResponse(
    @field:SerializedName("leaderboard")
    val leaderboard: List<UserLiteResponse>,
)