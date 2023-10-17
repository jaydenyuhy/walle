apply(from = rootProject.file("quality.gradle"))
apply(from = "../maven-publish.gradle")
plugins {
    id("java")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    //提供org.json环境，开发时注意与Android org.json api兼容
    compileOnly(libs.json)
    implementation(project(":payload_reader"))
}

java{
    targetCompatibility = JavaVersion.VERSION_17
    sourceCompatibility = JavaVersion.VERSION_17
}
