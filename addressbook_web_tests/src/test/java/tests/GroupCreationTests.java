package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import common.CommonFunctions;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    public static List<GroupData> singleRandomGroup() {
        return List.of(new GroupData()
                .withName(CommonFunctions.randomString(10))
                .withHeader(CommonFunctions.randomString(20))
                .withFooter(CommonFunctions.randomString(30)));
    }

    @ParameterizedTest
    @MethodSource("singleRandomGroup")
    public void canCreateGroup(GroupData group) {
        var oldGroups = app.jdbc().getGroupList();
        app.groups().createGroup(group);
        var newGroups = app.jdbc().getGroupList();
        Comparator<GroupData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newGroups.sort(compareById);
        var maxId = newGroups.get(newGroups.size() - 1).id();

        var expectedList = new ArrayList<>(oldGroups);
        expectedList.add(group.withId(maxId));
        expectedList.sort(compareById);
        Assertions.assertEquals(newGroups, expectedList);

        //var newUiGroups = app.groups().getList(); можно сделать потом
    }

    public static List<GroupData> negativeGroupProvider() {
        var result = new ArrayList<GroupData>(List.of(
                new GroupData("", "group name'", "", "")));
        return result;
    }

    @ParameterizedTest
    @MethodSource("negativeGroupProvider")
    public void canNotCreateGroup(GroupData group) {
        var oldGroups = app.groups().getList();
        app.groups().createGroup(group);
        var newGroups = app.groups().getList();
        Assertions.assertEquals(newGroups, oldGroups);
    }
}
