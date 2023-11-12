package hr.algebra.nasa.adapter

import android.content.ContentUris
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.nasa.POSITION
import hr.algebra.nasa.R
import hr.algebra.nasa.UserPagerActivity
import hr.algebra.nasa.dao.UsersSqlHelper
import hr.algebra.nasa.framework.startActivity
import hr.algebra.nasa.model.User
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File

class UserAdapter(
    private val context: Context,
    private val users: MutableList<User>
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(userView: View) : RecyclerView.ViewHolder(userView) {

        private val ivAvatar = userView.findViewById<ImageView>(R.id.ivAvatar)
        private val tvUserInfo = userView.findViewById<TextView>(R.id.tvUserInfo)

        fun bind(user: User) {
            Picasso.get()
                .load(File(user.avatar!!))
                .error(R.drawable.avatar_default)
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivAvatar)
            tvUserInfo.text = buildString {this.append("${user.firstName}  ${user.lastName}")}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.user, parent, false)
        )
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]

        holder.itemView.setOnLongClickListener {
            deleteItem(position)
            true
        }
        holder.itemView.setOnClickListener {
            context.startActivity<UserPagerActivity>(POSITION, position)
        }

        holder.bind(user)
    }
    private fun deleteItem(position: Int) {
        val user = users[position]
        val selection = "${User::id.name}=?"
        val selectionArgs = arrayOf(user.id!!.toString())
        context.applicationContext.let {
            val sqlHelper = UsersSqlHelper(it)
            sqlHelper.delete(selection, selectionArgs)
        }
        //File(user.avatar!!).delete()
        users.removeAt(position)
        notifyDataSetChanged()
    }

}