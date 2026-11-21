package br.edu.utfpr.usandosqlite.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import br.edu.utfpr.usandosqlite.R
import br.edu.utfpr.usandosqlite.entity.Cadastro
import kotlin.io.encoding.Base64

class ElementoListaAdapter(val context: Context, val registros: MutableList<Cadastro>) : BaseAdapter() {
    override fun getCount(): Int {
        return registros.size
    }

    override fun getItem(pos: Int): Any? {
        return registros.get(pos)
    }

    override fun getItemId(pos: Int): Long {
        return registros.get( pos ).id.toLong()
    }

    override fun getView(
        pos: Int,
        p1: View?,
        p2: ViewGroup?
    ): View? {

        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val v = inflater.inflate( R.layout.elemento_lista, null )

        val ivIconeElementoLista = v.findViewById<ImageView>(R.id.ivIconeElementoLista)
        val tvNomeElementoLista = v.findViewById<TextView>(R.id.tvNomeElementoLista)
        val tvTelefoneElementoLista = v.findViewById<TextView>(R.id.tvTelefoneElementoLista)
        val btEditarElementoLista = v.findViewById<ImageButton>(R.id.btEditarElementoLista)

        tvNomeElementoLista.text = registros.get(pos).nome
        tvTelefoneElementoLista.text = registros.get(pos).telefone

        return v

    }
}