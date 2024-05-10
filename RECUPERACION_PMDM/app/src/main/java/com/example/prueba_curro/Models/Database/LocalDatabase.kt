package com.example.prueba_curro.Models.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.prueba_curro.Models.Dao.UserDao
import com.example.prueba_curro.Models.Entity.User
import kotlin.concurrent.Volatile

@Database(entities = [User::class], version = 2)
abstract class LocalDatabase : RoomDatabase(){
    abstract fun userDao():UserDao

    companion object{
        private const val DATABASE_NAME = "RickYMorty.db"

        @Volatile
        private var INSTANCE:LocalDatabase? = null

        fun getInstance(context:Context):LocalDatabase {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    //Abrir la conexion
                    instance = Room.databaseBuilder(context.applicationContext, LocalDatabase::class.java, DATABASE_NAME).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}