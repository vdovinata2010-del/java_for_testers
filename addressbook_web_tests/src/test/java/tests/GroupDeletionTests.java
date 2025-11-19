package tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

@Epic("Addressbook")
@Feature("Groups")
public class GroupDeletionTests extends TestBase {

    @Test
    @DisplayName("Удаление одной группы")
    public void canDeleteGroup() {
        Allure.step("Проверка предусловия", step -> {
            if (app.hbm().getGroupCount() == 0) {
                app.hbm().createGroup(new GroupData("", "group name", "group header", "group footer"));
            }
        });
        var oldGroups = app.hbm().getGroupList();
        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size());
        app.groups().removeGroup(oldGroups.get(index));
        var newGroups = app.hbm().getGroupList();
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.remove(index);
        Allure.step("Валидация результата", step -> {
        Assertions.assertEquals(newGroups, expectedList);
        });
    }

    @Test
    @DisplayName("Удаление всех групп")
    void canDeleteAllGroups () {
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "group name", "group header", "group footer"));
        }
        app.groups().deleteAllGroups();
        Assertions.assertEquals(0, app.hbm().getGroupCount());
    }
}
