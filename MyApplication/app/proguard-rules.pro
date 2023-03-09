# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/gaofei/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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
#-keep class com.example.mylibrary.Test2{
#    *;
#}
-keep class com.tencent.** {*;}
-dontwarn com.tencent.**
-dontwarn okio.*

#@proguard_debug_start
# ------------------ Keep LineNumbers and properties ---------------- #
#-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
#-renamesourcefileattribute SourceFile
#-keepattributes SourceFile,LineNumberTable
#@proguard_debug_end
-keepclassmembers class com.gaofei.app.act.TestInstance {
  public  <init>(java.lang.Integer);
}
