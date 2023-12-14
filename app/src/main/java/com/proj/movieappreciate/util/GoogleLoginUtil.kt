package com.proj.movieappreciate.util

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.proj.movieappreciate.R

private const val TAG = "GoogleLoginUtil"
object GoogleLoginUtil {


    var googleLoginClient: GoogleSignInClient? = null
    val REQ_GOOGLE_LOGIN = 1001

    fun loginGoogle(activity: Activity){
        val webClientId = activity.getString(R.string.google_app_key)

        var signInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(webClientId)
                .requestEmail().build()

        googleLoginClient = GoogleSignIn.getClient(activity, signInOptions)

        googleLoginClient?.let { client ->

            client.signOut().addOnCompleteListener {
                // 로그아웃 후 진행
                activity.startActivityForResult(
                    client.signInIntent,
                    REQ_GOOGLE_LOGIN
                )
            }
        }
    }
    fun handleGoogleActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        activity: Activity,){

        if(requestCode == REQ_GOOGLE_LOGIN){
            // 유저 로그인
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null && account.id != null && account.email != null) {

                    Log.d(TAG, "email =${account.email}, id=${account.id}, token =${account.idToken}")
//                    account.email,
//                    account.id,
//                    account.displayName,
//                    account.photoUrl?.path ?: ""

                }

            } catch (e: ApiException) {
                Log.d(TAG, "google login fail = ${e.message}")
                if (e.statusCode == 12501) {
                    // 사용자 취소
                }
            }
        }

    }

}