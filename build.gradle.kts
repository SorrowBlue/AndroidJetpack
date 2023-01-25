/*
 * (c) 2020-2021 SorrowBlue.
 */

plugins {
	id("com.github.ben-manes.versions") version "0.43.0"
	id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
	id("org.ajoberstar.grgit") version "5.0.0" apply false
	id("com.android.application") version "7.4.0" apply false
	id("com.android.library") version "7.4.0" apply false
	kotlin("android") version "1.8.0" apply false
	kotlin("kapt") version "1.8.0" apply false
}

nexusPublishing {
	repositories {
		sonatype {
			stagingProfileId.set(findProperty("sonatypeStagingProfileId") as? String)
		}
	}
}

task<Delete>("clean") {
	delete(rootProject.buildDir)
}
