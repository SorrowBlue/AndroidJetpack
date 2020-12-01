/*
 * (c) 2020 SorrowBlue.
 */

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
	id("com.github.ben-manes.versions").version("0.31.0")
}

buildscript {
	val kotlin_version by extra("1.4.10")
	repositories {
		google()
		jcenter()
		mavenLocal()
	}
	dependencies {
		classpath("com.android.tools.build:gradle:4.0.1")
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")
//		classpath("jetpack.plugin:jetpack.plugin.gradle.plugin:1.0")
		// NOTE: Do not place your application dependencies here; they belong
		// in the individual module build.gradle files
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