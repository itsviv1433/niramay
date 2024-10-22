# Niramay - Healthcare Companion

**Niramay** is an all-in-one health management application that helps users store and access information on diseases and hospitals. It also includes extra features like a step tracker, BMI calculator, and COVID-19 vaccine tracker, making it a comprehensive companion for managing health.

## Features:
- Store and access detailed information about diseases and hospitals.
- Track daily steps with the step tracker.
- Calculate and monitor BMI (Body Mass Index).
- COVID-19 vaccine tracking and information.

---

## How to Run the Project

### Step 1: Set Up Localhost
1. Download and install a local server environment (e.g., **XAMPP**, **WAMP**, or **MAMP**).
2. Start the **Apache** and **MySQL** services.

### Step 2: Clone the Repository
1. Clone the repository and navigate into the project directory.

### Step 3: Import the Database
1. Open **phpMyAdmin** or your preferred MySQL interface.
2. Create a new database (e.g., `niramay_db`).
3. Import the database from the `db` folder:
   - Go to **Import** in phpMyAdmin.
   - Select the SQL file located in the `db` folder and click **Go** to import the database structure and data.

### Step 4: Host the API
1. Place the API files in the correct server directory (e.g., `htdocs/niramay` if you're using **XAMPP**).
2. Start your server and confirm that the APIs are working by visiting the corresponding API URLs (e.g., `http://localhost/niramay/api/...`).

### Step 5: Update API URLs in the Application
1. Open the `constants.java` file in your project.
2. Replace the old API URL with your new localhost or server URL to ensure the application is linked to the correct API paths.

---

This will ensure the project is set up and running successfully.
