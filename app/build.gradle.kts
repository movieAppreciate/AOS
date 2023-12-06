import org.jetbrains.kotlin.storage.CacheResetOnProcessCanceled.enabled

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.proj.movieappreciate"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.proj.movieappreciate"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

}


dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // material ui
    implementation ("com.google.android.material:material:1.9.0")

    //retrofit
    // https://github.com/square/retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    // https://github.com/square/okhttp
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    // https://github.com/square/retrofit/tree/master/retrofit-converters/gson
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    // https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor
//    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    //liveData dependency 추가
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    //framework ktx dependency 추가
    implementation ("androidx.fragment:fragment-ktx:1.4.1")
}