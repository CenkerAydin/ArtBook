plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id ("androidx.navigation.safeargs.kotlin")
    id ("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.cenkeraydin.artbook"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.cenkeraydin.artbook"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.cenkeraydin.artbook.HiltTestRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.core)
    testImplementation (libs.kotlinx.coroutines.test)
    testImplementation (libs.truth)
    testImplementation (libs.androidx.core.testing)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.core.testing)
    androidTestImplementation (libs.kotlinx.coroutines.test)
    androidTestImplementation (libs.truth)
    androidTestImplementation (libs.mockito.android)  // Make sure to use the latest version

    androidTestImplementation (libs.hilt.android.testing)
    kspAndroidTest (libs.hilt.android.compiler)
    debugImplementation (libs.androidx.fragment.testing)
    androidTestImplementation(libs.androidx.espresso.contrib)
    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    ksp (libs.dagger.hilt.compiler)


    //Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    ksp(libs.kotlinx.metadata.jvm)


    //Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.logging.interceptor)

    //Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    //Glide
    implementation (libs.glide)
    ksp (libs.compiler)
}