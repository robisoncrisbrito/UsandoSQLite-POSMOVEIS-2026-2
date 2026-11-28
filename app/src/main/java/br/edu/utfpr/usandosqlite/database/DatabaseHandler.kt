package br.edu.utfpr.usandosqlite.database

import android.content.Context
import br.edu.utfpr.usandosqlite.entity.Cadastro
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class DatabaseHandler( context: Context) {

    private val banco = Firebase.firestore

    suspend fun inserir( cadastro: Cadastro) {

        val registro = hashMapOf(
            "nome" to cadastro.nome,
            "telefone" to cadastro.telefone
        )

        banco.collection("cadastro")
            .document(cadastro.id.toString())
            .set(registro)
            .await()
    }

    suspend fun alterar( cadastro: Cadastro ) {
        val registro = hashMapOf(
            "nome" to cadastro.nome,
            "telefone" to cadastro.telefone
        )

        banco.collection("cadastro")
            .document(cadastro.id.toString())
            .set(registro)
            .await()
    }

    suspend fun excluir( id: Int ) {
        banco.collection("cadastro")
            .document(id.toString())
            .delete()
            .await()

    }

    suspend fun pesquisar( id: Int ): Cadastro? {
        val document = banco.collection("cadastro")
            .document(id.toString())
            .get()
            .await()

        if ( document.exists() ) {
            val cadastro = Cadastro(
                document.id.toInt(),
                document.data?.get("nome").toString(),
                document.data?.get("telefone").toString()
            )
            return cadastro
        } else {
            return null
        }

    }

    suspend fun listar(): MutableList<Cadastro> {
        val documentos = banco.collection("cadastro")
            .get()
            .await()

        val registros = mutableListOf<Cadastro>()

        for ( documento in documentos ) {
            val cadastro = Cadastro(
                documento.id.toInt(),
                documento.data?.get("nome").toString(),
                documento.data?.get("telefone").toString()
            )
            registros.add(cadastro)
        }

        return registros

    }

    suspend fun search(nome: String): MutableList<Cadastro> {
        val registros = mutableListOf<Cadastro>()

        val documentos = banco.collection("cadastro")
            .whereGreaterThanOrEqualTo("nome", nome)
            .get()
            .await()

        for ( documento in documentos ) {
            val cadastro = Cadastro(
                documento.id.toInt(),
                documento.data?.get("nome").toString(),
                documento.data?.get("telefone").toString()
            )
            registros.add(cadastro)
        }
        return registros
    }

}