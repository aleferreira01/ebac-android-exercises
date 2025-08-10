plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "br.com.alexander.awesomemovieapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "br.com.alexander.awesomemovieapp"
        minSdk = 24
        targetSdk = 34
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
    }
}

dependencies {

    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    //navigation components
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.navigation.ui6)
    // UI
    implementation(libs.material.v150)
    implementation(libs.constraintlayout)
    implementation(libs.androidx.recyclerview)
    //Carrousel
    implementation(libs.circleindicator)
    implementation(libs.whynotimagecarousel)
    //Retrofit and Moshi
    implementation(libs.retrofit.z)
    implementation(libs.squareup.moshi.kotlin)
    implementation(libs.converter.moshi)
    ksp(libs.moshi.kotlin.codegen)
    //Glide
    implementation(libs.glide)
    annotationProcessor(libs.glide)
    //OkHttp
    implementation(libs.okhttp)
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.fragment)
    implementation(libs.androidx.hilt.navigation)

    // Truth
    testImplementation(libs.truth)
    // MockK
    testImplementation(libs.mockk)
    // Hilt for local tests
    testImplementation(libs.hilt.android.testing)
    kaptTest(libs.hilt.android.compiler)
    // Hilt for instrumented tests
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.android.compiler)
    // Coroutines
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.kotlinx.coroutines.android)
    // JUnit
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    androidTestImplementation(libs.androidx.core)
}