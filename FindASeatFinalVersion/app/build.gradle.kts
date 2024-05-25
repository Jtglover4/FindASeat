plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}


//allprojects {
//    repositories {
//        jcenter();
//        google()
//    }
//}

android {
    namespace = "com.example.findaseatfinal2"
    compileSdk = 34
    useLibrary ("android.test.runner")
    useLibrary ("android.test.base")
    useLibrary ("android.test.mock")

    defaultConfig {
        applicationId = "com.example.findaseatfinal2"
        minSdk = 24
//        targetSdk = 33
//        versionCode = 1
//        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            isShrinkResources = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
        animationsDisabled = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

// AndroidX and UI libraries
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.10.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.squareup.picasso:picasso:2.71828")

// Firebase libraries
    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-storage:20.3.0")

// Test libraries
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("androidx.test:runner:1.5.2")
    testImplementation("androidx.test:rules:1.5.0")
//    testImplementation("androidx.test.espresso:espresso-core:3.5.1"){
//        exclude(group = "com.google.protobuf")
//    }
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.robolectric:robolectric:4.7.3")
    testImplementation(platform("com.google.firebase:firebase-bom:32.5.0"))
    testImplementation("com.google.firebase:firebase-firestore")
    testImplementation("com.google.firebase:firebase-storage:20.3.0")

// Android Test libraries
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1"){
//        exclude(group = "com.google.protobuf", module="protobuf-lite")
//        exclude(group = "com.google.protobuf", module="protobuf-javalite")
//    }
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.2.0")
    androidTestImplementation(platform("com.google.firebase:firebase-bom:32.5.0"))
    androidTestImplementation("com.google.firebase:firebase-firestore")
    androidTestImplementation("com.google.firebase:firebase-storage:20.3.0")
    androidTestImplementation ("com.jayway.android.robotium:robotium-solo:5.6.3")
    testImplementation ("com.jayway.android.robotium:robotium-solo:5.6.3")
    testImplementation("androidx.test.ext:junit:1.1.5")

}