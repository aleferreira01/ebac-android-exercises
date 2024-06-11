package br.com.alexander.jokenpo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alexander.jokenpo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        val toolbar = binding.toolbar

        setContentView(binding.root)
        setSupportActionBar(toolbar)

        supportActionBar?.title = ""

        val playerIntent = Intent(this, PlayerActivity::class.java)

        binding.startButton.setOnClickListener {
            startActivity(playerIntent)
        }

    }


}