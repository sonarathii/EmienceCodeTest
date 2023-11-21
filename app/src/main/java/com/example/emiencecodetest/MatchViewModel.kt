package com.example.emiencecodetest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emiencecodetest.api.ApiRepository
import com.example.emiencecodetest.base.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MatchViewModel @Inject constructor
    (
    private val apiRepository: ApiRepository

) : ViewModel() {

    private val _response: MutableLiveData<NetworkResult<MatchResponse>> = MutableLiveData()
    val response: LiveData<NetworkResult<MatchResponse>> = _response


    fun vehicleCallApi() = viewModelScope.launch {
        _response.value= NetworkResult.Loading()
        apiRepository.matchDetails().collect {
            _response.value = it
        }
    }
}