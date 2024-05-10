package com.example.prueba_curro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var btnBuscar:Button
    private lateinit var btnFavoritos:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.home

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> true
                R.id.buscar -> {
                    startActivity(Intent(applicationContext, BuscarActivity::class.java))
                    finish()
                    true
                }
                R.id.favoritos -> {
                    startActivity(Intent(applicationContext, FavoritosActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }

        btnBuscar = findViewById(R.id.btnBuscar)
        btnBuscar.setOnClickListener {
            val intent = Intent(this, BuscarActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnFavoritos = findViewById(R.id.btnFavoritos)
        btnFavoritos.setOnClickListener {
            val intent = Intent(this, FavoritosActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}