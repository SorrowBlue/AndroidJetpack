/*
 * (c) 2020-2021 SorrowBlue.
 */

plugins {
    ComAndroidPluginGroup(this).library
    `kotlin-android`
    `maven-publish`
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(30)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        @Suppress("UNUSED_VARIABLE") val release by getting {
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
    implementation(kotlin("stdlib-jdk8", "1.4.30"))
    implementation("androidx.fragment:fragment-ktx:1.3.0-rc02")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.0")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.3.0")
    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}

afterEvaluate {
    publishing {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/SorrowBlue/AndroidJetpack")
                credentials {
                    username = project.findProperty("gpr.user")?.toString()
                        ?: System.getenv("GITHUB_USERNAME")
                    password = project.findProperty("gpr.token")?.toString()
                        ?: System.getenv("GITHUB_TOKEN")
                }
            }
        }
        publications {
            create<MavenPublication>("release") {
                from(components.getByName("release"))
                groupId = "com.sorrowblue.jetpack"
                artifactId = "binding-ktx"
                version = "2.1.2"
            }
        }
    }
}
