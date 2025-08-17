package com.redbus;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.idealized.Javascript;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RedBusAutomationAssignment2 {

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
		
	     selectLocation(wd, wait, "Mumbai");
	     selectLocation(wd, wait, "Pune");
		
	
	 	//click on search button 
	 	By searchButtonLocator = By.xpath("//button[contains(@class,\"searchButtonWrapper\")]");
	 	WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(searchButtonLocator));
	 	searchButton.click();
	 	
	 	
	 	//click on primo and evening 
	 	By primoButtonLocator = By.xpath("//div[contains(text(),\"Primo\")]");
	 	WebElement primoButton = wait.until(ExpectedConditions.elementToBeClickable(primoButtonLocator));
	 	primoButton.click();
	 	Thread.sleep(4000);
	 	By eveningButtonLocator = By.xpath("//div[contains(text(),\"18:00-24:00\")]");
	 	WebElement eveningButton = wait.until(ExpectedConditions.elementToBeClickable(eveningButtonLocator));
	 	eveningButton.click();
	 	
	     
	 	//Get the number of Buses as shown 
	 	
	 	By subTitleLocator = By.xpath("//span[contains(@class,\"subtitle\")]");
	 	WebElement subTitle = null;
	 if(wait.until(ExpectedConditions.textToBePresentInElementLocated(subTitleLocator, "buses")))
	 {
		 subTitle   = wait.until(ExpectedConditions.visibilityOfElementLocated(subTitleLocator));
	 }
	 
	 System.out.println(subTitle.getText());
	 	
	 	
	 //get the all the buses until end of page comes up ---> lazy loading is here 
	 
	 
	 	By 	tuppleWrapperLocator = By.xpath("//li[contains(@class,\"tupleWrapper\")]");
	     By busesNameLocator = By.xpath(".//div[contains(@class,\"travelsName\")]");
	     //List<WebElement> rowList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tuppleWrapperLocator));
	     //System.out.println("Total number of buses"+rowList.size());
	     
	    
	     //now scroll list task bus 
	     //rowList.get(rowList.size()-1);
	     
	     //How to scroll to last element of your bus list and again the list loads further
	   //to load next set of buses so we need to scroll
	     JavascriptExecutor js = (JavascriptExecutor)wd;
	     
	     
	    // List<WebElement> newRowList = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(tuppleWrapperLocator,rowList.size()));
	     //System.out.println("Total number of buses"+newRowList.size());
	     
		while(true)
		{ 	//get the rows from the page
			List<WebElement> rowList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tuppleWrapperLocator));
			List<WebElement> endOfList = wd.findElements(By.xpath("//span[text()=\"End of list\"]"));
			
			if(!endOfList.isEmpty())
			{
				break;
			}
			
			js.executeScript("arguments[0].scrollIntoView({behavior:'smooth'})", rowList.get(rowList.size()-3));
	}
		List<WebElement> rowList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tuppleWrapperLocator));
		for(WebElement row : rowList)
		{
			String busName = row.findElement(busesNameLocator).getText();
			System.out.println(busName);
		}
		System.out.println("Total number of buses loaded with primo and evening"+ rowList.size());
	}

	private static void selectLocation(WebDriver wd, WebDriverWait wait, String locationData) {
		WebElement searchTextBoxElement	= wd.switchTo().activeElement(); //this will give me that textbox bcoz blinker is there
		searchTextBoxElement.sendKeys(locationData);
		
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
			if(lName.equalsIgnoreCase(locationData))
			{
				location.click();
				break;
			}
			
		}
	}
	

	

}
