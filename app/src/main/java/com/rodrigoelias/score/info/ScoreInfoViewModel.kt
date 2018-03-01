package com.rodrigoelias.score.info

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rodrigoelias.score.data.CreditReport
import com.rodrigoelias.score.data.ScoreRepository
import com.rodrigoelias.score.data.RepositoryListener

class ScoreInfoViewModel(repository: ScoreRepository = ScoreRepository()) : RepositoryListener, ViewModel() {

    private val creditInfo: MutableLiveData<CreditReport> = MutableLiveData()
    private val repositoryRequestStatus: MutableLiveData<Status> = MutableLiveData()

    val status: LiveData<Status>
        get() = repositoryRequestStatus

    val list: LiveData<CreditReport>
        get() = creditInfo

    init {
        repositoryRequestStatus.postValue(Status.STARTED)
        repository.getEmAll(this)
    }

    override fun onFail() {
        repositoryRequestStatus.postValue(Status.FAILED)
    }

    override fun onSuccess(data: CreditReport) {
        repositoryRequestStatus.postValue(Status.SUCCESS)
        creditInfo.postValue(data)
    }

    enum class Status { STARTED, FAILED, SUCCESS, NONE }
}