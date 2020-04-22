package su.j2e.recipes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

interface MainViewModel {
    val recipes: LiveData<List<Recipe>>
    val loading: LiveData<Boolean>
    val error: LiveEvent<String>
    fun loadRecipes(query: String)
}

class MainViewModelImp(private val app: Application) : AndroidViewModel(app), MainViewModel {

    override val recipes = MutableLiveData<List<Recipe>>()
    override val loading = MutableLiveData(false)
    override val error = MutableLiveEvent<String>()

    init {
        loadRecipes("")
    }

    override fun loadRecipes(query: String) {
        if (loading.value == true) return

        loading.value = true
        viewModelScope.launch {
            when (val result = Repo.getRecipes(query)) {
                is Result.Success -> {
                    recipes.value = result.data
                }
                is Result.Error -> {
                    if (recipes.value == null) {
                        recipes.value = emptyList()
                    }
                    error.send(app.getString(R.string.network_error))
                }
            }
            loading.value = false
        }
    }
}
