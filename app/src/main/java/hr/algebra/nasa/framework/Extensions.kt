package hr.algebra.nasa.framework

import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.getSystemService
import androidx.preference.PreferenceManager
import hr.algebra.nasa.USERS_PROVIDER_CONTENT_URI
import hr.algebra.nasa.handler.downloadImageAndStore
import hr.algebra.nasa.model.*

fun View.applyAnimation(animationId: Int)
    = startAnimation(AnimationUtils.loadAnimation(context, animationId))


inline fun<reified T: Activity> Context.startActivity()
    = startActivity(
        Intent(this, T::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

inline fun<reified T: Activity> Context.startActivity(key: String, value: Int)
    = startActivity(
        Intent(this, T::class.java).apply {
                putExtra(key, value)
        }.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

inline fun<reified T: BroadcastReceiver> Context.sendBroadcast()
        = sendBroadcast(Intent(this, T::class.java))

fun Context.setBooleanPreference(key: String, value: Boolean = true) {
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putBoolean(key, value)
                .apply()
}

fun Context.getBooleanPreference(key: String) =
        PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(key, false)

fun Context.isOnline() : Boolean{
        val connectivityManager = getSystemService<ConnectivityManager>()
        connectivityManager?.activeNetwork?.let { network ->
                connectivityManager.getNetworkCapabilities(network)?.let { cap ->
                        return cap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                                || cap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                }
        }
        return false
}

fun callDelayed(delay: Long, runnable: Runnable) {
        Handler(Looper.getMainLooper()).postDelayed(
                runnable,
                delay
        )
}

@SuppressLint("Range")
fun Context.fetchUsers() : MutableList<User> {
        val items = mutableListOf<User>()
        val cursor = contentResolver.query(USERS_PROVIDER_CONTENT_URI,null, null, null, null)
        while (cursor != null && cursor.moveToNext()) {
                items.add(
                        User(
                        cursor.getLong(cursor.getColumnIndex(User::id.name)),
                        cursor.getString(cursor.getColumnIndex(User::uid.name)),
                        cursor.getString(cursor.getColumnIndex(User::password.name)),
                        cursor.getString(cursor.getColumnIndex(User::firstName.name)),
                        cursor.getString(cursor.getColumnIndex(User::lastName.name)),
                        cursor.getString(cursor.getColumnIndex(User::username.name)),
                        cursor.getString(cursor.getColumnIndex(User::email.name)),
                        cursor.getString(cursor.getColumnIndex(User::avatar.name)),
                        cursor.getString(cursor.getColumnIndex(User::gender.name)),
                        cursor.getString(cursor.getColumnIndex(User::phoneNumber.name)),
                        cursor.getString(cursor.getColumnIndex(User::socialInsuranceNumber.name)),
                        cursor.getString(cursor.getColumnIndex(User::dateOfBirth.name)),
                        //cursor.getInt(cursor.getColumnIndex(User::read.name)) == 1
                        )
                )
        }
        cursor?.close()
        return items
}