package com.iamvishnu.samples.flowing.data

import com.iamvishnu.samples.flowing.data.model.DummyPost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class FakeDataRepository {

    companion object {
        val repository = FakeDataRepository()
    }

    private val list = mutableListOf<DummyPost>()

    private val _updates = MutableStateFlow<DummyPost?>(null)
    val updatesFlow = _updates.asStateFlow()


    fun generateData() = flow {

        (1..100).forEach {

            val post = DummyPost("post_$it", "Post $it", 0, 0)

            list.add(post)

            emit(post)

            delay(500)
        }
    }

    fun getPost(id: String) = flow {
        val item = list.find { it.id == id }

        if (item != null) {
            emit(item)
        } else {
            throw Exception("Unable to find the item!!")
        }
    }

    fun addLike(id: String) {

        CoroutineScope(Dispatchers.IO).launch {

            list.find { it.id == id }?.let {

                val index = list.indexOf(it)

                list[index] = it.copy(likes = it.likes + 1)

                // 1 update for all the listeners
                _updates.emit(list[index])

            }
        }
    }

    fun addShare(id: String) {

        CoroutineScope(Dispatchers.IO).launch {
            list.find { it.id == id }?.let {

                val index = list.indexOf(it)

                list[index] = it.copy(shares = it.shares + 1)

                // 1 update for all the listeners
                _updates.emit(list[index])
            }
        }
    }

}