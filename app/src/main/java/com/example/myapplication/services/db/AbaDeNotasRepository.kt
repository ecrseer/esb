package com.example.myapplication.services.db

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.myapplication.domain.AbaDeNotas
import com.example.myapplication.domain.AbaDeNotasImagemNota
import com.example.myapplication.domain.AbaDeNotasWithImagemNotas
import com.example.myapplication.domain.ImagemNota
import com.example.myapplication.services.db.AbaNotasRelacao.AbaDeNotasImagemNotaDao
import com.example.myapplication.services.db.AbaNotasRelacao.AbaDeNotasWithImagemNotasDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

 class AbaDeNotasRepository(
     private val daoAbaDeNotas: AbaDeNotasDao,
     private val daoAbaDeNotasWithImagemNotasDao: AbaDeNotasWithImagemNotasDao,
     private val daoAbaDeNotasImagemNotaDao: AbaDeNotasImagemNotaDao,
     private val daoImageNota:ImagemNotaDao
    ) {


        suspend fun criarAbaNova(abaDeNotas: AbaDeNotas) {
            return daoAbaDeNotas.adicionar(abaDeNotas)

        }

        fun listarTodasAbasLiveData(): Flow<List<AbaDeNotas>> {
            return daoAbaDeNotas.listar()

        }


         fun listaAbaDeNotasComImagemNotas(): Flow<List<AbaDeNotasWithImagemNotas>> {
             return daoAbaDeNotasWithImagemNotasDao.getAbaDeNotasWithImagemNotas()
         }
          fun abaDeNotasComImagemNotasByIdAba(idAba: Int): Flow<AbaDeNotasWithImagemNotas> {
            return daoAbaDeNotasWithImagemNotasDao.getAbaDeNotasWithImagemNotasById(idAba);
        }


         suspend fun adicionarRelacaoAbaNota(abaEnota: AbaDeNotasImagemNota){
               return daoAbaDeNotasImagemNotaDao.insert(abaEnota)

         }

        suspend fun criarNotaNova(idAba:Int,imagemNota: ImagemNota){
            val abaEnota = AbaDeNotasImagemNota(imagemNota.idNota,idAba)
            daoImageNota.inserir(imagemNota)
            daoAbaDeNotasImagemNotaDao.insert(abaEnota)
        }



    }

