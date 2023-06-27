package com.example.iwannapostit.ClassesPostits

import android.os.Handler
import android.view.MotionEvent
import android.view.View

class MyLongClickListener(var deletar: () -> Unit) : View.OnLongClickListener, View.OnTouchListener {
    private val longClickTime = 2000L // Tempo de longo clique (em milissegundos)
    private var handler: Handler? = null
    private var longClickRunnable: Runnable? = null

    override fun onLongClick(v: View): Boolean {
        startLongClickAction()
        return true
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN ) {

        }
        if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
            cancelLongClickAction()
        }
        return false
    }

    private fun startLongClickAction() {
        handler = Handler()
        longClickRunnable = Runnable {
            deletar()
        }
        handler?.postDelayed(longClickRunnable!!, longClickTime)
    }

    private fun cancelLongClickAction() {
        handler?.removeCallbacks(longClickRunnable!!)
    }
}