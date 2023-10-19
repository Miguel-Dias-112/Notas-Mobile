package com.example.iwannapostit.ClasseGlobais

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.iwannapostit.ClassesPostits.WorkbanchActivity
import com.example.iwannapostit.R

class ScreenManager {
    companion object{

        private  fun RegexData(_mes:Int,_ano:Int,_dia:Int): String {
            var mes="00"
            var dia="00"

            if((_mes+1).toString().length<2){
                mes="0${_mes}"
            }else{mes="${_mes}" }

            if((_dia+1).toString().length<2){
                dia="0${_dia}"
            }else{
                dia="${_dia}"
            }

            val selectedDate = "$dia/${mes}/$_ano" // Cria uma string com a data selecionada
            return selectedDate
        }
        ///////Gerencia fluxo do usuatio
        private fun DialogData(context:Context,data_text: TextView){
            val inflater = LayoutInflater.from(context)
            val dialog = AlertDialog.Builder(context).create()
            val view = inflater.inflate(R.layout.fragment_date2, null)

            val data_input = view.findViewById<CalendarView>(R.id.DateDialog_DataInput)
            val confirmar_button = view.findViewById<Button>(R.id.DateDialog_ConfirmarButton)


            data_input.setOnDateChangeListener { view, year, month, dayOfMonth ->
                val data = RegexData(month,year,dayOfMonth)
                data_text.setText(data)
            }
            confirmar_button.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setView(view)
            dialog.show()
        }
        fun DialogPostit(context:Context, modo: String, values:Map<String, String>){
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.fragment_adiciona_postit, null)
            val dialog = AlertDialog.Builder(context).create()

            val titulo_input = view.findViewById<EditText>(R.id.DialogPostit_TituloInput)
            val mensagem_input = view.findViewById<EditText>(R.id.DialogPostit_MessagemInput)
            val data_text = view.findViewById<TextView>(R.id.DialogPostit_DataText)
            val setdata_button = view.findViewById<Button>(R.id.DialogPostit_SetDataButton)
            val criar_button = view.findViewById<Button>(R.id.DialogPostit_CriarButton)
            val cancelar_button = view.findViewById<Button>(R.id.DialogPostit_CancelarButton)

            if( modo=="editar"){
                var titulo = values["titulo"]
                var mensagem = values["mensagem"]
                var data = values["data"]

                criar_button.setText("editar")
                titulo_input.setText(titulo)
                mensagem_input.setText(mensagem)
                data_text.setText(data)


            }
            setdata_button.setOnClickListener(){
                DialogData(context,data_text)
            }
            criar_button.setOnClickListener(){

                val titulo = titulo_input.text.toString()
                val mensagem = mensagem_input.text.toString()
                val data = data_text.text.toString()

                val postit = Postit(titulo,mensagem,data)
                val jsonHelper =JsonHelper(context)

                if (modo=="adicionar"){
                    jsonHelper.adicionar_postit(postit)
                }
                if (modo=="editar"){
                    jsonHelper.edita_postit(JsonHelper.postit_atual,postit)

                }

                dialog.dismiss()
            }
            cancelar_button.setOnClickListener(){
                dialog.dismiss()
            }
            dialog.setView(view)
            dialog.show()


        }
        fun DialogColeção( context:Context,modo:String){
            val jsonHelper = JsonHelper(context)
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.fragment_adiciona_projeto, null)
            val dialog = AlertDialog.Builder(context).create()
            val textinput = view.findViewById<EditText>(R.id.textinput_projeto)
            val botão_criar = view.findViewById<Button>(R.id.confirmar_projeto)
            val botão_cancelar = view.findViewById<Button>(R.id.cancelar_projeto)

            botão_criar.setOnClickListener {
                val coleção_nova = Coleção(textinput.text.toString(), mutableListOf<Postit>())
                val coleção_antiga = JsonHelper.coleção_atual

                if(modo.equals("adicionar")){
                    jsonHelper.adicionar_projeto(coleção_nova)
                }
                if (modo=="editar" && coleção_antiga!=null){
                    jsonHelper.editar_projetos(coleção_antiga.nome,coleção_nova)
                }
                dialog.dismiss()

            }
            botão_cancelar.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setView(view)
            dialog.show()
        }
        fun DialogDeletar(context: Context, funçãodeletar:()->Unit){
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setTitle("Confirmar exclusão")
            alertDialogBuilder.setMessage("Tem certeza de que deseja excluir este item?")

            alertDialogBuilder.setPositiveButton("Sim") { dialog, which ->
                // Executar código de exclusão aqui

                funçãodeletar()
                Toast.makeText(context, "Item excluído com sucesso", Toast.LENGTH_SHORT).show()
            }

            alertDialogBuilder.setNegativeButton("Não") { dialog, which ->
                // Não fazer nada
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
        fun WorkbanchActivity(context:Context){
            val intent = Intent(context, WorkbanchActivity::class.java);
            ContextCompat.startActivity(context, intent, Bundle())
        }

    }

}