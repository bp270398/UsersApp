package hr.algebra.nasa

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import hr.algebra.nasa.api.UsersFetcher

private const val JOB_ID = 1
@Suppress("DEPRECATION")
class UsersService : JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        // balkan fake work
        UsersFetcher(this).fetchUsers(10)
    }
    companion object {
        fun enqueue(context: Context) {
            enqueueWork(context, UsersService::class.java, JOB_ID,
                Intent(context, UsersService::class.java))
        }
    }
}