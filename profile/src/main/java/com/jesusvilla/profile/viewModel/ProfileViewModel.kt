package com.jesusvilla.profile.viewModel

import androidx.annotation.CheckResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jesusvilla.database.di.qualifiers.ProfileDBRepositoryQualifier
import com.jesusvilla.base.viewModel.BaseViewModel
import com.jesusvilla.database.repository.ProfileDataRepository
import com.jesusvilla.database.resources.doError
import com.jesusvilla.database.resources.doSuccess
import com.jesusvilla.profile.di.GetPopularProfileUseCaseQualifier
import com.jesusvilla.profile.domain.mapper.toKnownForEntityList
import com.jesusvilla.profile.domain.mapper.toProfileEntity
import com.jesusvilla.profile.domain.mapper.toProfileUI
import com.jesusvilla.profile.domain.model.ProfileUI
import com.jesusvilla.profile.domain.useCase.GetPopularProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @GetPopularProfileUseCaseQualifier private val getPopularProfileUseCase: GetPopularProfileUseCase,
    @ProfileDBRepositoryQualifier private val profileDataRepository: ProfileDataRepository
) : BaseViewModel() {
    private val profile = MutableLiveData<ProfileUI>()

    @CheckResult
    fun getProfileUI(): LiveData<ProfileUI> = profile


    fun getProfile() {
        launcher(
            invoke = {
                getPopularProfileUseCase()
            },
            responseResult = {
                val dataUI = it.toProfileUI()
                insertProfile(dataUI)
                profile.postValue(dataUI)
            },
            errorResponse = {
                //with this callback we can handle the error
                notifyError(it.first)
            }
        )
    }

    fun getLocalProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = profileDataRepository.getLastProfileCoroutine()
            data.doSuccess { profileDataComplete ->
                profileDataComplete?.let {
                    val profileData = profileDataComplete.profileEntity
                    val knownForListData = profileDataComplete.knownforList
                    profile.postValue(profileData.toProfileUI(knownForListData))
                }?: run {
                    notifyError("No hay data local disponible, sincronice")
                }
            }
            data.doError {
                notifyError("No hay data local disponible, sincronice")
            }
        }
    }

    private fun insertProfile(profileUI: ProfileUI) {
        viewModelScope.launch(Dispatchers.IO) {
            profileDataRepository.insertProfileWithKnownFor(
                profileEntity = profileUI.toProfileEntity(),
                knownforEntity = profileUI.list.toKnownForEntityList()
            )
        }
    }
}