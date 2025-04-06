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
# ========== General ==========
-keepattributes *Annotation*, Signature, Exceptions, InnerClasses, EnclosingMethod, RuntimeVisibleAnnotations, AnnotationDefault

# Prevent removing classes annotated with @Keep
-keep @androidx.annotation.Keep class * { *; }
-keepclassmembers class * {
    @androidx.annotation.Keep *;
}

# Retrofit and OkHttp
-dontwarn okhttp3.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }

# Keep annotations for Retrofit to resolve generic types
-keepattributes Signature, RuntimeVisibleAnnotations, RuntimeInvisibleAnnotations, AnnotationDefault

# Keep all model classes with their fields and generics
-keepclassmembers class com.app.assessment.model.** {
    <fields>;
    <methods>;
}

# Keep suspend functions metadata
-keepclassmembers class * {
    @kotlin.Metadata *;
}


# ========== Gson ==========
-keep class com.google.gson.** { *; }
-keepattributes *Annotation*

# ========== Kotlin Coroutines ==========
-dontwarn kotlinx.coroutines.**
-keepclassmembers class * {
    @kotlin.coroutines.** <methods>;
}

# ========== Room Database ==========
-keep class androidx.room.** { *; }
-keepclassmembers class * {
    @androidx.room.* <methods>;
    @androidx.room.* <fields>;
}

# ========== Dagger / Hilt ==========
-keep class dagger.** { *; }
-keep interface dagger.** { *; }
-keep class javax.inject.** { *; }

# Needed for Hilt code generation
-keep class * extends dagger.hilt.internal.GeneratedComponent { *; }
-keep class * extends dagger.hilt.android.internal.managers.* { *; }

# ========== AndroidX Biometric & Security ==========
-keep class androidx.biometric.** { *; }
-keep class androidx.security.** { *; }

# ========== Application / Activities ==========
-keep class com.app.assessment.MyApp { *; }
#-keep class com.app.assessment.MainActivity { *; }

-keep @androidx.annotation.Keep class * { *; }


