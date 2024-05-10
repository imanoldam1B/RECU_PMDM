package com.example.prueba_curro.Adaptador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.prueba_curro.Models.Database.LocalDatabase
import com.example.prueba_curro.Models.Entity.User
import com.example.prueba_curro.Models.UsersItem
import com.example.prueba_curro.databinding.ItemLayoutBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RvAdapter(private val userList: List<UsersItem>, private val context: Context) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.binding.apply {
            val imageLink = currentItem?.image
            //Se usa la corrutina coil aqui
            imageview.load(imageLink) {
                crossfade(true)
                crossfade(5000)
            }
            txtNombre.text = currentItem.name
            txtEspecie.text = currentItem.species
            txtGenero.text = currentItem.gender
            txtTipo.text = currentItem.type
            chkBoxStar.setOnClickListener {
                val usuario = User(currentItem.name, currentItem.species, currentItem.gender, currentItem.type, currentItem.image)
                val localdb = LocalDatabase.getInstance(context)
                GlobalScope.launch(Dispatchers.IO){
                    if (chkBoxStar.isChecked) {
                        localdb.userDao().insert(usuario)
                    }
                }
            }
        }
    }


}
