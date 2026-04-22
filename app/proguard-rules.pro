# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html

-dontobfuscate
-verbose

# Keep line numbers for debugging
-keepattributes SourceFile,LineNumberTable

# Keep all classes and methods in your app
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep custom application classes
-keep class com.example.financialstorybuilder.** { *; }

# Keep AndroidX
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

# Keep Material Design
-keep class com.google.android.material.** { *; }

# Keep Kotlin
-keep class kotlin.** { *; }
-keep class kotlinx.** { *; }

# Keep MPAndroidChart
-keep class com.github.mikephil.charting.** { *; }

# Keep ViewModel
-keep class androidx.lifecycle.ViewModel { *; }

# Preserve line numbers for stack traces
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

# Workaround for Kotlin
-dontwarn kotlin.Unit
-dontwarn kotlin.**
