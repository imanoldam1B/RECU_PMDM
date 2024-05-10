package com.example.prueba_curro

import android.content.ContentValues.TAG
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.prueba_curro.Adaptador.SqliteRvAdapter
import com.example.prueba_curro.Models.Dao.UserDao
import com.example.prueba_curro.Models.Database.LocalDatabase
import com.example.prueba_curro.databinding.ActivityFavoritosBinding
import com.example.prueba_curro.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritosActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var adapter: SqliteRvAdapter
    private lateinit var userDao: UserDao
    private lateinit var binding: ActivityFavoritosBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar View Binding
        binding = ActivityFavoritosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.selectedItemId = R.id.favoritos

        // Inicializar el adaptador
        adapter = SqliteRvAdapter(this)
        val recyclerView: RecyclerView = binding.rvFavs
        recyclerView.adapter = adapter

        // Obtener la instancia de UserDao
        userDao = Room.databaseBuilder(applicationContext, LocalDatabase::class.java, "RickYMorty.db")
            .fallbackToDestructiveMigration()
            .build()
            .userDao()

        // Obtener los usuarios de la base de datos y establecerlos en el adaptador
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val users = withContext(Dispatchers.IO) {
                    userDao.getAll()
                }
                adapter.setUserList(users)
                recyclerView.layoutManager = LinearLayoutManager(this@FavoritosActivity)
            } catch (e: Exception) {
                // Manejar la excepción adecuadamente, por ejemplo, mostrar un mensaje de error
                Log.e(TAG, "Error al obtener usuarios de la base de datos: ${e.message}")
            }
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                    true
                }
                R.id.buscar -> {
                    startActivity(Intent(applicationContext, BuscarActivity::class.java))
                    finish()
                    true
                }
                R.id.favoritos -> true
                else -> false
            }
        }
    }
}