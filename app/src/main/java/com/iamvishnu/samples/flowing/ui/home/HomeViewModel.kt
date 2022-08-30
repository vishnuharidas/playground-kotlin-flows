package com.iamvishnu.samples.flowing.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iamvishnu.samples.flowing.data.FakeDataRepository
import com.iamvishnu.samples.flowing.data.model.DummyPost
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repo = FakeDataRepository.repository

    private val _itemsList = mutableStateListOf<DummyPost>()
    val itemsList: List<DummyPost> = _itemsList

    private val _state = mutableStateOf<HomeUiState>(HomeUiState.None)
    val state: State<HomeUiState> = _state

    init {

        // Updates coming from other screens
        repo.updatesFlow
            .onEach { newPost ->

                if (newPost == null) return@onEach

                _itemsList.find { it.id == newPost.id }?.let {

                    val index = _itemsList.indexOf(it)

                    _itemsList[index] = newPost

                }
            }
            .catch {
                _state.value = HomeUiState.Error(it.message ?: "Unknown error")
            }
            .launchIn(viewModelScope)

        fetchData()

    }

    private fun fetchData() {

        _state.value = HomeUiState.Loading

        viewModelScope.launch {

            delay(1000) // Fake delay

            repo.generateData().collect {

                println("Data collected: $it")

                _itemsList.add(it)

                _state.value = HomeUiState.Success

            }
        }

    }

    fun likePost(postId: String) {

        repo.addLike(postId)

    }

    fun sharePost(postId: String) {

        repo.addShare(postId)

    }

}

sealed class HomeUiState {
    object None : HomeUiState()
    object Loading : HomeUiState()
    object Success : HomeUiState()
    data class Error(val error: String) : HomeUiState()
}