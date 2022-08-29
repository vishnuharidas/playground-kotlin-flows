package com.iamvishnu.samples.flowing.ui.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iamvishnu.samples.flowing.data.FakeDataRepository
import com.iamvishnu.samples.flowing.data.model.DummyPost
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    private val repo = FakeDataRepository.repository

    private val _state = mutableStateOf(DetailsUiState(loading = true))
    val state: State<DetailsUiState> = _state

    init {

        repo.updatesFlow
            .onEach {
                _state.value = _state.value.copy(
                    loading = false,
                    error = null,
                    data = it
                )
            }
            .catch {
                _state.value = _state.value.copy(
                    loading = false,
                    error = it.message,
                    data = null
                )
            }
            .launchIn(viewModelScope)

    }

    fun getPost(postId: String) {

        _state.value = _state.value.copy(
            loading = true,
            error = null,
            data = null
        )

        repo.getPost(postId)
            .onEach {
                _state.value = _state.value.copy(
                    loading = false,
                    error = null,
                    data = it
                )
            }
            .catch {
                _state.value = _state.value.copy(
                    loading = false,
                    error = it.message,
                    data = null
                )
            }
            .launchIn(viewModelScope)

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

data class DetailsUiState(
    val loading: Boolean = true,
    val error: String? = null,
    val data: DummyPost? = null,
)