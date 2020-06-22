package verifyCartFunctionality;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseTest 
{
	public static WebDriver driver;
	public WebElement element;
	public int item_count=0;
	public static String browser_driver="webdriver.chrome.driver";
	public static String browser_driver_path="D:\\Webdrivers\\chromedriver.exe";
	
	
	public void launch_browser(String browserType)   
	{
		switch(browserType)
		{
		case "chrome" : driver = initializeChromeDriver(browser_driver , browser_driver_path);
		}
	}
	
	public WebDriver initializeChromeDriver(String browser_driver, String browser_driver_path)
	{
		System.setProperty(browser_driver, browser_driver_path);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		return driver;
	}
	
	public void open_url(String url)
	{
		driver.get(url);
	}
	
	
	
	
	public WebElement findelement(WebDriver driver, String locator) 
	{
		elePresent(driver, locator, 10);
		element = driver.findElement(By.xpath(locator));
		return element;
	}
	

	public void Wait(int time)
	{
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}
	
	
	public BaseTest m_navigate(WebDriver driver, String locator) 
	{
		Actions action=new Actions(driver);
		action.moveToElement(findelement(driver,locator)).build().perform();
		return PageFactory.initElements(driver, BaseTest.class);
	}
	
	public void m_navigate_select(WebDriver driver, String locator)
	{
		Actions action=new Actions(driver);
		action.moveToElement(findelement(driver,locator)).click().build().perform();
	}
	
	public int getNumberOfProdOnPage(WebDriver driver, String locator) 
	{
		List<WebElement> list=driver.findElements(By.xpath(locator));
		int available_items=list.size();
		return available_items;	
	}

	public String getText_ofElement(WebDriver driver, String locator)
	{
		String text=driver.findElement(By.xpath(locator)).getText();
		return text;
	}
	
	
	public int getquantity(String item)
	{
		item = item.replaceAll("[^0-9]", "");
		int quantity=Integer.parseInt(item);
		return quantity;
	}
	
	public int add_to_cart(String locator)
	{
		
		WebElement item =findelement(driver , locator);
		item.click();
		return ++item_count;
	}
	
	public void remove_from_cart(String locator)
	{
		
		WebElement item =findelement(driver , locator);
		item.click();
		--item_count;
	}
	
	public double getUnitPrice(String locator) throws InterruptedException
	{
		Thread.sleep(1000);
		WebElement product =findelement(driver , locator);
		String[] s=product.getText().split(" ");
		double unit_price=Double.parseDouble(s[0].replaceAll("[$a-zA-Z]", ""));
		return unit_price;
	}
	
	public int getCartQuantity(String str) throws IOException
	{
		
		str=str.replaceAll("[^\\d]", " ");
		str=str.trim();
		str = str.replaceAll(" +", ",");
		List<String> list = Arrays.asList(str.split("\\s*,\\s*"));
		int cart_quantity = Integer.parseInt(list.get(0));
		return cart_quantity;
	}
	
	public double getCartPrice(String str) throws IOException
	{
		
		
		str=str.replaceAll("[^\\d]", " ");
		str=str.trim();
		str = str.replaceAll(" +", ",");
		List<String> list = Arrays.asList(str.split("\\s*,\\s*"));
		double cart_price1 = Double.parseDouble(list.get(1));
		double cart_price2 = Double.parseDouble(list.get(2));
		double cart_total_price= (cart_price1 + cart_price2/100);
		return cart_total_price;
	}
	
	public static String loaddata(String variable) throws IOException
	{
		Properties prop = new Properties();	
		FileInputStream ip = new FileInputStream("D:\\USQA-Eclipse\\zipal.in\\src\\verifyCartFunctionality\\config.properties");
		prop.load(ip);
		return prop.getProperty(variable);
	}
	
	public int getItemQtyInCart(WebDriver driver, String locator) 
	{
		List<WebElement> list=driver.findElements(By.xpath(locator));
		String item_quantity=list.get(0).getText().replaceAll("[x ]", "");
		int qty = Integer.parseInt(item_quantity);
		return qty;	
			
	}
	
	public double getItemPriceInCart(WebDriver driver, String locator)
	{
		List<WebElement> list=driver.findElements(By.xpath(locator));
		String item_price=list.get(1).getText().replaceAll("[$ ]", "");
		double price = Double.parseDouble(item_price);
		return price;	
	}
	
	public static void elePresent(WebDriver driver, String locator, int timeout)		
	
	{
		new WebDriverWait(driver , timeout).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));

	}
	
	public void reload()
	{
		driver.navigate().refresh();	
	}
	
	public void waitforelement(int timeout) throws InterruptedException
	{
		Thread.sleep(timeout);
	}
	
	public void terminateSession()
	{
		driver.close();
		
	}
	

}


