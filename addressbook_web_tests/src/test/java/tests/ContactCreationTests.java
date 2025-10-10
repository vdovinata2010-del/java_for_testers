package tests;

import model.ContactData;
import org.junit.jupiter.api.Test;

public class ContactCreationTests extends TestBase {

    @Test
    public void canCreateContactWithAllFields() {
        app.contacts().createContact(new ContactData()
                .withFirstname("1")
                .withMiddlename("2")
                .withLastname("3")
                .withNickname("4")
                .withTitle("5")
                .withCompany("6")
                .withAddress("7")
                .withHome("8")
                .withMobile("9")
                .withWork("10")
                .withFax("11")
                .withEmail("12@example.com")
                .withEmail2("13@example.com")
                .withEmail3("14@example.com")
                .withHomepage("http://example.com")
                .withBday("11")
                .withBmonth("December")
                .withByear("1111")
                .withAday("11")
                .withAmonth("December")
                .withAyear("2222")
                .withNewGroup("group name")
        );
    }

    @Test
    public void canCreateContactWithEmptyFields() {
        app.contacts().createContact(new ContactData());
    }

    @Test
    public void canCreateContactWithFirstnameOnly() {
        app.contacts().createContact(new ContactData().withFirstname("onlyFirst"));
    }
}
