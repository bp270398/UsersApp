package hr.algebra.nasa.api

import android.content.ContentValues
import android.content.Context
import android.util.Log
import hr.algebra.nasa.USERS_PROVIDER_CONTENT_URI
import hr.algebra.nasa.UsersReceiver
import hr.algebra.nasa.framework.sendBroadcast
import hr.algebra.nasa.handler.downloadImageAndStore
import hr.algebra.nasa.model.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UsersFetcher(private val context: Context) {
    private var usersApi:UsersApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(RDG_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        usersApi = retrofit.create(UsersApi::class.java)
    }
    fun fetchUsers(count: Int){
        val request = usersApi.fetchUsers(count)

        request.enqueue(object : Callback<List<User>>{
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                // vratio se u glavnu dretvu
                response.body()?.let { populateUsers(it) }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e(javaClass.name,t.toString(),t)
            }
        })
    }
    private fun populateUsers(users: List<User>) {
        GlobalScope.launch {
            users.forEach{
                var picturePath = downloadImageAndStore(context, it.avatar!!)
                val values = ContentValues().apply {
                    put(User::id.name, it.id)
                    put(User::uid.name, it.uid)
                    put(User::password.name, it.password)
                    put(User::firstName.name, it.firstName)
                    put(User::lastName.name, it.lastName)
                    put(User::username.name, it.username)
                    put(User::email.name, it.email)
                    put(User::avatar.name, picturePath.toString())
                    put(User::gender.name, it.gender)
                    put(User::phoneNumber.name, it.phoneNumber)
                    put(User::socialInsuranceNumber.name, it.socialInsuranceNumber)
                    put(User::dateOfBirth.name, it.dateOfBirth)
                }
                context.contentResolver.insert(USERS_PROVIDER_CONTENT_URI, values)
            }
            context.sendBroadcast<UsersReceiver>()
        }
    }
}