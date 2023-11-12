package hr.algebra.nasa

import android.R
import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import hr.algebra.nasa.R.*
import hr.algebra.nasa.R.drawable.ic_menu
import hr.algebra.nasa.R.menu.host_menu
import hr.algebra.nasa.databinding.ActivityHostBinding


class HostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initHamburgerMenu()
        initNavigation()
    }

    companion object MyDialogBuilder{
        fun showDialog(
            context: Context?,
            view: View?,
            okButtonText: String,
            dialogTitle: String,
            dialogMessage: String,
            okListener: OnClickListener
        ): AlertDialog? {
            val builder: AlertDialog.Builder? = this?.let {
                AlertDialog.Builder(context!!)
            }
            builder?.apply {
                setPositiveButton(okButtonText, okListener)
                setNegativeButton(R.string.cancel
                ) { dialog, id -> dialog.cancel() }

                setTitle(dialogTitle)
                setMessage(dialogMessage)
            }
            return builder?.create()
        }
    }


    private fun initHamburgerMenu() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(ic_menu)
    }

    private fun initNavigation() {
        val navController = Navigation.findNavController(this, id.navController)
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(host_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                toggleDrawer()
                return true
            }
            id.menuExit -> {
                exitApp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun exitApp() {
        AlertDialog.Builder(this).apply {
            setTitle(string.exit)
            setIcon(drawable.exit)
            setMessage(getString(string.really))
            setCancelable(true)
            setPositiveButton("Ok") { _, _ -> finish() }
            setNegativeButton(getString(string.cancle), null)
            show()
        }
    }

    private fun toggleDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawers()
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}