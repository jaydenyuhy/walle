import com.android.build.api.dsl.LibraryExtension
import org.gradle.kotlin.dsl.configure

apply(from = rootProject.file("quality.gradle"))
apply(from = "../maven-publish_library.gradle")
plugins {
    alias(libs.plugins.android.library)
}

extensions.configure<LibraryExtension> {
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        lint.targetSdk = libs.versions.targetSdk.get().toInt()
    }

    lint {
        abortOnError = false
        checkReleaseBuilds = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    namespace = "com.meituan.android.walle"

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.androidx.annotation)
    implementation(project(":payload_reader"))
}

java{
    targetCompatibility = JavaVersion.VERSION_17
    sourceCompatibility = JavaVersion.VERSION_17
}
