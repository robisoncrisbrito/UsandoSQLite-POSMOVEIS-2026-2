package br.edu.utfpr.usandosqlite

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.usandosqlite.database.DatabaseHandler
import br.edu.utfpr.usandosqlite.databinding.ActivityMainBinding
import br.edu.utfpr.usandosqlite.entity.Cadastro

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var banco: DatabaseHandler

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

        banco = DatabaseHandler.getInstance( this )

        if ( intent.getIntExtra( "id", 0 ) != 0 ) {
            binding.etCod.setText(intent.getIntExtra("id", 0).toString())
            binding.etNome.setText(intent.getStringExtra("nome"))
            binding.etTelefone.setText(intent.getStringExtra("telefone"))
        } else {
            binding.btExcluir.visibility = View.GONE
            binding.btPesquisar.visibility = View.GONE
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

    }

    fun btAlterarOnClick() {

        if ( binding.etCod.text.toString().isEmpty() ) {
            val cadastro = Cadastro(
                0,
                binding.etNome.text.toString(),
                binding.etTelefone.text.toString()
            )
            banco.inserir(  cadastro )

        } else {
            val cadastro = Cadastro(
                binding.etCod.text.toString().toInt(),
                binding.etNome.text.toString(),
                binding.etTelefone.text.toString()
            )
            banco.alterar(cadastro)

        }

        Toast.makeText( this, "Sucesso", Toast.LENGTH_SHORT ).show()
        finish()
    }

    fun btExcluirOnClick() {
        banco.excluir(binding.etCod.text.toString().toInt())

        Toast.makeText( this, "Sucesso", Toast.LENGTH_SHORT ).show()

        finish()
    }

    fun btPesquisarOnClick() {

        val cadastro = banco.pesquisar(binding.etCod.text.toString().toInt())

        if( cadastro != null ) {
            binding.etNome.setText( cadastro.nome )
            binding.etTelefone.setText( cadastro.telefone )
        } else {
            Toast.makeText( this, "Registro não encontrado", Toast.LENGTH_SHORT ).show()
        }

    }

}