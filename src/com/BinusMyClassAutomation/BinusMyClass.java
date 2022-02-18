package com.BinusMyClassAutomation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.Utility.Courses;
import com.Utility.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BinusMyClass {
	public static void main(String[] args) {
		new BinusMyClass();
	}

	List<Courses> courses = null;
	ChromeDriver driver = null;
	ChromeOptions option = null;
	Scanner sc = new Scanner(System.in);

	/**Setup chromeDriver to open a BinusMyClass (using chrome driver)*/
	void setup() {
		Util.setProperty();
		option = new ChromeOptions();
		option.addArguments("headless");
		option.addArguments("disable-infobars");
		driver = new ChromeDriver(option);

		driver.get("https://myclass.apps.binus.ac.id/Auth");
	}

	/**Setup chromeDriver to open a zoom links (using original chrome) */
	void setup(String zoomLink) {
		option = new ChromeOptions();
		
		//use the original chrome path (from chrome://version) -> harusnya lgsung ambil aja pake webdriver
		String usernameAccount = System.getProperty("user.name");
		String chromePath = "C:\\Users\\"+ usernameAccount +"\\AppData\\Local\\Google\\Chrome\\User Data\\";
		option.addArguments("user-data-dir=" + chromePath);
		option.addArguments("profile-directory=Default");
		option.addArguments("disable-infobars");

		driver = new ChromeDriver(option);
		try {
			driver.get(zoomLink);
		} catch (Exception e) {
			System.out.println("Please close all chrome browser to get the link and run this program again.");
			Util.exit(driver);
		}
	}

	void joiningZoom(String zoomLink) {
		//quit the browser that have open the BinusMyClass 
		Util.quit(driver);
		Util.sleep(1.5);
		
		setup(zoomLink);
		
		Util.sleep(2);
		Util.quit(driver);
		Util.clear();
	}

	void displayData() {
		Util.clear();

		//display data
		System.out.println("Your Class List");
		System.out.println("==================================================================================================================================");
		int counter = 0;
		if(courses.isEmpty()) System.out.println("No Schedule Available Yet");
		else {
			for(var i : courses) {
				System.out.print("No. " + ++counter + "\t: ");
				System.out.printf("%-65s %-7s %-5s %-15s %-20s\n", i.getCourseName().getText(), i.getClassCode().getText(),
				i.getDeliveryMode().getText(), i.getDate().getText(), i.getTime().getText());
			}
		}
		System.out.println("==================================================================================================================================");
		System.out.println("Note: Please close your all chrome browser to get the Zoom class link. Thank you!\n");
		
		//if there's schedule, prompt user input
		if(!courses.isEmpty()) {
			
			//get user input
			int input = 0;
			do {
				try {
					System.out.print("Enter the number (1.." + courses.size() +") => ");
					input = sc.nextInt();
				} catch (Exception e) {
					input = 0;
					System.out.println("Input must be number between 1..." + courses.size());
				}
				sc.nextLine();
			} while(input < 1 ||input > courses.size());
			
			//get the index
			int idx = input-1;
			String courseName = courses.get(idx).getCourseName().getText();
			String deliveryMode = courses.get(idx).getDeliveryMode().getText();
	
			Util.clear();
			System.out.println("Your choosen courses is " + courseName + " with Delivery Mode is " + deliveryMode);
			
			//check the delivery mode (GSLC / Zoom)
			if(deliveryMode.equals("GSLC")) {
				System.out.println("Please open the newBinusMaya app");
				Util.pause();
				Util.quit(driver);
				System.exit(0);			
			}
			else {
				System.out.println("Joining in about 10 seconds");
				
				//Joining zoom
				joiningZoom(courses.get(idx).getZoomLink().findElement(By.tagName("a")).getAttribute("href"));
			}
		} else {
			//if there's no schedule, then exit program
			Util.exit(driver);
		}
	}

	void getAllCourses() {
		//get the table of courses
		Util.sleep(2);
		WebElement getTableElement = null;

		try {
			getTableElement = driver.findElement(By.xpath("//*[@id='studentViconList']"));
		} catch (Exception e) {
			System.out.println("The website is currently unavailable.. please try again");
			Util.exit(driver);
		}

		//get the rows of the table courses
		List<WebElement> rows = getTableElement.findElements(By.tagName("tr"));
		List<WebElement> columns = null;
		
		courses = new ArrayList<Courses>();
		String time = "";
		
		//take the columns and store to arraylist
		for(int row=3;row<rows.size();row++) {
			columns = rows.get(row).findElements(By.tagName("td"));
			
			if(!columns.get(1).getText().equals(time)) {
				courses.add(new Courses(columns.get(0), columns.get(1), columns.get(2), columns.get(5), columns.get(6),
				columns.get(7), columns.get(8), columns.get(11)));
			}
			time = columns.get(1).getText();
		}
	}

	public BinusMyClass() {		
		setup();
		Util.UsernamePassword();

		Util.sleep(1.5);
	
		//username
		WebElement getUsername = driver.findElement(By.xpath("//*[@id='Username']"));
		getUsername.sendKeys(Util.username);

		//password
		WebElement getPassword = driver.findElement(By.xpath("//*[@id='Password']"));
		getPassword.sendKeys(Util.password);
		//click button
		Util.sleep(0.2);
		driver.findElement(By.xpath("//*[@id='btnSubmit']")).click();
		
		Util.sleep(0.5);
		Util.clear();
		//check unsuccessfull login by checking the name
		try {
			String name = driver.findElement(By.xpath("//*[@id='aUsername']")).getText();
			String[] splitSpace = name.split(" ");
			if(!Util.username.contains(splitSpace[0].toLowerCase())) {
				System.out.println("Invalid username or password!");
				System.out.println("Please edit your username and password by deleting the database.txt files.");
				Util.exit(driver);
			}
		} catch (Exception e) {
			System.out.println("Invalid username or password!");
			System.out.println("Please edit your username and password by deleting the database.txt files.");
			Util.exit(driver);
		}

		getAllCourses();
		displayData();
	}

}
