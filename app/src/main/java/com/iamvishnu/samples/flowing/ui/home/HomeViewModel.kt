package com.iamvishnu.samples.flowing.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iamvishnu.samples.flowing.data.FakeDataRepository
import com.iamvishnu.samples.flowing.data.model.DummyPost
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repo = FakeDataRepository()

    private val _itemsList = mutableStateListOf<DummyPost>()
    val itemsList: List<DummyPost> = _itemsList

    private val _state = mutableStateOf<HomeUiState>(HomeUiState.None)
    val state: State<HomeUiState> = _state

    init {

        viewModelScope.launch {

            // Updates coming from other screens
            repo.updatesFlow.collect { newPost ->

                println("New: $newPost")

                if (newPost == null) return@collect

                _itemsList.find { it.id == newPost.id }?.let {

                    val index = _itemsList.indexOf(it)

                    _itemsList[index] = newPost

                }

            }
        }

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

        viewModelScope.launch {
            repo.addLike(postId)
        }

    }

    fun sharePost(postId: String) {

        viewModelScope.launch {
            repo.addShare(postId)
        }

    }

}

sealed class HomeUiState {
    object None : HomeUiState()
    object Loading : HomeUiState()
    object Success : HomeUiState()
}