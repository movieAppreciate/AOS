package com.proj.movieappreciate.config

import android.app.Application
import android.content.Context
import com.kakao.sdk.common.KakaoSdk
import com.proj.movieappreciate.R

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }
}