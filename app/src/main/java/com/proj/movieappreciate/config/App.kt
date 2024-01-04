package com.proj.movieappreciate.config

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kakao.sdk.common.KakaoSdk
import com.proj.movieappreciate.R
import com.proj.movieappreciate.data.dataSource.remote.RemoteDataSourceImpl
import com.proj.movieappreciate.data.dataSource.remote.retrofit.AuthService
import com.proj.movieappreciate.data.dataSource.remote.retrofit.RemoteDataSource
import com.proj.movieappreciate.data.repository.AuthRepository
import com.proj.movieappreciate.util.RetrofitUtil
import com.proj.movieappreciate.util.SharedPreferencesUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@HiltAndroidApp
class App : Application() {

    val API_URL = "http://ec2-52-79-224-23.ap-northeast-2.compute.amazonaws.com:8080/"
    private var appContext: Context? = null
    lateinit var sharedPreferences: SharedPreferencesUtil
    companion object {


        const val SHARED_PREFERENCES_NAME = "SHARED_PREFERENCE"

        // Retrofit 인스턴스, 앱 실행시 한번만 생성하여 사용합니다.
        lateinit var retrofit: Retrofit

    }

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
        initRetrofitInstance()
        sharedPreferences = SharedPreferencesUtil(applicationContext)

    }

    private fun initRetrofitInstance() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        // retrofit 이라는 전역변수에 API url, 인터셉터, Gson을 넣어주고 빌드해주는 코드
        // 이 전역변수로 http 요청을 서버로 보내면 됩니다.
        retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    // GSon은 엄격한 json type을 요구하는데, 느슨하게 하기 위한 설정. success, fail이 json이 아니라 단순 문자열로 리턴될 경우 처리..
    val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

}