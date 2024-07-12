package hr.algebra.schengenexplorer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import hr.algebra.schengenexplorer.R
import hr.algebra.schengenexplorer.databinding.ActivityHostBinding
import kotlin.system.exitProcess

class HostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initHamburgerMenu()
        initNavigation()
    }

    private fun initNavigation() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

    }

    private fun initHamburgerMenu() {
        val navController = Navigation.findNavController(this,R.id.navController)
        NavigationUI.setupWithNavController(binding.navView,navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.host_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                toggleDrawer()
                return true
            }R.id.menuExit->{
                exitApp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun exitApp() {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.exit)
            setMessage("Are you sure that you want to exit from the app?")
            setIcon(R.drawable.exit)
            setCancelable(true)
            setNegativeButton("Cancel",null)
            setPositiveButton("Ok"){_,_->finish() }
            show()
        }
    }

    private fun toggleDrawer() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawers()

        }else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}