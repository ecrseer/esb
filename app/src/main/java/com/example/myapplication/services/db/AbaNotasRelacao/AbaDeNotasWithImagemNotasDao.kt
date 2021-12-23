package com.example.myapplication.services.db.AbaNotasRelacao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.myapplication.domain.AbaDeNotasWithImagemNotas
import com.example.myapplication.domain.ImagemNota
import kotlinx.coroutines.flow.Flow

@Dao
interface AbaDeNotasWithImagemNotasDao {

    @Transaction
    @Query("SELECT * FROM abadenotas")
    fun getAbaDeNotasWithImagemNotas(): Flow<List<AbaDeNotasWithImagemNotas>>

    @Transaction
    @Query("SELECT * FROM abadenotas WHERE idAba = :idA")
    fun getAbaDeNotasWithImagemNotasById(idA:Int): LiveData<List<AbaDeNotasWithImagemNotas>>

    @Transaction
    @Query("""
        SELECT * FROM imagemnota INNER JOIN abadenotasimagemnota ON
        imagemnota.idNota = abadenotasimagemnota.idNota WHERE
        abadenotasimagemnota.idAba = :idA
        """)
    fun listaDaAbaById(idA:Int): LiveData<List<ImagemNota>>
}
