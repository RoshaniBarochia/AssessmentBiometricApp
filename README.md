# AssessmentBiometricApp
    Android applications in Kotlin and Java that demonstrate API integration, biometric authentication, secure token handling, and user-friendly interfaces.

# Project Overview.
    This Android application allows users to authenticate securely, fetch transaction data from an API, and view it in a searchable list. The app also includes biometric authentication and offline access via Room Database.

# Setup Instructions 

   1. Clone the Repository:
   git clone https://github.com/RoshaniBarochia/AssessmentBiometricApp

   2. Open in Android Studio:
   Open Android Studio and select "Open an existing project."
   Navigate to the cloned project folder and select it.

   3. Setup Dependencies:
   Ensure you have the latest Gradle version installed.
   Sync the project by clicking "Sync Now" in Android Studio.

   4. Configure API Base URL:
   Add your BASE_URL and MASTER_KEY_ALIAS in your gradle.properties:
      BASE_URL=https://api.prepstripe.com/
      MASTER_KEY_ALIAS=your_master_key_alias

   5. Build the project:
      Sync Gradle and build the project using Build > Make Project or Ctrl+F9.

# Signed APK Build Instructions

   1. Build the Signed APK using Gradle:
   ./gradlew assembleRelease
   The APK will be generated inside the app/build/outputs/apk/release/app_release.apk directory.

# Features Available

   1. Core Features:
       - Authenticate using POST: https://api.prepstripe.com/login
       - Fetch and display transactions using GET: https://api.prepstripe.com/transactions
       - Biometric Authentication: Secure access on subsequent launches. 
       - Encrypted Token Storage: Uses EncryptedSharedPreferences for secure session management. 
       - Logout Option: Clears the token and returns the user to the login screen.

# Bonus Features (Optional but Implemented):
    
   - Dark Mode Support: Adapts to the system's theme settings.
   - Offline Mode: Uses Room Database for caching transactions.
   - Search Functionality: Allows users to search transactions easily.

