package su.j2e.recipes

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException

object Repo {

    private val api = Retrofit.Builder()
        .baseUrl("http://www.recipepuppy.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(Api::class.java)

    suspend fun getRecipes(query: String): Result<List<Recipe>> =
        try {
            Result.Success(api.getRecipes(query).results.map {
                Recipe(it.title, it.thumbnail, it.ingredients.split(", "), it.href)
            })
        } catch (ex: IOException) {
            Result.Error(ex)
        }
}

data class Recipe(
    val title: String,
    val img: String,
    val ingredients: List<String>,
    val url: String
)

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val ex: Throwable? = null) : Result<T>()
}