package su.j2e.recipes

import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("api/")
    suspend fun getRecipes(@Query("q") query: String): RecipesResponse

    data class RecipesResponse(val results: List<RecipeData>)

    data class RecipeData(
        val title: String,
        val href: String,
        val ingredients: String,
        val thumbnail: String
    )
}