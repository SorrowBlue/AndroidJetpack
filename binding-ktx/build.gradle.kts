/*
 * (c) 2020-2021 SorrowBlue.
 */

plugins {
    ComAndroidPluginGroup(this).library
    `kotlin-android`
}

android {
    compileSdk = 31
    buildToolsVersion = "31.0.0"
    defaultConfig {
        minSdk = 24
        targetSdk = 31
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        @Suppress("UNUSED_VARIABLE")
        val release by getting {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8", "1.5.31"))
    implementation("androidx.fragment:fragment-ktx:1.4.0-alpha10")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-rc01")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.4.0-rc01")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

ext["PUBLISH_GROUP_ID"] = project.property("PUBLISH_GROUP_ID")
ext["PUBLISH_ARTIFACT_ID"] = project.property("PUBLISH_ARTIFACT_ID")
ext["PUBLISH_VERSION"] = project.property("PUBLISH_VERSION")

afterEvaluate {
    apply<MavenCentralRepository>()
    apply<GithubPackagesRepository>()
}
