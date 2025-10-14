package com.example.steps;

import com.example.testsupport.Config;
import io.cucumber.java.en.*; 
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*; 
import org.openqa.selenium.chrome.ChromeDriver; 
import org.openqa.selenium.chrome.ChromeOptions;
import static org.assertj.core.api.Assertions.assertThat;

public class UiSteps {
    private static WebDriver driver;
    
    @Given("I open the calculator page in a browser") 
    public void open() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup(); ChromeOptions opt = new ChromeOptions();
            
            if (Config.headless()) opt.addArguments("--headless=new","--disable-gpu"); else opt.addArguments("--start-maximized");
            driver = new ChromeDriver(opt);
        }
        
        driver.get(Config.calcUrl());
    }
    
    @Then("I should see a page title or visible content") 
    public void verify() {
        assertThat(!driver.getTitle().isBlank() || !driver.findElements(By.tagName("body")).isEmpty()).isTrue();
    }
    
    static { 
    	Runtime.getRuntime().addShutdownHook(new Thread(() -> { 
    		if (driver != null) 
    			driver.quit(); 
    		})); 
	}
}