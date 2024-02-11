package com.example.iwannapostit.ClassesPostits

import android.R
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import com.example.iwannapostit.ClasseGlobais.JsonHelper
import com.example.iwannapostit.ClasseGlobais.Postit


class CriaPostits(private val context: Context,var Layout:TableLayout ) {
    val postits = JsonHelper.coleção_atual?.postits
    fun carrega_postits(){
        for (postit: Postit in postits!!) {

//            var divpostit = View.inflate(context, R.layout.postit,Layout)
//            var titulo = divpostit.findViewById<TextView>(R.id.titulo_postit)
//            titulo.setText(postit.titulo)
        //    Layout.addView(divpostit)


            val myButton = Button(context)
            myButton.layoutParams = LinearLayout.LayoutParams(
               200,
               250
            )
            myButton.setText(postit.titulo)
            Layout.addView(myButton)
        }
    }
    init {

    }
}