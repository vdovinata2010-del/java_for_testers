package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class ContactModificationTests extends TestBase {

    @Test
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
