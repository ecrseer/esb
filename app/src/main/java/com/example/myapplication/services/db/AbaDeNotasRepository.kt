package com.example.myapplication.services.db

import androidx.lifecycle.LiveData
import com.example.myapplication.domain.AbaDeNotas
import com.example.myapplication.domain.AbaDeNotasImagemNota
import com.example.myapplication.domain.AbaDeNotasWithImagemNotas
import com.example.myapplication.domain.ImagemNota
import com.example.myapplication.services.db.AbaNotasRelacao.AbaDeNotasImagemNotaDao
import com.example.myapplication.services.db.AbaNotasRelacao.AbaDeNotasWithImagemNotasDao
import kotlinx.coroutines.flow.Flow

class AbaDeNotasRepository(
    private val daoAbaDeNotas: AbaDeNotasDao,
    private val daoAbaDeNotasWithImagemNotasDao: AbaDeNotasWithImagemNotasDao,
    private val daoAbaDeNotasImagemNotaDao: AbaDeNotasImagemNotaDao,
    private val daoImageNota: ImagemNotaDao
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

    fun abaDeNotasComImagemNotasByIdAba(idAba: Int): LiveData<List<AbaDeNotasWithImagemNotas>> {
        return daoAbaDeNotasWithImagemNotasDao.getAbaDeNotasWithImagemNotasById(idAba);
    }
    fun listaDaAbaByIdAba(idAba: Int): LiveData<List<ImagemNota>> {
        return daoAbaDeNotasWithImagemNotasDao.listaDaAbaById(idAba);
    }

    suspend fun adicionarRelacaoAbaNota(abaEnota: AbaDeNotasImagemNota): Long {
        return daoAbaDeNotasImagemNotaDao.insert(abaEnota)

    }


    suspend fun criarNotaNova(idAba: Int, imagemNota: ImagemNota): Long {
        val notanova = daoImageNota.inserir(imagemNota)
        val abaEnota = AbaDeNotasImagemNota(notanova, idAba)
        return daoAbaDeNotasImagemNotaDao.insert(abaEnota)
    }


}

