package com.example.iwannapostit.ClassesColeções

import com.example.iwannapostit.ClasseGlobais.JsonHelper
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.iwannapostit.ClasseGlobais.ScreenManager
import com.example.iwannapostit.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProjetosActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    val  jsonHelper = JsonHelper(this);

    override fun onStart() {
        super.onStart()

        val recicleview = findViewById<RecyclerView>(R.id.lista_reciclavel)
        JsonHelper.lista_coleções=recicleview
        jsonHelper.carregar_projetos()

        val botão_flutuante_noval_coleção = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        botão_flutuante_noval_coleção.setOnClickListener {
            ScreenManager.DialogColeção(this,"adicionar")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_projetos)

    }
}