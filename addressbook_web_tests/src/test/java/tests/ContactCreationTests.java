package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

public class ContactCreationTests extends TestBase {

    public static List<ContactData> contactCreateProvider() {
        var result = new ArrayList<ContactData>();

        for (var firstname : List.of("", "firstname")) {
            for (var lastname : List.of("", "lastname")) {
                for (var mobile : List.of("", "+1234567890")) {
                    result.add(new ContactData()
                            .withFirstname(firstname)
                            .withLastname(lastname)
                            .withMobile(mobile));
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            result.add(new ContactData()
                    .withFirstname(randomString(i * 10))
                    .withMiddlename(randomString(i * 10))
                    .withLastname(randomString(i * 10))
                    .withNickname(randomString(i * 10))
                    .withTitle(randomString(i * 10))
                    .withCompany(randomString(i * 10))
                    .withAddress(randomString(i * 10))
                    .withHome(randomString(i * 10))
                    .withMobile(randomString(i * 10))
                    .withWork(randomString(i * 10))
                    .withFax(randomString(i * 10))
                    .withEmail(randomString(i * 10) + "@example.com")
                    .withEmail2(randomString(i * 10) + "@example.com")
                    .withEmail3(randomString(i * 10) + "@example.com")
                    .withHomepage("http://" + randomString(i * 10) + ".com")
                    .withBday("11")
                    .withBmonth("December")
                    .withByear("1111")
                    .withAday("10")
                    .withAmonth("June")
                    .withAyear("2222")
                    .withNewGroup("[none]"));
        }
        return result;
    }

        @ParameterizedTest
        @MethodSource("contactCreateProvider")
        public void canCreateContacts(ContactData contact) {
            int contactCount = app.contacts().getCount();
            app.contacts().createContact(contact);
            int newContactCount = app.contacts().getCount();
            Assertions.assertEquals(contactCount + 1, newContactCount);
        }
}
