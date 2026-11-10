# UnmockGPS

**UnmockGPS** is a Xposed/LSPatch module written in Kotlin designed to bypass mock location detection.

## 🛠 Features

### 📍 Location Sanitization (`android.location.Location`)
*   **Flag Suppression**: Forces `isMock()` and `isFromMockProvider()` to return `false`.
*   **Hardware Metadata Injection**: Injects realistic accuracy and altitude data (Accuracy: 5.0f, Altitude: 35.0).
*   **Bundle Scrubbing**: Automatically removes hidden `mockLocation` flags in `getExtras` left by common spoofing apps.

---

## 🚀 How to Use

To successfully bypass modern detection, follow this workflow:

1.  **Activate**: Enable the UnmockGPS module via LSPosed or embed it using LSPatch, then launch the target app.
2.  **Mock Location**: Set your desired location in a standard "Fake GPS" app.

---

## 🏗 Technical Details

*   **Language**: Kotlin
*   **Xposed API**: v82
*   **Targeting**: Android 9.0 (API 28) and above

## ⚖️ Disclaimer

This project is for educational and research purposes only. The developers are not responsible for any misuse or violations of third-party Terms of Service.
