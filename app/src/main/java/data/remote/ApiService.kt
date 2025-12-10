package data.remote


import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Respuesta paginada común de SWAPI
data class PagedResponse<T>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<T>
)

// Define aquí solo lo que necesitas de Person
data class Person(
    val name: String,
    val height: String,
    val mass: String,
    val hair_color: String,
    val skin_color: String,
    val eye_color: String,
    val birth_year: String,
    val gender: String,
    val url: String
)

interface ApiService {
    @GET("people/")
    suspend fun getPeople(@Query("page") page: Int = 1): PagedResponse<Person>

    @GET("people/{id}/")
    suspend fun getPerson(@Path("id") id: Int): Person

    @GET("films/")
    suspend fun getFilms(@Query("page") page: Int = 1): PagedResponse<Any>

    @GET("planets/")
    suspend fun getPlanets(@Query("page") page: Int = 1): PagedResponse<Any>

    @GET("starships/")
    suspend fun getStarships(@Query("page") page: Int = 1): PagedResponse<Any>

    @GET("vehicles/")
    suspend fun getVehicles(@Query("page") page: Int = 1): PagedResponse<Any>

    @GET("species/")
    suspend fun getSpecies(@Query("page") page: Int = 1): PagedResponse<Any>

    // SWAPI supports search, e.g. /people/?search=r2
    @GET("people/")
    suspend fun searchPeople(@Query("search") query: String): PagedResponse<Person>
}
