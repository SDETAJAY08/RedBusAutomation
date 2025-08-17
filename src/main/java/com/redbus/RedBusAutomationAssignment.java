package com.redbus;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RedBusAutomationAssignment {

	public static void main(String[] args) throws InterruptedException {
		
		ChromeOptions chromeoptions = new ChromeOptions();
		chromeoptions.addArguments("--start-maximized");
		//Launch the chrome browser 
		WebDriver wd = new ChromeDriver(chromeoptions);
		WebDriverWait wait = new WebDriverWait(wd,Duration.ofSeconds(30));
		//visit redbus.in website 
		wd.get("https://www.redbus.in");
		
		By sourceButtonLocator = By.xpath("//*[text()=\"From\"]");
		//WebElement sourceButton = wd.findElement(sourceButtonLocator); //while sync remove this findElement
		WebElement sourceButton = wait.until(ExpectedConditions.visibilityOfElementLocated(sourceButtonLocator));
		sourceButton.click();
		
		//check if the suggestion box is visible or not -- use breakpoints XHR(AJAX element)
				By searchSuggestionSelectionLocator = By.xpath("//div[contains(@class,\"searchSuggestionWrapper\")]");
				WebElement searchbox =wait.until(ExpectedConditions.visibilityOfElementLocated(searchSuggestionSelectionLocator));
		
	     WebElement searchTextBoxElement	= wd.switchTo().activeElement(); //this will give me that textbox bcoz blinker is there
		searchTextBoxElement.sendKeys("Mumbai");
		
		// verify there are 3 search categories opened 
		By searchCategoryLocator = By.xpath("//div[contains(@class,\"searchCategory\")]");
		List<WebElement> searchList=wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(searchCategoryLocator,2));
		System.out.println(searchList.size());
		
		//we are taking first index of our list and in that we find mumbai so chaining will be there
		WebElement locationSearchResult = searchList.get(0);
		By locationNameLocator = By.xpath(".//div[contains(@class,\"listHeader\")]"); // dot is for chaining in next step
		List<WebElement> locationList = locationSearchResult.findElements(locationNameLocator);
		System.out.println(locationList.size());
		for(WebElement location : locationList)
		{
			System.out.println(location.getText());
			String lName = location.getText();
			if(lName.equalsIgnoreCase("Mumbai"))
			{
				location.click();
				break;
			}
			
		}
		
		//focus went automatically to To section 
		WebElement toTextBox = wd.switchTo().activeElement();
		toTextBox.sendKeys("Pune");
		By toSearchCategoryLocator = By.xpath("//div[contains(@class,\"searchCategory\")]");
		List<WebElement> toSearchList=wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(toSearchCategoryLocator,2));
		System.out.println(toSearchList.size());
		WebElement toLocationCategory = toSearchList.get(0);
		By toLocationNameLocator = By.xpath(".//div[contains(@class,\"listHeader\")]");
		List<WebElement> toLocationList= toLocationCategory.findElements(toLocationNameLocator);
		
		for(WebElement toLocation : toLocationList)
		{
			System.out.println(toLocation.getText());
			String lName = toLocation.getText();
			if(lName.equalsIgnoreCase("Pune"))
			{
				toLocation.click();
				break;
			}
			
		}
		
		
		
		
	}

}
