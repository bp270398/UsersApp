package hr.algebra.nasa.adapter

import android.app.Activity
import android.content.*
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.nasa.HostActivity
import hr.algebra.nasa.R
import hr.algebra.nasa.USERS_PROVIDER_CONTENT_URI
import hr.algebra.nasa.UpdateEntityActivity
import hr.algebra.nasa.model.User
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File


class UserPagerAdapter(
    public val context: Context,
    private val users: MutableList<User>
    ) : RecyclerView.Adapter<UserPagerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //val ivRead = itemView.findViewById<ImageView>(R.id.ivRead)

        private val ivAvatar = itemView.findViewById<ImageView>(R.id.ivAvatar)
        private val tvUsername = itemView.findViewById<TextView>(R.id.tvUsername)
        private val tvDateOfBirth = itemView.findViewById<TextView>(R.id.tvDateOfBirth)
        private val tvName = itemView.findViewById<TextView>(R.id.tvName)
        private val tvGender = itemView.findViewById<TextView>(R.id.tvGender)
        private val tvEmail = itemView.findViewById<TextView>(R.id.tvEmail)
        private val tvPhoneNumber = itemView.findViewById<TextView>(R.id.tvPhoneNumber)
        private val tvSocialInsuranceNumber = itemView.findViewById<TextView>(R.id.tvSocialInsuranceNumber)

        val bEdit = itemView.findViewById<TextView>(R.id.bEdit)
        val context : Context = this.itemView.context

        fun bind(user: User) {

            Picasso.get()
                .load(File(user.avatar!!))
                .error(R.drawable.avatar_default)
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivAvatar)

            tvUsername.text = user.username
            tvDateOfBirth.text = user.dateOfBirth
            tvName.text = buildString {this.append(user.firstName).append(" ").append(user.lastName) }
            tvGender.text = user.gender
            tvEmail.text = user.email
            tvPhoneNumber.text = user.phoneNumber
            tvSocialInsuranceNumber.text = user.socialInsuranceNumber
            //ivRead.setImageResource(if (user.read) R.drawable.green_flag else R.drawable.red_flag)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                this.context).inflate(R.layout.user_pager,
                parent,
                false
            ),
        )
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]

        /*
        holder.ivRead.setOnClickListener {
            // update
            user.read = !user.read

            context.contentResolver.update(
                ContentUris.withAppendedId(USERS_PROVIDER_CONTENT_URI,user.id!!),
                ContentValues().apply {
                    put(User::read.name, user.read)
                },
                null, null
            )
            notifyItemChanged(position)
        }
         */

        holder.bEdit.setOnClickListener {
            val intent = Intent(context, UpdateEntityActivity::class.java)
            intent.putExtra("User", user)
            context.startActivity(intent)
        }

        holder.bind(user)
    }

    override fun getItemCount() = users.size



}