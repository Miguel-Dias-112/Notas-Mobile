package com.example.iwannapostit.ClasseGlobais

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import android.content.Context.MODE_PRIVATE
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iwannapostit.ClassesColeções.RecyclerView_ColeçõesAdapter
import com.example.iwannapostit.ClassesPostits.RecyclerView_PostitsAdapter
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileOutputStream

class JsonHelper(var context: Context) : java.io.Serializable {
    private val gson = Gson()
  //public var projetos = mutableListOf<Projeto>()

    fun atualizar_lista_de_coleções(){
        lista_coleções?.adapter = RecyclerView_ColeçõesAdapter(context)
        lista_coleções?.layoutManager = LinearLayoutManager(context)
    }
    fun atualizarListaDePostits(){
        lista_postits?.adapter = RecyclerView_PostitsAdapter(context)
        lista_postits?.layoutManager = LinearLayoutManager(context)
    }
    private fun escrever_dados() {
        val gson = Gson()
        val json = gson.toJson(coleçoes)
        val byteArray = json.toByteArray(Charsets.UTF_8)

        val outputStream: FileOutputStream = context.openFileOutput("dados.json", MODE_PRIVATE)
        outputStream.write(byteArray)
        outputStream.close()
        carregar_projetos()
    }
    //coleção
    fun deletar_projeto(coleção: Coleção){
        val projetoscopia = coleçoes.toMutableList() // criando uma cópia da lista original
        for (_projeto in projetoscopia){
            if (_projeto.nome==coleção.nome){
                coleçoes.remove(_projeto)
                escrever_dados()
                atualizar_lista_de_coleções()
            }
        }
    }
    fun adicionar_projeto(coleção: Coleção) {
        coleçoes.add(coleção)
        escrever_dados()
        atualizar_lista_de_coleções()
    }
    fun carregar_projetos() {
        val file = File(context.applicationContext.filesDir, "dados.json")
        if (!file.exists()) {
            Log.d("jsonhelper","criei o json")
            adicionar_projeto(Coleção("start", mutableListOf<Postit>()))
            file.createNewFile()

        }
        Log.d("jsonhelper","projeto carregado")
        Log.d("jsonhelper","projetos"+ coleçoes.toString())

        val jsonString = context.applicationContext.openFileInput("dados.json").bufferedReader().use { it.readText() }
        Log.d("jsonhelper","json:"+jsonString)

        val projetosType = object : TypeToken<MutableList<Coleção>>() {}.type
        if (jsonString!=""){
            coleçoes = gson.fromJson(jsonString, projetosType)
        }
        else {
            coleçoes = mutableListOf()
        }
        atualizar_lista_de_coleções()
    }
    fun editar_projetos(nome_antigo:String, projeto_novo:Coleção){

        val projetoscopia = coleçoes.toMutableList() // criando uma cópia da lista original
        for (projeto in projetoscopia){
            if (projeto.nome==nome_antigo){
                var index = coleçoes.indexOf(projeto)
                coleçoes[index]=projeto_novo
                escrever_dados()
                atualizar_lista_de_coleções()
            }
        }

    }
    //postits
    fun deletar_postit( postit: Postit){

        coleção_atual?.postits?.remove(postit)
        escrever_dados()
        atualizarListaDePostits()
    }
    fun adicionar_postit( postit: Postit): Boolean {

        if (coleção_atual!=null){

            for (elemento in coleçoes) {


                var x =(coleção_atual!!.nome == elemento.nome && elemento.postits.equals(
                    coleção_atual!!.postits))

                if (coleção_atual!!.nome == elemento.nome) {
                    val index = coleçoes.indexOf(elemento)
                    coleção_atual!!.postits.add(postit)
                    coleçoes[index] = coleção_atual!!
                    escrever_dados()
                    atualizarListaDePostits()

                }

            }
        }

        return true
    }

    fun edita_postit(postit_antigo: Postit, postit_novo:Postit){
        var index = coleção_atual!!.postits.indexOf(postit_antigo)
        coleção_atual!!.postits[index] = postit_novo
        escrever_dados()
        atualizarListaDePostits()

    }



    companion object{
         var coleçoes = mutableListOf<Coleção>()
         var lista_coleções: RecyclerView? =null
         var lista_postits: RecyclerView? =null
         var coleção_atual:Coleção?=null
         var postit_atual:Postit=Postit("","","")

    }
}