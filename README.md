# Namma Santhe Ledger 📱📊

**Namma Santhe Ledger** is a modern Android application designed for small-scale vendors, shopkeepers, and farmers to digitize their traditional "Bahi Khata" or credit ledgers. It simplifies financial tracking by managing receivables and payables with an intuitive, bilingual interface.

---

## 🌟 Key Features

- **🔐 Secure Authentication:** Full User registration and login system with persistent local storage.
- **📊 Professional Dashboard:** Real-time summary showing "You will Receive" (Customers) and "You will Give" (Vendors) amounts.
- **👥 Customer Management:** Searchable directory to track individual balances and transaction histories.
- **✏️ Quick Edit:** Fix mistakes in customer names or phone numbers instantly.
- **💬 WhatsApp Reminders:** Send professional balance reminders to customers with one tap.
- **🌍 Kannada Language Support:** Toggle between English and ಕನ್ನಡ seamlessly from the Profile screen.
- **📱 Modern UI:** Built using **Jetpack Compose** with a clean, responsive Material 3 design.

---

## 🛠️ Technical Stack

- **Language:** Kotlin
- **UI Framework:** Jetpack Compose
- **Database:** Room Persistence Library (SQLite)
- **Architecture:** MVVM (Model-View-ViewModel)
- **Asynchronous Tasks:** Coroutines & Flow
- **Dependency Management:** Gradle Version Catalogs

---

## 🚀 How to Run the Project

### Prerequisites
- Android Studio (Koala or newer recommended)
- Android SDK 31+
- A physical Android device or Emulator (API 31+)

### Installation Steps
1. **Clone the Repository:**
   ```bash
   git clone https://github.com/RakshithaMunegowda/NammaSantheLedger.git
   ```
2. **Open in Android Studio:**
   - Launch Android Studio and select `Open`.
   - Navigate to the cloned folder and click `OK`.
3. **Sync Gradle:**
   - Wait for the project to sync and download dependencies.
4. **Run the App:**
   - Connect your device/emulator.
   - Click the **Run** button (green arrow) in the toolbar.

---

## 📖 Implementation Highlights

### 1. Unified State Management
The project uses a single `MainActivity` with a `MainViewModel` to manage navigation and data flow. This ensures data consistency across the entire app without complex Intent handling.

### 2. Localization
Implemented using `AppCompatDelegate` and `locales_config.xml`. All UI strings are stored in `strings.xml` and `values-kn/strings.xml`, allowing for dynamic language switching without app restarts.

### 3. Smart Net-Balance Logic
Unlike simple transaction logs, this app calculates the **Net Balance** per customer first. This prevents settled accounts from cluttering the dashboard totals.

---

## 📸 Screenshots (Output)
<img width="361" height="775" alt="Screenshot 2026-05-07 225517" src="https://github.com/user-attachments/assets/590ffe3b-587e-40e5-a863-656086ce852a" />
<img width="364" height="790" alt="Screenshot 2026-05-07 225618" src="https://github.com/user-attachments/assets/a7881cc9-e82c-46d8-a22b-e94c28b38e5b" />
<img width="371" height="788" alt="Screenshot 2026-05-12 143128" src="https://github.com/user-attachments/assets/136c2987-fa77-4446-9b0e-3193da671024" />
<img width="363" height="783" alt="Screenshot 2026-05-07 225752" src="https://github.com/user-attachments/assets/5c08f886-207b-4983-84a8-8641186a5cee" />
<img width="362" height="791" alt="Screenshot 2026-05-07 225839" src="https://github.com/user-attachments/assets/2bd3103c-b7c3-4216-b66e-5741c8470554" />
<img width="361" height="788" alt="Screenshot 2026-05-12 144246" src="https://github.com/user-attachments/assets/b83ee8bc-49b1-4d27-a878-51330707db4e" />
<img width="363" height="790" alt="Screenshot 2026-05-07 230954" src="https://github.com/user-attachments/assets/e6fc187b-d239-4205-84be-43775b415a24" />
<img width="371" height="795" alt="Screenshot 2026-05-12 134440" src="https://github.com/user-attachments/assets/d99fa54f-8afe-4bba-83d7-d8914748c18e" />

---

## 📜 License
This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

**Developed with 😊 by Rakshitha Munegowda**
