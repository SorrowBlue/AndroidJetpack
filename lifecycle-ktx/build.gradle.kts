/*
 * (c) 2020 SorrowBlue.
 */

plugins {
    ComAndroidPluginGroup(this).library
    `kotlin-android`
    `kotlin-android-extensions`
    `maven-publish`
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.2")

    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(30)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        val release by getting {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8","1.4.10"))
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.0-alpha07")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components.getByName("release"))
                groupId= "com.sorrowblue.jetpack"
                artifactId = "lifecycle-ktx"
                version = "1.0.0"
            }
        }
    }
}

