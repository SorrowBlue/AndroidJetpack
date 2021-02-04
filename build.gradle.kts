/*
 * (c) 2020-2021 SorrowBlue.
 */

plugins {
	id("com.github.ben-manes.versions").version("0.31.0")
}

buildscript {
	repositories {
		google()
		jcenter()
		mavenLocal()
	}
	dependencies {
		classpath("com.android.tools.build:gradle:4.1.2")
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.30")
	}
}

allprojects {
	repositories {
		google()
		jcenter()
	}
}

task<Delete>("clean") {
	delete(rootProject.buildDir)
}
