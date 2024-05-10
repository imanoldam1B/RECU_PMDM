package com.example.prueba_curro.Models.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val nombre: String,
    @ColumnInfo(name = "ESPECIE") val especie: String?,
    @ColumnInfo(name = "GENERO") val genero: String?,
    @ColumnInfo(name = "TIPO") val tipo: String?,
    @ColumnInfo(name = "IMAGE_LINK") val imageLink: String? // Campo para la URL de la imagen
)
