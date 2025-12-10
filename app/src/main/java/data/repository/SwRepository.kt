package data.repository



import data.remote.ApiService

class SwRepository(private val api: ApiService) {
    suspend fun getPeople(page: Int = 1) = api.getPeople(page)
    suspend fun getPerson(id: Int) = api.getPerson(id)
    suspend fun searchPeople(query: String) = api.searchPeople(query)
    // añadir demás wrappers para films, planets, etc.
}
