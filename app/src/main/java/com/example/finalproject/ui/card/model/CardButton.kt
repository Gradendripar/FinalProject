package com.example.finalproject.ui.card.model

import android.content.Context
import android.util.AttributeSet

class CardButton(context: Context, attributes: AttributeSet):androidx.appcompat.widget.AppCompatButton(context,attributes){
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width,(width*1.2).toInt())
    }
}