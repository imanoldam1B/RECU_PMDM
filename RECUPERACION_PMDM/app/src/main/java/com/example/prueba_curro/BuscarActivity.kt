package com.example.prueba_curro

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prueba_curro.Adaptador.RvAdapter
import com.example.prueba_curro.Models.Database.LocalDatabase
import com.example.prueba_curro.Models.Entity.User
import com.example.prueba_curro.Models.UsersItem
import com.example.prueba_curro.Utils.RetrofitInstance
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class BuscarActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var rvAdapter: RvAdapter
    private lateinit var usersList: List<UsersItem>
    private lateinit var rvMain:RecyclerView

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar)
        rvMain = findViewById(R.id.rvMain)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.buscar

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                    true
                }
                R.id.buscar -> true
                R.id.favoritos -> {
                    startActivity(Intent(applicationContext, FavoritosActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }

        }

        usersList = listOf()

        //Corrutinas
        GlobalScope.launch (Dispatchers.IO){
            val response = try {
                RetrofitInstance.api.getAllUsers(6)
            }catch (e:IOException){
                Toast.makeText(applicationContext, "app error ${e.message}", Toast.LENGTH_LONG).show()
                return@launch
            }catch (e: HttpException){
                Toast.makeText(applicationContext, "app error ${e.message}", Toast.LENGTH_LONG).show()
                return@launch
            }
            if (response.isSuccessful && response.body() != null){
                withContext(Dispatchers.Main){
                    usersList = response.body()?.results!!
                    rvMain.apply {
                        rvAdapter = RvAdapter(usersList, this@BuscarActivity)
                        adapter = rvAdapter
                        layoutManager = LinearLayoutManager(this@BuscarActivity)
                    }
                }
            }
        }
    }

}