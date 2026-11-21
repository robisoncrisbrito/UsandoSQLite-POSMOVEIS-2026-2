package br.edu.utfpr.usandosqlite.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.edu.utfpr.usandosqlite.entity.Cadastro

class DatabaseHandler( context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION ) {

    override fun onCreate(banco: SQLiteDatabase?) {
        banco?.execSQL( "CREATE TABLE IF NOT EXISTS ${TABLE_NAME}( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT, telefone TEXT )" )
    }


    override fun onUpgrade(
        banco: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        banco?.execSQL("DROP TABLE IF EXISTS ${TABLE_NAME}")
        this.onCreate(banco)
    }

    fun inserir( cadastro: Cadastro) {
        val banco = this.writableDatabase

        val registro = ContentValues()
        registro.put( "nome", cadastro.nome )
        registro.put( "telefone", cadastro.telefone )

        banco.insert(  TABLE_NAME, null, registro )
    }

    fun alterar( cadastro: Cadastro ) {
        val banco = this.writableDatabase

        val registro = ContentValues()
        registro.put( "nome", cadastro.nome )
        registro.put( "telefone", cadastro.telefone )

        banco.update( TABLE_NAME, registro, "_id = ${cadastro.id}", null )
    }

    fun excluir( id: Int ) {
        val banco = this.writableDatabase

        banco.delete( TABLE_NAME, "_id =  ${id}", null )
    }

    fun pesquisar( id: Int ): Cadastro? {
        val banco = this.writableDatabase

        val cursor = banco.query(
            TABLE_NAME,
            null,
            "_id = ${id}",
            null,
            null,
            null,
            null
        )

        if( cursor.moveToNext() ) {
            val cadastro = Cadastro(
                cursor.getInt(ID),
                cursor.getString(NOME),
                cursor.getString(TELEFONE)
            )
            return cadastro
        } else {
            return null
        }

    }

    fun listarCursor(): Cursor { //método só pode ser usado com o SQLIte
        val banco = this.writableDatabase

        val cursor = banco.query(
            TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        return cursor
    }

    fun listar(): MutableList<Cadastro> {
        val banco = this.writableDatabase

        val cursor = banco.query(
            "cadastro",
            null,
            null,
            null,
            null,
            null,
            null
        )

        val registros = mutableListOf<Cadastro>()

        while ( cursor.moveToNext() ) {
            val cadastro = Cadastro(
                cursor.getInt(ID),
                cursor.getString(NOME),
                cursor.getString(TELEFONE)
            )
            registros.add(cadastro)
        }

        return registros

    }

    companion object {
        private const val ID = 0
        private const val NOME = 1
        private const val TELEFONE = 2
        private const val DATABASE_NAME = "banco.db"
        private const val TABLE_NAME = "cadastro"
        private const val DATABASE_VERSION = 1
    }

}