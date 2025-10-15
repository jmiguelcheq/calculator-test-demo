package com.example.steps;

import io.cucumber.java.en.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import com.example.testsupport.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Cucumber steps that perform operations using the actual
 * web calculator at https://jmiguelcheq.github.io/calculator-demo
 */
public class UnitSteps {

    private static WebDriver driver;
    private double a, b;
    private String operation;
    private double actualResult;

    @Given("two numbers {double} and {double}")
    public void nums(double a, double b) {
        this.a = a;
        this.b = b;

        // Initialize WebDriver once
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            if (Config.headless()) {
                options.addArguments("--headless=new", "--disable-gpu");
            } else {
                options.addArguments("--start-maximized");
            }
            driver = new ChromeDriver(options);
        }

        // Open the calculator app
        driver.get("https://jmiguelcheq.github.io/calculator-demo");
    }

    @When("I {word} them")
    public void op(String op) {
        this.operation = op;

        WebElement inputA = driver.findElement(By.id("a"));
        WebElement inputB = driver.findElement(By.id("b"));
        WebElement selectOp = driver.findElement(By.id("op"));
        WebElement computeBtn = driver.findElement(By.id("compute"));

        // clear & input numbers
        inputA.clear();
        inputA.sendKeys(String.valueOf(a));
        inputB.clear();
        inputB.sendKeys(String.valueOf(b));

        // map operation to UI select value
        String value;
        switch (op) {
            case "add": value = "add"; break;
            case "subtract": value = "sub"; break;
            case "multiply": value = "mul"; break;
            case "divide": value = "div"; break;
            default: throw new IllegalArgumentException("Unknown op: " + op);
        }

        // select operation using JS (since <select> is simple)
        ((JavascriptExecutor) driver).executeScript(
                "document.getElementById('op').value = arguments[0]", value);

        // click compute
        computeBtn.click();
    }

    @Then("the result should be {double}")
    public void verify(double expected) {
        // read result element
        WebElement resultElem = driver.findElement(By.id("result"));
        String resultText = resultElem.getText().replace("Result:", "").trim();
        System.out.println("Displayed result text: " + resultText);

        // parse number from "Result: X"
        try {
            actualResult = Double.parseDouble(resultText);
        } catch (NumberFormatException e) {
            throw new AssertionError("Invalid result text: " + resultText);
        }

        // validate numeric equality (allow minor floating differences)
        assertThat(actualResult)
                .as("Verify calculator result equals expected")
                .isCloseTo(expected, within(0.001));

        // close browser at end of scenario
        driver.quit();
        driver = null;
    }
}
