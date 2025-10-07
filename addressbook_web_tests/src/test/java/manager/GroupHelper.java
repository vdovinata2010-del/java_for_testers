package manager;

import model.GroupData;
import org.openqa.selenium.By;

public class GroupHelper extends HelperBase {

    public GroupHelper(ApplicationManager manager) {
        super(manager);
    }

    public void openGroupsPage() {
        if (!isElementPresent(By.name("new"))) {
            click(By.linkText("groups"));
        }
    }

    public boolean isGroupPresent() {
        openGroupsPage();
        return isElementPresent(By.name("selected[]"));
    }

    public void createGroup(GroupData group) {
        openGroupsPage();
        click(By.name("new"));
        click(By.name("group_name"));
        type(By.name("group_name"), group.name());
        type(By.name("group_header"), group.header());
        click(By.name("group_footer"));
        type(By.name("group_footer"), group.footer());
        click(By.name("submit"));
        click(By.linkText("group page"));
    }

    public void removeGroup() {
        openGroupsPage();
        click(By.name("selected[]"));
        click(By.name("delete"));
        click(By.linkText("group page"));
    }
}
