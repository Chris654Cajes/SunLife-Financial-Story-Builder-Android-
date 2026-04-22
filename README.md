# Financial Story Builder

A comprehensive Android application that helps users understand their financial protection needs through interactive scenario analysis and visualization.

## Features

### 1. **Input Screen (InputActivity)**
- Age input with validation (18-100 years)
- Annual income entry with decimal support
- Number of dependents field
- Real-time input validation
- Intuitive Material Design UI

### 2. **Financial Calculations**
- **Income Replacement**: Calculates annual income needed until retirement (65)
- **Education Fund**: Estimates education costs for dependents (4 years per dependent)
- **Financial Gap**: Total protection needed = Income Replacement + Education + Emergency Fund + Dependent Buffer
- Inflation adjustment (3% annually)
- Comprehensive financial modeling

### 3. **Result Screen (ResultActivity)**
- Generated financial scenario with key metrics
- Detailed financial gap analysis
- MPAndroidChart bar chart visualization
- Comparison: With Insurance vs Without Insurance
- Color-coded insights and recommendations

### 4. **Chart Visualization**
- MPAndroidChart integration
- Bar chart comparison
- Green bar: Protection with insurance
- Red bar: Current financial gap
- Animated chart rendering
- Responsive to different screen sizes

## Project Structure

```
FinancialStoryBuilder/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/financialstorybuilder/
│   │   │   │   ├── InputActivity.kt          # Input screen logic
│   │   │   │   ├── ResultActivity.kt         # Results screen logic
│   │   │   │   ├── FinancialViewModel.kt     # Data management
│   │   │   │   └── FinancialCalculator.kt    # Financial calculations
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_input.xml    # Input screen layout
│   │   │   │   │   └── activity_result.xml   # Result screen layout
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml           # String resources
│   │   │   │   │   ├── colors.xml            # Color palette
│   │   │   │   │   └── themes.xml            # Theme definitions
│   │   │   │   └── drawable/
│   │   │   │       ├── edit_text_background.xml
│   │   │   │       ├── button_background.xml
│   │   │   │       ├── button_secondary_background.xml
│   │   │   │       └── chart_background.xml
│   │   │   └── AndroidManifest.xml
│   │   └── test/                             # Unit tests
│   └── build.gradle
├── build.gradle                              # Project-level config
├── settings.gradle                           # Project settings
├── gradle.properties                         # Gradle properties
└── README.md                                 # This file
```

## Tech Stack

- **Language**: Kotlin with K2 compiler ready
- **Architecture**: MVVM with ViewModel
- **UI Framework**: AndroidX AppCompat
- **Charting**: MPAndroidChart v3.1.0
- **Build Tool**: Gradle 7.4.2
- **Target SDK**: Android 13 (API 33)
- **Min SDK**: Android 5.0 (API 21)

## Dependencies

```gradle
// AndroidX
androidx.core:core-ktx:1.9.0
androidx.appcompat:appcompat:1.5.1
androidx.constraintlayout:constraintlayout:2.1.4
androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1

// Material Design
com.google.android.material:material:1.7.0

// Charts
com.github.PhilJay:MPAndroidChart:v3.1.0

// Kotlin
kotlin-stdlib:1.8.10
```

## How to Use

### 1. Input Your Information
- Enter your current age
- Input your gross annual income
- Specify number of dependents

### 2. Generate Your Story
- Click "Generate My Financial Story"
- The app validates all inputs
- Shows error messages for invalid data

### 3. View Results
- See your personalized financial scenario
- Review detailed financial gap analysis
- Study the comparison chart
- Understand insurance protection value

### 4. Financial Calculations Explained

**Income Replacement** (until age 65)
```
Annual Living Expenses = Annual Income × 70%
Total = Sum of inflated expenses for each year
```

**Education Fund**
```
Per Dependent = Sum of 4 years education costs
Total Education = Dependents × Education per dependent
```

**Financial Gap**
```
Total Gap = Income Replacement + Education Fund + Emergency Fund + Dependent Buffer
Emergency Fund = 6 months of expenses
Dependent Buffer = $50,000 per dependent
```

## Installation & Setup

### Requirements
- Android Studio Giraffe or newer
- Java 11+
- Gradle 7.4.2+

### Steps

1. **Extract Project**
   ```bash
   unzip FinancialStoryBuilder.zip
   cd FinancialStoryBuilder
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - File → Open → Select project folder

3. **Sync Gradle**
   - Android Studio will prompt to sync
   - Click "Sync Now"

4. **Run on Device/Emulator**
   - Connect device or start emulator
   - Click "Run" or press Shift+F10

### Build APK
```bash
./gradlew assembleDebug      # Debug APK
./gradlew assembleRelease    # Release APK
```

## Calculation Examples

### Example 1: Young Family
- **Age**: 35
- **Income**: $75,000
- **Dependents**: 2

**Results**:
- Income Replacement: ~$945,000
- Education Fund: ~$80,000
- Total Financial Gap: ~$1,140,000

### Example 2: Single Professional
- **Age**: 28
- **Income**: $120,000
- **Dependents**: 0

**Results**:
- Income Replacement: ~$1,950,000
- Education Fund: $0
- Total Financial Gap: ~$2,025,000

## UI Components

### Material Design
- Primary Color: #1976D2 (Blue)
- Secondary Color: #26A69A (Teal)
- Error Color: #D32F2F (Red)
- Success Color: #2E7D32 (Green)

### Typography
- Headers: 26-28sp, Bold
- Subheaders: 16sp, Bold
- Body text: 13-14sp
- Hints: 12sp

### Drawables
- Rounded corners (8dp radius)
- Gradient buttons with borders
- Custom EditText styling
- Chart background containers

## Testing

### Unit Tests
```bash
./gradlew test
```

### Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

## Code Quality

- **Pattern**: MVVM Architecture
- **Data Flow**: Activity → ViewModel → Calculator
- **Separation**: UI (XML) completely separate from Logic (Kotlin)
- **Validation**: Input validation on both client and logic layer

## Future Enhancements

1. **Advanced Scenarios**
   - Inflation rate adjustment
   - Retirement spending changes
   - Multiple income scenarios

2. **Data Persistence**
   - Save scenarios locally
   - Compare multiple scenarios
   - Export reports as PDF

3. **Additional Features**
   - Dark mode support
   - Multi-language support
   - Pie chart for expense breakdown
   - Risk assessment calculator

4. **Analytics**
   - Track common financial gaps
   - Gender/age demographic analysis
   - Regional comparison data

## Troubleshooting

### Build Errors
- **Gradle sync fails**: Delete `.gradle` folder, resync
- **SDK version issues**: Update Android SDK to API 33+
- **MPAndroidChart not found**: Check `settings.gradle` has JitPack repo

### Runtime Errors
- **Chart not displaying**: Ensure data is non-empty
- **Input validation fails**: Check input ranges (Age: 18-100)
- **ViewModel null**: Use ViewModelProvider instead of direct instantiation

## License

This project is provided as-is for educational and commercial use.

## Support

For issues or questions:
1. Check the troubleshooting section
2. Review code comments
3. Verify dependencies in build.gradle
4. Check Android logcat for error details

---

**Version**: 1.0.0  
**Last Updated**: April 2026  
**Compatibility**: Android 5.0+  
**Copyright © 2026** - All rights reserved
