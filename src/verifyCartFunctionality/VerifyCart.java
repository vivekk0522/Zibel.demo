package verifyCartFunctionality;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class VerifyCart extends BaseTest
{
	static BaseTest test=new VerifyCart();
	
	@BeforeMethod
	public void beforeMethod() throws IOException
	{
	 test.launch_browser("chrome");
	 Reporter.log("Launching chrome browser....");
	
	 test.open_url(loaddata("open_cart_url"));  
	 Reporter.log("Opening url....");
	}
	
	
	
	
	@Test
	public void verifyCart() throws IOException, InterruptedException
	{
		 //BaseTest test = new BaseTest();	
		 boolean iselementpresent=true;
		 int iMac_added=0;
		 int iPhone_added=0;
		 int number_of_products=0;
		 int actual_num_of_prod=0;
		 BaseTest test = PageFactory.initElements(driver, BaseTest.class); 
		 
		 
		 test.m_navigate(driver, loaddata("tab_Desktops"));
		 Reporter.log("Navigating to Desktops....");
		 
		 test.m_navigate(driver, loaddata("Mac"));
		 String excpected_text=test.getText_ofElement(driver, loaddata("Mac")).replaceAll("[^\\w\\d[0-9]]", "");
		 test.m_navigate_select(driver, loaddata("Mac"));
		 Reporter.log("Navigating to Mac Page...");
		 
		 String actual_text=test.getText_ofElement(driver, loaddata("page_Mac"));
		 Reporter.log("Verifying page");
		 Assert.assertTrue(excpected_text.equals(actual_text));
		 Reporter.log("Page verified, desired product page is open");
		 
		 double iMac_price=test.getUnitPrice(loaddata("item_iMacUnitPrice"));
		 int updated_cart_qty=test.add_to_cart(loaddata("item_iMac_addtocart"));
		 iMac_added++;
		 Reporter.log("\nAddding iMac to cart");
		 
		 test.reload();
		 String cart_value=getText_ofElement(driver, loaddata("shopping_cartdrpdwn"));
		 double cart_price = getCartPrice(cart_value);
		 int cart_quantity = getCartQuantity(cart_value);
		 Reporter.log("Verifying cart button & in Cart button number of item and price is getting reflected");
		
		 Assert.assertTrue(Double.compare(cart_price,iMac_price)==0); 
		 Assert.assertTrue(cart_quantity==updated_cart_qty);
		 Reporter.log("Cart verified, added item is reflecting in cart");
		 
		 Reporter.log("Verifying Phones&PDAs link on page");
		 boolean link_present=test.findelement(driver, loaddata("list_PhoneandPDAs")).isDisplayed();
		 Assert.assertTrue(link_present==true);
		 Reporter.log("Link found, link ‘Phones & PDAs’ is displayed on page");
		 
		 String actual_link_text=test.getText_ofElement(driver, loaddata("list_PhoneandPDAs"));
		  number_of_products=getquantity(actual_link_text);
		  test.m_navigate_select(driver, loaddata("list_PhoneandPDAs"));
		  actual_num_of_prod=test.getNumberOfProdOnPage(driver, loaddata("availbale_products"));
		  Assert.assertEquals(actual_num_of_prod , number_of_products);
		  Reporter.log("Number of products getting displayed on page are correct");
		 
		  test.m_navigate_select(driver, loaddata("list_PhoneandPDAs"));
		  double iPhone_price=test.getUnitPrice(loaddata("item_iPhoneUnitPrice"));
		  updated_cart_qty=test.add_to_cart(loaddata("item_iphone_addtocart"));
		  iPhone_added++;
		  Reporter.log("\nAddding iPhone to cart");
		  
		  double updated_cart_price= (iMac_price+iPhone_price);
		  test.waitforelement(2000);
		  cart_value=getText_ofElement(driver, loaddata("shopping_cartdrpdwn"));
		  double act_cart_price = getCartPrice(cart_value);
		  int act_cart_qty = getCartQuantity(cart_value);
		  Assert.assertTrue(Double.compare(act_cart_price, updated_cart_price)==0);
		  Assert.assertTrue(act_cart_qty==updated_cart_qty);
		  Reporter.log("Cart is showing updated quantity & price.");  
		  
		  test.waitforelement(1000);
		  test.findelement(driver, loaddata("shopping_cartdrpdwn")).click();
		  Reporter.log("Verifying cart");
		  
		  String item_1_InCart=test.getText_ofElement(driver, loaddata("shopping_cartdrpdw_item_iPhone"));
		  String item_2_InCart=test.getText_ofElement(driver, loaddata("shopping_cartdrpdw_item_iMac"));
		  test.waitforelement(1000);
		  double iMac_price_incart=test.getItemPriceInCart(driver, loaddata("shopping_cart_item_iMac_qtyprice"));
		  int iMac_qty_incart=test.getItemQtyInCart(driver, loaddata("shopping_cart_item_iMac_qtyprice"));
		  double iPhone_price_incart=test.getItemPriceInCart(driver, loaddata("shopping_cart_item_iPhone_qtyprice"));
		  int iPhone_qty_incart=test.getItemQtyInCart(driver, loaddata("shopping_cart_item_iPhone_qtyprice"));
		  
		  Assert.assertTrue(Double.compare(iPhone_price_incart , iPhone_price)==0);
		  Assert.assertTrue(iPhone_qty_incart==iPhone_added);
		  Assert.assertEquals(item_1_InCart, "iPhone", "iPhone added in cart successfully");
		  
		  Assert.assertTrue(Double.compare(iMac_price_incart , iMac_price)==0);
		  Assert.assertTrue(iMac_qty_incart==iMac_added);
		  Assert.assertEquals(item_2_InCart, "iMac", "iMac added in cart successfully");
		  
		  Reporter.log("Items verified in cart");
		  
		  test.findelement(driver, loaddata("view_cart")).click();
		  Reporter.log("Opening shopping cart page...");
		  
		  
		  test.findelement(driver, loaddata("quantity_field_ofiMac")).clear();
		  test.findelement(driver, loaddata("quantity_field_ofiMac")).sendKeys("2");
		  test.findelement(driver, loaddata("item_update_button_iMac")).click();
		  Reporter.log("Updating iMac quantity on shopping cart page...");
		  
		  String success_msg=test.getText_ofElement(driver, loaddata("item_update_message"));
		  Assert.assertEquals(success_msg.contains("Success: You have modified your shopping cart!"), true);
		  Reporter.log("Cart updated successfully");
		  
		  String updated_qty=test.findelement(driver, loaddata("quantity_field_ofiMac")).getAttribute("value");
		  Assert.assertEquals(updated_qty.equals("2"), true);
		  Reporter.log("iMac quantity is updated");
		  
		  Reporter.log("Removing iPhone from cart");
		  test.findelement(driver, loaddata("item_remove_button_iPhone")).click();
		  Reporter.log("iPhone removed from cart");
		  test.waitforelement(2000);
		  iselementpresent= driver.findElements(By.xpath(loaddata("shopping_cartpage_item_iPhone"))).size()!=0;
		  Assert.assertEquals(iselementpresent, false);
		  Reporter.log("Cart is updated and Removed product is not listed in cart");
		 
		  cart_value=getText_ofElement(driver, loaddata("shopping_cartdrpdwn"));
		  act_cart_price = getCartPrice(cart_value);
		  act_cart_qty = getCartQuantity(cart_value);
		  updated_qty=test.findelement(driver, loaddata("quantity_field_ofiMac")).getAttribute("value");
		  Assert.assertTrue(Integer.parseInt(updated_qty)==act_cart_qty);
		  Assert.assertTrue(Double.compare((iMac_price*Integer.parseInt(updated_qty)),act_cart_price)==0);
		  Reporter.log("Cart button is updated with new item count and price");
		  
	}
	
	@AfterMethod
	public void afterMethod()
	{
		test.terminateSession();
	}
	
}
