# AssessmentBiometricApp
    Android applications in Kotlin and Java that demonstrate API integration, biometric authentication, secure token handling, and user-friendly interfaces.

# Project Overview.
    This project is a secure transaction management application that allows users to authenticate, fetch transactions, and manage their sessions securely. It includes biometric authentication and encrypted token storage for enhanced security.


# Setup Instructions 

   1. Clone the Repository:
   git clone https://github.com/RoshaniBarochia/AssessmentBiometricApp
   cd your-repo-name

   2. Open in Android Studio:
   Open Android Studio and select "Open an existing project."
   Navigate to the cloned project folder and select it.

   3. Setup Dependencies:
   Ensure you have the latest Gradle version installed.
   Sync the project by clicking "Sync Now" in Android Studio.

   4. Configure API Base URL:
   Open gradle.properties and ensure the base URL is set:
   BASE_URL="https://api.prepstripe.com/"

   5. Run the App:
   Connect a physical device or start an emulator.
   Click "Run" to install and launch the application.

# APK Build Instructions

   1. Build the APK using Gradle:
   ./gradlew assembleRelease
   The APK will be generated inside the app/build/outputs/apk/release/ directory.

   2. Generate a Signed APK (For Production):
   Go to Android Studio: Build > Generate Signed Bundle / APK...
   Follow the wizard to sign the APK with a keystore.

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

