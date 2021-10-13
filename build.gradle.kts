/*
 * (c) 2020-2021 SorrowBlue.
 */

plugins {
	id("com.github.ben-manes.versions").version("0.39.0")
}

buildscript {
	repositories {
		google()
		mavenLocal()
	}
	dependencies {
		classpath("com.android.tools.build:gradle:7.0.3")
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
		classpath("io.codearte.gradle.nexus:gradle-nexus-staging-plugin:0.22.0")
	}
}

task<Delete>("clean") {
	delete(rootProject.buildDir)
}
