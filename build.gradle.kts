buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")

    }

    repositories{
        google()

    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.0.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.20" apply false
    id ("com.android.library") version "8.1.3" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
    id ("com.google.dagger.hilt.android") version "2.47" apply false



}