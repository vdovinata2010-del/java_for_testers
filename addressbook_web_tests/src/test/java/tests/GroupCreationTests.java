package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import common.CommonFunctions;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Epic("Addressbook")
@Feature("Groups")
public class GroupCreationTests extends TestBase {

    public static List<GroupData> groupCreateProvider() throws IOException {
        var result = new ArrayList<GroupData>();

        /*for (var name : List.of("", "group name")) {
            for (var header : List.of("", "group header")) {
                for (var footer : List.of("", "group footer")) {
                    result.add(new GroupData().withName(name) .withHeader(header) .withFooter(footer));
                }
            }
        }*/
        /*var json = "";
        try (var reader = new FileReader("groups.json");
             var breader = new BufferedReader(reader)) {
            var line = breader.readLine();
            while (line != null) {
                json = json + line;
                line = breader.readLine();
            }
        }*/
        //var json = Files.readString(Paths.get("groups.json"));

        var mapper = new JsonMapper();
        var value = mapper.readValue(new File(properties.getProperty("data.groups.file")), new TypeReference<List<GroupData>>() {});
        result.addAll(value);
        return result;
    }

    public static Stream<GroupData> randomGroups() {
        Supplier<GroupData> randomGroup = () -> new GroupData()
                .withName(CommonFunctions.randomString(10))
                .withHeader(CommonFunctions.randomString(20))
                .withFooter(CommonFunctions.randomString(30));
        return Stream.generate(randomGroup).limit(1);
    }

    @DisplayName("Создание группы")
    @ParameterizedTest(name = "{displayName}")
    @MethodSource("randomGroups")
    public void canCreateGroup(GroupData group) {

        Allure.parameter("Group name", group.name());
        Allure.parameter("Group header", group.header());
        Allure.parameter("Group footer", group.footer());

        var oldGroups = app.jdbc().getGroupList();
        app.groups().createGroup(group);
        var newGroups = app.jdbc().getGroupList();
        var extraGroups = newGroups.stream().filter(g -> ! oldGroups.contains(g)).toList();
        var newId = extraGroups.get(0).id();

        var expectedList = new ArrayList<>(oldGroups);
        expectedList.add(group.withId(newId));
        Assertions.assertEquals(Set.copyOf(newGroups), Set.copyOf(expectedList));

        //var newUiGroups = app.groups().getList(); можно сделать потом
    }

    public static List<GroupData> negativeGroupProvider() {
        var result = new ArrayList<GroupData>(List.of(
                new GroupData("", "group name'", "", "")));
        return result;
    }

    @DisplayName("Создание некорректной группы")
    @ParameterizedTest(name = "{displayName}")
    @MethodSource("negativeGroupProvider")
    public void canNotCreateGroup(GroupData group) {

        Allure.parameter("Invalid name", group.name());
        Allure.parameter("Invalid header", group.header());
        Allure.parameter("Invalid footer", group.footer());

        var oldGroups = app.groups().getList();
        app.groups().createGroup(group);
        var newGroups = app.groups().getList();
        Assertions.assertEquals(newGroups, oldGroups);
    }
}
