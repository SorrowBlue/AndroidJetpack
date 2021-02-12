/*
 * (c) 2020-2021 SorrowBlue.
 */

plugins {
	id("com.github.ben-manes.versions").version("0.31.0")
}

buildscript {
	repositories {
		google()
		mavenLocal()
	}
	dependencies {
		classpath("com.android.tools.build:gradle:7.0.0-alpha06")
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.30")
	}
}

allprojects {
	repositories {
		google()
		mavenCentral()
	}
}

task<Delete>("clean") {
	delete(rootProject.buildDir)
}
