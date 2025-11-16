package ru.stqa.mantis.manager;

import org.openqa.selenium.By;

public class RegistrationHelper extends HelperBase {

    public RegistrationHelper(ApplicationManager manager) {
        super(manager);
    }

    public void startRegistration(String username, String email) {
        driver.get(manager.property("web.baseURL") + "signup_page.php");
        type(By.name("username"), username);
        type(By.name("email"), email);
        click(By.cssSelector("input[type='submit'], input[value='Signup'], input[value='Зарегистрироваться']"));
    }

    public void finishRegistration(String confirmationUrl, String password) {
        driver.get(confirmationUrl);

        if (isElementPresent(By.name("realname"))) {
            driver.findElement(By.name("realname")).clear();
        }

        type(By.name("password"), password);
        type(By.name("password_confirm"), password);

        click(By.xpath("//button[.//span[text()='Update User']]"));
    }
}
