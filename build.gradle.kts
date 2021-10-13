/*
 * (c) 2020-2021 SorrowBlue.
 */

plugins {
	id("com.github.ben-manes.versions") version "0.39.0"
	id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
	id("org.ajoberstar.grgit") version "4.1.0"
}

buildscript {
	repositories {
		google()
		mavenLocal()
	}
	dependencies {
		classpath("com.android.tools.build:gradle:7.0.3")
		classpath(kotlin("gradle-plugin", "1.5.31"))
	}
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
