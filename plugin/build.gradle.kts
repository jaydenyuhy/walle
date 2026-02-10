apply(from = "../maven-publish.gradle")
plugins {
    id("groovy")
    id("java-gradle-plugin")
}

gradlePlugin {
    plugins {

        //插件名,每一个插件都可以有
        create("walle-plugin") {
            val GROUP_ID: String by project.extra
            id = GROUP_ID
            implementationClass = "com.meituan.android.walle.GradlePlugin"
        }
    }
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.commons.io)
    implementation(libs.commons.codec)
    implementation(libs.commons.lang3)
    implementation(libs.json)
    implementation(libs.gson)
    implementation(libs.gradle)
    implementation(libs.bouncycastle.bcpkix)
    implementation(libs.bouncycastle.bcprov)
    implementation(libs.walle.payload.writer)
}

java{
    targetCompatibility = JavaVersion.VERSION_17
    sourceCompatibility = JavaVersion.VERSION_17
}
