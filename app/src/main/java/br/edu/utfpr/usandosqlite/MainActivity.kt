package br.edu.utfpr.usandosqlite

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.usandosqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var banco: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        banco = openOrCreateDatabase("banco.db", MODE_PRIVATE, null)
        banco.execSQL( "CREATE TABLE IF NOT EXISTS cadastro( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT, telefone TEXT ) ")


        binding.btIncluir.setOnClickListener {
            btIncluirOnClick()
        }
        binding.btAlterar.setOnClickListener {
            btAlterarOnClick()
        }
        binding.btExcluir.setOnClickListener {
            btExcluirOnClick()
        }
        binding.btPesquisar.setOnClickListener {
            btPesquisarOnClick()
        }
        binding.btListar.setOnClickListener {
            btListarOnClick()
        }

    }

    fun btIncluirOnClick() {
        val registro = ContentValues()
        registro.put( "nome", binding.etNome.text.toString() )
        registro.put( "telefone", binding.etTelefone.text.toString() )

        banco.insert(  "cadastro", null, registro )

        Toast.makeText( this, "Sucesso", Toast.LENGTH_SHORT ).show()
    }

    fun btAlterarOnClick() {
        val registro = ContentValues()
        registro.put( "nome", binding.etNome.text.toString() )
        registro.put( "telefone", binding.etTelefone.text.toString() )

        banco.update( "cadastro", registro, "_id = ${binding.etCod.text.toString()}", null )

        Toast.makeText( this, "Sucesso", Toast.LENGTH_SHORT ).show()

    }

    fun btExcluirOnClick() {
        banco.delete( "cadastro", "_id =  ${binding.etCod.text.toString()}", null )

        Toast.makeText( this, "Sucesso", Toast.LENGTH_SHORT ).show()
    }

    fun btPesquisarOnClick() {

        val cursor = banco.query(
            "cadastro",
            null,
            "_id = ${binding.etCod.text.toString()}",
            null,
            null,
            null,
            null
        )

        if( cursor.moveToNext() ) {
            binding.etNome.setText( cursor.getString(NOME) )
            binding.etTelefone.setText( cursor.getString(TELEFONE) )
        } else {
            Toast.makeText( this, "Registro não encontrado", Toast.LENGTH_SHORT ).show()
        }

    }

    fun btListarOnClick() {
        val cursor = banco.query(
            "cadastro",
            null,
            null,
            null,
            null,
            null,
            null
        )

       val saida = StringBuilder()

        while ( cursor.moveToNext() ) {
            saida.append(cursor.getString(NOME))
            saida.append( " - " )
            saida.append(cursor.getString(TELEFONE))
            saida.append( "\n" )
        }

        Toast.makeText(
            this,
            saida.toString(),
            Toast.LENGTH_SHORT
        ).show()

    }

    companion object {
        private const val ID = 0
        private const val NOME = 1
        private const val TELEFONE = 2
    }
}