package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.mantis.common.CommonFunctions;

import java.time.Duration;

public class UserRegistrationTests extends TestBase {

    @Test
    void canRegisterUser() throws Exception{
        var username = CommonFunctions.randomString(8);
        var email = String.format("%s@localhost", username);
        var password = "password";

        //создать адрес(пользователя) на почтовом сервере
        //через Cli
        //app.jamesCli().addUser(email, password);
        //через Api
        app.jamesApi().addUser(email, password);
        app.mail().drain(email, password);

        //регистрация
        // через Api
        var userId = app.rest().userRegistration(username, email);
        //через Браузер
        //app.registration().startRegistration(username, email);

        //ждем и получаем почту
        var messages = app.mail().receive(email, password, Duration.ofSeconds(10));

        //из письма извлечь ссылку
        var confirmationUrl = app.mail().findLink(email, password, Duration.ofSeconds(10));

        //проходим по ссылке и завершаем регистрация
        app.registration().finishRegistration(confirmationUrl, password);

        //проверка - проверяем, что пользователь может залогиниться
        app.http().login(username, password);
        Assertions.assertTrue(app.http().isLoggedIn(), "НЕ Успех");

        System.out.println("Успех!!!");
    }
}
