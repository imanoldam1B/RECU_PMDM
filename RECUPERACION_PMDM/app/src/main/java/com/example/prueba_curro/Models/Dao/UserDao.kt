package com.example.prueba_curro.Models.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.prueba_curro.Models.Entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>


    @Insert
    fun insert(vararg users:User)

    @Delete
    fun delete(user:User)

}