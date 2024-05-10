package com.example.prueba_curro.Interface

import android.provider.SyncStateContract.Constants
import com.example.prueba_curro.Models.Users
import com.example.prueba_curro.Utils.Utils
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET(Utils.END_POINT)
    suspend fun getAllUsers(
        @Query("page") page:Int
    ): Response<Users>
}