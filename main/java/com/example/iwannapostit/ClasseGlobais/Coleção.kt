package com.example.iwannapostit.ClasseGlobais

import java.io.Serializable

class Coleção(val nome: String,
              val postits:MutableList<Postit>) : Serializable
