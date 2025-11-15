package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import ru.stqa.mantis.common.CommonFunctions;

import java.time.Duration;
import java.util.regex.Pattern;

public class UserRegistrationTests extends TestBase {

    @Test
    void canRegisterUser() throws Exception{
        var username = CommonFunctions.randomString(8);
        var email = String.format("%s@localhost", username);
        var password = "password";

        //создать адрес(пользователья) на почтовом сервере(jameshelper)
        //app.jamesCli().addUser(email, password);
        app.jamesApi().addUser(email, password);
        app.mail().drain(email, password);

        //регистрация через Api
        var userId = app.rest().userRegistration(username, email);

        // открываем браузер и заполняем форму (браузер)
        var driver = app.driver();
        /*driver.get(app.property("web.baseURL") + "signup_page.php");
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.cssSelector("input[value='Зарегистрироваться'],input[value='Signup']")).click();*/

        //ждем и получаем почту (mailhelper)
        var messages = app.mail().receive(email, password, Duration.ofSeconds(30));
        var text = messages.get(0).content();

        //из письма извлечь ссылку
        var pattern = Pattern.compile("http://\\S+");
        var matcher = pattern.matcher(text);
        Assertions.assertTrue(matcher.find(), "Не найдена ссылка подтверждения в письме");
        var confirmationUrl = text.substring(matcher.start(), matcher.end());

        //проходим по ссылке и завершаем регистрация (браузер)
        driver.get(confirmationUrl);
        driver.findElement(By.name("realname")).clear();
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("password_confirm")).sendKeys(password);
        driver.findElement(By.xpath("//button[.//span[text()='Update User']]")).click();

        //проверка - проверяем, что пользователь может залогиниться(httpsessionhelper)
        app.http().login(username, password);
        Assertions.assertTrue(app.http().isLoggedIn(), "НЕ Успех");

        System.out.println("Успех!!!");
    }
}
