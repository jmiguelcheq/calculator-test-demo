package com.example.hooks;

import io.cucumber.java.AfterStep; 
import io.cucumber.java.Scenario; 
import io.qameta.allure.Allure;

public class TestHooks {
    @AfterStep public void afterStep(Scenario s) {
        if(s.isFailed()) 
        	Allure.addAttachment("Failure","text/plain","Step failed: "+ s.getName(),".txt");
    }
}