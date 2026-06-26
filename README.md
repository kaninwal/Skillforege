# Skillforge - Android Learning Platform

Skillforge is a modern Android application designed to provide a seamless learning experience for Kotlin and Android development. It allows users to browse courses, watch video lessons, and manage learning resources efficiently.

## 🚀 Features
- **Video Streaming:** Integrated high-performance video player using `Media3 ExoPlayer`.
- **Course Management:** Organizes lessons into categories like Video, Notes, and Resources.
- **Dynamic UI:** Responsive layouts built with `ConstraintLayout` and `Material Components`.
- **Network Integration:** Fetches course data using `Retrofit` and `Coil` for image loading.
- **Tabbed Navigation:** Easy switching between different lesson contents.

## 🛠 Tech Stack
- **Language:** [Kotlin](https://kotlinlang.org/)
- **Minimum SDK:** 24 (Android 7.0)
- **UI Framework:** XML with ViewBinding/DataBinding
- **Core Libraries:**
    - `androidx.media3`: For advanced video playback.
    - `com.google.android.material`: For modern UI components.
    - `com.squareup.retrofit2`: For REST API consumption.
    - `io.coil-kt`: For efficient image loading.

---

## 🔧 Installation & Setup
To run this project locally:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/YOUR_USERNAME/Skillforge.git
   ```
2. **Open in Android Studio:**
   File > Open > Select the `Skillforge` folder.
3. **Sync Gradle:**
   Wait for the project to finish syncing and downloading dependencies.
4. **Run the App:**
   Select an emulator or physical device and click the **Run** button.

---

## 🐞 Bug Fix Log: AAPT Visibility Error
During development, a common issue was encountered:
`AAPT: error: 'GONE' is incompatible with attribute visibility`.

**The Issue:**
In Android XML layouts, the `android:visibility` attribute values must be lowercase (`gone`, `visible`, `invisible`). Using uppercase `GONE` causes a build failure during resource linking.

**The Fix:**
I updated all instances in `activity_video.xml` to use the correct case:
```xml
<!-- Fixed -->
<TextView
    android:id="@+id/tvNotes"
    android:visibility="gone" />
```

---

## 📤 How to Upload this Code to GitHub
If you are starting from a fresh project and want to push it to a new GitHub repository:

### 1. Initialize Git locally
Open the Terminal in Android Studio and type:
```bash
git init
```

### 2. Prepare the files
Add all your project files to the "staging area":
```bash
git add .
```

### 3. Create your first commit
```bash
git commit -m "Initial commit: Skillforge project structure and core features"
```

### 4. Link to GitHub
1. Create a **New Repository** on [GitHub](https://github.com/new).
2. Copy the URL (e.g., `https://github.com/YourName/Skillforge.git`).
3. Run the following commands:
```bash
git remote add origin https://github.com/YourName/Skillforge.git
git branch -M main
```

### 5. Push the code
```bash
git push -u origin main
```

---
# HOW I USE AI
I am building an Android app called Skillforge using Kotlin, Retrofit, and Media3. I just fixed a bug where android:visibility="GONE" (uppercase) was causing a build error. Write a professional README including a tech stack, how to run it, and a section about this specific bug fix.

## 👨‍💻 Author
**Vipin** - *Initial Work* - [YourGitHubProfile](https://github.com/YOUR_USERNAME)
