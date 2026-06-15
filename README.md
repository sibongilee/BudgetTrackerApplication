# Budget Tracker Application

A comprehensive mobile application built with **Kotlin** for tracking personal finances, managing expenses, and setting budget goals.

---

## Overview

Budget Tracker is an Android app designed to help users take control of their financial life. Track daily spending, organize expenses by category, set budget limits, and gain valuable insights into sp[...]

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
│   │   └── GraphActivity.kt
│   │   └──RewardsActivity.kt

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
│   │   │   └──activity_rewards.xml
│   │   │   └──activity_graph.xml

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
Design Decisions

Several design decisions were made during the development of this application:

A simple and user-friendly interface was designed using Material Design principles.
Green colour themes were selected to represent financial growth and stability.
SQLite was chosen for local data storage because it is lightweight and suitable for mobile applications.
CardViews were used throughout the application to create a modern and organized user interface.
Separate activities were created for Login, Registration, Expense Management, Category Management, Budget Goals, Rewards, and Spending Graphs to improve usability and navigation.
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
GitHub and GitHub Actions

GitHub was used throughout the project for version control and collaboration between team members. Changes were committed regularly to track development progress and maintain code history.

GitHub Actions was implemented to automate the application build process and verify that the project could be successfully built in a cloud environment. This helps ensure that the application works correctly across different systems and development environments.
---

## Testing

```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

---

## Recent Commits by ronewa1974

| Commit | Message | Date |
|--------|---------|------|
| [9c74df0](https://github.com/sibongilee/BudgetTrackerApplication/commit/9c74df04338f22666ac07c30d4bd77150a4af1ca) | Remove duplicate log statement in onCreate | 2026-06-15 19:09:08 |
| [76551f5](https://github.com/sibongilee/BudgetTrackerApplication/commit/76551f5daa7b297a1dbe94bc3a909e838706d1a0) | Log budget goals update in BudgetActivity | 2026-06-15 19:08:10 |
| [27d8cbf](https://github.com/sibongilee/BudgetTrackerApplication/commit/27d8cbf9563e86d58ead2199fedab9b8aa7a67af) | Log success message when a category is added | 2026-06-15 19:06:44 |
| [372e034](https://github.com/sibongilee/BudgetTrackerApplication/commit/372e03456dd7d6b1f56a77981680aa7256cb6c8f) | Add logging for successful expense save | 2026-06-15 19:05:04 |
| [7e7ff2d](https://github.com/sibongilee/BudgetTrackerApplication/commit/7e7ff2df2dd930bbcb7b5b8bac6325951bafcc47) | Log new user registration in RegisterActivity | 2026-06-15 19:03:17 |
| [e6dbefb](https://github.com/sibongilee/BudgetTrackerApplication/commit/e6dbefb5ae11131075dd0bef8892286a6bd9d9d7) | Add logging for login success and failure | 2026-06-15 19:01:35 |
| [64076869](https://github.com/sibongilee/BudgetTrackerApplication/commit/64076869c7f96cd253dc76ccb0b95523f232bac1) | Add logging for navigation and dashboard events | 2026-06-15 18:59:04 |

---
Video Demonstration

YouTube Video Link:
(https://youtube.com/shorts/GWmahnVScro?si=rF7s191qFit_8ADd)


--

Custom Feature 1: Rewards and Badges System

The application includes a gamification feature that rewards users for achieving budgeting goals and maintaining consistent expense tracking habits. Users can earn badges such as:

Budget Master
Expense Logger
Savings Champion

This feature encourages users to remain engaged and motivated while managing their finances.
---

Custom Feature 2: Dashboard Statistics

The dashboard displays financial summary information, including total expenses and category statistics. This provides users with a quick overview of their spending activity without needing to navigate through multiple screens.

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
- Added comprehensive logging throughout the application
