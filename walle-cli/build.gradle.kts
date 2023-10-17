apply(from = rootProject.file("quality.gradle"))
plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("java")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.json)
    implementation(libs.gson)
    implementation(libs.jcommander)
    implementation(libs.commons.io)
    implementation(project(":payload_reader"))
    implementation(project(":payload_writer"))
}

java{
    targetCompatibility = JavaVersion.VERSION_17
    sourceCompatibility = JavaVersion.VERSION_17
}

tasks.register("customJar", Jar::class) {

    manifest {
        attributes["Main-Class"] = "com.meituan.android.walle.Main"
        val VERSION_NAME: String by project.extra
        attributes["Walle-Version"] = VERSION_NAME
    }
}