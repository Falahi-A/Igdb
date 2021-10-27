package com.pinch.codeassignment.igdb.ui.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.pinch.codeassignment.igdb.databinding.ErrorViewCustomBinding

class CustomErrorView : ConstraintLayout {

    private lateinit var binding: ErrorViewCustomBinding

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {

        init()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        binding = ErrorViewCustomBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setError(error: String) {
        binding.textError.text = error
    }

    fun setReloadListener(reload:()->Unit){
        binding.btnReload.setOnClickListener {
            reload.invoke()
        }

    }


}