package manager;

import io.qameta.allure.Step;
import model.ContactData;
import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactHelper extends HelperBase {

    public ContactHelper(ApplicationManager manager) {
        super(manager);
    }

    @Step
    public void openContactsPage() {
        if (!isElementPresent(By.linkText("add new"))) {
            click(By.linkText("home"));
        }
    }

    public int getCount() {
        openContactsPage();
        return manager.driver.findElements(By.name("selected[]")).size();
    }

    @Step
    public void createContact(ContactData contact) {
        initContactCreation();
        fillContactForm(contact);
        submitContactCreation();
        returnToHome();
    }

    @Step
    public void createContactInGroup(ContactData contact, GroupData group) {
        initContactCreation();
        fillContactForm(contact);
        selectGroup(group);
        submitContactCreation();
        returnToHome();
    }

    @Step
    private void selectGroup(GroupData group) {
        new Select(manager.driver.findElement(By.name("new_group"))).selectByValue(group.id());
    }

    @Step
    public void removeContact(ContactData contact) {
        openContactsPage();
        selectContact(contact);
        deleteSelectedContact();
        // Алерта нет, команду сохраню для себя
        //manager.driver.switchTo().alert().accept();
        returnToHome();
    }

    @Step
    public void deleteAllContacts() {
        openContactsPage();
        selectAllContacts();
        deleteSelectedContact();
    }

    @Step
    public void modifyContact(ContactData contact, ContactData modifiedContact) {
        selectContact(contact);
        initContactModification(contact);
        fillContactForm(modifiedContact);
        submitContactModification();
        returnToHome();
    }

    @Step
    private void initContactModification(ContactData contact) {
        var locator = By.cssSelector("a[href='edit.php?id=" + contact.id() + "']");
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);",
                        driver.findElement(locator));
        click(locator);
    }

    @Step
    private void submitContactModification() {
        click(By.name("update"));
    }

    @Step
    private void submitContactCreation() {
        click(By.name("submit"));
    }

    @Step
    private void initContactCreation() {
        click(By.linkText("add new"));
    }

    @Step
    private void deleteSelectedContact() {
        var locator = By.xpath("//input[@value='Delete']");
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", driver.findElement(locator));
        click(locator);
    }

    @Step
    private void selectAllContacts() {
        var checkboxes = driver.findElements(By.name("selected[]"));
        for (var checkbox : checkboxes) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView(true);", checkbox);
            checkbox.click();
        }
    }

    @Step
    private void selectContact(ContactData contact) {
        var locator = By.cssSelector(String.format("input[value='%s']", contact.id()));
        new WebDriverWait(manager.driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", driver.findElement(locator));
        click(locator);
    }

    @Step
    public void selectGroupFilter(String name) {
        openContactsPage();
        new Select(manager.driver.findElement(By.name("group"))).selectByVisibleText(name);
    }

    @Step
    public void addContactToGroup(ContactData contact, GroupData group) {
        selectContact(contact);
        new Select(manager.driver.findElement(By.name("to_group"))).selectByValue(group.id());
        click(By.name("add"));
        returnToHome();
    }

    @Step
    public void removeContactFromGroup(ContactData contact, GroupData group) {
        new Select(manager.driver.findElement(By.name("group"))).selectByValue(group.id());
        selectContact(contact);
        click(By.name("remove"));
        returnToHome();
    }

    @Step
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

    public void returnToHome() {
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

    @Step
    public String getPhones(ContactData contact) {
        return manager.driver.findElement(By.xpath(
                String.format("//input[@id='%s']/../../td[6]", contact.id()))).getText();
    }

    public Map<String, String> getPhones () {
        var result = new HashMap<String, String>();
        List<WebElement> rows = manager.driver.findElements(By.name("entry"));
        for (WebElement row : rows) {
            var id = row.findElement(By.tagName("input")).getAttribute("id");
            var phones = row.findElements(By.tagName("td")).get(5).getText();
            result.put(id, phones);
        }
        return result;
    }

    @Step
    public String getEmails(ContactData contact) {
        return manager.driver.findElement(By.xpath(
                String.format("//input[@id='%s']/../../td[5]", contact.id()))).getText();
    }

    @Step
    public String getAddress(ContactData contact) {
        return manager.driver.findElement(By.xpath(
                String.format("//input[@id='%s']/../../td[4]", contact.id()))).getText();
    }
}
