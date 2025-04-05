# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# Keep model classes with Gson (or Moshi)
-keepclassmembers class com.app.assessment.model.** {
    <fields>;
    <methods>;
}

# Retrofit
-dontwarn okhttp3.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# Gson
-keep class com.google.gson.** { *; }
-keepattributes *Annotation*

# Hilt / Dagger
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }

# Keep Room entities and DAOs
-keep class androidx.room.** { *; }
-keep class com.app.assessment.data.** { *; }

# Biometric / Security Crypto
-keep class androidx.biometric.** { *; }
-keep class androidx.security.** { *; }

# Kotlin coroutines
-dontwarn kotlinx.coroutines.**

# Prevent removing @Keep
-keep @androidx.annotation.Keep class * { *; }

# Optional: Keep MainActivity and Application
-keep class com.app.assessment.MainActivity { *; }
-keep class com.app.assessment.MyApp { *; }
