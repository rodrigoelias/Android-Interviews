package data

import com.google.gson.annotations.SerializedName

/**
 * Created by rodrigoelias on 28/02/2018.
 */

data class RemoteNode(@SerializedName("name")
                      val name: String,
                      @SerializedName("resource_uri")
                      val resourceUri: String)
{
}
data class PokeAPIResponse(@SerializedName("pokemon") val pokemon: List<RemoteNode>)
