package Pages;

import org.apache.commons.lang3.ObjectUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import utils.ExcelUtils;

import java.io.IOException;

public class TransferFundsPage {
    private WebDriver driver;
    //Locators
    By OpenTransferFunds=By.xpath("//a[text()='Transfer Funds']");
    By EnterAmount=By.id("amount");
    By SelectfromAcc=By.id("fromAccountId");
    By SelectToAcc= By.id("toAccountId");
    By TransferButtn=By.xpath("//input[@value='Transfer']");

    public TransferFundsPage(WebDriver driver){
        this.driver=driver;
        if(driver == null){
            System.out.println("Webdriver is Null under TransferFundspage constructors.");
        }
    }
    public void TransferFundMethod(int rowNum) throws IOException {
        ExcelUtils.setExcelFile("src/test/resources/TestData.xlsx", "LoginData");
        driver.findElement(OpenTransferFunds).click();

        String Amount=ExcelUtils.getCellData(rowNum,12);
        driver.findElement(EnterAmount).sendKeys(Amount);

        int FromAccIndex = Integer.parseInt(ExcelUtils.getCellData(rowNum, 13)); // ExistingAccountIndex column
        Select select1=new Select(driver.findElement(SelectfromAcc));
        select1.selectByIndex(FromAccIndex);

        int ToAccIndex = Integer.parseInt(ExcelUtils.getCellData(rowNum, 14)); // ExistingAccountIndex column
        Select select2=new Select(driver.findElement(SelectToAcc));
        select2.selectByIndex(ToAccIndex);

        driver.findElement(TransferButtn).click();


    }

}
