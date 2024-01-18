// Importing necessary libraries for Selenium, file handling, and testing
package com.Amazon.Tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

// Class to test Amazon web application
public class AmazonWebAppTest {
    RemoteWebDriver driver;

    // Setup method to initialize the web driver and perform initial actions
    @BeforeSuite
    public void setup() {
        // Initializing ChromeDriver
        driver = new ChromeDriver();
        // Opening Amazon website
        driver.get("https://www.amazon.in/");
        // Maximizing the browser window
        driver.manage().window().maximize();
        // Setting implicit wait time
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        // Hover over the Sign-In link in the navigation bar
        WebElement signIn = driver.findElementById("nav-link-accountList");
        Actions builder = new Actions(driver);
        builder.moveToElement(signIn).perform();
        // Clicking on the Sign-In button that appears after hovering
        driver.findElementByXPath("(//span[@class='nav-action-inner'])[1]").click();
    }

    // Test method for user login
    @Test(priority = 1)
    public void login() throws InterruptedException {
        // Entering email and password for login
        driver.findElementById("ap_email").sendKeys("zxcv@gmail.com", Keys.ENTER);
        driver.findElementById("ap_password").sendKeys("123456789");
        driver.findElementById("signInSubmit").click();
        // Adding a delay for demonstration purposes (not recommended in real testing)
        Thread.sleep(10000);
    }

    // Method to search for a product and add it to the cart
    public void searchAndAddItem(String productName) {
        // Searching for the product using the search bar
        driver.findElementById("twotabsearchtextbox").sendKeys(productName);
        driver.findElementById("nav-search-submit-button").click();
        // Waiting for the product link to be visible
        WebDriverWait wait = new WebDriverWait(driver, 50);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(productName)));
        // Clicking on the product link
        driver.findElement(By.partialLinkText(productName)).click();

        // Switching to the new tab
        Set<String> newTab1 = driver.getWindowHandles();
        List<String> list = new ArrayList<>(newTab1);
        driver.switchTo().window(list.get(1));

        // Checking product availability
        String availability = driver.findElementByXPath("//div[@id='availability']//span[1]").getText();
        if (availability.equals("In stock")) {
            driver.findElementById("add-to-cart-button").click();
        } else {
            driver.navigate().back();
        }

        // Switching back to the original tab
        wait = new WebDriverWait(driver, 20);
        WebElement proceedButton = driver.findElementByName("proceedToRetailCheckout");
        wait.until(ExpectedConditions.visibilityOf(proceedButton));
        driver.switchTo().window(list.get(0));
        driver.close();
        driver.switchTo().window(list.get(1));
        driver.findElementById("twotabsearchtextbox").clear();
    }

    // Test method for searching and adding a specific item
    @Test(priority = 2)
    public void searchAndAddItemTest() throws InterruptedException {
        searchAndAddItem("Girls Junior Dorjee Casual Shoes");
    }

    // Test method for adding a shipping address
    @Test(priority = 3)
    public void addAddress() {
        // Clicking on the "Proceed to Retail Checkout" button
        driver.findElementByName("proceedToRetailCheckout").click();
        // Clicking on "Add a new address" link
        driver.findElementByLinkText("Add a new address").click();
        // Entering address details
        driver.findElementById("address-ui-widgets-enterAddressFullName").sendKeys("AK");
        driver.findElementById("address-ui-widgets-enterAddressPhoneNumber").sendKeys("123456");
        driver.findElementById("address-ui-widgets-enterAddressPostalCode").sendKeys("600026");
        driver.findElementById("address-ui-widgets-enterAddressLine1").sendKeys("no.21");
        driver.findElementById("address-ui-widgets-enterAddressLine2").sendKeys("1st street");
        driver.findElementById("address-ui-widgets-landmark").sendKeys("near flats");
        driver.findElementById("address-ui-widgets-enterAddressCity").sendKeys("Chennai");
        driver.findElementByXPath("(//span[@class='a-button-text a-declarative'])[2]").click();
        driver.findElementByXPath("//a[contains(text(),'TAMIL NADU')]").click();
        driver.findElementByXPath("//span[@class='a-expander-prompt']//span[1]").click();
        driver.findElementByXPath("(//button[@type='button'])[2]").click();
        driver.findElementByXPath("//span[@id='address-ui-widgets-form-submit-button']/span[1]/input[1]").click();
    }

    // Test method for checking payment and downloading receipt
    @Test(priority = 4)
    public void checkPaymentAndRecieptDownload() throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        // Selecting payment method
        driver.findElementByXPath("//input[@value='instrumentId=0h_PE_CUS_18b1c868-2e63-40e2-8b24-414fe05d88c8%2FCash&isExpired=false&paymentMethod=COD&tfxEligible=false']").click();
        driver.findElementByXPath("(//input[@name='ppw-widgetEvent:SetPaymentPlanSelectContinueEvent'])[1]").click();
        WebElement placeOrder = driver.findElementByXPath("//body/div[@id='a-page']/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]");
        placeOrder.click();
        WebElement account = driver.findElementById("nav-link-accountList");
        Actions builder = new Actions(driver);
        builder.moveToElement(account).perform();
        driver.findElementByXPath("//span[text()='Your Orders']").click();
        WebElement orderOne = driver.findElementByXPath("(//div[@class='a-box-inner'])[1]");
        wait.until(ExpectedConditions.visibilityOf(orderOne));
        driver.findElementByXPath("(//a[@class='a-popover-trigger a-declarative'])[1]").click();
        driver.findElementByLinkText("Printable Order Summary").click();
        wait.until(ExpectedConditions.urlContains("https://www.amazon.in/documents/download/"));
        // Capture screenshot of the invoice
        File Invoice = driver.getScreenshotAs(OutputType.FILE);
        File dest = new File("./snaps/Invoice.png");
        FileHandler.copy(Invoice, dest);
        // Moving back to the order page
        driver.navigate().back();
    }

    // Test method for canceling an order
    @Test(priority = 5)
    public void cancelOrder() {
        driver.findElementByLinkText("View or edit order").click();
        driver.findElementByLinkText("Cancel items").click();
        driver.findElementByName("cancel.reason").click();
        driver.findElementByXPath("//option[@value='other']").click();
        driver.findElementByName("cq.submit").click();
    }

    // Closing the browser after the test suite
    @AfterSuite
    public void quit() {
        driver.quit();
    }
}
