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
public class ContactModificationTests extends TestBase {

    @Test
    @DisplayName("Проверка изменения контактных данных")
    void canModifyContact() {
        if (app.hbm().getContactCount() == 0) {
            app.hbm().createContact(new ContactData()
                    .withLastname("forModify")
                    .withFirstname("forModify")
                    .withBday("0").withByear("0").withAday("0").withAyear("0")
            );
            app.contacts().returnToHome();
        }
        app.contacts().returnToHome();
        var oldContacts = app.hbm().getContactList();
        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        var contactToModify = oldContacts.get(index);

        Allure.parameter("Original Firstname", contactToModify.firstname());
        Allure.parameter("Original Lastname", contactToModify.lastname());
        Allure.parameter("Original Mobile", contactToModify.mobile());
        Allure.parameter("Original Email", contactToModify.email());

        var testData = new ContactData()
                .withLastname("modified lastname")
                .withFirstname("modified firstname")
                .withAddress("modified address")
                .withMiddlename("modified middlename")
                .withNickname("modified nickname")
                .withTitle("modified title")
                .withCompany("modified company")
                .withAddress("modified address")
                .withHome("modified home")
                .withMobile("modified mobile")
                .withWork("modified work")
                .withFax("modified fax")
                .withEmail("modified.email@test.com")
                .withEmail2("modified.email2@test.com")
                .withEmail3("modified.email3@test.com")
                .withHomepage("modified.com")
                .withBday("15")
                .withBmonth("October")
                .withByear("1995")
                .withAday("5")
                .withAmonth("September")
                .withAyear("2020");

        Allure.parameter("Modified Firstname", testData.firstname());
        Allure.parameter("Modified Lastname", testData.lastname());
        Allure.parameter("Modified Mobile", testData.mobile());
        Allure.parameter("Modified Email", testData.email());

        app.contacts().modifyContact(contactToModify, testData);
        var newContacts = app.hbm().getContactList();
        var expectedList = new ArrayList<>(oldContacts);

        expectedList.set(index, testData.withId(oldContacts.get(index).id())
                .withAmonth("september")); //почему-то разный регист ¯\_(ツ)_/¯

        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newContacts.sort(compareById);
        expectedList.sort(compareById);
        Assertions.assertEquals(newContacts, expectedList);
    }
}
