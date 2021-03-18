

//引用插件
plugins {

    id ("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")//注解处理器参数插件
}
//Android属性
android {
    compileSdkVersion(AppConfig.compileSdkVersion)
    buildToolsVersion(AppConfig.buildToolsVersion)
    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdkVersion(AppConfig.minSdkVersion)
        targetSdkVersion(AppConfig.targetSdkVersion)
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        //ARouter软路由
        kapt {
            arguments {
                arg("AROUTER_MODULE_NAME", project.name)
            }
        }


    }

    //签名配置
   signingConfigs{
       register("release"){
           //别名
           keyAlias = "imooc"
           //别名密码
           keyPassword = "123456"
           //路径
           storeFile = file("src/main/jks/aivoice.jks")
           //签名密码
           storePassword = "123456"
       }
   }
    //编译类型
    buildTypes {
        getByName("debug") {

        }
        getByName("release")
         {
            isMinifyEnabled = false
             signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    //输出类型
    android.applicationVariants.all {
        //编译类型
        val buildType = this.buildType.name
        outputs.all {
            //输出APK
            if (this is  com.android.build.gradle.internal.api.ApkVariantOutputImpl){
                                             //版本                     类型
               if(buildType=="release") {
                   this.outputFileName="AI_V${defaultConfig.versionName}_$buildType.apk"
               }

            }
        }
    }


    //依赖操作
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility  = JavaVersion.VERSION_1_8
    }
}
//依赖
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
  /*  implementation (Dependencies.APP_COMPAT)
    implementation (Dependencies.Ktx_CORE)
    implementation (Dependencies.STD_LID)*/

    implementation ("org.jetbrains.kotlin:kotlin-stdlib:${KotlinConstants.kotlin_version}")
    implementation ("androidx.core:core-ktx:1.3.2")
    implementation ("androidx.appcompat:appcompat:1.2.0")

    if(!ModuleConfig.isApp){//如果不是APP就导入进来

    }

    //app 自带库
    implementation ("com.google.android.material:material:1.2.1")
    implementation ("androidx.constraintlayout:constraintlayout:2.0.4")
    testImplementation ("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")


    //运行时注解
 //   kapt(Dependencies.AROUTER_COMPILER)


}