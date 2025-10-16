package tests;

import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContactDeletionTests extends TestBase {

    /*@Test
    public void canDeleteContact() {
        if (!app.contacts().isContactPresent()) {
            app.contacts().createContact(new ContactData().withFirstname("forDelete"));
        }
        app.contacts().removeContact();
    }*/

    @Test
    public void canDeleteContact() {
        if (app.contacts().getCount() == 0) {
            app.contacts().createContact(new ContactData().withFirstname("forDelete"));
        }
        int contactCount = app.contacts().getCount();
        app.contacts().removeContact();
        int newContactCount = app.contacts().getCount();
        Assertions.assertEquals(contactCount - 1, newContactCount);
    }

    @Test
    void canDeleteAllContacts () {
        if (app.contacts().getCount() == 0) {
            app.contacts().createContact(new ContactData().withFirstname("forDelete"));
        }
        app.contacts().deleteAllContacts();
        Assertions.assertEquals(0, app.contacts().getCount());
    }
}
