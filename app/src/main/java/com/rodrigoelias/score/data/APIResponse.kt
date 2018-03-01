package com.rodrigoelias.score.data

import com.google.gson.annotations.SerializedName

// Represents the data retrieved from the Remote Server (API)
data class APIResponse(@SerializedName("creditReportInfo") val creditReport: CreditReport)

// Leaf node used within the APIResponse
data class CreditReport(@SerializedName("score") val score: Int,
                      @SerializedName("maxScoreValue") val maxScore: Int)
