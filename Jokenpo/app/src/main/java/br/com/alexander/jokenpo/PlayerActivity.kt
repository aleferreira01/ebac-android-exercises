package br.com.alexander.jokenpo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import br.com.alexander.jokenpo.databinding.ActivityPlayerBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class PlayerActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var navDrawer: NavigationView
    private lateinit var spinner: Spinner
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var resultActivityIntent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityPlayerBinding.inflate(layoutInflater)

        drawer = binding.root
        navDrawer = binding.navLayout.navView
        spinner = binding.movesSpinner
        bottomNav = binding.bottomLayout.bottomNav
        bottomNav.menu.findItem(R.id.bottom_player).isEnabled = false

        resultActivityIntent = Intent(this, ResultActivity::class.java)

        setContentView(drawer)
        setSupportActionBar(binding.toolbarLayout.toolbar)

        supportActionBar?.title = ""

        setupDrawer()
        setupToolBar()
        setupSpinner()
        setupBottomNavigation()
    }

    private fun setupDrawer() {

        navDrawer.menu.findItem(R.id.drawer_player).isEnabled = false

        navDrawer.setNavigationItemSelectedListener { menuItem ->
            drawer.closeDrawers()
            when (menuItem.itemId) {

                R.id.drawer_result -> {
                    startActivity(resultActivityIntent)
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

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.moves_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

    }

    private fun setupBottomNavigation() {

        bottomNav.menu.findItem(R.id.bottom_result).setChecked(true)

        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.bottom_result -> {
                    startActivity(resultActivityIntent)
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
                finish()
                true
            }

            else -> false
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        Snackbar.make(drawer, parent?.getItemAtPosition(pos).toString(), Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

}