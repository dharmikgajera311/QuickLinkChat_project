buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
        classpath("de.hdodenhof:circleimageview:3.1.0@aar")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.1" apply false
    id("com.android.library") version "8.2.1" apply false
}