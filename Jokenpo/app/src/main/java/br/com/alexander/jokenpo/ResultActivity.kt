package br.com.alexander.jokenpo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import br.com.alexander.jokenpo.databinding.ActivityResultBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class ResultActivity : AppCompatActivity() {

    private lateinit var drawer: DrawerLayout
    private lateinit var navDrawer: NavigationView
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var mainActivityIntent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityResultBinding.inflate(layoutInflater)

        drawer = binding.root
        navDrawer = binding.navLayout.navView
        bottomNav = binding.bottomLayout.bottomNav
        bottomNav.menu.findItem(R.id.bottom_result).isEnabled = false

        mainActivityIntent = Intent(this, HomeFragment::class.java)
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        setContentView(drawer)
        setSupportActionBar(binding.toolbarLayout.toolbar)

        supportActionBar?.title = ""

        setupToolBar()
        setupDrawer()
        setupBottomNavigation()

    }

    private fun setupDrawer() {

        navDrawer.menu.findItem(R.id.drawer_result).isEnabled = false

        navDrawer.setNavigationItemSelectedListener { menuItem ->
            drawer.closeDrawers()

            when (menuItem.itemId) {
                R.id.drawer_player -> {
                    finish()
                    true
                }

                else -> false
            }
        }
    }

    private fun setupToolBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    private fun setupBottomNavigation() {

        bottomNav.menu.findItem(R.id.bottom_player).setChecked(true)

        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_player -> {
                    finish()
                    true
                }

                else -> false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        drawer.openDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.player_top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.restart_game -> {
                startActivity(mainActivityIntent)
                true
            }

            else -> false
        }
    }
}