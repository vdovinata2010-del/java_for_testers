package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import common.CommonFunctions;
import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

    public static List<ContactData> contactCreateProvider() throws IOException {
        var result = new ArrayList<ContactData>();

        /*for (var firstname : List.of("", "firstname")) {
            for (var lastname : List.of("", "lastname")) {
                for (var mobile : List.of("", "+1234567890")) {
                    result.add(new ContactData()
                            .withFirstname(firstname)
                            .withLastname(lastname)
                            .withMobile(mobile)
                            .withPhoto(randomFile("src/test/resources/images"))
                            );
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            result.add(new ContactData()
                    .withFirstname(CommonFunctions.randomString(i * 10))
                    .withMiddlename(CommonFunctions.randomString(i * 10))
                    .withLastname(CommonFunctions.randomString(i * 10))
                    .withNickname(CommonFunctions.randomString(i * 10))
                    .withTitle(CommonFunctions.randomString(i * 10))
                    .withCompany(CommonFunctions.randomString(i * 10))
                    .withAddress(CommonFunctions.randomString(i * 10))
                    .withHome(CommonFunctions.randomString(i * 10))
                    .withMobile(CommonFunctions.randomString(i * 10))
                    .withWork(CommonFunctions.randomString(i * 10))
                    .withFax(CommonFunctions.randomString(i * 10))
                    .withEmail(CommonFunctions.randomString(i * 10) + "@example.com")
                    .withEmail2(CommonFunctions.randomString(i * 10) + "@example.com")
                    .withEmail3(CommonFunctions.randomString(i * 10) + "@example.com")
                    .withHomepage("http://" + CommonFunctions.randomString(i * 10) + ".com")
                    .withBday("11")
                    .withBmonth("December")
                    .withByear("1111")
                    .withAday("10")
                    .withAmonth("June")
                    .withAyear("2222")
                    .withNewGroup("[none]")
                    .withPhoto(randomFile("src/test/resources/images"))
            );
        }
        return result;*/

        var mapper = new JsonMapper();
        var value = mapper.readValue(new File(properties.getProperty("data.contacts.file")), new TypeReference<List<ContactData>>() {});
        result.addAll(value);
        return result;
    }

    public static List<ContactData> singleRandomContact() {
        return List.of(new ContactData()
                .withFirstname(CommonFunctions.randomString(10))
                .withLastname(CommonFunctions.randomString(10))
                .withMiddlename(CommonFunctions.randomString(10))
                .withNickname(CommonFunctions.randomString(10))
                .withTitle(CommonFunctions.randomString(10))
                .withCompany(CommonFunctions.randomString(10))
                .withAddress(CommonFunctions.randomString(10))
                .withHome(CommonFunctions.randomString(10))
                .withMobile(CommonFunctions.randomString(10))
                .withWork(CommonFunctions.randomString(10))
                .withFax(CommonFunctions.randomString(10))
                .withEmail(CommonFunctions.randomString(10))
                .withEmail2(CommonFunctions.randomString(10))
                .withEmail3(CommonFunctions.randomString(10))
                .withHomepage(CommonFunctions.randomString(10)));
    }

    @ParameterizedTest
    @MethodSource("singleRandomContact")
    public void canCreateContacts(ContactData contact) {
        var oldContacts = app.hbm().getContactList();
        app.contacts().createContact(contact);
        var newContacts = app.hbm().getContactList();
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newContacts.sort(compareById);

        var maxId = newContacts.get(newContacts.size() - 1).id();

        var expectedList = new ArrayList<>(oldContacts);
        expectedList.add(contact.withId(maxId)
                .withBday("0")
                .withBmonth("-")
                .withAday("0")
                .withAmonth("-"));
        expectedList.sort(compareById);
        Assertions.assertEquals(newContacts, expectedList);
    }

    @Test
    void canCreateContactInGroup() {
        var contact = new ContactData()
                .withFirstname(CommonFunctions.randomString(10))
                .withLastname(CommonFunctions.randomString(10))
                .withAddress(CommonFunctions.randomString(10));
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "group name", "group header", "group footer"));
        }
        var group = app.hbm().getGroupList().get(0);

        var oldRelated = app.hbm().getContactsInGroup(group);
        app.contacts().createContactInGroup(contact, group);
        var newRelated = app.hbm().getContactsInGroup(group);
        Assertions.assertEquals(oldRelated.size() + 1, newRelated.size());
    }
}
