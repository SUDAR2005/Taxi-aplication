plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.taxi_application"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.taxi_application"
        minSdk = 32
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // OpenStreetMap (OSM) for Maps
    implementation("org.osmdroid:osmdroid-android:6.1.17")

    // Location Services (for GPS)
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Permissions (Easy permission handling)
    implementation("com.vmadalin:easypermissions-ktx:1.0.0")

    // Notifications (Required for Android 13+)
    implementation("androidx.core:core-ktx:1.12.0") // Already added above
}