package hr.algebra.nasa

import android.content.ContentUris
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import com.squareup.picasso.Picasso
import hr.algebra.nasa.adapter.UserPagerAdapter
import hr.algebra.nasa.databinding.ActivityUpdateEntityBinding
import hr.algebra.nasa.model.User
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File

class UpdateEntityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateEntityBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityUpdateEntityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getSerializableExtra("User") as User

        binding.tvFirstName.text=Editable.Factory.getInstance().newEditable(user.firstName)
        binding.tvLastName.text=Editable.Factory.getInstance().newEditable(user.lastName)
        binding.tvGender.text=Editable.Factory.getInstance().newEditable(user.gender)
        binding.tvEmail.text=Editable.Factory.getInstance().newEditable(user.email)
        binding.tvPhoneNumber.text=Editable.Factory.getInstance().newEditable(user.phoneNumber)
        binding.tvSocialInsuranceNumber.text=Editable.Factory.getInstance().newEditable(user.socialInsuranceNumber)

        Picasso.get()
            .load(File(user.avatar!!))
            .error(R.drawable.avatar_default)
            .transform(RoundedCornersTransformation(50, 5))
            .into(binding.ivAvatar)

        binding.bSave.setOnClickListener{
            val data = ContentValues().apply {
                put("firstName", binding.tvFirstName.text.toString())
                put("lastName", binding.tvLastName.text.toString())
                put("avatar",user.avatar)
                put("gender", binding.tvGender.text.toString())
                put("email", binding.tvEmail.text.toString())
                put("phoneNumber", binding.tvPhoneNumber.text.toString())
                put("socialInsuranceNumber", binding.tvSocialInsuranceNumber.text.toString())

            }
            val id = user.id
            if (id == -1L) return@setOnClickListener
            val uri = ContentUris.withAppendedId(USERS_PROVIDER_CONTENT_URI, id!!)
            contentResolver.update(uri, data, null, null)

            val intent = Intent(this, UserPagerAdapter::class.java)
            startActivity(intent)
            finish()
        }
    }
}