package hr.algebra.nasa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hr.algebra.nasa.adapter.UserPagerAdapter
import hr.algebra.nasa.databinding.ActivityUserPagerBinding
import hr.algebra.nasa.framework.fetchUsers
import hr.algebra.nasa.model.User

const val POSITION = "hr.algebra.nasa.position"
class UserPagerActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityUserPagerBinding
    private lateinit var items: MutableList<User>
    private var itemPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPager()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
    
    private fun initPager() {
        items = fetchUsers()
        itemPosition = intent.getIntExtra(POSITION, itemPosition)
        binding.viewPager.adapter = UserPagerAdapter(this, items)
        binding.viewPager.currentItem = itemPosition
    }

}