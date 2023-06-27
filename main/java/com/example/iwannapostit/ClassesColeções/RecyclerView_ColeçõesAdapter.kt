package com.example.iwannapostit.ClassesColeções

import com.example.iwannapostit.ClasseGlobais.JsonHelper
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.iwannapostit.ClasseGlobais.Coleção
import com.example.iwannapostit.ClasseGlobais.ScreenManager
import com.example.iwannapostit.R
import com.google.android.material.card.MaterialCardView


class RecyclerView_ColeçõesAdapter(
    private val context: Context, ) : RecyclerView.Adapter<RecyclerView_ColeçõesAdapter.ViewHolder>() {
    val projetos = JsonHelper.coleçoes


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        @SuppressLint("ResourceAsColor")
        fun vincula(coleção: Coleção, context: Context) {

            var jsonHelper = JsonHelper(context)
            val titulo_da_nova_coleção = itemView.findViewById<TextView>(R.id.titulo)
            val container_da_nova_coleção = itemView.findViewById<MaterialCardView>(R.id.container)
            val editar_da_nova_coleção = itemView.findViewById<ImageButton>(R.id.editar_btn)

            titulo_da_nova_coleção.text = coleção.nome

            editar_da_nova_coleção.setOnClickListener{
                JsonHelper.coleção_atual=coleção
                ScreenManager.DialogColeção(context,"editar")
            }
            container_da_nova_coleção.setOnClickListener {

                JsonHelper.coleção_atual=coleção
                ScreenManager.WorkbanchActivity(context)
            }
            container_da_nova_coleção.setOnLongClickListener {
                ScreenManager.DialogDeletar(context, { jsonHelper.deletar_projeto(coleção) })
                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflador = LayoutInflater.from(context)
        val view = inflador.inflate(R.layout.projetos, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val projeto = projetos[position]
        holder.vincula(projeto, context)
    }
    override fun getItemCount(): Int {
        return projetos.size
    }

}

