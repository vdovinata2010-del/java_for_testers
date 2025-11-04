package tests;

import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

public class ContactGroupMembership extends TestBase {

    @Test
    void canAddContactToGroup() {
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "test_group_for_add", "header", "footer"));
        }
        var group = app.hbm().getGroupList().get(0);

        var contact = app.hbm().getContactWithoutGroups();
        if (contact == null) {
            var oldContacts = app.hbm().getContactList();
            var newContactData = new ContactData().withFirstname("new_for_add");
            app.hbm().createContact(newContactData);
            var newContacts = app.hbm().getContactList();
            Comparator<ContactData> compareById = (o1, o2) -> Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
            newContacts.sort(compareById);
            var newId = newContacts.get(newContacts.size() - 1).id();
            contact = newContactData.withId(newId);
        }

        app.contacts().returnToHome();
        app.contacts().selectGroupFilter("[all]");
        var oldRelated = app.hbm().getContactsInGroup(group);
        app.contacts().addContactToGroup(contact, group);
        var newRelated = app.hbm().getContactsInGroup(group);

        Assertions.assertEquals(oldRelated.size() + 1, newRelated.size());
    }


    @Test
    void canRemoveContactFromGroup() {
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "group_for_remove", "header", "footer"));
        }
        var group = app.hbm().getGroupList().get(0);

        var contactsInGroup = app.hbm().getContactsInGroup(group);

        if (contactsInGroup.isEmpty()) {
            var contactToAdd = app.hbm().getContactWithoutGroups();
            if (contactToAdd == null) {
                var newContactData = new ContactData().withFirstname("new_for_removal");
                app.hbm().createContact(newContactData);

                var allContacts = app.hbm().getContactList();
                Comparator<ContactData> compareById = (o1, o2) -> Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
                allContacts.sort(compareById);
                var newId = allContacts.get(allContacts.size() - 1).id();
                contactToAdd = newContactData.withId(newId);
            }
            app.contacts().returnToHome();
            app.contacts().addContactToGroup(contactToAdd, group);
            contactsInGroup = app.hbm().getContactsInGroup(group);
        }

        var contactToRemove = contactsInGroup.get(0);

        var oldRelated = contactsInGroup;
        app.contacts().removeContactFromGroup(contactToRemove, group);
        var newRelated = app.hbm().getContactsInGroup(group);
        Assertions.assertEquals(oldRelated.size() - 1, newRelated.size());
    }
}

