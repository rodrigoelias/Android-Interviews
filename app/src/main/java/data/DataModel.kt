package data

import com.google.gson.annotations.SerializedName

data class RemoteNode(@SerializedName("name") val name: String,
                      @SerializedName("resource_uri") val resourceUri: String)

data class PokeAPIResponse(@SerializedName("pokemon") val pokemon: List<RemoteNode>)
