import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinxSerialization)
    id("org.jetbrains.kotlin.native.cocoapods") version "2.0.20"

    // kotlin("native.cocoapods") version "2.0.0"

}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }


    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    cocoapods {
        version = "1.15.2"
        summary = "Some description for the Kotlin Multiplatform Module"
        //  ios.deploymentTarget = "17.2"
        homepage = "Link to the project homepage"

        framework {
            baseName = "composeApp"

            isStatic = false
        }


        // Specify dependencies from the Podfile
        pod("GoogleMLKit/FaceDetection") {
            version = "3.2.0"
            moduleName = "MLKitFaceDetection"
        }
//        pod("GoogleMLKit/SubjectSegmentation"){
//            version = "3.2.0"
//            moduleName = "MLKitSubjectSegmentation"
//        }
//        pod("GoogleMLKit/Vision"){
//            version = "3.2.0"
//            moduleName = "MLKitVision"
//        }
    }




    sourceSets {

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.accompanist)
            implementation(libs.ktor.client.android)
            val cameraxVersion = "1.3.1"
            implementation(libs.androidx.camera.core)
            implementation(libs.play.services.mlkit.subject.segmentation)
            implementation(libs.play.services.mlkit.face.detection)
            implementation(libs.play.services.gcm)

            implementation(libs.androidx.camera.camera2)
            implementation(libs.androidx.camera.view)
            implementation(libs.androidx.camera.lifecycle)

            //  implementation "com.google.accompanist:accompanist-permissions:<version>"

        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.navigation.compose)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            //   implementation(libs.runtime)
            implementation(libs.kotlinx.datetime)
            // implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.composeVM)
            implementation(libs.multiplatform.settings)
            implementation(libs.konnectivity)
            implementation(libs.ktorlogging)
            implementation(libs.napier)
            implementation(libs.coil.compose.core)
            implementation(libs.coil.compose)
            implementation(libs.coil.mp)
            implementation(libs.coil.network.ktor)
            implementation("tech.annexflow.compose:constraintlayout-compose-multiplatform:0.4.0")
            // implementation("tech.annexflow.compose:constraintlayout-compose-multiplatform:0.5.0-alpha01")


        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)

        }
    }
}

android {
    namespace = "com.crezent.photoid"
    compileSdk = 34

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.crezent.photoid"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdkVersion(libs.versions.android.targetSdk.get().toInt())
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    buildToolsVersion = "34.0.0"
    dependencies {
        debugImplementation(compose.uiTooling)
        implementation(libs.accompanist)
        implementation(libs.play.services.mlkit.face.detection)
        implementation(libs.play.services.mlkit.subject.segmentation)


//        val composeBom = platform("androidx.compose:compose-bom:2023.10.00")
//        implementation(composeBom)
//
//        implementation(libs.androidx.ui.tooling.preview)
//        debugImplementation(libs.androidx.ui.tooling)
    }
}

