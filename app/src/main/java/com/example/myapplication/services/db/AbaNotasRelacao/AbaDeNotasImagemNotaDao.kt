package com.example.myapplication.services.db.AbaNotasRelacao

import androidx.room.Dao
import androidx.room.Insert
import com.example.myapplication.domain.AbaDeNotasImagemNota

@Dao
interface AbaDeNotasImagemNotaDao {

    @Insert
    suspend fun insert(abaComNotas: AbaDeNotasImagemNota)
}