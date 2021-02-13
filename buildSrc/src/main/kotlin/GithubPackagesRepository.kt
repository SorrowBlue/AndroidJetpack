/*
 * (c) 2020-2021 SorrowBlue.
 */

import org.gradle.api.Plugin
import org.gradle.api.PolymorphicDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create

class GithubPackagesRepository : Plugin<Project> {

    override fun apply(target: Project) = target.execute()

    private fun Project.execute() {
        apply(plugin = "maven-publish")
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
                create<MavenPublication>("githubRelease") {
                    from(components.getByName("release"))
                    groupId = ext["PUBLISH_GROUP_ID"].toString()
                    artifactId = ext["PUBLISH_ARTIFACT_ID"].toString()
                    version = ext["PUBLISH_VERSION"].toString()
                }
            }
        }
    }
}

inline fun <reified U : Any> PolymorphicDomainObjectContainer<in U>.create(
    name: String,
    noinline configuration: U.() -> Unit
) =

    this.create(name, U::class.java, configuration)
