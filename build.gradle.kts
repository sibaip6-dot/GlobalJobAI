plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
android { namespace = "com.globaljobai"; compileSdk = 35
    defaultConfig { applicationId = "com.globaljobai"; minSdk = 24; targetSdk = 35; versionCode = 1; versionName = "1.0" }
}
dependencies {
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation(platform("androidx.compose:compose-bom:2025.01.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.8.5")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
}
