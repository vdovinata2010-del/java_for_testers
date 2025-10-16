package manager;

import model.ContactData;
import org.openqa.selenium.By;

public class ContactHelper extends HelperBase {

    public ContactHelper(ApplicationManager manager) {
        super(manager);
    }

    public void openContactsPage() {
        if (!isElementPresent(By.linkText("add new"))) {
            click(By.linkText("home"));
        }
    }

    /*
    public boolean isContactPresent() {
        openContactsPage();
        return isElementPresent(By.name("selected[]"));
    }
     */

    public int getCount() {
        openContactsPage();
        return manager.driver.findElements(By.name("selected[]")).size();
    }

    public void createContact(ContactData contact) {
        initContactCreation();
        fillContactForm(contact);
        submitContactCreation();
        returnToHome();
    }

    public void removeContact() {
        openContactsPage();
        selectContact();
        deleteSelectedContact();
        // Алерта нет, команду сохраню для себя
        //manager.driver.switchTo().alert().accept();
        returnToHome();
    }

    public void deleteAllContacts() {
        openContactsPage();
        selectAllContacts();
        deleteSelectedContact();
    }

    private void submitContactCreation() {
        click(By.name("submit"));
    }

    private void initContactCreation() {
        click(By.linkText("add new"));
    }

    private void deleteSelectedContact() {
        click(By.xpath("//input[@value='Delete']"));
    }

    private void selectAllContacts() {
        var checkboxes = manager.driver.findElements(By.name("selected[]"));
        for (var checkbox : checkboxes) {
            checkbox.click();
        }
    }

    private void selectContact() {
        click(By.name("selected[]"));
    }

    private void fillContactForm(ContactData contact) {
        // Text fields
        type(By.name("firstname"), contact.firstname());
        type(By.name("middlename"), contact.middlename());
        type(By.name("lastname"), contact.lastname());
        type(By.name("nickname"), contact.nickname());
        type(By.name("title"), contact.title());
        type(By.name("company"), contact.company());
        type(By.name("address"), contact.address());
        type(By.name("home"), contact.home());
        type(By.name("mobile"), contact.mobile());
        type(By.name("work"), contact.work());
        type(By.name("fax"), contact.fax());
        type(By.name("email"), contact.email());
        type(By.name("email2"), contact.email2());
        type(By.name("email3"), contact.email3());
        type(By.name("homepage"), contact.homepage());

        // BDay
        if (!contact.bday().isEmpty()) {
            click(By.name("bday"));
            click(By.xpath("//select[@name='bday']/option[. = '" + contact.bday() + "']"));
        }
        if (!contact.bmonth().isEmpty()) {
            click(By.name("bmonth"));
            click(By.xpath("//select[@name='bmonth']/option[. = '" + contact.bmonth() + "']"));
        }
        if (!contact.byear().isEmpty()) {
            type(By.name("byear"), contact.byear());
        }

        // Anniversary
        if (!contact.aday().isEmpty()) {
            click(By.name("aday"));
            click(By.xpath("//select[@name='aday']/option[. = '" + contact.aday() + "']"));
        }
        if (!contact.amonth().isEmpty()) {
            click(By.name("amonth"));
            click(By.xpath("//select[@name='amonth']/option[. = '" + contact.amonth() + "']"));
        }
        if (!contact.ayear().isEmpty()) {
            type(By.name("ayear"), contact.ayear());
        }

        // Group
        if (!contact.newGroup().isEmpty()) {
            click(By.name("new_group"));
            click(By.xpath("//select[@name='new_group']/option[. = '" + contact.newGroup() + "']"));
        }
    }
    
    private void returnToHome() {
        click(By.linkText("home"));
    }
}
