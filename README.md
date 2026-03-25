# Expense Logger Android App (Kotlin)

Native Android mobile app built with Kotlin and Android Studio.

## Features
- Capture receipt photos using the device camera.
- Store each receipt with:
  - photo URI
  - timestamp (when saved)
  - user-entered claim amount
- Display a running claim total.
- Persist receipt records locally with Room database.

## Tech Stack
- Kotlin
- Android SDK (Activity + XML layouts)
- Room (SQLite persistence)
- RecyclerView
- FileProvider for camera image output URI

## Build and Run
1. Open `ExpenseLoggerApp` in Android Studio.
2. Let Android Studio sync Gradle dependencies.
3. Run on an Android emulator or physical Android device.
4. Tap **Add Receipt**, take a photo, enter amount, and save.

## Project Structure
- `app/src/main/java/com/example/expenselogger/ui` - UI, activity, adapter, viewmodel
- `app/src/main/java/com/example/expenselogger/data` - Room entities/DAO/database/repository
- `app/src/main/res` - layouts, strings, theme, drawables, FileProvider paths

## Version Control and GitHub Link
This project is initialized as a local Git repository.

Use these commands to publish to your GitHub account and generate a link for submission:

```powershell
cd "C:\Users\baile\OneDrive\Desktop\JHUB\Moudle 2 - Ongoing (App)\ExpenseLoggerApp"
& "C:\Program Files\Git\cmd\git.exe" add .
& "C:\Program Files\Git\cmd\git.exe" commit -m "Initial expense logger Android app"
& "C:\Program Files\Git\cmd\git.exe" branch -M main
& "C:\Program Files\Git\cmd\git.exe" remote add origin https://github.com/<your-username>/expense-logger-android.git
& "C:\Program Files\Git\cmd\git.exe" push -u origin main
```

After pushing, include your GitHub repository URL in the assignment submission.
