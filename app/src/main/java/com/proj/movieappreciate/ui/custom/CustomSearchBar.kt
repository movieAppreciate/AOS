package com.proj.movieappreciate.ui.custom


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.proj.movieappreciate.R

class  CustomSearchBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var editTextSearch: EditText
    private lateinit var imageButtonSearch: ImageButton

    init {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.custom_search_bar, this, true)

        // 뷰 요소 초기화
        editTextSearch = findViewById(R.id.movieSearchEdittText)
        imageButtonSearch = findViewById(R.id.movieSearchButton)

        // 검색 버튼 클릭 이벤트 설정
        imageButtonSearch.setOnClickListener {
            val searchText = editTextSearch.text.toString()
            // 원하는 동작 수행
        }
    }
}
