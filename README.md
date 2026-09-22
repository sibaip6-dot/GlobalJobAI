name: Build GlobalJob AI APK

on:
  workflow_dispatch:
  push:
    branches:
      - main

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Setup Java
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: "17"

      - name: Setup Android
        uses: android-actions/setup-android@v3

      - name: Install Android SDK
        run: |
          yes | sdkmanager "platforms;android-35" "build-tools;35.0.0"

      - name: Prepare Android project
        run: |
          mkdir -p app/src/main/java/com/globaljobai
          mkdir -p app/src/main/res/values

          mv AndroidManifest.xml app/src/main/AndroidManifest.xml
          mv MainActivity.kt app/src/main/java/com/globaljobai/MainActivity.kt
          mv styles.xml app/src/main/res/values/styles.xml

          cat > app/build.gradle.kts <<'EOF'
          plugins {
              id("com.android.application")
              id("org.jetbrains.kotlin.android")
          }

          android {
              namespace = "com.globaljobai"
              compileSdk = 35

              defaultConfig {
                  applicationId = "com.globaljobai"
                  minSdk = 24
                  targetSdk = 35
                  versionCode = 1
                  versionName = "1.0"
              }
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
          EOF

      - name: Install Gradle
        run: |
          curl -L -o gradle.zip https://services.gradle.org/distributions/gradle-8.9-bin.zip
          unzip -q gradle.zip
          echo "$PWD/gradle-8.9/bin" >> "$GITHUB_PATH"

      - name: Build APK
        run: gradle assembleDebug

      - name: Upload APK
        uses: actions/upload-artifact@v4
        with:
          name: GlobalJobAI-APK
          path: app/build/outputs/apk/debug/app-debug.apk
