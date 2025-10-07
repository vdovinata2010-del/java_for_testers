package manager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class HelperBase {

    protected WebDriver driver;
    protected ApplicationManager manager;

    public HelperBase(ApplicationManager manager) {
        this.manager = manager;
        this.driver = manager.driver;
    }

    protected void click(By locator) {
        driver.findElement(locator).click();
    }

    protected void type(By locator, String text) {
        driver.findElement(locator).sendKeys(text);
    }

    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException exception) {
            return false;
        }
    }
}
