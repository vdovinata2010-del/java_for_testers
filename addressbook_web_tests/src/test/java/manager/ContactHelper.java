package manager;

import model.ContactData;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(ApplicationManager manager) {
        super(manager);
    }

    public void openContactsPage() {
        if (!isElementPresent(By.linkText("add new"))) {
            click(By.linkText("home"));
        }
    }

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

    public void removeContact(ContactData contact) {
        openContactsPage();
        selectContact(contact);
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

    public void modifyContact(ContactData contact, ContactData modifiedContact) {
        openContactsPage();
        selectContact(contact);
        initContactModification(contact);
        fillContactForm(modifiedContact);
        submitContactModification();
        returnToHome();
    }

    private void initContactModification(ContactData contact) {
        var locator = By.cssSelector("a[href='edit.php?id=" + contact.id() + "']");
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);",
                        driver.findElement(locator));
        click(locator);
    }

    private void submitContactModification() {
        click(By.name("update"));
    }

    private void submitContactCreation() {
        click(By.name("submit"));
    }

    private void initContactCreation() {
        click(By.linkText("add new"));
    }

    private void deleteSelectedContact() {
        var locator = By.xpath("//input[@value='Delete']");
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", driver.findElement(locator));
        click(locator);
    }

    private void selectAllContacts() {
        var checkboxes = driver.findElements(By.name("selected[]"));
        for (var checkbox : checkboxes) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView(true);", checkbox);
            checkbox.click();
        }
    }

    private void selectContact(ContactData contact) {
        var locator = By.cssSelector(String.format("input[value='%s']", contact.id()));
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", driver.findElement(locator));
        click(locator);
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

        //Photo
        //attach(By.name("photo"), contact.photo());

        if (contact.photo() != null && !contact.photo().isEmpty()) {
            attach(By.name("photo"), contact.photo());
        }
    }

    private void returnToHome() {
        click(By.linkText("home"));
    }

    public List<ContactData> getList() {
        openContactsPage();
        var contacts = new ArrayList<ContactData>();
        var tds = manager.driver.findElements(By.xpath("//tr[.//input[@name='selected[]']]"));
        for (var td : tds) {
            var cells = td.findElements(By.tagName("td"));
            var checkbox = cells.get(0).findElement(By.name("selected[]"));
            var lastname = cells.get(1).getText();
            var firstname = cells.get(2).getText();
            var id = checkbox.getAttribute("value");
            contacts.add(new ContactData()
                    .withId(id)
                    .withLastname(lastname)
                    .withFirstname(firstname));
        }
        return contacts;
    }
}
