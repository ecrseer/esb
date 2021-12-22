package com.example.myapplication.services.db.AbaNotasRelacao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.myapplication.domain.AbaDeNotasImagemNota

@Dao
interface AbaDeNotasImagemNotaDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(abaComNotas: AbaDeNotasImagemNota):Long
}