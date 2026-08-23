package com.github.app.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.api.RepositoryService
import com.github.app.homepage.model.entity.event.Repository
import com.github.core.net.HttpClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RepoDetailViewModel : ViewModel() {

    private val mRepository = MutableLiveData<Repository>()
    val repository: LiveData<Repository> = mRepository

    private val mError = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = mError

    private val mLoading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = mLoading

    private val mDisposables = CompositeDisposable()

    fun loadRepo(owner: String, repoName: String) {
        mLoading.value = true
        val service = HttpClient.service(RepositoryService::class.java)
        mDisposables.add(
            service.getRepository(owner, repoName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mRepository.value = it
                    mLoading.value = false
                }, {
                    mError.value = it
                    mLoading.value = false
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        mDisposables.clear()
    }
}
