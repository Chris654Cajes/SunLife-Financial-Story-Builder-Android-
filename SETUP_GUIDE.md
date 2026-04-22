# Financial Story Builder - Setup Guide

## Quick Start (5 Minutes)

### Prerequisites
- Android Studio 2022.1.1 or newer (Giraffe recommended)
- JDK 11 or higher
- Android SDK 33 (compileSdk)
- Minimum API 21 (minSdk)

### Installation Steps

#### 1. Extract the ZIP File
```bash
unzip FinancialStoryBuilder.zip
cd FinancialStoryBuilder
```

#### 2. Open in Android Studio
1. Launch Android Studio
2. Click **File** → **Open**
3. Navigate to the `FinancialStoryBuilder` folder
4. Click **OK**

#### 3. Wait for Gradle Sync
- Android Studio will automatically start Gradle sync
- If it doesn't, click **File** → **Sync with Gradle Files**
- Wait for the sync to complete (may take 2-5 minutes on first run)

#### 4. Run the App
**On Android Emulator:**
1. Click **AVD Manager** (device icon in toolbar)
2. Create or select an emulator (API 30+ recommended)
3. Start the emulator
4. Click **Run** or press **Shift+F10**

**On Physical Device:**
1. Enable Developer Mode: Settings → About Phone → tap Build Number 7 times
2. Enable USB Debugging: Settings → Developer Options → USB Debugging
3. Connect phone via USB cable
4. Click **Run** in Android Studio

#### 5. Grant Permissions
- App may request runtime permissions
- Grant as needed for full functionality

---

## Detailed Configuration

### Android SDK Setup
If you don't have API 33 installed:

1. Open **SDK Manager** (Tools → SDK Manager)
2. Go to **SDK Platforms** tab
3. Check **Android 13.0 (API 33)**
4. Click **Apply** → **OK**
5. Wait for download and installation

### Gradle Build System

**Understanding the Build Files:**

- **build.gradle (Project)**: Top-level configuration
  - Contains plugin versions
  - Repository configuration
  - Shared properties

- **app/build.gradle (App)**: App-specific configuration
  - compileSdk, targetSdk, minSdk
  - Dependencies
  - Build types (debug/release)

- **settings.gradle**: Project structure
  - JitPack repository for MPAndroidChart
  - Module configuration

- **gradle.properties**: Build properties
  - Java heap size
  - AndroidX migration settings

### Troubleshooting Gradle Issues

**Problem: "Gradle sync failed"**
```bash
# Solution 1: Clean gradle cache
rm -rf ~/.gradle
rm -rf .gradle
./gradlew clean

# Solution 2: Update gradle wrapper
./gradlew wrapper --gradle-version 7.4.2
```

**Problem: "SDK version mismatch"**
```bash
# Update SDK to API 33
sdkmanager "platforms;android-33"
```

**Problem: "Dependencies not found"**
- Check internet connection
- Verify JitPack repo in `settings.gradle`
- Run: `./gradlew dependencies --refresh-dependencies`

---

## Project Structure Explained

```
FinancialStoryBuilder/
│
├── app/                              # Main app module
│   ├── src/main/
│   │   ├── java/
│   │   │   └── com/example/financialstorybuilder/
│   │   │       ├── InputActivity.kt           ← Entry point
│   │   │       ├── ResultActivity.kt          ← Results screen
│   │   │       ├── FinancialViewModel.kt      ← Data management
│   │   │       └── FinancialCalculator.kt     ← Calculations
│   │   │
│   │   └── res/
│   │       ├── layout/
│   │       │   ├── activity_input.xml         ← Input UI
│   │       │   └── activity_result.xml        ← Result UI
│   │       │
│   │       ├── drawable/                      ← UI shapes
│   │       │   ├── edit_text_background.xml
│   │       │   ├── button_background.xml
│   │       │   └── chart_background.xml
│   │       │
│   │       └── values/
│   │           ├── strings.xml                ← String resources
│   │           ├── colors.xml                 ← Color palette
│   │           └── themes.xml                 ← Themes
│   │
│   ├── build.gradle                  ← App dependencies
│   └── proguard-rules.pro            ← ProGuard rules
│
├── build.gradle                      ← Project config
├── settings.gradle                   ← Module settings
├── gradle.properties                 ← Build properties
├── README.md                         ← Documentation
├── SETUP_GUIDE.md                    ← This file
└── .gitignore                        ← Git ignore rules
```

---

## Building APK Files

### Debug APK (for testing)
```bash
./gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk
```

### Release APK (for distribution)

**Step 1: Create a signing key (one-time)**
```bash
keytool -genkey -v -keystore my-release-key.jks \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias my-key-alias
```

**Step 2: Build signed release**
```bash
./gradlew assembleRelease \
  -Pandroid.injected.signing.store.file=my-release-key.jks \
  -Pandroid.injected.signing.store.password=your_password \
  -Pandroid.injected.signing.key.alias=my-key-alias \
  -Pandroid.injected.signing.key.password=your_password
```

**Output**: `app/build/outputs/apk/release/app-release.apk`

---

## Dependencies Explained

| Dependency | Purpose | Version |
|-----------|---------|---------|
| androidx.core:core-ktx | AndroidX core utilities | 1.9.0 |
| androidx.appcompat | Material Design v2 | 1.5.1 |
| androidx.lifecycle | ViewModel/LiveData | 2.5.1 |
| com.google.android.material | Material Design v3 | 1.7.0 |
| com.github.PhilJay:MPAndroidChart | Charts library | v3.1.0 |
| kotlin-stdlib | Kotlin standard library | 1.8.10 |

### Adding New Dependencies

1. Edit `app/build.gradle`
2. Add to `dependencies` block:
```gradle
implementation 'com.example:library:1.0.0'
```
3. Click **Sync Now** or run `./gradlew sync`

---

## Running Tests

### Unit Tests
```bash
./gradlew test
# Output: app/build/reports/tests/testDebugUnitTest/
```

### Instrumented Tests (on device/emulator)
```bash
./gradlew connectedAndroidTest
```

### Code Coverage
```bash
./gradlew testDebugCoverage
# Output: app/build/reports/coverage/
```

---

## Performance Optimization

### Reduce Build Time
```bash
# Enable parallel builds
./gradlew build --parallel

# Use local Maven repository
./gradlew build --offline
```

### Monitor App Performance
1. **Android Profiler**: Tools → Profiler → Run
2. **Layout Inspector**: Tools → Layout Inspector
3. **Database Inspector**: Tools → Database Inspector (if using Room)

---

## Common Tasks

### Update Android Gradle Plugin
Edit `build.gradle`:
```gradle
classpath "com.android.tools.build:gradle:7.4.2"
```

### Update Kotlin Version
Edit `build.gradle`:
```gradle
classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10"
```

### Change App Name
Edit `app/src/main/res/values/strings.xml`:
```xml
<string name="app_name">Your New App Name</string>
```

### Change Package Name
1. Right-click package in Project view
2. Select **Refactor** → **Rename**
3. Update `AndroidManifest.xml` package attribute

---

## Emulator Tips

### Create High-Performance Emulator
1. **AVD Manager** → **Create Virtual Device**
2. Select: **Pixel 6** (good balance)
3. API Level: **33 (Android 13)**
4. RAM: **2GB or more**
5. Storage: **2GB**

### Speed Up Emulator
- Use **x86_64** architecture (if available)
- Enable **KVM** on Linux
- Use **Hyper-V** on Windows
- Allocate 4GB+ RAM to emulator

### Issues with Emulator
```bash
# Kill all emulator processes
adb kill-server

# Restart emulator
emulator -avd pixel_6_api_33
```

---

## IDE Shortcuts

| Shortcut | Action |
|----------|--------|
| Ctrl/Cmd + Shift + F10 | Run App |
| Ctrl/Cmd + F9 | Build Project |
| Ctrl/Cmd + Alt + L | Format Code |
| Ctrl/Cmd + / | Toggle Comment |
| Alt + Enter | Show Intentions |
| Ctrl/Cmd + Shift + O | Optimize Imports |

---

## Debugging

### Debug App
1. Set breakpoint (click line number)
2. Click **Debug** instead of **Run**
3. Use debugger window to inspect variables
4. Step through code: F10 (step over), F11 (step into)

### View Logs
1. **Logcat** window (bottom of Android Studio)
2. Filter by app name or tag
3. Clear with trash icon
4. Search with Ctrl+F

### Common Errors

**Error: "MainActivity not found"**
- Check `AndroidManifest.xml` has correct activity name
- Verify activity class exists in source

**Error: "Resource not found"**
- Run `./gradlew clean`
- Rebuild project

**Error: "Gradle sync failed"**
- Check internet connection
- Update Gradle: File → Settings → Gradle

---

## Next Steps

1. ✅ Follow setup instructions above
2. ✅ Run the app on emulator/device
3. ✅ Test input validation
4. ✅ Review financial calculations
5. ✅ Customize colors in `colors.xml`
6. ✅ Modify calculations in `FinancialCalculator.kt`
7. ✅ Build and release APK

---

## Support & Resources

### Official Documentation
- [Android Developers](https://developer.android.com/)
- [Kotlin Documentation](https://kotlinlang.org/docs/)
- [Android Studio Guide](https://developer.android.com/studio/intro)
- [MPAndroidChart Wiki](https://github.com/PhilJay/MPAndroidChart/wiki)

### Useful Tools
- Android Studio built-in inspections
- Lint warnings (quality checks)
- Layout preview (XL Previews)
- Profiler (memory, CPU, network)

### Community
- Stack Overflow: Tag `android` or `kotlin`
- Android Developers Reddit: r/androiddev
- GitHub Issues for library problems

---

**Last Updated**: April 2026  
**Android Studio Version**: Giraffe or newer  
**Kotlin Version**: 1.8.10  
**Target API**: 33 (Android 13)
