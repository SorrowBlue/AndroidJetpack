/*
 * (c) 2020-2021 SorrowBlue.
 */

import com.android.build.gradle.BaseExtension
import java.io.FileInputStream
import java.util.Properties
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.register

class MavenCentralRepository : Plugin<Project> {

    override fun apply(target: Project) = target.execute()

    private fun Project.execute() {
        apply(plugin = "maven-publish")
        apply(plugin = "signing")
        apply(plugin = "io.codearte.nexus-staging")

        val androidSourcesJar = tasks.register<Jar>("androidSourcesJar") {
            archiveClassifier.set("source")
            from(extensions.findByType<BaseExtension>()?.sourceSets?.get("main")?.java?.srcDirs)
        }

        artifacts {
            add("archives", androidSourcesJar)
        }

        loadExt()

        nexusStaging {
            packageGroup = ext["PUBLISH_GROUP_ID"].toString()
            stagingProfileId = ext["sonatypeStagingProfileId"].toString()
            username = ext["ossrhUsername"].toString()
            password = ext["ossrhPassword"].toString()
        }

        publishing {
            publications {
                register<MavenPublication>("release") {
                    groupId = ext["PUBLISH_GROUP_ID"].toString()
                    artifactId = ext["PUBLISH_ARTIFACT_ID"].toString()
                    version = ext["PUBLISH_VERSION"].toString()
                    artifact("$buildDir/outputs/aar/${project.getName()}-release.aar")
                    artifact(androidSourcesJar)

                    pom {
                        name.set(ext["PUBLISH_ARTIFACT_ID"].toString())
                        description.set("AndroidJetpackExtension")
                        url.set("https://github.com/SorrowBlue/AndroidJetpack")
                        licenses {
                            license {
                                name.set("The Apache License, Version 2.0")
                                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                            }
                        }
                        developers {
                            developer {
                                id.set("zsmb13")
                                name.set("Sorrow Blue")
                                email.set("sorrowblue.sb@gmail.com")
                            }
                        }
                        scm {
                            connection.set("scm:git:github.com/SorrowBlue/AndroidJetpack.git")
                            developerConnection.set("scm:git:ssh://github.com/SorrowBlue/AndroidJetpack.git")
                            url.set("https://github.com/SorrowBlue/AndroidJetpack/tree/main")
                        }
                        withXml {
                            val dependenciesNode = asNode().appendNode("dependencies")

                            project.configurations.get("implementation").allDependencies.forEach {
                                val dependencyNode = dependenciesNode.appendNode("dependency")
                                dependencyNode.appendNode("groupId", it.group)
                                dependencyNode.appendNode("artifactId", it.name)
                                dependencyNode.appendNode("version", it.version)
                            }
                        }
                    }
                }
            }
            repositories {
                maven {
                    name = "sonatype"
                    val releasesUrl =
                        "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
                    val snapshotsUrl =
                        "https://oss.sonatype.org/content/repositories/snapshots/"
                    val uploadUri =
                        if (version.toString().endsWith("SNAPSHOT")) snapshotsUrl else releasesUrl
                    url = uri(uploadUri)

                    credentials {
                        username = ext["ossrhUsername"].toString()
                        password = ext["ossrhPassword"].toString()
                    }
                }
            }
        }
        signing {
            sign(publishing.publications)
        }
    }

    private fun Project.loadExt() {
        ext {
            val secretPropsFile = project.rootProject.file("local.properties")
            if (secretPropsFile.exists()) {
                println("Found secret props file, loading props")
                val p = Properties()
                p.load(FileInputStream(secretPropsFile))
                p.forEach { name, value ->
                    set("$name", "$value")
                }
            } else {
                println("No props file, loading env vars")
                ext["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
                ext["signing.password"] = System.getenv("SIGNING_PASSWORD")
                ext["signing.secretKeyRingFile"] = System.getenv("SIGNING_SECRET_KEY_RING_FILE")
                ext["ossrhUsername"] = System.getenv("OSSRH_USERNAME")
                ext["ossrhPassword"] = System.getenv("OSSRH_PASSWORD")
                ext["sonatypeStagingProfileId"] = System.getenv("SONATYPE_STAGING_PROFILE_ID")

            }
        }
    }
}
