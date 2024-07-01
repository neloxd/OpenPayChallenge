package com.jesusvilla.base.viewModel

import android.content.Context
import androidx.annotation.CheckResult
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesusvilla.base.R
import com.jesusvilla.base.lifecycle.Event
import com.jesusvilla.base.models.CustomModalModel
import com.jesusvilla.base.models.CustomToastModel
import com.jesusvilla.base.models.CustomToastType
import com.jesusvilla.base.utils.UiText
import com.jesusvilla.core.network.data.Resource
import com.jesusvilla.core.network.data.Status
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Jesús Villa on 11/03/24
 */
abstract class BaseViewModel : ViewModel() {

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    //region livedata
    protected val showCustomToast = MutableLiveData<CustomToastModel>()

    protected val showErrorModal = MutableLiveData<CustomModalModel>()

    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    protected val _showErrorDialog = MutableLiveData<Event<UiText>>()
    val showErrorDialog: LiveData<Event<UiText>> get() = _showErrorDialog

    protected val _showNotificationToast = MutableLiveData<Event<UiText>>()
    val showNotificationToast: LiveData<Event<UiText>> get() = _showNotificationToast
    //endregion

    fun notifyError(errorMsg: String) {
        Timber.e(("launcher error: $errorMsg"))
        showAttentionService(body = errorMsg)
    }

    fun showAttentionService(
        body: String,
    ) {
        showErrorModal.postValue(
            CustomModalModel
                .Builder()
                .title("")
                .body(body)
                .primaryButton("")
                .build()
        )
    }

    fun showToast(message: String, type: CustomToastType = CustomToastType.ERROR) {
        showCustomToast.postValue(
            CustomToastModel(
                text = message,
                type = type
            )
        )
    }

    /**
     * The launcher method execute viewModelsScope,
     * and then processing the method
     *
     * @param invoke: It is suspend function
     * @param navigateModel: It is the model to navigate
     * @param responseResult: Return de result from invoke proccess
     * @param errorResponse: Return de error from invoke proccess
     *
     */
    protected fun <T> launcher(
        invoke: suspend () -> Resource<T>,
        responseResult: ((T) -> Unit),
        errorResponse: ((Pair<String, Boolean>) -> Unit)? = null
    ) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val response = invoke()
            _isLoading.postValue(false)
            if (response.status == Status.SUCCESS) {
                //navego con la data ok
                //removed!!
                //devuelvo la data ok para hacer otra logica
                responseResult.invoke(response.data!!)
            } else {
                response.message?.let { error ->
                    errorResponse?.let { callBackError ->
                        callBackError(Pair(error, response.isNetworkRelated))
                    } ?: notifyError(error)
                }
            }
        }
    }

    @CheckResult
    fun onIsLoading(): LiveData<Boolean> = isLoading

    @CheckResult
    fun onShowErrorDialogChange(): LiveData<Event<UiText>> = showErrorDialog
    //endrefion

    @CheckResult
    fun onShowErrorModal(): LiveData<CustomModalModel> = showErrorModal

    @CheckResult
    fun onShowCustomToast(): LiveData<CustomToastModel> = showCustomToast
}