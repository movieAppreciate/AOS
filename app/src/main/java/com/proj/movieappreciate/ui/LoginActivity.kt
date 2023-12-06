package com.proj.movieappreciate.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import com.proj.movieappreciate.config.BaseActivity
import com.proj.movieappreciate.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 액티비티를 세로모드로 고정
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        init()

    }

    fun init () = binding.apply {
        kakaoLoginBtn.setOnClickListener {

        }
    }


}