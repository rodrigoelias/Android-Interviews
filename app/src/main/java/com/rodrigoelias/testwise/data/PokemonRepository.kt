package data

class PokemonRepository {
    private val remoteSource: RemoteDataSource = RemoteDataSource()
    //private val localSource :  = RemoteDataSource()

    fun getEmAll(listener: RepositoryListener) {
        remoteSource.fetchFromRemote(listener)
    }
}

interface RepositoryListener {
    fun onSuccess(data: List<Pokemon>)
    fun onFail()
}