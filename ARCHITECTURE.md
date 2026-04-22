# Financial Story Builder - Architecture Guide

## Overview

The Financial Story Builder uses **MVVM (Model-View-ViewModel)** architecture with clean separation of concerns.

```
┌─────────────────────────────────────────────────┐
│              UI Layer (Activities)              │
│   InputActivity          ResultActivity         │
│   (Input Screen)         (Result Screen)        │
└────────────────┬──────────────────┬─────────────┘
                 │                  │
                 ▼                  ▼
┌─────────────────────────────────────────────────┐
│         ViewModel Layer (Data Management)       │
│          FinancialViewModel                     │
│  - Handles state management                     │
│  - Manages data between activities              │
│  - Survives configuration changes               │
└────────────────┬──────────────────┬─────────────┘
                 │                  │
                 ▼                  ▼
┌─────────────────────────────────────────────────┐
│         Business Logic Layer (Calculator)       │
│          FinancialCalculator                    │
│  - Income replacement calculation               │
│  - Education fund calculation                   │
│  - Financial gap calculation                    │
│  - Inflation adjustments                        │
└─────────────────────────────────────────────────┘
```

---

## Architecture Patterns

### 1. MVVM (Model-View-ViewModel)

**Benefits:**
- ✅ Clear separation of concerns
- ✅ Testable business logic
- ✅ Lifecycle-aware data management
- ✅ Survives configuration changes (rotation)

**Implementation:**
```
Model (Data)           → FinancialCalculator
ViewModel (Presenter)  → FinancialViewModel  
View (UI)              → InputActivity, ResultActivity
```

### 2. Dependency Injection (Constructor)

**Example:**
```kotlin
class ResultActivity : AppCompatActivity() {
    // Not injected - simple case
    private lateinit var viewModel: FinancialViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Use ViewModelProvider for lifecycle-aware creation
        viewModel = ViewModelProvider(this).get(FinancialViewModel::class.java)
    }
}
```

### 3. Separation of Concerns

| Layer | Responsibility | Files |
|-------|-----------------|-------|
| **UI/View** | Display data, handle user input | `InputActivity.kt`, `ResultActivity.kt`, `*.xml` |
| **ViewModel** | Manage data, coordinate between layers | `FinancialViewModel.kt` |
| **Business Logic** | Calculations, data processing | `FinancialCalculator.kt` |

---

## Data Flow Diagram

```
┌─────────────────────────────────────────────────────┐
│ User Input Screen (InputActivity)                   │
│ • Age: [____]                                       │
│ • Income: [____]                                    │
│ • Dependents: [____]                                │
│ [Generate Story Button]                             │
└────────────────┬────────────────────────────────────┘
                 │ User clicks button
                 ▼
        ┌─────────────────────┐
        │  Input Validation   │
        │  • Check age 18-100  │
        │  • Check income > 0  │
        │  • Check valid count │
        └────────┬────────────┘
                 │ Valid?
        ┌────────▼────────┐
        │  YES   │   NO   │
        │        │        │
        │        ▼        │
        │   Show Error    │
        │        │        │
        │        ▼        │
        │    Stay on      │
        │    Input Screen │
        │                 │
        ▼                 │
┌──────────────────────┐ │
│ Store in ViewModel   │ │
│ • age: Int           │ │
│ • income: Double     │ │
│ • dependents: Int    │ │
└─────────┬────────────┘ │
          │              │
          ▼              │
  ┌───────────────────┐  │
  │ Calculate Results │  │
  │ via Calculator    │  │
  └─────────┬─────────┘  │
            │            │
            ▼            │
  ┌──────────────────────────────┐
  │ FinancialData object:        │
  │ • incomeReplacement: Double  │
  │ • educationFund: Double      │
  │ • financialGap: Double       │
  │ • scenario: String           │
  └─────────┬────────────────────┘
            │
            ▼
  ┌──────────────────────┐
  │ Navigate to Result   │
  │ Activity via Intent  │
  └─────────┬────────────┘
            │
            ▼
┌──────────────────────────────────┐
│ Result Screen (ResultActivity)   │
│ • Display Scenario               │
│ • Show Financial Gap             │
│ • Render Chart Visualization     │
│ • Compare With/Without Insurance │
└──────────────────────────────────┘
```

---

## Class Responsibilities

### InputActivity.kt
**Purpose:** Collect user information

```kotlin
class InputActivity : AppCompatActivity() {
    // Responsibilities:
    // 1. Inflate XML layout
    // 2. Bind UI elements
    // 3. Validate user input
    // 4. Pass valid data to ViewModel
    // 5. Navigate to ResultActivity
}
```

**Key Methods:**
- `onCreate()`: Initialize UI components
- `validateAndProceed()`: Validate input and navigate

**Validation Rules:**
```
Age:        18 ≤ age ≤ 100
Income:     income > 0
Dependents: dependents ≥ 0
```

---

### ResultActivity.kt
**Purpose:** Display calculated results and visualizations

```kotlin
class ResultActivity : AppCompatActivity() {
    // Responsibilities:
    // 1. Retrieve calculated data from ViewModel
    // 2. Display scenario text
    // 3. Display financial gap analysis
    // 4. Render MPAndroidChart visualization
    // 5. Handle back navigation
}
```

**Key Methods:**
- `onCreate()`: Initialize UI and retrieve data
- `displayResults()`: Show financial analysis
- `setupBarChart()`: Configure and render chart

**Chart Data:**
```
Entry 0: Income with insurance (Green)
Entry 1: Financial gap without insurance (Red)
```

---

### FinancialViewModel.kt
**Purpose:** Manage application state and data

```kotlin
class FinancialViewModel : ViewModel() {
    // Responsibilities:
    // 1. Store user input (age, income, dependents)
    // 2. Generate financial scenario description
    // 3. Coordinate calculations via FinancialCalculator
    // 4. Return FinancialData object to activities
    // 5. Persist data across configuration changes
}
```

**Key Methods:**
- `setUserData()`: Store user input
- `generateFinancialStory()`: Trigger calculation
- `getFinancialData()`: Return calculated results
- `generateScenario()`: Create scenario description

**Benefits:**
- ✅ Survives screen rotation
- ✅ Shared between activities
- ✅ Automatic cleanup on activity destruction

---

### FinancialCalculator.kt
**Purpose:** Perform all financial calculations

```kotlin
class FinancialCalculator(
    val age: Int,
    val annualIncome: Double,
    val dependents: Int
) {
    // Responsibilities:
    // 1. Calculate income replacement until retirement
    // 2. Calculate education fund for dependents
    // 3. Calculate total financial gap
    // 4. Apply inflation adjustments
    // 5. Return calculated values
}
```

**Key Methods:**
- `calculateIncomeReplacement()`: Income until age 65
- `calculateEducationFund()`: Education costs
- `calculateFinancialGap()`: Total protection needed

**Calculation Constants:**
```kotlin
RETIREMENT_AGE = 65
LIFE_EXPECTANCY = 85
ANNUAL_EDUCATION_COST = $8,000
EDUCATION_YEARS = 4
EXPENSE_MULTIPLE = 70% of income
INFLATION_RATE = 3%
```

---

## Data Classes

### FinancialData
Immutable data holder for calculation results

```kotlin
data class FinancialData(
    val age: Int,
    val income: Double,
    val dependents: Int,
    val incomeReplacement: Double,
    val educationFund: Double,
    val financialGap: Double,
    val scenario: String
)
```

**Usage:**
```kotlin
val data = viewModel.getFinancialData()
println("Gap: $${data.financialGap}")
```

---

## Lifecycle Management

### Activity Lifecycle
```
onCreate()
   ↓
onStart()
   ↓
onResume() ← App visible to user
   ↓
onPause()  ← User left app (HOME button)
   ↓
onStop()
   ↓
onDestroy() ← Activity destroyed
```

### ViewModel Lifecycle
```
Created ← ViewModelProvider.get()
   ↓
Active  ← While Activity exists
   ↓
Cleared ← When Activity destroyed
```

**Key Point:** ViewModel outlives Activity destruction during configuration changes!

---

## Configuration Changes

When device rotates (landscape ↔ portrait):

```
Without ViewModel:
Activity created → onCreate() → Recreate UI ✗ Lose data

With ViewModel:
Activity destroyed → ViewModel persists data
Activity recreated → retrieve same ViewModel ✓ Data retained
```

---

## Calculation Details

### Income Replacement Formula
```
Annual Living Expense = Annual Income × 70%

Total Replacement = Σ(for year 1 to retirement years)
                      Annual Expense × (1.03)^(year-1)

Example (Age 35, Income $75,000):
- Years to retirement: 30
- Annual expense: $75,000 × 0.70 = $52,500
- With 3% inflation per year
- Total: ~$945,000
```

### Education Fund Formula
```
Cost per Dependent = Σ(for 4 education years)
                       $8,000 × (1.03)^(years to education start + year)

Example (1 dependent, age 35):
- Dependent starts education at age 18 (17 years from now)
- Each year adjusted for inflation
- Total per dependent: ~$40,000

Total Education Fund = Cost per Dependent × Number of Dependents
```

### Financial Gap Formula
```
Total Gap = Income Replacement 
          + Education Fund 
          + Emergency Fund (6 months expenses)
          + Dependent Buffer ($50,000 per dependent)

Example:
- Income Replacement: $945,000
- Education Fund: $80,000
- Emergency Fund: $31,500
- Dependent Buffer: $100,000 (2 dependents × $50K)
- TOTAL: $1,156,500
```

---

## Testing Strategy

### Unit Tests (Future Implementation)

```kotlin
// Test FinancialCalculator
@Test
fun testIncomeReplacementCalculation() {
    val calc = FinancialCalculator(35, 75000.0, 2)
    val result = calc.calculateIncomeReplacement()
    assertTrue(result > 0)
    assertTrue(result < 1000000)
}

@Test
fun testEducationFundCalculation() {
    val calc = FinancialCalculator(35, 75000.0, 2)
    val result = calc.calculateEducationFund()
    assertEquals(result, expectedValue, 0.01)
}
```

### Integration Tests (Future Implementation)

```kotlin
@Test
fun testInputToResultFlow() {
    // Test: User input → ViewModel → Calculation → Result
    activityRule.launchActivity(Intent())
    
    onView(withId(R.id.ageInput)).perform(typeText("35"))
    onView(withId(R.id.incomeInput)).perform(typeText("75000"))
    onView(withId(R.id.dependentsInput)).perform(typeText("2"))
    onView(withId(R.id.generateButton)).perform(click())
    
    // Verify ResultActivity starts
    intended(hasComponent(ResultActivity::class.java.name))
}
```

---

## Extension Points

### Adding New Calculations

```kotlin
// Add to FinancialCalculator.kt
fun calculateRetirementFund(): Double {
    // New calculation logic
}

// Add to FinancialViewModel.kt
fun getRetirementFund(): Double {
    val calculator = FinancialCalculator(age, income, dependents)
    return calculator.calculateRetirementFund()
}

// Use in ResultActivity.kt
val retirementFund = viewModel.getFinancialData().retirementFund
```

### Adding New Chart Types

```kotlin
// In ResultActivity.kt
private fun setupPieChart() {
    val educationPercent = financialData.educationFund / financialData.financialGap
    
    val entries = arrayListOf(
        PieEntry(educationPercent.toFloat(), "Education"),
        PieEntry((1 - educationPercent).toFloat(), "Other")
    )
    
    val dataset = PieDataSet(entries, "Expense Breakdown")
    val data = PieData(dataset)
    pieChart.data = data
}
```

---

## Error Handling

### Validation Layer

```kotlin
// In InputActivity.kt
when {
    age.isEmpty() → Toast.makeText(this, "Please enter age", LENGTH_SHORT).show()
    age.toInt() < 18 → Toast.makeText(this, "Age must be 18+", LENGTH_SHORT).show()
    income.isEmpty() → Toast.makeText(this, "Please enter income", LENGTH_SHORT).show()
}
```

### Calculation Safety

```kotlin
// In FinancialCalculator.kt
if (dependents == 0) return 0.0  // Handle edge case
val totalGap = Math.max(incomeReplacement + educationFund, 0.0)  // Prevent negatives
```

---

## Performance Considerations

### Memory
- ✅ ViewModel automatically cleaned up
- ✅ No memory leaks from async operations
- ✅ Calculations complete quickly (<100ms)

### CPU
- ✅ Calculations use basic arithmetic
- ✅ Chart rendering is efficient
- ✅ No heavy processing on main thread

### Network
- ✅ No network calls in app
- ✅ Pure local calculations
- ✅ Instant results

---

## Future Architecture Improvements

### 1. Repository Pattern
```kotlin
interface FinancialRepository {
    fun calculateGap(userData: UserData): FinancialData
}

class FinancialRepositoryImpl : FinancialRepository {
    override fun calculateGap(userData: UserData): FinancialData {
        return FinancialCalculator(...).calculate()
    }
}
```

### 2. Dependency Injection (Hilt)
```kotlin
@HiltViewModel
class FinancialViewModel @Inject constructor(
    private val repository: FinancialRepository
) : ViewModel() { }
```

### 3. Database (Room)
```kotlin
@Entity(tableName = "financial_scenarios")
data class FinancialScenario(
    @PrimaryKey val id: Int,
    val age: Int,
    val income: Double,
    val calculatedGap: Double
)
```

### 4. Networking (Retrofit)
```kotlin
interface FinancialApi {
    @GET("api/insurance-rates")
    suspend fun getInsuranceRates(): List<InsuranceRate>
}
```

---

## Summary

- **Architecture**: MVVM with clear layer separation
- **Data Flow**: UI → ViewModel → Calculator → Results
- **Lifecycle**: Activities are recreated, ViewModel persists
- **Calculations**: Deterministic, inflation-adjusted, comprehensive
- **Error Handling**: Input validation + edge case handling
- **Performance**: Fast calculations, efficient UI updates
- **Extensibility**: Easy to add calculations, charts, features

---

**Last Updated**: April 2026  
**Architecture Pattern**: MVVM  
**Kotlin Version**: 1.8.10  
**Target API**: 33 (Android 13)
