package com.jesusvilla.profile.viewModel

import android.content.Context
import androidx.annotation.CheckResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jesusvilla.base.viewModel.BaseViewModel
import com.jesusvilla.core.network.data.Session
import com.jesusvilla.profile.data.model.StoresResponse
import com.jesusvilla.profile.di.GetMoreStoresUseCaseQualifier
import com.jesusvilla.profile.di.GetStoresUseCaseQualifier
import com.jesusvilla.profile.domain.mapper.toDataStore
import com.jesusvilla.profile.domain.model.AnyStore
import com.jesusvilla.profile.domain.model.BaseStore
import com.jesusvilla.profile.domain.model.DataStore
import com.jesusvilla.profile.domain.useCase.GetMoreStoresUseCase
import com.jesusvilla.profile.domain.useCase.GetStoresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StoresViewModel @Inject constructor(
    @GetStoresUseCaseQualifier private val getStoresUseCase: GetStoresUseCase,
    @GetMoreStoresUseCaseQualifier private val getMoreStoresUseCase: GetMoreStoresUseCase
) : BaseViewModel() {
    private val dataStore = MutableLiveData<DataStore>()
    private val list = MutableLiveData<List<BaseStore>>()
    private var isShowMoreLoading = false

    private var localCounter = 0

    var isCalled = false

    companion object {
        private const val ASSET_STORES = "stores.json"
        private const val MAX_LOCAL_SHOW_MORE = 3
        private const val DELAY_LOCAL = 1500L
        private const val PER_PAGE_DEFAULT = 10
        private const val PAGE_DEFAULT = 1
    }

    @CheckResult
    fun getStoresList(): LiveData<List<BaseStore>> = list

    @CheckResult
    fun getShowMoreLoading() = isShowMoreLoading

    fun getStores(showMore: Boolean = false) {
        isShowMoreLoading = showMore
        if(showMore)
            Session.nextUrl = dataStore.value!!.next!!
        launcher(
            invoke = {
                if (showMore) getMoreStoresUseCase(Session.nextUrl)
                else getStoresUseCase()
            },
            responseResult = {
                val result = it.toDataStore()
                dataStore.postValue(result)
                isShowMoreLoading = false
                processList(result)
            },
            errorResponse = {
                //with this callback we can handle the error
                isShowMoreLoading = false
                notifyError(it.first)
            }
        )
    }

    private fun processList(dataStore: DataStore) {
        Timber.i("processList -> dataStore:${dataStore.next}")
        list.value?.let {
            val moreItems = ArrayList<BaseStore>()
            moreItems.addAll(it)
            moreItems.removeAt(it.size - 1)
            moreItems.addAll(dataStore.stores)
            if(!dataStore.next.isNullOrEmpty())
                moreItems.add(AnyStore())
            list.postValue(moreItems)
        }?:run {
            val moreItems = ArrayList<BaseStore>()
            moreItems.addAll(dataStore.stores)
            if(!dataStore.next.isNullOrEmpty()) {
                Session.nextUrl = dataStore.next
                moreItems.add(AnyStore())
            }
            list.postValue(moreItems)
        }
    }

    //region LOCAL
    fun getLocalList(context: Context) {
        val data =  context.assets.open(ASSET_STORES).bufferedReader().use { it.readText() }
        val parseData = Gson().fromJson(data, StoresResponse::class.java)
        val result = parseData.toDataStore()
        dataStore.postValue(result)
        processLocalList(result)
    }

    private fun processLocalList(dataStore: DataStore) {
        val newList = ArrayList<BaseStore>()
        newList.addAll(dataStore.stores)
        newList.addAll(dataStore.stores)
        newList.addAll(dataStore.stores)
        newList.addAll(dataStore.stores)
        newList.addAll(dataStore.stores)
        newList.addAll(dataStore.stores)
        newList.addAll(dataStore.stores)
        newList.addAll(dataStore.stores)
        newList.addAll(dataStore.stores)
        newList.addAll(dataStore.stores)
        newList.addAll(dataStore.stores)
        newList.addAll(dataStore.stores)
        newList.addAll(dataStore.stores)
        newList.addAll(dataStore.stores)
        newList.addAll(dataStore.stores)
        newList.addAll(dataStore.stores)
        newList.addAll(dataStore.stores)
        newList.addAll(dataStore.stores)
        newList.addAll(dataStore.stores)
        newList.add(AnyStore())
        list.postValue(newList)
    }

    fun getMoreLocalItems() {
        viewModelScope.launch {
            list.value?.let {
                delay(DELAY_LOCAL)
                Timber.i("getMoreLocalItems -> localCounter:$localCounter")
                val moreItems = ArrayList<BaseStore>()
                moreItems.addAll(it)
                moreItems.removeAt(it.size - 1)
                moreItems.addAll(moreItems)
                localCounter++
                if(localCounter < MAX_LOCAL_SHOW_MORE) {
                    moreItems.add(AnyStore())
                }
                list.postValue(moreItems)
            }
        }
    }
    //endregion
}