package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import common.CommonFunctions;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Epic("Addressbook")
@Feature("Contacts")
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

    @DisplayName("Создание нового контакта")
    @ParameterizedTest (name = "{displayName}")
    @MethodSource("singleRandomContact")
    public void canCreateContacts(ContactData contact) {
        Allure.parameter("Firstname", contact.firstname());
        Allure.parameter("Lastname", contact.lastname());
        Allure.parameter("Middlename", contact.middlename());
        Allure.parameter("Nickname", contact.mobile());
        Allure.parameter("Title", contact.email());
        Allure.parameter("Company", contact.email());
        Allure.parameter("Address", contact.email());
        Allure.parameter("Home", contact.email());
        Allure.parameter("Mobile", contact.email());
        Allure.parameter("Work", contact.email());
        Allure.parameter("Fax", contact.email());
        Allure.parameter("Email", contact.email());
        Allure.parameter("Email2", contact.email());
        Allure.parameter("Email3", contact.email());
        Allure.parameter("Homepage", contact.email());

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

    @DisplayName("Создание контакта в выбранной группе")
    @Test
    void canCreateContactInGroup() {
        var contact = new ContactData()
                .withFirstname(CommonFunctions.randomString(10))
                .withLastname(CommonFunctions.randomString(10))
                .withAddress(CommonFunctions.randomString(10));

        Allure.parameter("Firstname", contact.firstname());
        Allure.parameter("Lastname", contact.lastname());
        Allure.parameter("Address", contact.address());

        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "group name", "group header", "group footer"));
        }
        var group = app.hbm().getGroupList().get(0);

        Allure.parameter("Group Name", group.name());

        var oldRelated = app.hbm().getContactsInGroup(group);
        app.contacts().createContactInGroup(contact, group);
        var newRelated = app.hbm().getContactsInGroup(group);
        Assertions.assertEquals(oldRelated.size() + 1, newRelated.size());
    }
}
