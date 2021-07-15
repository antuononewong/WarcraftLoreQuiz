package com.dreamgrove.android.warcraftlorequiz

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast

class ToastMaker private constructor() {

    companion object {
        fun make(context: Context, text: String) {
           Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }
}