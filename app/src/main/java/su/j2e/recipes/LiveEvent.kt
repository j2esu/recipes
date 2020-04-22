package su.j2e.recipes

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe

typealias LiveEvent<T> = LiveData<Consumable<T>>
typealias MutableLiveEvent<T> = MutableLiveData<Consumable<T>>

data class Consumable<T>(val data: T) {

    private var consumed = false

    fun consume(consumer: (T) -> Unit) {
        if (!consumed) {
            consumed = true
            consumer(data)
        }
    }
}

fun <T> LiveEvent<T>.consume(lifecycleOwner: LifecycleOwner, consumer: (T) -> Unit) =
    observe(lifecycleOwner) { it.consume(consumer) }

fun <T> MutableLiveEvent<T>.send(item: T) = postValue(Consumable(item))