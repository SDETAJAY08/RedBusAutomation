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

public class RedBusAutomationFinalSolution {

	public static void main(String[] args)  {

		ChromeOptions chromeoptions = new ChromeOptions();
		chromeoptions.addArguments("--start-maximized");

		WebDriver wd = new ChromeDriver(chromeoptions);
		WebDriverWait wait = new WebDriverWait(wd, Duration.ofSeconds(30));

		wd.get("https://www.redbus.in");

		By sourceButtonLocator = By.xpath("//*[text()=\"From\"]");

		WebElement sourceButton = wait.until(ExpectedConditions.visibilityOfElementLocated(sourceButtonLocator));
		sourceButton.click();

		By searchSuggestionSelectionLocator = By.xpath("//div[contains(@class,\"searchSuggestionWrapper\")]");
		WebElement searchbox = wait
				.until(ExpectedConditions.visibilityOfElementLocated(searchSuggestionSelectionLocator));

		selectLocation(wd, wait, "Mumbai");
		selectLocation(wd, wait, "Pune");

		By searchButtonLocator = By.xpath("//button[contains(@class,\"searchButtonWrapper\")]");
		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(searchButtonLocator));
		searchButton.click();

		By primoButtonLocator = By.xpath("//div[contains(text(),\"Primo\")]");
		WebElement primoButton = wait.until(ExpectedConditions.elementToBeClickable(primoButtonLocator));
		primoButton.click();

		By tuppleWrapperLocator = By.xpath("//li[contains(@class,\"tupleWrapper\")]"); // found the row locator
		By busesNameLocator = By.xpath(".//div[contains(@class,\"travelsName\")]");// bus name locator
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tuppleWrapperLocator));

		By eveningButtonLocator = By.xpath("//div[contains(text(),\"18:00-24:00\")]");
		WebElement eveningButton = wait.until(ExpectedConditions.elementToBeClickable(eveningButtonLocator));
		eveningButton.click();

		By subTitleLocator = By.xpath("//span[contains(@class,\"subtitle\")]");
		WebElement subTitle = null;
		if (wait.until(ExpectedConditions.textToBePresentInElementLocated(subTitleLocator, "buses"))) {
			subTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(subTitleLocator));
		}

		System.out.println(subTitle.getText());

		JavascriptExecutor js = (JavascriptExecutor) wd;

		while (true) // lazy loading
		{
			List<WebElement> rowList = wait
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tuppleWrapperLocator));
			List<WebElement> endOfList = wd.findElements(By.xpath("//span[text()=\"End of list\"]"));

			if (!endOfList.isEmpty()) {
				break;
			}

			js.executeScript("arguments[0].scrollIntoView({behavior:'smooth'})", rowList.get(rowList.size() - 3));
		}
		List<WebElement> rowList = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tuppleWrapperLocator));
		for (WebElement row : rowList) {
			String busName = row.findElement(busesNameLocator).getText();
			System.out.println(busName);
		}
		System.out.println("Total number of buses loaded with primo and evening " + rowList.size());
	}

	private static void selectLocation(WebDriver wd, WebDriverWait wait, String locationData) {
		WebElement searchTextBoxElement = wd.switchTo().activeElement();
		searchTextBoxElement.sendKeys(locationData);

		By searchCategoryLocator = By.xpath("//div[contains(@class,\"searchCategory\")]");
		List<WebElement> searchList = wait
				.until(ExpectedConditions.numberOfElementsToBeMoreThan(searchCategoryLocator, 2));
		System.out.println(searchList.size());

		WebElement locationSearchResult = searchList.get(0);
		By locationNameLocator = By.xpath(".//div[contains(@class,\"listHeader\")]");
		List<WebElement> locationList = locationSearchResult.findElements(locationNameLocator);
		System.out.println(locationList.size());
		for (WebElement location : locationList) {
			System.out.println(location.getText());
			String lName = location.getText();
			if (lName.equalsIgnoreCase(locationData)) {
				location.click();
				break;
			}

		}
	}

}
