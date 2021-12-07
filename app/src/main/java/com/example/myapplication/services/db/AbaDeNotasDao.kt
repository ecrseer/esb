package com.example.myapplication.services.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.domain.AbaDeNotas
import kotlinx.coroutines.flow.Flow

@Dao
interface AbaDeNotasDao {

    @Query("SELECT * FROM abadenotas")
      fun listar(): Flow<List<AbaDeNotas>>

      @Insert
      suspend fun adicionar(aba:AbaDeNotas)
}