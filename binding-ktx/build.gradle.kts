/*
 * (c) 2020-2021 SorrowBlue.
 */

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    `maven-publish`
    signing
    id("org.jetbrains.dokka") version "1.7.20"
    id("org.ajoberstar.grgit")
}
group = "com.sorrowblue.jetpack"

version = grgit.describe {
    longDescr = false
    isTags = true
}?.toVersion() ?: "0.0.1-SNAPSHOT"

fun String.toVersion() = this + if (matches(".*-[0-9]+-g[0-9a-f]{7}".toRegex())) "-SNAPSHOT" else ""

android {
    compileSdk = 33
    namespace = "com.sorrowblue.jetpack.binding"
    defaultConfig {
        minSdk = 24
        targetSdk = 33
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
    implementation(kotlin("stdlib-jdk8", "1.7.20"))
    implementation("androidx.fragment:fragment-ktx:1.5.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.5.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
val androidSourcesJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
    from((android.sourceSets.getByName("main").kotlin.srcDirs() as com.android.build.gradle.internal.api.DefaultAndroidSourceDirectorySet).srcDirs)
}
artifacts {
    archives(androidSourcesJar)
}
val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    val dokkaHtml by tasks.getting(org.jetbrains.dokka.gradle.DokkaTask::class)
    dependsOn(dokkaHtml)
    archiveClassifier.set("javadoc")
    from(dokkaHtml.outputDirectory)
}


afterEvaluate {
    publishing {
        publications {
            val release by creating(MavenPublication::class) {
                artifactId = "binding-ktx"
                logger.lifecycle("Publish Library : $group:$artifactId:$version")
                if (project.plugins.findPlugin("com.android.library") != null) {
                    from(components.getByName("release"))
                } else {
                    from(components.getByName("java"))
                }
                artifact(androidSourcesJar)
                artifact(javadocJar)
                pom {
                    name.set(artifactId)
                    description.set("Jetpack")
                    url.set("https://github.com/SorrowBlue/AndroidJetpack")
                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("sorrowblue_sb")
                            name.set("Sorrow Blue")
                            email.set("sorrowblue.sb@gmail.com")
                        }
                    }
                    scm {
                        connection.set("scm:git:github.com/SorrowBlue/AndroidJetpack.git")
                        developerConnection.set("scm:git:ssh://github.com/SorrowBlue/AndroidJetpack.git")
                        url.set("https://github.com/SorrowBlue/AndroidJetpack/tree/main")
                    }
                }
            }
        }
    }
    signing {
        if (!hasProperty("signing.secretKeyRingFile")) {
            val signingKeyId: String? by project
            val signingKey: String? by project
            val signingPassword: String? by project
            useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
        }
        sign(publishing.publications)
    }
}
