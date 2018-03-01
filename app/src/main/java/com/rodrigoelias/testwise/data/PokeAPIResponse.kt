package com.rodrigoelias.testwise.data

import com.google.gson.annotations.SerializedName

// Represents the data retrieved from the Remote Server (API)
data class PokeAPIResponse(@SerializedName("pokemon") val pokemon: List<RemoteNode>)

// Leaf node used within the PokeAPIResponse
data class RemoteNode(@SerializedName("name") val name: String,
                      @SerializedName("resource_uri") val resourceUri: String)
