package com.example.intermediatedua.utils

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText

class CustomEditText: AppCompatEditText{

    constructor(context: Context):super(context){
        init()
    }
    constructor(context: Context, attrs:AttributeSet):super(context, attrs){
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int):super(context, attrs, defStyleAttr){
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }
    private fun init(){
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                error = when {
                    p0.isNullOrEmpty() -> "Field ini tidak boleh kosong"
                    else -> {
                        null
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

}