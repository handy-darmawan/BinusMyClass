package com.Utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Util {

	public static String username = null;
	public static String password = null;
	static Scanner sc = new Scanner(System.in);

	public static void setProperty() {
		WebDriverManager.chromedriver().setup();
	}

	public static void sleep(double times) {
		try {
			Thread.sleep((int)(times*1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void clear() {
		if(System.getProperty("os.name").contains("Windows")) {
			try {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void pause() {
		if(System.getProperty("os.name").contains("Windows")) {
			try {
				new ProcessBuilder("cmd", "/c", "pause").inheritIO().start().waitFor();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void exit(ChromeDriver a) {
		Util.quit(a);
		Util.pause();
		System.exit(0);	
	}

	public static void quit(ChromeDriver a) {
		try {
			a.quit();
		} catch (Exception e) {}
	}

	static String getUsernamePassword() {
		System.out.println("Welcome to BinusMyClass");
		System.out.println("=======================");
		System.out.print("Enter your username (without @binus.ac.id): "); String usernameInput = sc.nextLine();
		System.out.print("Enter your password: "); String passwordInput = sc.nextLine();

		Util.username = usernameInput;
		Util.password = passwordInput;
		System.out.println("Register Success!");
		return (usernameInput + "#" + passwordInput);
	}

	public static void UsernamePassword() {
		Util.clear();

		File file = new File("database.txt");

		//if database.txt doesn't exists then create a file and take user input
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//get the username and password input
			String userPass = getUsernamePassword();

			//write to file
			try (BufferedWriter buffWriter = new BufferedWriter(new FileWriter("database.txt"))) {
				buffWriter.write(userPass);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		 
		//if database.txt exists then read the file from database.txt and use the username and pass	
		try (BufferedReader buffReader = new BufferedReader(new FileReader("database.txt"))) {
			
			String[] getUserPass = buffReader.readLine().split("#");
			Util.username = getUserPass[0];
			Util.password = getUserPass[1];

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
