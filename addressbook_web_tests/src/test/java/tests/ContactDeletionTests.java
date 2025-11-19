package tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

@Epic("Addressbook")
@Feature("Contacts")
public class ContactDeletionTests extends TestBase {

    @Test
    @DisplayName("Удаление одного контакта")
    public void canDeleteContact() {
        Allure.step("Проверка предусловия", step -> {
            if (app.hbm().getContactCount() == 0) {
                app.hbm().createContact(new ContactData().withFirstname("forDelete"));
                app.contacts().returnToHome();
            }
        });

        var oldContacts = app.hbm().getContactList();
        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        app.contacts().removeContact(oldContacts.get(index));
        var newContacts = app.hbm().getContactList();
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.remove(index);
        expectedList.sort(Comparator.comparing(ContactData::id));
        newContacts.sort(Comparator.comparing(ContactData::id));
        Allure.step("Валидация результата", step -> {
        Assertions.assertEquals(newContacts, expectedList);
        });
    }

    @Test
    @DisplayName("Удаление всех контактов")
    void canDeleteAllContacts () {
        if (app.hbm().getContactCount() == 0) {
            app.hbm().createContact(new ContactData().withFirstname("forDelete"));
            app.contacts().returnToHome();
        }
        app.contacts().deleteAllContacts();
        Assertions.assertEquals(0, app.hbm().getContactCount());
    }
}
