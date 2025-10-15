package com.example.steps;
import io.cucumber.java.en.*; 
import static org.assertj.core.api.Assertions.assertThat;

public class UnitSteps {
	
    double a,b,result;
    
    @Given("two numbers {double} and {double}") 
    public void nums(double a,double b){
    	this.a=a;
    	this.b=b;
	}
    
    @When("I {word} them") 
    public void op(String op) {
    	switch(op) {
    		case"add"-> result=a+b;
    		case"subtract"-> result=a-b;
    		case"multiply"-> result=a*b;
		}
	}
    
    @Then("the result should be {double}") 
    public void verify(double e) {
    	assertThat(result).isEqualTo(e);
	}
 }