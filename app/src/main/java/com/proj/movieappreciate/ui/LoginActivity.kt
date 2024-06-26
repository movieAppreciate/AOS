package com.proj.movieappreciate.ui


import android.content.Context
import android.content.Intent

import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.credentials.Credential
import androidx.credentials.PasswordCredential
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException


import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.Constants
import com.kakao.sdk.user.UserApiClient

import com.proj.movieappreciate.ui.announcement.AnnouncementActivity
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import com.proj.movieappreciate.R
import com.proj.movieappreciate.config.BaseActivity
import com.proj.movieappreciate.databinding.ActivityLoginBinding
import com.proj.movieappreciate.ui.viewModel.LoginActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

private  val TAG = "LoginActivity"
@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    // 원 탭 로그인 클라이언트 구성
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
    private var showOneTapUI = true

    //    var mGoogleSignInClient: GoogleSignInClient? = null
    val loginActivityViewModel : LoginActivityViewModel by viewModels()
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
            Log.i(TAG, "카카오톡으로 로그인 성공 : Refresh ${token.refreshToken}")
            getKakaoUserInfo()
            getKakaoTokenInfo()
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 액티비티를 세로모드로 고정
        //requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

//
//        /** Naver Login Module Initialize */
//        val naverClientId = getString(R.string.social_login_info_naver_client_id)
//        val naverClientSecret = getString(R.string.social_login_info_naver_client_secret)
//        val naverClientName = getString(R.string.social_login_info_naver_client_name)
//        NaverIdLoginSDK.initialize(this, naverClientId, naverClientSecret , naverClientName)


        init()

        //Move to Annoucement Page
        binding.moveAnnouncementBtn.setOnClickListener {
            val intent = Intent(this,AnnouncementActivity::class.java)
            startActivity(intent)
        }


    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when (requestCode) {
//            REQ_ONE_TAP -> {
//                try {
//                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
//                    val idToken = credential.googleIdToken
//                    val username = credential.id
//                    val password = credential.password
//
//                    when {
//                        idToken != null -> {
//                            // Handle successful sign-in
//                            Log.d("구글", "Got ID token: $idToken")
//                            // You can now use this ID token for authentication or other purposes
//                            Log.d("구글","id : $username")
//                            //todo : 로그인 api로 uid값 전송
//                            loginActivityViewModel.signUp(uid = credential.id, type = "google","")
//                            loginActivityViewModel.login(uid = credential.id, type = "google","")
//                            Log.d("로그인 onresult",loginActivityViewModel.signUpResponse.toString())
//
//                        }
//                        password != null -> {
//                            Log.d("구글","Got password")
//                        }
//                        else -> {
//                            Log.d("구글","no id token or password")
//                        }
//                    }
//
//                } catch (e: ApiException) {
//                    when (e.statusCode) {
//                        CommonStatusCodes.CANCELED-> {
//                            Log.d("구글","dialog was closed")
//                            showOneTapUI = false
//                        }
//                    }
//
//                    // Handle API exceptions
//                    Log.e("Google", e.toString())
//                }
//            }
//        }
//    }



    private fun init () = binding.apply {
        kakaoLoginBtn.setOnClickListener {
            kakaoLogin()
        }

        googleLoginBtn.setOnClickListener{
            lifecycleScope.launch{
                googleLogin()
            }
        }

        naverLoginBtn.setOnClickListener {
//            naverLogin()
        }
        loginActivityViewModel.signUpResponse.observe(this@LoginActivity) {
            val data = it.getOrNull()
            if (data != null){
                Toast.makeText(this@LoginActivity, "회원가입 완료", Toast.LENGTH_LONG).show()
                Log.d(TAG, "init: 회원가입 ${it}")
            }
        }
        loginActivityViewModel.loginResponse.observe(this@LoginActivity) {
            val data = it.getOrNull()
            Log.d("로그인",data.toString())
            if (data != null){
                Toast.makeText(this@LoginActivity, "로그인 완료", Toast.LENGTH_LONG).show()
                Log.d(TAG, "init: 로그인 ${it}")
            }
        }
    }

    suspend  fun googleLogin() {
            requestGoogleLogin(this@LoginActivity)
    }


    suspend fun requestGoogleLogin(
        activityContext: Context
    ) {

        val credentialManager = CredentialManager.create(this)

        val googleIdOption:GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(getString(R.string.google_server_client_ID))
            .build()

        val request : GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

       kotlin.runCatching {
           credentialManager.getCredential(
               request = request,
               context =  activityContext
           )
       }
           .onSuccess {
               val credential = it.credential
               Log.d("구글",credential.toString())
               handleGoogleCredential(credential)
           }
           .onFailure {
               Log.d("구글", it.toString())

           }

    }

    private fun handleGoogleCredential(credential: Credential) {
        when (credential) {
            is GoogleIdTokenCredential -> {
                try {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

                    loginActivityViewModel.login(uid = googleIdTokenCredential.id, type = "google", profileURL = "")

                } catch (e: GoogleIdTokenParsingException) {
                    Log.e("구글", e.toString())
                }
            }
            is PasswordCredential -> {
                // PasswordCredential에 대한 처리 로직 추가 (필요에 따라)
                val username = credential.id
                val password = credential.password
                loginActivityViewModel.login(uid = credential.id, type = "google", profileURL = "")
            }
            else -> {
                Log.e("구글", "Unexpected type of credential")
            }
        }
    }

    private fun kakaoLogin(){
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e(Constants.TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
//                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
//                        return@loginWithKakaoTalk
//                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    Log.i(Constants.TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }


    private fun getKakaoUserInfo() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(Constants.TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i(
                    Constants.TAG, "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n이메일: ${user.kakaoAccount?.email}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                            "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")

                user.kakaoAccount?.profile?.thumbnailImageUrl?.let {
                    loginActivityViewModel.login(user.id.toString(), "kakao",
                        it
                    )
                }
            }
        }
    }

    private fun getKakaoTokenInfo() {
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Log.e(Constants.TAG, "토큰 정보 보기 실패", error)
            }
            else if (tokenInfo != null) {
                Log.i(
                    Constants.TAG, "토큰 정보 보기 성공" +
                            "\n회원번호: ${tokenInfo.id}" +
                            "\n만료시간: ${tokenInfo.expiresIn} 초")
            }
        }
    }



    private fun naverLogin() {
        var naverToken :String? = ""

        val profileCallback = object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(response: NidProfileResponse) {
                val userId = response.profile?.id
                Log.d(TAG, "네이버 로그인 성공 !!, 아이디 : $userId")
                response.profile?.profileImage?.let {
                    if (userId != null) {
                        loginActivityViewModel.login(userId, "naver",
                            it
                        )
                    }
                }
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Log.w(TAG, "onFailure: $errorCode, $errorDescription")

            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
                naverToken = NaverIdLoginSDK.getAccessToken()
//                var naverRefreshToken = NaverIdLoginSDK.getRefreshToken()
//                var naverExpiresAt = NaverIdLoginSDK.getExpiresAt().toString()
//                var naverTokenType = NaverIdLoginSDK.getTokenType()
//                var naverState = NaverIdLoginSDK.getState().toString()

                //로그인 유저 정보 가져오기
                NidOAuthLogin().callProfileApi(profileCallback)
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()

            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }
        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
    }







    }



