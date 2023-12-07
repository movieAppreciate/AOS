package com.proj.movieappreciate.config

import android.app.Application
import android.content.Context
import com.kakao.sdk.common.KakaoSdk
import com.proj.movieappreciate.R

class App : Application() {

    private var appContext: Context? = null

    override fun onCreate() {
        super.onCreate()
        appContext = this
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }
}