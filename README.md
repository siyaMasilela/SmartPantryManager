# Smart Pantry Manager

An Android application written in Java that helps users reduce food waste by suggesting recipes based on the ingredients they already have at home.

## About the App

Smart Pantry Manager lets a user track the ingredients in their pantry and then suggests recipes they can cook right now, without needing to buy anything extra. The main feature of the app is a strict matching algorithm. This means a recipe will only appear in the suggestions list if the user has every single ingredient it needs, in at least the required quantity. If even one ingredient is missing, that recipe is not shown.

The idea behind this is to help people use up leftover ingredients instead of letting them go to waste.

## Features

- Pantry management - add, edit, and delete pantry items with a name, quantity, unit, and optional expiry date.
- Pantry list screen that displays all current ingredients using a RecyclerView.
- Recipe suggestions screen that runs the strict matching algorithm against the user's pantry.
- Recipe detail screen showing the full ingredient list and preparation steps for a selected recipe.
- Settings screen with a toggle for expiry alerts and a unit preference option.
- Empty state message when no recipes match the current pantry.
- Bottom navigation bar for switching between Pantry, Recipes, and Settings.

## Database Choice - SQLite

I chose SQLite for this project for a few reasons:

1. It is built into Android, so there is no need for an internet connection or external service.
2. The pantry data is personal and should work offline, so a local database makes the most sense.
3. SQLiteOpenHelper makes it straightforward to implement full CRUD functionality.
4. It is fast because the data is stored directly on the device.
5. It matches the database content we covered in the module.

The app uses three tables:

- `pantry` - stores the user's ingredients
- `recipes` - stores recipe names and preparation steps
- `recipe_ingredients` - a junction table that links recipes to their required ingredients

## Technologies Used

- Language: Java
- IDE: Android Studio
- Database: SQLite (using SQLiteOpenHelper)
- UI: RecyclerView, CardView, Material Components
- Navigation: BottomNavigationView and Intents
- Minimum SDK: 24 (Android 7.0)
- Target SDK: 34

## How to Run the App

### Requirements

- Android Studio
- JDK 21 (Android Studio's built-in JDK works fine)
- An emulator (API 24 or higher) or a physical Android device

### Setup

1. Clone the repository:

   git clone https://github.com/siyaMasilela/SmartPantryManager.git

2. Open the project in Android Studio:
   - File, then Open, then select the SmartPantryManager folder.

3. Sync Gradle:
   - Click "Sync Project with Gradle Files" and wait for the dependencies to download.

4. Run the app:
   - Select an emulator or connected device, then click the green Run button.

### How to Use the App

1. On the pantry screen, tap the plus button to add ingredients (for example, eggs, butter, salt, pepper).
2. Tap Recipes in the bottom navigation bar to see recipes you can make right now.
3. Tap any recipe to view its full ingredient list and preparation steps.
4. To test the strict matching rule, delete one ingredient from the pantry and go back to the Recipes screen. Any recipe that needed that ingredient will no longer appear.


## The Strict Matching Algorithm

The logic for matching recipes to the pantry is in StrictMatchingLogic.java. The basic idea is:

- Loop through every recipe in the database.
- For each recipe, check if every required ingredient is available in the pantry.
- Also check that the user has at least the required quantity of each ingredient.
- Handle simple singular and plural differences, for example "tomato" and "tomatoes".
- Only add the recipe to the suggestions list if all checks pass.

If any single ingredient is missing, the recipe is skipped entirely.

## Author

Name: Siya Masilela
Student Number: 402110604
Module: Mobile App Development 700
Year: 2026

## Note

This project was created for academic purposes as part of the Mobile App Development 700 module.
