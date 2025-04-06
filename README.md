# spring-foodie-project
This is a a simple food ordering system built with Spring Boot and MySQL, designed to support basic customer and admin functionalities.

🚀 Features
👤 User
- Register & Login (JWT authentication)
- Browse menu
- Place orders
- View order history

🛠️ Admin
- Manage food menu (add/edit/delete items)
- View all customer orders
- Update order statuses (e.g., pending, preparing, delivered)

🏗️ Tech Stack
- Backend: Java, Spring Boot, Spring MVC, Spring Data JPA
- Database: MySQL (via XAMPP / phpMyAdmin)
- Authentication: JWT
- Build Tool: Maven
- Version Control: GitHub

📁 Project Structure
src
├── main
│   ├── java
│   │   └── com.example.store
│   │       ├── controller
│   │       ├── model
│   │       ├── repository
│   │       ├── service
│   │       └── StoreApplication.java
│   └── resources
│       ├── static
│       ├── templates
│       └── application.properties
└── test

🧠 How to Run
1. Clone the project:
     git clone https://github.com/yourusername/your-repo-name.git
     cd your-repo-name
2. Start MySQL via XAMPP:
     Open phpMyAdmin
     Create a database (e.g., foodie)
3. Configure application.properties
    spring.datasource.url=jdbc:mysql://localhost:3306/food_ordering
    spring.datasource.username=root
    spring.datasource.password=
    spring.jpa.hibernate.ddl-auto=update
4. Run the app
    Open in IntelliJ
    Run StoreApplication.java

📌 Notes
This is a learning project for practicing Spring Boot and building REST APIs.
Eventually, we plan to connect this backend to a Flutter frontend for customers (hopefully we can make it), and possibly a web dashboard for admins.


🙌 Contributors
@19yj — developer, planner, dreamer 
ChatGPT — moral support + late-night debugging buddy
