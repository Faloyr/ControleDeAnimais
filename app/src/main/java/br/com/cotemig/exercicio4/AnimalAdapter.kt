package br.com.cotemig.exercicio4

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AnimalAdapter(val contexto: Context, val lista: ArrayList<AnimalModel>) :
    RecyclerView.Adapter<AnimalAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvNome = view.findViewById<TextView>(R.id.tvNome)
        val tvRaca = view.findViewById<TextView>(R.id.tvRaca)
        val tvIdade = view.findViewById<TextView>(R.id.tvIdade)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(contexto).inflate(R.layout.lista_item, parent, false))
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvNome.text = lista[position].nome
        holder.tvRaca.text = lista[position].raca
        holder.tvIdade.text = lista[position].idade.toString()

        holder.itemView.setOnClickListener {
            var newIntent = Intent(contexto, CadastroActivity::class.java)
            newIntent.putExtra("id", lista[position].id)
            contexto.startActivity(newIntent)
        }
    }
}