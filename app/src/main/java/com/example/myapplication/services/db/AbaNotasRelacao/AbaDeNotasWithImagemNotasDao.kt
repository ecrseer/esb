package com.example.myapplication.services.db.AbaNotasRelacao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.myapplication.domain.AbaDeNotasWithImagemNotas
import kotlinx.coroutines.flow.Flow

@Dao
interface AbaDeNotasWithImagemNotasDao {

    @Transaction
    @Query("SELECT * FROM abadenotas")
    fun getAbaDeNotasWithImagemNotas(): Flow<List<AbaDeNotasWithImagemNotas>>

    @Transaction
    @Query("SELECT * FROM abadenotas WHERE idAba = :idA")
    fun getAbaDeNotasWithImagemNotasById(idA:Int): Flow<List<AbaDeNotasWithImagemNotas>>

}
