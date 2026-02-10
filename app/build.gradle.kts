import com.android.build.api.dsl.ApplicationExtension
import org.gradle.kotlin.dsl.configure

plugins {
    alias(libs.plugins.android.application)
//    alias(libs.plugins.walle)
    id("com.github.jaydenyuhy.walle")
}

apply(from = rootProject.file("quality.gradle"))

//configurations.all {
//    resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS)
//}

extensions.configure<ApplicationExtension> {
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        versionCode = 1
        versionName = "1.0.0"

        applicationId = "com.meituan.android.key.sample"
        minSdk = libs.versions.minSdk.get().toInt()
        lint.targetSdk = libs.versions.targetSdk.get().toInt()

    }

    buildFeatures {
        buildConfig = true
    }

    signingConfigs {
        maybeCreate("release")
        getByName("release") {
            storeFile = project.file("key")
            storePassword = "12345678"
            keyAlias = "walle"
            keyPassword = "12345678"
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig =
                signingConfigs.getByName("release") as com.android.build.api.dsl.ApkSigningConfig
            isDebuggable = true
            isMinifyEnabled = false

        }

        getByName("release") {
            signingConfig =
                signingConfigs.getByName("release") as com.android.build.api.dsl.ApkSigningConfig
            isDebuggable = false

            isShrinkResources = true
            //是否开启混淆
            isMinifyEnabled = true
            //assumenosideeffects命令不能有dontoptimize
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

//    lint {
//        abortOnError = false
//        checkReleaseBuilds = false
//    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    namespace = "com.meituan.android.walle.sample"

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(libs.androidx.appcompat)
    implementation(libs.walle.library)
}

walle {
    apkOutputFolder = File("${project.buildDir}/outputs/channels")
    apkFileNameFormat = "\${appName}-\${packageName}-\${channel}-\${buildType}-v\${versionName}-\${versionCode}-\${buildTime}-\${flavorName}.apk"
    //configFile与channelFile两者必须存在一个，否则无法生成渠道包。两者都存在时优先执行configFile
    channelFile = File("${project.getProjectDir()}/channel_debug")
    //configFile = new File("${project.getProjectDir()}/config.json")
}