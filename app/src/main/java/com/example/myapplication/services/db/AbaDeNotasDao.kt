package com.example.myapplication.services.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.domain.AbaDeNotas

@Dao
interface AbaDeNotasDao {

    @Query("SELECT * FROM abadenotas")
      fun listar(): LiveData<List<AbaDeNotas>>

      @Insert
      fun adicionar(aba:AbaDeNotas)
}