package br.edu.utfpr.usandosqlite

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SimpleCursorAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.usandosqlite.adapter.ElementoListaAdapter
import br.edu.utfpr.usandosqlite.database.DatabaseHandler
import br.edu.utfpr.usandosqlite.databinding.ActivityListarBinding

class ListarActivity : AppCompatActivity() {

    private lateinit var binding : ActivityListarBinding
    private lateinit var banco : DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        banco = DatabaseHandler.getInstance(this)

        binding.btIncluir.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        montarListaInicial()

    }

    private fun montarListaInicial() {
        /*val registros = listOf<String>(
            "Brasil",
            "Argentina",
            "Paraguai",
            "Uruguai"
        )

        val adapter = ArrayAdapter(
            this, //quem está "mantendo" a tela
            android.R.layout.simple_list_item_1, //destino dos dados
            registros //origem dos dados
        )

        binding.lvCadastro.adapter = adapter */

        /*
        val registros = banco.listarCursor()

        val adapter = SimpleCursorAdapter(
            this,
            android.R.layout.simple_list_item_2,
            registros,
            arrayOf("nome", "telefone"),
            intArrayOf(android.R.id.text1, android.R.id.text2),
            0
        )

        binding.lvCadastro.adapter = adapter
*/
        val registros = banco.listar()

        val adapter = ElementoListaAdapter(
            this,
            registros
        )

        binding.lvCadastro.adapter = adapter


    }
}