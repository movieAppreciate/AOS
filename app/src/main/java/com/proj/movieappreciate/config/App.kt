package com.proj.movieappreciate.config

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kakao.sdk.common.KakaoSdk
import com.proj.movieappreciate.R
import com.proj.movieappreciate.data.dataSource.remote.RemoteDataSourceImpl
import com.proj.movieappreciate.data.dataSource.remote.retrofit.AuthService
import com.proj.movieappreciate.data.dataSource.remote.retrofit.RemoteDataSource
import com.proj.movieappreciate.data.repository.AuthRepository
import com.proj.movieappreciate.data.token.AuthInterceptor
import com.proj.movieappreciate.data.token.JwtTokenManager
import com.proj.movieappreciate.util.RetrofitUtil
import com.proj.movieappreciate.util.SharedPreferencesUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@HiltAndroidApp
class App : Application() {

    val API_URL = "http://ec2-54-180-121-146.ap-northeast-2.compute.amazonaws.com:8080/"
    private var appContext: Context? = null
    lateinit var sharedPreferences: SharedPreferencesUtil

    @Inject
    lateinit var tokenManager: JwtTokenManager



    companion object {

        // jwt token intercept를 위한 상수
        const val HEADER_AUTHORIZATION = "Authorization"
        const val TYPE = "Bearer"

        // preferences datastore
        val USER_PREFERENCES_NAME = "user_preferences"
        val Context.datastore by  preferencesDataStore(name = USER_PREFERENCES_NAME)
        val ACCESS_TOKEN_KEY = stringPreferencesKey("user_access_token")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("user_refresh_token")


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

    //todo : 레트로핏, okhttpClient 생성 코드 위치 이동 필요!
    private fun initRetrofitInstance() {

        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(AuthInterceptor(tokenManager))
            .build()

        // retrofit 이라는 전역변수에 API url, 인터셉터, Gson을 넣어주고 빌드해주는 코드
        // 이 전역변수로 http 요청을 서버로 보내면 됩니다.
        retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(client)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

//    // GSon은 엄격한 json type을 요구하는데, 느슨하게 하기 위한 설정. success, fail이 json이 아니라 단순 문자열로 리턴될 경우 처리..
//    val gson: Gson = GsonBuilder()
//        .setLenient()
//        .create()

}

class NullOnEmptyConverterFactory : Converter.Factory() {
    fun converterFactory() = this
    override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit) = object :
        Converter<ResponseBody, Any?> {
        val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)
        override fun convert(value: ResponseBody) = if (value.contentLength() != 0L) {
            try{
                nextResponseBodyConverter.convert(value)
            }catch (e:Exception){
                e.printStackTrace()
                null
            }
        } else{
            null
        }
    }
}