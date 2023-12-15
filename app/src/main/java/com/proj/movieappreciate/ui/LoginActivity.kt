package com.proj.movieappreciate.ui


import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.Constants
import com.kakao.sdk.user.UserApiClient
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


private  val TAG = "LoginActivity"
@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {


    var mGoogleSignInClient: GoogleSignInClient? = null
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
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_app_key))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        /** Naver Login Module Initialize */
        val naverClientId = getString(R.string.social_login_info_naver_client_id)
        val naverClientSecret = getString(R.string.social_login_info_naver_client_secret)
        val naverClientName = getString(R.string.social_login_info_naver_client_name)
        NaverIdLoginSDK.initialize(this, naverClientId, naverClientSecret , naverClientName)


        init()

    }

    private fun init () = binding.apply {
        kakaoLoginBtn.setOnClickListener {
            kakaoLogin()
        }
        googleLoginBtn.setOnClickListener {
            googleLogin()

        }
        naverLoginBtn.setOnClickListener {
            naverLogin()
        }
        loginActivityViewModel.signUpResponse.observe(this@LoginActivity) {
            Toast.makeText(this@LoginActivity, "회원가입 완료", Toast.LENGTH_LONG).show()
            Log.d(TAG, "init: 회원가입")
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
                    loginActivityViewModel.signUp(user.id.toString(), "kakao",
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


    private fun googleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, 1001)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            } catch (e: ApiException) {
                // 로그인 실패
                Log.w(TAG, "signInResult:failed code=" + e.statusCode)

            }
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // 로그인 성공: account.getEmail(), account.getIdToken() 등을 활용할 수 있습니다.
            Log.d(TAG, "handleSignInResult: ${account.email}")
        } catch (e: ApiException) {
            // 로그인 실패
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)

        }
    }

    private fun naverLogin() {
        var naverToken :String? = ""

        val profileCallback = object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(response: NidProfileResponse) {
                val userId = response.profile?.id
                Log.d(TAG, "네이버 로그인 성공 !!, 아이디 : $userId")
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