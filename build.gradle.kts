/*
 * (c) 2020-2021 SorrowBlue.
 */

plugins {
	id("com.github.ben-manes.versions") version "0.43.0"
	id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
	id("org.ajoberstar.grgit") version "5.0.0"
}

buildscript {
	repositories {
		google()
		mavenLocal()
	}
	dependencies {
		classpath("com.android.tools.build:gradle:7.3.1")
		classpath(kotlin("gradle-plugin", "1.7.20"))
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
