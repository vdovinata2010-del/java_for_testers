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

    public boolean isContactPresent() {
        openContactsPage();
        return isElementPresent(By.name("selected[]"));
    }

    public void createContact(ContactData contact) {
        click(By.linkText("add new"));

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

        click(By.name("submit"));
        click(By.linkText("home"));
    }

    public void removeContact() {
        openContactsPage();
        click(By.name("selected[]"));
        click(By.xpath("//input[@value='Delete']"));
        // Алерта нет, команду сохраню для себя
        //manager.driver.switchTo().alert().accept();
        click(By.linkText("home"));
    }
}
