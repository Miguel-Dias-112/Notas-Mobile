package com.example.iwannapostit.ClassesPostits

import com.example.iwannapostit.ClasseGlobais.JsonHelper
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.iwannapostit.ClasseGlobais.ScreenManager
import com.example.iwannapostit.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WorkbanchActivity: AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workbanch)

        var jsonHelper= JsonHelper(this)
        JsonHelper.lista_postits=findViewById<RecyclerView>(R.id.listapostits)
        jsonHelper.atualizarListaDePostits()
        setTitle(JsonHelper.coleção_atual?.nome )

        val float = findViewById<FloatingActionButton>(R.id.floatingActionButton2)
        float.setOnClickListener{
            ScreenManager.DialogPostit(this,"adicionar", mapOf())
        }

    }
}