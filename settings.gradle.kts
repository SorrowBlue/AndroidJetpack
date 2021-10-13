/*
 * (c) 2020-2021 SorrowBlue.
 */
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Android Jetpack"
include(":sample")
include(":binding-ktx")
