package com.proj.movieappreciate.util

import android.content.Context
import android.content.SharedPreferences
import com.proj.movieappreciate.config.App
import com.proj.movieappreciate.data.dataSource.model.SignUpResponse

class SharedPreferencesUtil(context: Context) {
    private var preferences: SharedPreferences =
        context.getSharedPreferences(App.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun getString(key: String): String {
        return preferences.getString(key, null) ?: ""
    }
    fun getLong(key: String): Long {
        return preferences.getLong(key, -1)
    }

//   fun addSignUpInfo(data : SignUpResponse){
//       val editor = preferences.edit()
//       editor.putString("id", data.uid.toString())
//       editor.putString("type", data.type)
//   }




}