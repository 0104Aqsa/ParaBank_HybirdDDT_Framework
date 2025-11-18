package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import utils.ExcelUtils;

import java.io.IOException;

public class Home_OpenAccount {
    private WebDriver driver;
    //Locators
    By OpenAccount = By.xpath("//a[text()='Open New Account']");
    By SelectAccountType=By.id("type");
    By SelectExisitngAcc=By.id("fromAccountId");
    By ButtonOpenAcc=By.xpath("//input[@value='Open New Account']");
    By SuccessAccountMsg=By.xpath("//p[text()='Congratulations, your account is now open.']");

    public Home_OpenAccount(WebDriver driver) {
        this.driver = driver;
        if (driver == null) {
            System.out.println("Driver is null under Home_openAccount methos");
        }
    }
    public void OpenAccountMethod(int rowNum) throws IOException, InterruptedException {
        ExcelUtils.setExcelFile("src/test/resources/TestData.xlsx", "LoginData");

        driver.findElement(OpenAccount).click();

        String accountType = ExcelUtils.getCellData(rowNum, 10); // AccountType column
        int accountIndex = Integer.parseInt(ExcelUtils.getCellData(rowNum, 11)); // ExistingAccountIndex column

        Select select1 = new Select(driver.findElement(SelectAccountType));
        select1.selectByContainsVisibleText(accountType);
        Thread.sleep(8000);

        Select select2 = new Select(driver.findElement(SelectExisitngAcc));
        select2.selectByIndex(accountIndex);
        Thread.sleep(8000);

        driver.findElement(ButtonOpenAcc).click();
    }
    public boolean SuccesscreatedAccMethod(){
        try{return
                driver.findElement(SuccessAccountMsg).isDisplayed();
        } catch (Exception e) {
            return
                    false;
        }
    }

}
