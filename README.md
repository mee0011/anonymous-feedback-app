# TrueVoice - Web Feedback Application

TrueVoice is a full-stack web application built with **Java** and the **Spring Boot** framework. It allows users to register, log in, and post feedback. The application is secured using Spring Security and features a fully responsive, in-memory data management, and data encryption.

## 🚀 Features Implemented

This project successfully implements all required features for the module, including:

* **Full Authentication:** Users can register for a new account and log in.
* **Role-Based Security (Spring Security):**
    * **ROLE\_USER:** Can post feedback, view their own dashboard, and delete their own posts.
    * **ROLE\_ADMIN:** Can be assigned to delete any post.
    * Guests (not logged in) can only view the homepage and feedback details.
* **Dynamic Navigation:** The navbar links for "User Dashboard," "Post Feedback," and "Logout" are dynamically shown or hidden based on the user's login status (**Features 1 & 2**).
* **Endpoint Protection:** All sensitive routes (`/user-dashboard`, `/feedback`, `/delete`) are secured at the backend, blocking any unauthorized access (**Features 4 & 5**).
* **Full Pagination:** All data-heavy pages (Homepage, Search Results, and User Dashboard) are fully paginated to improve performance (**Feature 6**).
* **Database Encryption:** The `feedbackBy` field (storing the user's username) is **automatically encrypted** in the database using a JPA `AttributeConverter` (AES) and is decrypted in the application, keeping user data secure while remaining functional (**Feature 7**).
* **Responsive Design:** The navigation bar is fully responsive and collapses into a "hamburger" menu on mobile devices using only HTML and CSS (no JavaScript) (**Feature 8**).

## 🛠 Tech Stack

* **Backend:** Java 17, Spring Boot 3
* **Security:** Spring Security 6
* **Frontend:** Thymeleaf (Server-Side Templating), HTML5, CSS3
* **Database:** H2 In-Memory Database
* **Build Tool:** Gradle
* **Utilities:** Lombok

## 🏃‍♂️ How to Run

1.  **Clone the repository:**
    ```sh
    git clone <your-repository-url>
    ```
2.  **Navigate to the project directory:**
    ```sh
    cd anonymous-feedback-app
    ```
3.  **Run the application using Gradle:**
    ```sh
    ./gradlew bootRun
    ```
    (If you are on Windows, you may need to use `gradlew.bat bootRun`)

4.  **Access the application:**
    Open your browser and go to `http://localhost:9991`

5.  **Access the H2 Database Console:**
    * Go to `http://localhost:9991/h2-console`
    * Make sure the **JDBC URL** is set to `jdbc:h2:mem:app-db`
    * Click "Connect".
