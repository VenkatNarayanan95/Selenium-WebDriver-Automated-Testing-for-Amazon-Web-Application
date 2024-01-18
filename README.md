# Selenium-WebDriver-Automated-Testing-for-Amazon-Web-Application
This repository contains a Selenium WebDriver automation script written in Java for testing the functionalities of the Amazon web application. The script covers various scenarios, including user login, product search, adding items to the cart, shipping address addition, payment selection, receipt download, and order cancellation.


Key Features:

Cross-Browser Testing:

Utilizes Selenium WebDriver to perform automated testing on the Google Chrome browser.
Page Object Model (POM):

Implements the Page Object Model for maintaining a modular and scalable structure.
TestNG Integration:

Utilizes TestNG for test case management, prioritization, and parallel execution.
Actions and Waits:

Implements actions class for performing mouse-hover and keyboard actions.
Uses explicit waits for handling dynamic elements and improving test stability.
Screenshot Capture:

Captures screenshots of the order summary for documentation purposes.
Test Cases:

User Login:
Enters user credentials and performs login.
Product Search and Addition:
Searches for a specific product and adds it to the cart.
Shipping Address Addition:
Adds a new shipping address to the user account.
Payment Selection and Receipt Download:
Selects a payment method, places the order, and downloads the order receipt.
Order Cancellation:
Cancels an order and verifies the cancellation process.
How to Use:

Clone the repository to your local machine.
Ensure you have the necessary dependencies installed (Java, ChromeDriver, etc.).
Set up your IDE with the project and execute the test cases using TestNG.
Contributions:
Contributions and feedback are welcome! Feel free to fork the repository, add improvements, and submit pull requests.

Disclaimer:
This script is created for educational and testing purposes. Use it responsibly and ensure compliance with Amazon's terms of service.

Dependencies:

Java
Selenium WebDriver
ChromeDriver
TestNG
