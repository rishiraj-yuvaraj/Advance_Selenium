package practice;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AddProductToCartUsingDDT {
	public static void main(String[] args) throws IOException {
		
		//Read common data from property file
		FileInputStream fis = new FileInputStream(".\\src\\test\\resources\\CommonData.properties");
		Properties pro = new Properties();
		pro.load(fis);
		
		String URL = pro.getProperty("url");
		String USERNAME = pro.getProperty("username");
		String PASSWORD = pro.getProperty("password");
		
		//Read Test Data from Excel File
		FileInputStream fist = new FileInputStream(".\\src\\test\\resources\\TestData.xlsx");
		Workbook wb = WorkbookFactory.create(fist);
		Sheet sh = wb.getSheet("Sheet1");
		Row rw = sh.getRow(1);
		Cell cl = rw.getCell(2);
		String PRODUCTNAME = cl.getStringCellValue();
		
		//Step 1: Launch the browser
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		//Step 2: Load the URL
		driver.get(URL);
		
		//Step 3: Login to Application
		driver.findElement(By.id("user-name")).sendKeys(USERNAME);
		driver.findElement(By.id("password")).sendKeys(PASSWORD);
		driver.findElement(By.id("login-button")).click();
		
		//Step 4: Add product to cart
		driver.findElement(By.xpath("//div[.='"+PRODUCTNAME+"']")).click();
		driver.findElement(By.xpath("//button[.='Add to cart']")).click();
		
		//Step 5: Navigate to Cart
		driver.findElement(By.id("shopping_cart_container")).click();
		
		//Step 6: Validate for the Product in cart
		String pInCart = driver.findElement(By.xpath("//div[.='"+PRODUCTNAME+"']")).getText();
		
		if(pInCart.equals(PRODUCTNAME)) {
			System.out.println(pInCart);
			System.out.println("Test Case Pass");
		}else {
			System.out.println("Test Case Fail");
		}
		
		//Step 7: Logout of Application
		driver.findElement(By.id("react-burger-menu-btn")).click();
		driver.findElement(By.id("logout_sidebar_link")).click();
		driver.quit();
	}
}
