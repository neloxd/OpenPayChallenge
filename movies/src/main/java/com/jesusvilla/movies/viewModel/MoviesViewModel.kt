package com.jesusvilla.movies.viewModel

import androidx.annotation.CheckResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jesusvilla.base.models.BaseUI
import com.jesusvilla.base.viewModel.BaseViewModel
import com.jesusvilla.database.di.qualifiers.MovieDBRepositoryQualifier
import com.jesusvilla.database.repository.MoviesDataRepository
import com.jesusvilla.database.resources.doError
import com.jesusvilla.database.resources.doSuccess
import com.jesusvilla.movies.di.GetMoviesUseCaseQualifier
import com.jesusvilla.movies.domain.mapper.baseDataItemResponseToMovieBaseUIList
import com.jesusvilla.movies.domain.mapper.toMovieBaseUIList
import com.jesusvilla.movies.domain.mapper.toMovieEntityList
import com.jesusvilla.movies.domain.useCase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    @GetMoviesUseCaseQualifier private val getMoviesUseCase: GetMoviesUseCase,
    @MovieDBRepositoryQualifier private val moviesDataRepository: MoviesDataRepository
) : BaseViewModel() {
    private val list = MutableLiveData<List<BaseUI>>()

    @CheckResult
    fun getBaseUIList(): LiveData<List<BaseUI>> = list


    fun getProfile() {
        launcher(
            invoke = {
                getMoviesUseCase()
            },
            responseResult = {
                val dataUI = it.baseDataItemResponseToMovieBaseUIList()
                dataUI?.let { result ->
                    if(result.isNotEmpty()) {
                        insertList(result)
                        list.postValue(result)
                    }
                }?: run {
                    notifyError("No hay data online disponible, sincronice")
                }
            },
            errorResponse = {
                //with this callback we can handle the error
                notifyError(it.first)
            }
        )
    }

    fun getLocalProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = moviesDataRepository.getMovieList()
            data.doSuccess { result ->
               if(result.isNotEmpty()) {
                   list.postValue(result.toMovieBaseUIList())
               } else {
                   notifyError("No hay data de peliculas local disponible, sincronice")
               }
            }
            data.doError {
                notifyError("No hay data local disponible, sincronice")
            }
        }
    }

    private fun insertList(list: List<BaseUI>) {
        viewModelScope.launch(Dispatchers.IO) {
            moviesDataRepository.insertMovies(list.toMovieEntityList())
        }
    }
}