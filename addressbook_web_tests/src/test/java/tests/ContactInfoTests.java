package tests;

import common.CommonFunctions;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContactInfoTests extends TestBase {

    @Test
    void testPhones() {
        if (app.hbm().getContactCount() == 0) {
            app.hbm().createContact(new ContactData()
                    .withFirstname("CheckInfo")
                    .withHome(CommonFunctions.randomString(10))
                    .withMobile(CommonFunctions.randomString(10))
                    .withWork(CommonFunctions.randomString(10))
            );
            app.contacts().returnToHome();
        }
        var contacts = app.hbm().getContactList();
        var contact = contacts.get(0);
        var phones = app.contacts().getPhones(contact);
        var expected = Stream.of(contact.home(), contact.mobile(), contact.work())
                .filter(s -> s != null && ! "".equals(s))
                .collect(Collectors.joining("\n"));
        Assertions.assertEquals(expected, phones);
    }

    @Test
    void testPhonesAllContacts() {
        if (app.hbm().getContactCount() == 0) {
            app.hbm().createContact(new ContactData()
                    .withFirstname("CheckInfo")
                    .withHome(CommonFunctions.randomString(10))
                    .withMobile(CommonFunctions.randomString(10))
                    .withWork(CommonFunctions.randomString(10))
            );
            app.contacts().returnToHome();
        }
        var contacts = app.hbm().getContactList();
        var expected = contacts.stream().collect(Collectors.toMap(ContactData::id, contact ->
            Stream.of(contact.home(), contact.mobile(), contact.work())
                    .filter(s -> s != null && ! "".equals(s))
                    .collect(Collectors.joining("\n"))
        ));
        var phones = app.contacts().getPhones();
        Assertions.assertEquals(expected, phones);
    }

    @Test
    void testInfoContact() {
        if (app.hbm().getContactCount() == 0) {
            app.hbm().createContact(new ContactData()
                    .withFirstname("CheckInfo")
                    .withHome(CommonFunctions.randomString(10))
                    .withMobile(CommonFunctions.randomString(10))
                    .withWork(CommonFunctions.randomString(10))
                    .withEmail(CommonFunctions.randomString(10) + "@example.com")
                    .withEmail2(CommonFunctions.randomString(10) + "@example.com")
                    .withEmail3(CommonFunctions.randomString(10) + "@example.com")
                    .withAddress(CommonFunctions.randomString(10))
            );
            app.contacts().returnToHome();
        }
        var contacts = app.hbm().getContactList();
        var contact = contacts.get(0);
        var phones = app.contacts().getPhones(contact);
        var emails = app.contacts().getEmails(contact);
        var address = app.contacts().getAddress(contact);
        var expectedPhones = Stream.of(contact.home(), contact.mobile(), contact.work())
                .filter(s -> s != null && ! "".equals(s))
                .collect(Collectors.joining("\n"));
        var expectedEmails = Stream.of(contact.email(), contact.email2(), contact.email3())
                .filter(s -> s != null && ! "".equals(s))
                .collect(Collectors.joining("\n"));
        var expectedAddress = contact.address().replace("\r\n", "\n").trim();;

        Assertions.assertEquals(expectedPhones, phones);
        Assertions.assertEquals(expectedEmails, emails);
        Assertions.assertEquals(expectedAddress, address);
    }
}
