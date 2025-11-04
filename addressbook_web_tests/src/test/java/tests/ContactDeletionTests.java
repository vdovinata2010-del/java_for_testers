package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class ContactDeletionTests extends TestBase {

    @Test
    public void canDeleteContact() {
        if (app.hbm().getContactCount() == 0) {
            app.hbm().createContact(new ContactData().withFirstname("forDelete"));
            app.contacts().returnToHome();
        }
        var oldContacts = app.hbm().getContactList();
        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        app.contacts().removeContact(oldContacts.get(index));
        var newContacts = app.hbm().getContactList();
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.remove(index);
        expectedList.sort(Comparator.comparing(ContactData::id));
        newContacts.sort(Comparator.comparing(ContactData::id));
        Assertions.assertEquals(newContacts, expectedList);
    }

    @Test
    void canDeleteAllContacts () {
        if (app.hbm().getContactCount() == 0) {
            app.hbm().createContact(new ContactData().withFirstname("forDelete"));
            app.contacts().returnToHome();
        }
        app.contacts().deleteAllContacts();
        Assertions.assertEquals(0, app.hbm().getContactCount());
    }
}
