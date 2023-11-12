package hr.algebra.nasa.api

import hr.algebra.nasa.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val RDG_API_BASE_URL ="https://random-data-api.com/api/v2/"
interface UsersApi {
    @GET("users?size=20")
    fun fetchUsers(@Query("count") count:Int): Call<List<User>>
}