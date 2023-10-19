package com.example.iwannapostit.ClassesPostits

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.iwannapostit.ClasseGlobais.JsonHelper
import com.example.iwannapostit.ClasseGlobais.Postit
import com.example.iwannapostit.ClasseGlobais.ScreenManager
import com.example.iwannapostit.R
import com.google.android.material.card.MaterialCardView


class RecyclerView_PostitsAdapter(
    private val context: Context,
): RecyclerView.Adapter<RecyclerView_PostitsAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        @SuppressLint("ResourceAsColor")
        fun vincula(postit: Postit, context: Context){
            val titulo = itemView.findViewById<TextView>(R.id.titulo_postit)
            titulo.text=postit.titulo
            val data = itemView.findViewById<TextView>(R.id.data_input)
            data.text=postit.data
            val conteudo = itemView.findViewById<TextView>(R.id.conteudo_postit)
            conteudo.text=postit.texto

            val postitcontainer = itemView.findViewById<MaterialCardView >(R.id.materialCardView)

            val edit_mode_container= itemView.findViewById<ConstraintLayout>(R.id.edit_mode_container)
            var doubleClick: Boolean? = false

            postitcontainer.setOnClickListener {

                    if (doubleClick!!) {
                        JsonHelper.postit_atual=postit
                        ScreenManager.DialogPostit(context!!,"editar", mapOf("titulo" to postit.titulo,
                            "data" to postit.data,
                            "mensagem" to postit.texto))
                    }

                    doubleClick = true
                    edit_mode_container.elevation=10f
                    edit_mode_container.visibility=View.VISIBLE
                    Handler().postDelayed({ doubleClick = false
                        edit_mode_container.elevation=0f
                        edit_mode_container.visibility=View.INVISIBLE
                                          }, 800)



            }

            fun deletar_postit(){
                JsonHelper.postit_atual=postit
                val jsonHelper=JsonHelper(context)
                var função_deletar ={jsonHelper.deletar_postit(postit)}
                ScreenManager.DialogDeletar(context!!, função_deletar)
            }

            postitcontainer.setOnLongClickListener{
                deletar_postit()
                return@setOnLongClickListener true
            }


        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflador = LayoutInflater.from(context)
        val  view = inflador.inflate(R.layout.postit, parent,false)
        return  ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val postit = JsonHelper.coleção_atual?.postits?.get(position)
        if (postit != null) {
            holder.vincula(postit,context)
        }
    }
    override fun getItemCount(): Int {
        return      JsonHelper.coleção_atual?.postits!!.size
    }

}

