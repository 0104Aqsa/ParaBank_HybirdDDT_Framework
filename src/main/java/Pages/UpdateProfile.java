package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ExcelUtils;

import java.io.IOException;

public class UpdateProfile {
    private WebDriver driver;
    //Locators
    By UpdateContactInfo = By.xpath("//a[text()='Update Contact Info']'");
    By FirstName = By.name("customer.firstName");
    By LastName = By.name("customer.lastName");
    By Address = By.name("customer.address.street");
    By City = By.name("customer.address.city");
    By State = By.name("customer.address.state");
    By Zipcode = By.name("customer.address.zipCode");
    By UpdateProfileButton=By.xpath("//input[@text()='Update Profile']");
    By SuccessUpdate=By.xpath("//h1[@text()='Profile Updated']");

    public UpdateProfile(WebDriver driver){
        this.driver=driver;
        if(driver==null){
            System.out.println("Webdriver is null");
        }
    }
    public void UpdateProfileMethod(int rowNum) throws IOException {
        ExcelUtils.setExcelFile("src/test/resources/TestData.xlsx","UpdateProfile");
        String FName = ExcelUtils.getCellData(rowNum, 1);
        String LName = ExcelUtils.getCellData(rowNum, 2);
        String Add = ExcelUtils.getCellData(rowNum, 3);
        String Cityy = ExcelUtils.getCellData(rowNum, 4);
        String Stat = ExcelUtils.getCellData(rowNum, 5);
        String Zip = ExcelUtils.getCellData(rowNum, 6);
        driver.findElement(UpdateContactInfo).click();
        driver.findElement(FirstName).sendKeys(FName);
        driver.findElement(LastName).sendKeys(LName);
        driver.findElement(Address).sendKeys(Add);
        driver.findElement(City).sendKeys(Cityy);
        driver.findElement(State).sendKeys(Stat);
        driver.findElement(Zipcode).sendKeys(Zip);
        driver.findElement(UpdateProfileButton).click();
    }
   public boolean SuccessUpdateMethod(){
        try{
            return driver.findElement(SuccessUpdate).isDisplayed();
        } catch (Exception e){
            return false;}
   }
}
