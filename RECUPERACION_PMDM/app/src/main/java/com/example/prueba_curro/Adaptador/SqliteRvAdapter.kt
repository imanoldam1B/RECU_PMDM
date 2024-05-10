package com.example.prueba_curro.Adaptador

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import coil.load
import com.example.prueba_curro.Models.Database.LocalDatabase
import com.example.prueba_curro.Models.Entity.User
import com.example.prueba_curro.R
import com.example.prueba_curro.databinding.SqliteItemLayoutBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SqliteRvAdapter(private val context: Context) : RecyclerView.Adapter<SqliteRvAdapter.ViewHolder>() {
    private var userList: List<User> = emptyList()

    fun setUserList(users: List<User>) {
        this.userList = users
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SqliteItemLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
        holder.binding.btnDelete.setOnClickListener {
            deleteUser(holder.adapterPosition)
        }
    }

    private fun deleteUser(position: Int) {
        val userDao = Room.databaseBuilder(context, LocalDatabase::class.java, "RickYMorty.db")
            .fallbackToDestructiveMigration()
            .build()
            .userDao()

        CoroutineScope(Dispatchers.IO).launch {
            userDao.delete(userList[position])
            withContext(Dispatchers.Main) {
                userList = userList.filterIndexed { index, _ -> index != position }
                notifyItemRemoved(position)
            }
        }
    }

    inner class ViewHolder(val binding: SqliteItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.apply {
                // Cargar la imagen desde la URL usando Coil
                imageview.load(user.imageLink) {
                    crossfade(true)
                    crossfade(5000)
                }
                txtNombre.text = user.nombre
                txtEspecie.text = user.especie
                txtGenero.text = user.genero
                txtTipo.text = user.tipo

                root.setOnClickListener {
                    Toast.makeText(context, "${user.nombre}, ${user.especie}, ${user.genero}, ${user.tipo}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
