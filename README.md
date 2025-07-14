# 🚗 QuickRent Console Car Rental System

> A complete console-based Car Rental System developed in Core Java.  
> This system supports **Admin operations**, **Customer rentals**, **PDF/Receipt generation**, **QR UPI payment**, **image preview**, and **feedback storage in CSV format**.

---

## 📜 Features

### 👨‍💼 Admin Panel
- Login with username & password
- Add new cars with brand, model, price, and image
- Delete cars by ID
- Auto-generate unique Car IDs (e.g., `C001`, `C002`)

### 🙋‍♂️ Customer Interface
- Enter name, mobile, and email to rent a car
- Choose available cars by ID
- Automatic rental receipt generation (saved to `/User_List`)
- View car image (opens from local system)
- Provide feedback after returning the car

### 🧾 Receipt Generation
- Auto-generated `.txt` receipt saved to a folder
- Includes customer + order details + timestamp

### 💬 Feedback System
- Collects experience, rating, suggestions, and recommendations
- Stored in `feedback_logs.csv` inside `/Feedbacks` folder
- Viewable in Excel

---

## 📂 Folder Structure

QuickRent/
├── Main.java
├── Feedbacks/
│   └── feedback_logs.csv
├── User_List/
│   └── [username].txt
├── Images/
    ├── camry.jpg
    ├── honda.jpg
    └── thar.jpg


### ⚙️ How to Run
Install JDK (Java 8+)

### Compile:
javac Main.java

### Run:
java Main



## Technologies Used

*Java SE (Core Java)*

*File I/O (FileWriter, Scanner, File)*

*Desktop.getDesktop().open() for image preview*

*CSV for feedback storage*

*Simple class-based architecture (OOP)*

