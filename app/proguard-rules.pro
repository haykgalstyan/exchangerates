# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keep,includedescriptorclasses class galstyan.hayk.exchangerates.**$$serializer { *; }
-keepclassmembers class galstyan.hayk.exchangerates.** {
    *** Companion;
}
-keepclasseswithmembers class galstyan.hayk.exchangerates.** {
    kotlinx.serialization.KSerializer serializer(...);
}
