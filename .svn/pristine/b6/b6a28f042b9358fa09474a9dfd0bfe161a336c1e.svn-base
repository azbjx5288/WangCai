# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\sdk/tools/proguard/proguard-android.txt
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

#butterknife库的：
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# Gson specific classes
-keep class com.google.gson.** { *; }
-keep interface com.google.gson.** { *; }
-dontwarn com.google.gson.**
-keep class sun.misc.Unsafe { *; }

-keep class android.support.** { *; }
-keep interface android.support.** { *; }
-dontwarn android.support.**

-keep class com.android.volley.** { *; }
-keep interface com.android.volley.** { *; }
-dontwarn com.android.volley.**

-keep class com.nostra13.universalimageloader.** { *; }
-keep interface com.nostra13.universalimageloader.** { *; }
-dontwarn com.nostra13.universalimageloader.**

-keep class org.jsoup.** { *; }
-keep interface org.jsoup.** { *; }
-dontwarn org.jsoup.**

-keepattributes *Annotation*
-keepattributes *JavascriptInterface*

# 玩法相关
-keep class * extends com.wangcai.lottery.game.Game {*;}

# 网络处理相关类
-keep class com.wangcai.lottery.base.net.** {*;}

# 网络接口对应的数据类
-keep class com.wangcai.lottery.data.** { *; }

#给WebView的js接口
-keep class **.**$JsInterface {*;}

-dontwarn java.lang.invoke.*

-dontwarn com.google.zxing.**
-keep class com.google.zxing.** {*;}

-dontwarn in.srain.**
-keep class in.srain.** {*;}

-dontwarn com.itrus.**
-keep class com.itrus.** {*;}

-dontwarn org.bouncycastle.**
-keep class org.bouncycastle.** {*;}

-dontwarn com.gyf.barlibrary.**
-keep class com.gyf.barlibrary.** {*;}

-dontwarn org.apache.cordova.**
-keep class org.apache.cordova.** {*;}

-keep class com.bumptech.glide.integration.volley.VolleyGlideModule
-keep public class * implements com.bumptech.glide.module.GlideModule

-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
