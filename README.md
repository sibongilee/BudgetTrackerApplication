# Budget Tracker Application

A comprehensive mobile application built with **Kotlin** for tracking personal finances, managing expenses, and setting budget goals.

---

## Overview

Budget Tracker is an Android app designed to help users take control of their financial life. Track daily spending, organize expenses by category, set budget limits, and gain valuable insights into spending habits.

---

## Features

### Implemented

| Feature | Description |
|---------|-------------|
| **User Authentication** | Login and Registration system with password validation |
| **Expense Tracking** | Add, view, and manage daily expenses |
| **Category Management** | Create custom spending categories |
| **Budget Settings** | Set minimum and maximum budget ranges using SeekBars |
| **Date Filtering** | Filter expenses by date range |
| **Total Spending** | View total expenses with automatic calculation |
| **SQLite Database** | Persistent local data storage |
| **Material Design UI** | Modern, user-friendly interface with CardViews |
| **Welcome Message** | Personalized greeting with username |

### Coming Soon

- Spending charts and analytics
- Export reports (PDF/CSV)
- Monthly spending summaries
- Recurring expense tracking
- Backup and restore functionality
- Expense photos/ receipts

---

## Tech Stack

| Category | Technology |
|----------|------------|
| **Language** | Kotlin |
| **Platform** | Android (API 21+ / Android 5.0+) |
| **Build System** | Gradle 7.4.2 |
| **Database** | SQLite (Custom DatabaseHelper) |
| **UI Framework** | Material Design Components |
| **Java Version** | JDK 17 |

---

## Getting Started

### Prerequisites

| Requirement | Version |
|-------------|---------|
| Android Studio | Hedgehog (2023.1.1) or newer |
| JDK | 17 |
| Android SDK | API 33+ |
| Gradle | 7.4.2 (configured in wrapper) |

### Installation

```bash
# Clone the repository
git clone https://github.com/sibongilee/BudgetTrackerApplication.git

# Open in Android Studio
# Click File → Open → Select project folder

# Sync Gradle
# Click File → Sync Project with Gradle Files

# Build the project
./gradlew build

# Run the app
./gradlew installDebug
```

---

## Project Structure

```
BudgetTrackerApplication/
├── app/src/main/
│   ├── java/.../budgettrackerapplication/
│   │   ├── LoginActivity.kt
│   │   ├── RegisterActivity.kt
│   │   ├── MainActivity.kt
│   │   ├── AddExpenseActivity.kt
│   │   ├── ViewExpensesActivity.kt
│   │   ├── AddCategoryActivity.kt
│   │   ├── BudgetActivity.kt
│   │   ├── DatabaseHelper.kt
│   │   ├── Expense.kt
│   │   └── ExpenseAdapter.kt
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_login.xml
│   │   │   ├── activity_register.xml
│   │   │   ├── activity_main.xml
│   │   │   ├── activity_add_expense.xml
│   │   │   ├── activity_view_expenses.xml
│   │   │   ├── activity_add_category.xml
│   │   │   ├── activity_budget.xml
│   │   │   └── item_expense.xml
│   │   ├── drawable/
│   │   │   ├── logo.png
│   │   │   └── spinner_background.xml
│   │   └── values/
│   │       ├── colors.xml
│   │       └── strings.xml
│   └── AndroidManifest.xml
└── build.gradle
```

---

## Database Schema

| Table | Columns |
|-------|---------|
| **users** | id, username, password |
| **categories** | id, name |
| **expenses** | id, amount, description, category, date, startTime, endTime, photoPath |
| **budgets** | id, minBudget, maxBudget |

---

## Color Palette

| Color | Hex Code | Usage |
|-------|----------|-------|
| Primary Green | `#2E7D32` | Buttons, headers, accents |
| Light Green | `#E8F5E9` | Category badges |
| Background Green | `#F1F8E9` | Main background |
| Text Gray | `#757575` | Secondary text |
| Dark Gray | `#424242` | Primary text |
| Error Red | `#D32F2F` | Error messages |
| Success Green | `#388E3C` | Success messages |

---

## Authentication Flow

1. **Login Screen** - Existing users enter credentials
2. **Register Screen** - New users create account (password must be 8+ characters)
3. **Dashboard** - Personalized welcome message with username
4. **Session persists** until user logs out

---

## Dependencies

```gradle
dependencies {
    implementation 'androidx.core:core-ktx:1.12.0'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.11.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'androidx.cardview:cardview:1.0.0'
    testImplementation 'junit:junit:4.13.2'
}
```

---

## Testing

```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

---

## Authors

| Author | GitHub |
|--------|--------|
| **Sibongile** | [@sibongilee](https://github.com/sibongilee) |
| **Natasha Mvundlela** | [@Natasha-Mvundlela](https://github.com/Natasha-Mvundlela) |
| **Ronewa** | [@ronewa1974](https://github.com/ronewa1974) |

---

## Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

---

## Support

- Open an [issue](https://github.com/sibongilee/BudgetTrackerApplication/issues)
- Contact the authors directly

---

## Show Your Support

If this project helps you, please give it a star on GitHub!

---

### Recent Updates

- Added separate Login and Register screens
- Fixed layout_width/layout_height attributes in all XML files
- Added password confirmation validation
- Personalized welcome message with username
- Fixed Gradle JDK compatibility (Java 17)
- Added SeekBar budget selection (R0 - R10,000)
- Date range filtering for expenses
- Material Design CardView UI components


This README now accurately reflects your current project state with all the fixes we've implemented!