package com.rodrigoelias.score.data

//Abstracts the Model from the ViewModel.
// The Repository is responsible for the abstraction between the API EndPoint and the data to be show
// by the ViewModel
class ScoreRepository(private val remoteSource: RemoteDataSource = RemoteDataSource()) {
    //private val localSource :  = RemoteDataSource()

    fun getEmAll(listener: RepositoryListener) {
        remoteSource.fetchFromRemote(listener)
    }
}

// RepositoryListener is the callback to be executed by the Repository after the request
// is completed
interface RepositoryListener {
    fun onSuccess(data: CreditReport)
    fun onFail()
}