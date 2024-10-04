
---

## NotifyMe - Notification Service POC

This project is a Proof of Concept (POC) for a notification service using multiple channels such as email, SMS, and push notifications. The project is developed using Java with Guice for dependency injection and MariaDB as the database.

### Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Usage](#usage)
- [Testing](#testing)

---

### Project Overview

The NotifyMe service is designed to send notifications through various channels, including:
- Email Notifications
- SMS Notifications
- Push Notifications

It uses Google Guice for dependency injection and binds different notifier classes to a `Multibinder<Notifier>` to facilitate sending notifications.

---

### Features

- Multiple notification channels (Email, SMS, Push)
- Database connection to MariaDB
- Modular design using Guice for dependency injection
- Unit tests using Mockito and TestNG

---

### Technologies Used

- **Java** (Core language)
- **Google Guice** (Dependency Injection)
- **MariaDB** (Database)
- **JUnit / TestNG** (Testing Framework)
- **Mockito** (Mocking framework)
- **Apache Maven** (Build and Dependency Management)

---

### Prerequisites

Before you begin, ensure you have met the following requirements:

- Java 8 or above installed
- MariaDB database setup locally
- Maven installed (for build and dependency management)
- Git installed (for version control)
- IDE such as IntelliJ IDEA or Eclipse (for development)

---

### Setup

1. **Clone the repository:**

   ```bash
   git clone https://github.com/ygsh23/notifyme.git
   ```

2. **Navigate to the project directory:**

   ```bash
   cd notifyme
   ```

3. **Configure MariaDB:**

   Ensure MariaDB is installed and running on your machine. The default configuration expects a database called `notify_me` with the following credentials:

   ```
   URL: jdbc:mariadb://localhost:3306/notify_me
   User: root
   Password: 11223344
   ```

   If needed, you can modify these values in the `AppModule.java` class.

4. **Install dependencies:**

   ```bash
   mvn clean install
   ```

5. **Run the project:**

   You can run the project by executing your main class or by running a test file.

---

### Usage

1. **Configure Notifications:**

   The notification channels are bound in `AppModule.java`. By default, the following notifiers are available:
    - `EmailNotifier`
    - `SMSNotifier`
    - `PushNotifier`

2. **Triggering Notifications:**

   In the application, you can create an instance of `Notifier` and send notifications through the appropriate channels.

3. **Database Connection:**

   The application connects to MariaDB via the `DataSource` provided in the `AppModule` class. Ensure that your MariaDB instance is correctly set up before running the project.

---

### Testing

Unit tests have been written using TestNG and Mockito. To run the tests, execute the following command:

```bash
mvn test
```

The tests cover the notifier services and the data source configuration.

---
