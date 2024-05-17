# Banking App Project

## Description

This project is a banking application that allows users to manage their finances. Users can create an account, deposit and withdraw money, view their transaction history, and more. The application is built with a focus on security and user-friendly interfaces.

## Table of Contents

- [Installation](#installation)
- [Functional](#functional)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Installation

To configure and install the Banking App project, follow these steps:

1. Clone the project repository from GitHub:

    ```bash
    git clone https://github.com/phamduyben/BankingApp.git
    ```

2. Navigate to the project directory:

    ```bash
    cd banking-app
    ```

3. Install the required dependencies using Maven:

    ```bash
    mvn install
    ```

4. Set up the database:

    - Create a MySQL database named `banking_app`.
    - Update the database configuration in the `application-dev.yml` file located in the `src/main/resources` directory. Set the `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` properties according to your database configuration.
    
5. Run the application:

    ```bash
    mvn spring-boot:run
    ```

6. Access the application:

    Open your web browser and go to `http://localhost:8081` to access the Banking App.

Note: Make sure you have Java and Maven installed on your system before proceeding with the installation.

-Install jdk 17+ [install jdk](https://www.oracle.com/java/technologies/downloads/#java21)

-Install maven 3.5+[install maven](https://maven.apache.org/download.cgi)

If you encounter any issues during the installation process, refer to the project's documentation or seek help from the project's community.

# Functional

The banking app has two main roles: admin and user. Here are the details of each role:

## Admin

The admin role has the following functionalities:
- Create and manage user accounts
- View and edit user information
- Approve or reject user transactions
- Generate reports and analytics on user activity
- Manage system settings and configurations

## User

The user role has the following functionalities:
- Create an account with personal information
- Deposit and withdraw money from their account
- View their account balance and transaction history
- Transfer money to other user accounts
- Set up recurring payments and bill reminders
- Update their personal information

In addition to the admin and user roles, the banking app also has the following functional features:

- Account Security: The app ensures the security of user accounts through authentication mechanisms such as passwords and multi-factor authentication.
- Transaction History: Users can view a detailed history of their transactions, including the date, time, amount, and transaction type.
- Notifications: Users receive notifications for important account activities such as deposits, withdrawals, and account balance updates.
- Currency Conversion: The app supports currency conversion for international transactions, providing real-time exchange rates.
- Account Statements: Users can generate and download account statements for a specific period, which include all transactions and balances.

These functionalities make the banking app a comprehensive solution for managing personal finances securely and conveniently.

## Usage

After installation, you can start the application by running `mvn spring-boot:run` in the project directory. You will then be able to access the application at `http://localhost:8081`.

## Contributing

Contributions are welcome! Please read the [contributing guide](CONTRIBUTING.md) to learn about our development process, how to propose bugfixes and improvements, and how to build the application from source.

## License

This project is licensed under the terms of the MIT license. For more information, see the [LICENSE](LICENSE) file.